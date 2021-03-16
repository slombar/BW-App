package edu.wpi.cs3733.teamO.Controllers.ServiceRequest;

// switch case i might need
/*
switch (type) {
      case "COMP":

        break;
      case "FLOR":

        break;
      case "MAIT":

        break;
      case "SECU":

        break;
      case "LAUN":

        break;
      case "SANA":

        break;
      case "LANG":

        break;
      case "GIFT":

        break;
      case "MEDI":

        break;
      case "TRAN":

        break;
      default:
        throw new IllegalStateException("Unexpected value: " + requestType);
    }
 */

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDrawer;
import edu.wpi.cs3733.teamO.Database.RequestHandling;
import edu.wpi.cs3733.teamO.Database.UserHandling;
import edu.wpi.cs3733.teamO.SRequest.Request;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class RequestPageController implements Initializable {
  public JFXDrawer sideDrawerForAdd;
  public VBox requestList;
  public Text requestListTypeText;
  public JFXComboBox typeOfRequestCombo;
  public JFXComboBox assignedEmployeeCombo;
  public JFXComboBox dateNeededCombo;
  @FXML private JFXDrawer drawer;
  public static String reqType;

  private final ObservableList<String> listOfTypes =
      FXCollections.observableArrayList(
          "ALL", "COMP", "FLOR", "LANG", "LAUN", "GIFT", "TRAN", "MAIT", "MEDI", "SECU", "SANA");

  private final ObservableList<String> listOfAssigned = UserHandling.getEmployeeNames();

  private final ObservableList<String> listOfDates =
      FXCollections.observableArrayList("Old First", "New First");

  public void typeComboAction(ActionEvent actionEvent) {
    reqType = (String) typeOfRequestCombo.getValue();
    displayServiceList(RequestHandling.getRequests(reqType));
  }

  public void assignedComboAction(ActionEvent actionEvent) {
    String employee = (String) assignedEmployeeCombo.getValue();
    String firstName = employee.split(" ")[0];
    String lastName = employee.split(" ")[1];
    displayServiceList(RequestHandling.getEmployeeRequests(firstName, lastName));
  }

  public void dateComboAction(ActionEvent actionEvent) {
    String dateAction = (String) dateNeededCombo.getValue();

    displayServiceList(RequestHandling.getDateSortedRequests(dateAction));
  }

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    // Set drawer to SideMenu
    String sideMenu = "";

    // set items for my combo boxes
    typeOfRequestCombo.setItems(listOfTypes);
    assignedEmployeeCombo.setItems(listOfAssigned);
    dateNeededCombo.setItems(listOfDates);

    if (UserHandling.getAdmin()) {
      sideMenu = "/Views/SideMenuAdmin.fxml";
    } else {
      sideMenu = "/Views/SideMenuStaff.fxml";
    }
    try {
      VBox vbox = FXMLLoader.load(getClass().getResource(sideMenu));
      drawer.setSidePane(vbox);
    } catch (IOException e) {
      e.printStackTrace();
    }

    displayServiceList(RequestHandling.getRequests("ALL"));
  }

  public static String getReqType() {
    return reqType;
  }

  /**
   * Display all service requests of the specific type OR all.
   *
   * @param requests
   */
  public void displayServiceList(ObservableList<Request> requests) {
    // todo @sadie
    // display all requests on page into fxml by adding hboxes dynamically. all should be sized: 100
    // in width besides summary and spacing: 10
    requestList.getChildren().clear();
    for (Request toDisplay : requests) {
      HBox oneRow = new HBox();
      oneRow.setSpacing(20);

      Label id = new Label(String.valueOf(toDisplay.getRequestID()));
      Label requestedOn = new Label(toDisplay.getDateRequested().toString());
      Label requestedBy = new Label(toDisplay.getRequestedBy());
      Label needBy = new Label(toDisplay.getDateNeeded().toString());
      Label assigned = new Label(toDisplay.getAssignedTo());
      Label rLocation = new Label(toDisplay.getRequestLocation());
      Label summary = new Label(toDisplay.getSummary());
      JFXButton assignEmployee = new JFXButton("A");
      JFXButton delete = new JFXButton("D");
      // todo JFXButton edit = new JFXButton("D"); @sadie make this edit button work

      id.setPrefWidth(100);
      requestedOn.setPrefWidth(100);
      requestedBy.setPrefWidth(100);
      needBy.setPrefWidth(100);
      assigned.setPrefWidth(100);
      rLocation.setPrefWidth(100);
      summary.setPrefWidth(200);

      oneRow
          .getChildren()
          .addAll(id, requestedOn, requestedBy, needBy, assigned, rLocation, summary);
      requestList.getChildren().add(oneRow);
    }
  }

  public void closeAddMenu() {

    sideDrawerForAdd.close();
    sideDrawerForAdd.toBack();
  }

  /**
   * switches the drawer fxml out for a new one to add requests
   *
   * @param url
   */
  public void switchAddBox(String url) {
    if (sideDrawerForAdd.isOpened()) {
      sideDrawerForAdd.close();
      sideDrawerForAdd.toBack();

    } else {
      try {
        VBox bp = FXMLLoader.load(getClass().getResource(url));
        sideDrawerForAdd.setSidePane(bp);
      } catch (IOException e) {
        e.printStackTrace();
      }
      sideDrawerForAdd.toFront();
      sideDrawerForAdd.open();
    }
  }

  /**
   * Adding functionality (:
   *
   * <p>type variable is for getting to the proper fxml document
   *
   * @param actionEvent
   */
  public void goToComputerReq(ActionEvent actionEvent) {
    reqType = "COMP";
    switchAddBox("/RevampedViews/DesktopApp/ServiceRequests/ComputerServiceRequest.fxml");
  }

  public void goToFloralReq(ActionEvent actionEvent) {
    reqType = "FLOR";
    switchAddBox("/RevampedViews/DesktopApp/ServiceRequests/FloralDeliveryRequest.fxml");
  }

  public void goToLanguageReq(ActionEvent actionEvent) {
    reqType = "LANG";
    switchAddBox("/RevampedViews/DesktopApp/ServiceRequests/InterpreterForm.fxml");
  }

  public void goToLaundryReq(ActionEvent actionEvent) {
    reqType = "LAUN";
    switchAddBox("/RevampedViews/DesktopApp/ServiceRequests/LaundryRequest.fxml");
  }

  public void goToGiftReq(ActionEvent actionEvent) {
    reqType = "GIFT";
    switchAddBox("/RevampedViews/DesktopApp/ServiceRequests/GiftDeliveryService.fxml");
  }

  public void goToTransportReq(ActionEvent actionEvent) {
    reqType = "TRAN";
    switchAddBox("/RevampedViews/DesktopApp/ServiceRequests/InternalTransportForm.fxml");
  }

  public void goToMaintenance(ActionEvent actionEvent) {
    reqType = "MAIT";
    switchAddBox("/RevampedViews/DesktopApp/ServiceRequests/mait.fxml");
  }

  public void goToMedicineReq(ActionEvent actionEvent) {
    reqType = "MEDI";
    switchAddBox("/RevampedViews/DesktopApp/ServiceRequests/MedicineDeliveryService.fxml");
  }

  public void goToSecurityReq(ActionEvent actionEvent) {
    reqType = "SECU";
    switchAddBox("/RevampedViews/DesktopApp/ServiceRequests/SecurityRequest.fxml");
  }

  public void goToSanitationReq(ActionEvent actionEvent) {
    reqType = "SANA";
    switchAddBox("/RevampedViews/DesktopApp/ServiceRequests/SANA.fxml");
  }
}
