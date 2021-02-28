package edu.wpi.cs3733.teamO.Controllers.Popups;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import edu.wpi.cs3733.teamO.Database.UserHandling;
import edu.wpi.cs3733.teamO.Opp;
import edu.wpi.cs3733.teamO.UserTypes.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.GridPane;

import java.io.IOException;

public class PatientSignInPopupController {

    @FXML
    private JFXPasswordField password;
    @FXML
    private JFXTextField username;
    @FXML
    private JFXTextField email;
    @FXML
    private JFXTextField fName;
    @FXML
    private JFXTextField lName;

    public void signIn(ActionEvent actionEvent) {
        UserHandling.login(username.getText(), password.getText());

        try {
            GridPane root = FXMLLoader.load(getClass().getResource("/Views/MainPage.fxml"));
            Opp.getPrimaryStage().getScene().setRoot(root);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void close(ActionEvent actionEvent) {
        System.out.println("EASY");
    }

    public void create(ActionEvent actionEvent) {
        UserHandling.createAccount(username.getText(), password.getText(), email.getText(), fName.getText(), lName.getText());
        try {
            GridPane root = FXMLLoader.load(getClass().getResource("/Views/MainPage.fxml"));
            Opp.getPrimaryStage().getScene().setRoot(root);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
