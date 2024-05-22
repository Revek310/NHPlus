package de.hitec.nhplus.utils;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.util.Optional;

        public class ConfirmDeletion {


            /**
             * calls a confirm dialog for deletion of db entries
             * @return user-input yes/no as boolean
             */
            public static boolean showConfirmationDialog() {
                Alert alert = new Alert(AlertType.CONFIRMATION);
                alert.setTitle("Confirmation Dialog");
                alert.setHeaderText("Delete Entry");
                alert.setContentText("Are you sure you want to delete this entry?" +
                        "\n You have to preserve patient data for at least 30 years");

                Optional<ButtonType> result = alert.showAndWait();
                return result.isPresent() && result.get() == ButtonType.OK;
            }
        }