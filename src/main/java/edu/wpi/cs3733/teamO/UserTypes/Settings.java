package edu.wpi.cs3733.teamO.UserTypes;

public class Settings {

  // --------------------
  // singleton things:
  // --------------------

  private Settings() {
    algoChoice = "A*";
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

  public String getAlgoChoice() {
    return algoChoice;
  }

  public void setAlgoChoice(String algoChoice) {
    this.algoChoice = algoChoice;
  }
}
