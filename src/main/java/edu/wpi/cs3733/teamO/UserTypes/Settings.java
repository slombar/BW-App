package edu.wpi.cs3733.teamO.UserTypes;

public class Settings {

  // --------------------
  // singleton things:
  // --------------------

  private Settings() {
    algoChoice = "A*";
    elevatorOnly = false;
  }

  public static Settings getInstance() {
    return SingletonHelper.settings;
  }

  private static class SingletonHelper {
    private static final Settings settings = new Settings();
  }

  // --------------------
  // attributes/methods:
  // --------------------

  String algoChoice;
  boolean elevatorOnly;

  public String getAlgoChoice() {
    return algoChoice;
  }

  public void setAlgoChoice(String algoChoice) {
    this.algoChoice = algoChoice;
  }

  public boolean isElevatorOnly() {
    return elevatorOnly;
  }

  public void setElevatorOnly(boolean elevatorOnly) {
    this.elevatorOnly = elevatorOnly;
  }
}
