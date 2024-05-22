package de.hitec.nhplus.controller;

import de.hitec.nhplus.datastorage.CaregiverDao;
import de.hitec.nhplus.datastorage.DaoFactory;
import de.hitec.nhplus.datastorage.PatientDao;
import de.hitec.nhplus.datastorage.TreatmentDao;
import de.hitec.nhplus.model.Caregiver;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import de.hitec.nhplus.model.Patient;
import de.hitec.nhplus.model.Treatment;
import de.hitec.nhplus.utils.DateConverter;
import javafx.util.StringConverter;

import java.sql.Array;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class NewTreatmentController {

    @FXML
    private Label labelFirstName;

    @FXML
    private Label labelSurname;

    @FXML
    private TextField textFieldBegin;

    @FXML
    private TextField textFieldEnd;

    @FXML
    private TextField textFieldDescription;

    @FXML
    private TextArea textAreaRemarks;

    @FXML
    private DatePicker datePicker;

    @FXML
    private Button buttonAdd;

    @FXML
    private ComboBox<String> comboBoxCaregiverSelection;

    private AllTreatmentController controller;

    private final ObservableList<String> caregiverSelection = FXCollections.observableArrayList();
    private Patient patient;

    private Caregiver caregiver;
    private Stage stage;
    private ArrayList<Caregiver> caregivers = new ArrayList<>();;

    /**
     * When <code>initialize()</code> gets called, all fields are already initialized. For example from the FXMLLoader
     * after loading an FXML-File. At this point of the lifecycle of the Controller, the fields can be accessed and
     * configured.
     */
    public void initialize(AllTreatmentController controller, Stage stage, Patient patient) {
        comboBoxCaregiverSelection.setItems(caregiverSelection);
        comboBoxCaregiverSelection.getSelectionModel().select(0);

        this.controller= controller;
        this.patient = patient;
        this.stage = stage;

        this.buttonAdd.setDisable(true);
        ChangeListener<String> inputNewPatientListener = (observableValue, oldText, newText) ->
                NewTreatmentController.this.buttonAdd.setDisable(NewTreatmentController.this.areInputDataInvalid());
        this.textFieldBegin.textProperty().addListener(inputNewPatientListener);
        this.textFieldEnd.textProperty().addListener(inputNewPatientListener);
        this.textFieldDescription.textProperty().addListener(inputNewPatientListener);
        this.textAreaRemarks.textProperty().addListener(inputNewPatientListener);
        this.datePicker.valueProperty().addListener((observableValue, localDate, t1) -> NewTreatmentController.this.buttonAdd.setDisable(NewTreatmentController.this.areInputDataInvalid()));
        this.datePicker.setConverter(new StringConverter<>() {
            @Override
            public String toString(LocalDate localDate) {
                return (localDate == null) ? "" : DateConverter.convertLocalDateToString(localDate);
            }

            @Override
            public LocalDate fromString(String localDate) {
                return DateConverter.convertStringToLocalDate(localDate);
            }
        });
        this.showPatientData();
        this.createComboBoxData();
    }

    /**
     * sets the name of the patient into the input-fields
     */
    private void showPatientData(){
        this.labelFirstName.setText(patient.getFirstName());
        this.labelSurname.setText(patient.getSurname());
    }

    /**
     * creates a new treatment object
     */
    @FXML
    public void handleAdd(){
        LocalDate date = this.datePicker.getValue();
        LocalTime begin = DateConverter.convertStringToLocalTime(textFieldBegin.getText());
        LocalTime end = DateConverter.convertStringToLocalTime(textFieldEnd.getText());
        String description = textFieldDescription.getText();
        String remarks = textAreaRemarks.getText();
        Treatment treatment = new Treatment(patient.getPid(), date, begin, end, description, remarks, caregiver.getCid());
        createTreatment(treatment);
        controller.readAllAndShowInTableView();
        stage.close();
    }

    /**
     * saves treatment object to the treatment table
     * @param treatment
     */
    private void createTreatment(Treatment treatment) {
        TreatmentDao dao = DaoFactory.getDaoFactory().createTreatmentDao();
        try {
            dao.create(treatment);
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    @FXML
    public void handleCancel(){
        stage.close();
    }

    /**
     * validates input fields
     * @return true if valid
     */
    private boolean areInputDataInvalid() {
        if (this.textFieldBegin.getText() == null || this.textFieldEnd.getText() == null) {
            return true;
        }
        try {
            LocalTime begin = DateConverter.convertStringToLocalTime(this.textFieldBegin.getText());
            LocalTime end = DateConverter.convertStringToLocalTime(this.textFieldEnd.getText());
            if (!end.isAfter(begin)) {
                return true;
            }
        } catch (Exception exception) {
            return true;
        }
        return this.textFieldDescription.getText().isBlank() || this.datePicker.getValue() == null;
    }

    @FXML
    public void handleCaregiverComboBox() {
        for(Caregiver element : caregivers){
            if(this.comboBoxCaregiverSelection.getSelectionModel().getSelectedItem().equals(element.getSurname())){
                caregiver = element;
            }
        }
    }

    private void createComboBoxData() {
        CaregiverDao dao = DaoFactory.getDaoFactory().createCaregiverDAO();
        try {
            caregivers = (ArrayList<Caregiver>) dao.readAll();
            for (Caregiver caregiver: caregivers) {
                if(caregiver.isLocked().equals("")) {
                    this.caregiverSelection.add(caregiver.getSurname());
                }
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }


}