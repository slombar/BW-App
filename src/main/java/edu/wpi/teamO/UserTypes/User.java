package edu.wpi.teamO.UserTypes;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;

public class User {
    public String name;
    public String username;
    public String email;
    public String password;
    @FXML private JFXButton guestButton;

    public void asGuest(){
    }

}
