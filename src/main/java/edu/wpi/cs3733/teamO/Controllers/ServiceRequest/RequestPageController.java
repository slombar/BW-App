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
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDrawer;
import edu.wpi.cs3733.teamO.Database.UserHandling;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
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

  public static String getReqType() {
    return reqType;
  }

  /** Display all service requests of the specific type OR all. */
  public void displayServiceList() {
    // display all requests
    if (reqType.equals("ALL")) {
      // get all service requests from database

    } else {
      // get only the specific type of requests from the database

    }
  }

  private ObservableList<String> listOfSizes =
      FXCollections.observableArrayList(
          "ALL", "COMP", "FLOR", "LANG", "LAUN", "GIFT", "TRAN", "MAIT", "MEDI", "SECU", "SANA");

  public void typeComboAction(ActionEvent actionEvent) {
    reqType = (String) typeOfRequestCombo.getValue();
    displayServiceList();
  }

  public void closeAddMenu() {

    sideDrawerForAdd.close();
    sideDrawerForAdd.toBack();
  }

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    // Set drawer to SideMenu
    String sideMenu = "";

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
  }

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
   * below are all on action methods for the buttons on the request page it uses the goToRequest
   * helper and then leads to the specific form
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
