package de.hitec.nhplus.controller;

import de.hitec.nhplus.datastorage.CaregiverDao;
import de.hitec.nhplus.datastorage.DaoFactory;
import de.hitec.nhplus.datastorage.PatientDao;
import de.hitec.nhplus.model.Caregiver;
import de.hitec.nhplus.model.Patient;
import de.hitec.nhplus.utils.ConfirmDeletion;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;

public class AllCaregiverController {

    @FXML
    private TableView<Caregiver> tableView;

    @FXML
    private TableColumn<Caregiver, Integer> colID;
    @FXML
    private CheckBox CheckBoxSearchLocked;

    @FXML
    private TableColumn<Caregiver, String> colFirstName;

    @FXML
    private TableColumn<Caregiver, String> colSurname;

    @FXML
    private TableColumn<Caregiver, String> colTelephone;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnAdd;

    @FXML
    private TextField txfSurname;

    @FXML
    private TextField txfFirstname;

    @FXML
    private TextField txfTelephone;



    private ArrayList<Caregiver> caregivers = new ArrayList<>();
    private ObservableList<Caregiver> caregiversModel = FXCollections.observableArrayList();
    private CaregiverDao dao;


    /**
     * When <code>initialize()</code> gets called, all fields are already initialized. For example from the FXMLLoader
     * after loading an FXML-File. At this point of the lifecycle of the Controller, the fields can be accessed and
     * configured.
     */
    public void initialize() {
        this.readAllAndShowInTableView();

        this.colID.setCellValueFactory(new PropertyValueFactory<>("cid"));

        // CellValueFactory to show property values in TableView
        this.colFirstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        // CellFactory to write property values from with in the TableView
        this.colFirstName.setCellFactory(TextFieldTableCell.forTableColumn());

        this.colSurname.setCellValueFactory(new PropertyValueFactory<>("surname"));
        this.colSurname.setCellFactory(TextFieldTableCell.forTableColumn());

        this.colTelephone.setCellValueFactory(new PropertyValueFactory<>("telephone"));
        this.colTelephone.setCellFactory(TextFieldTableCell.forTableColumn());

        //Anzeigen der Daten
        this.tableView.setItems(this.caregiversModel);

        this.btnDelete.setDisable(true);
        this.tableView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Caregiver>() {
            @Override
            public void changed(ObservableValue<? extends Caregiver> observableValue, Caregiver oldPatient, Caregiver newPatient) {
                ;
                AllCaregiverController.this.btnDelete.setDisable(newPatient == null);
            }
        });

        this.btnAdd.setDisable(true);
        ChangeListener<String> inputNewCaregiverListener = (observableValue, oldText, newText) ->
                AllCaregiverController.this.btnAdd.setDisable(!AllCaregiverController.this.areInputDataValid());
        this.txfSurname.textProperty().addListener(inputNewCaregiverListener);
        this.txfFirstname.textProperty().addListener(inputNewCaregiverListener);
        this.txfTelephone.textProperty().addListener(inputNewCaregiverListener);
    }


    /**
     * validates the input fields, if the user didn't enter all necessary fields this method will return false.
     * @return true if valid
     */
    private boolean areInputDataValid() {
        return !this.txfFirstname.getText().isBlank() && !this.txfSurname.getText().isBlank() &&
                !this.txfTelephone.getText().isBlank();
    }

    /**
     * Reloads all caregivers to the table by clearing the list of all caregivers and filling it again by all persisted
     * caregivers, delivered by {@link CaregiverDao}.
     */
    private void readAllAndShowInTableView() {
        this.caregivers.clear();
        this.dao = DaoFactory.getDaoFactory().createCaregiverDAO();
        try {
            this.caregivers.addAll(this.dao.readAll());
            filter();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    /**
     * Method is called when the delete button is pressed.
     * shows a confirmation dialog and then
     * deletes the selected entry.
     */
    @FXML
    public void handleDelete() {
        if(ConfirmDeletion.showConfirmationDialog()) {
            Caregiver selectedItem = this.tableView.getSelectionModel().getSelectedItem();
            if (selectedItem != null) {
                try {
                    DaoFactory.getDaoFactory().createPatientDAO().deleteById(selectedItem.getCid());
                    caregivers.remove(selectedItem);
                    filter();
                } catch (SQLException exception) {
                    exception.printStackTrace();
                }
            }
        }
    }


    /**
     * Updates a patient by calling the method <code>update()</code> of {@link PatientDao}.
     *
     * @param event Event including the changed object and the change.
     */
    private void doUpdate(TableColumn.CellEditEvent<Caregiver, String> event) {
        try {
            this.dao.update(event.getRowValue());
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }


    public void onSearchChange() {
        filter();
    }


    /**
     * called when the locked checkbox is altered
     * filters out locked caregiver if the checkbox isn't ticked
     */
    private void filter() {
        caregiversModel.clear();
        caregiversModel.addAll(FXCollections.observableArrayList(caregivers));
        Iterator<Caregiver> iterator = caregiversModel.iterator();
        Caregiver element;
        if (!CheckBoxSearchLocked.isSelected()) {
            while (iterator.hasNext()) {
                element = iterator.next();
                if (element.isLocked().equals("x")) {
                    iterator.remove();
                }
            }
        }
    }

    /**
     * called when the lock button is pressed
     * toggles the locked field of a caregiver
     */
    public void onLock(){
        Caregiver selectedItem = this.tableView.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            try {
                if (selectedItem.isLocked().equals("x")) {
                    selectedItem.setLocked("");
                } else {
                    selectedItem.setLocked("x");
                }
                DaoFactory.getDaoFactory().createCaregiverDAO().update(selectedItem);
                filter();
            } catch (SQLException exception) {
                exception.printStackTrace();
            }
        }
    }

}
