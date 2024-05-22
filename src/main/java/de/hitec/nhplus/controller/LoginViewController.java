package de.hitec.nhplus.controller;

import de.hitec.nhplus.Main;
import de.hitec.nhplus.datastorage.CaregiverDao;
import de.hitec.nhplus.datastorage.DaoFactory;
import de.hitec.nhplus.datastorage.PatientDao;
import de.hitec.nhplus.model.Caregiver;
import de.hitec.nhplus.model.Patient;
import de.hitec.nhplus.model.Treatment;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Controller for the login view
 */
public class LoginViewController {


    @FXML
    private TextField inpUsername;
    @FXML
    private TextField inpPassword;
    @FXML
    private Button btnLogin;
    @FXML
    private Text txtLoginFeedback;

    private LoginViewController controller;
    private Stage stage;
    private ArrayList<Caregiver> caregivers = new ArrayList<>();


    /**
     * called by pressing the login button
     * checks if the users credentials equal any account
     */
    @FXML
    public void handleLogin() {

        CaregiverDao cDao = DaoFactory.getDaoFactory().createCaregiverDAO();
        try {
            this.caregivers = (ArrayList<Caregiver>) cDao.readAll();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        boolean correctLogin = false;
        for (Caregiver caregiver : caregivers) {
            if (inpUsername.getText().equals(caregiver.getUsername()) && inpPassword.getText().equals(caregiver.getPassword())) {
                correctLogin = true;
                break;
            }
        }
        if (correctLogin) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/de/hitec/nhplus/MainWindowView.fxml"));
                BorderPane pane = loader.load();
                Scene scene = inpUsername.getScene();
                Stage stage = (Stage) scene.getWindow();
                stage.setScene(new Scene(pane));

            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }else{
            txtLoginFeedback.setVisible(true);
        }
    }
}
