package edu.wpi.cs3733.teamO.UserTypes;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;

public class User {
  public String firstName;
  public String lastName;
  public String username;
  public String email;
  public String password;
  @FXML private JFXButton guestButton;

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public JFXButton getGuestButton() {
    return guestButton;
  }

  public void setGuestButton(JFXButton guestButton) {
    this.guestButton = guestButton;
  }

  public void asGuest() {}

}
