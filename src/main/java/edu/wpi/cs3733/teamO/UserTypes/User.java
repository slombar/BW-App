package edu.wpi.cs3733.teamO.UserTypes;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;

public class User {
  public String firstName;
  public String lastName;
  public String username;
  public String email;
  public String password;
  public boolean admin;
  public boolean employee;
  public String parkingSpot;
  @FXML private JFXButton guestButton;

  /**
   * Constructor for user
   *
   * @param uname
   * @param pass
   * @param email
   * @param fName
   * @param lName
   * @param employee
   * @param admin
   */
  public User(
      String uname,
      String pass,
      String email,
      String fName,
      String lName,
      boolean employee,
      boolean admin) {
    this.username = uname;
    this.password = pass;
    this.email = email;
    this.firstName = fName;
    this.lastName = lName;
    this.admin = admin;
    this.employee = employee;
    this.parkingSpot = null;
  }

  public User() {}

  public String getParkingSpot() {
    return parkingSpot;
  }

  public void setParkingSpot(String parkingSpot) {
    this.parkingSpot = parkingSpot;
  }

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

  public boolean isAdmin() {
    return admin;
  }

  public void setAdmin(boolean admin) {
    this.admin = admin;
  }

  public boolean isEmployee() {
    return employee;
  }

  public void setEmployee(boolean employee) {
    this.employee = employee;
  }

  public JFXButton getGuestButton() {
    return guestButton;
  }

  public void setGuestButton(JFXButton guestButton) {
    this.guestButton = guestButton;
  }

  public void asGuest() {}
}
