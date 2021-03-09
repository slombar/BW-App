package edu.wpi.cs3733.teamO.UserTypes;

public class Settings {

  // --------------------
  // singleton things:
  // --------------------

  private Settings() {
    // fill in constructor
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

}
