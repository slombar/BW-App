package edu.wpi.cs3733.teamO.Controllers;

import static edu.wpi.cs3733.teamO.Database.DataHandling.importExcelData;

import edu.wpi.cs3733.teamO.Database.DataHandling;
import edu.wpi.cs3733.teamO.Database.RequestHandling;
import edu.wpi.cs3733.teamO.Opp;
import edu.wpi.cs3733.teamO.UserTypes.Staff;
import java.util.Date;
import javafx.event.ActionEvent;

public class Upload {

  /**
   * event handler for upload nodes button. will open file explorer and upload stuff to db
   *
   * @param actionEvent
   */
  public void uploadNodes(ActionEvent actionEvent) {
    // run function to open file explorer
    String filepath = DataHandling.explorer(Opp.getPrimaryStage());
    System.out.println("File path: " + filepath);
    // import data
    importExcelData(filepath, true);
  }

  /**
   * event handler for upload edges button. will open file explorer and upload stuff to db
   *
   * @param actionEvent
   */
  public void uploadEdges(ActionEvent actionEvent) {
    // run function to open file explorer
    String filepath = DataHandling.explorer(Opp.getPrimaryStage());
    System.out.println("File path: " + filepath);
    // import data
    importExcelData(filepath, false);
  }

  public void addRequest(ActionEvent actionEvent) {
    String random = "dog";
    Staff staff = new Staff();
    Date date = new Date(2020, 10, 10);
    RequestHandling.addRequest(
        staff, staff, date, date, random, random, random, random, random, random);
  }
}
