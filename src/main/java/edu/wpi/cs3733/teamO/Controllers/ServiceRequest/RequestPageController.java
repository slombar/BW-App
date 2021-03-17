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

import com.jfoenix.controls.*;
import com.jfoenix.transitions.hamburger.HamburgerBackArrowBasicTransition;
import edu.wpi.cs3733.teamO.Database.RequestHandling;
import edu.wpi.cs3733.teamO.Database.UserHandling;
import edu.wpi.cs3733.teamO.HelperClasses.PopupMaker;
import edu.wpi.cs3733.teamO.HelperClasses.SwitchScene;
import edu.wpi.cs3733.teamO.SRequest.Request;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class RequestPageController implements Initializable {
  public JFXDrawer sideDrawerForAdd;
  public VBox requestList;
  public Text requestListTypeText;
  public JFXComboBox typeOfRequestCombo;
  public JFXComboBox assignedEmployeeCombo;
  public JFXComboBox dateNeededCombo;
  public StackPane popUpPane;
  @FXML private VBox menuVBox;
  @FXML private JFXHamburger hamburger;
  @FXML private JFXButton profileBtn;
  @FXML private JFXButton homeBtn;
  @FXML private JFXButton navBtn1;
  @FXML private JFXButton trackBtn;
  @FXML private JFXButton reqBtn;
  @FXML private JFXButton patientsBtn;
  @FXML private JFXButton employeesBtn;
  @FXML private JFXButton loginBtn;
  @FXML private JFXDrawer drawerSM;
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
    displayServiceList(RequestHandling.getEmployeeRequests(employee));
  }

  public void dateComboAction(ActionEvent actionEvent) {
    String dateAction = (String) dateNeededCombo.getValue();
    displayServiceList(RequestHandling.getDateSortedRequests(dateAction));
  }

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    if (!UserHandling.getEmployee()) {
      reqBtn.setVisible(false);
      reqBtn.setDisable(true);
      employeesBtn.setVisible(false);
      employeesBtn.setDisable(true);
    } else if (!UserHandling.getAdmin() && UserHandling.getEmployee()) {
      employeesBtn.setVisible(false);
      employeesBtn.setDisable(true);
    }

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
    drawerSM.toBack();

    try {
      VBox vbox =
          FXMLLoader.load(getClass().getResource("/RevampedViews/DesktopApp/NewSideMenu.fxml"));
      drawerSM.setSidePane(vbox);
    } catch (IOException e) {
      e.printStackTrace();
    }

    // transition animation of Hamburger icon
    HamburgerBackArrowBasicTransition burgerTransition =
        new HamburgerBackArrowBasicTransition(hamburger);
    burgerTransition.setRate(-1);

    // click event - mouse click
    hamburger.addEventHandler(
        MouseEvent.MOUSE_PRESSED,
        (e) -> {
          burgerTransition.setRate(burgerTransition.getRate() * -1);
          burgerTransition.play();
          if (drawerSM.isOpened()) {
            drawerSM.close(); // this will close slide pane
            drawerSM.toBack();
          } else {
            drawerSM.open(); // this will open slide pane
            drawerSM.toFront();
            menuVBox.toFront();
          }
        });

    // transition animation of Hamburger icon
    HamburgerBackArrowBasicTransition transition = new HamburgerBackArrowBasicTransition(hamburger);
    transition.setRate(-1);
  }

  public static String getReqType() {
    return reqType;
  }

  public static ArrayList<JFXTextField> createFields(ArrayList<String> labels) {
    ArrayList<JFXTextField> listOfFields = new ArrayList<>();
    for (String label : labels) {
      JFXTextField text = new JFXTextField();
      text.setPromptText(label);
      listOfFields.add(text);
    }
    return listOfFields;
  }

  /**
   * Button functionality that will assign the user to the request
   *
   * @param actionEvent
   */
  public void assignStaff(ActionEvent actionEvent, int reqid) {

    // addEdgePopup has the content of the popup
    // addEdgeDialog creates the dialog popup

    JFXDialogLayout assignStaffLayout = new JFXDialogLayout();
    assignStaffLayout.setHeading(new Text("Assign Staff to Service Request"));
    VBox assignStaffVBox = new VBox(12);

    // Creating an HBox of buttons
    HBox buttonBox = new HBox(20);
    JFXButton closeButton = new JFXButton("Close");
    JFXButton submitButton = new JFXButton("Assign");
    buttonBox.getChildren().addAll(closeButton, submitButton);

    // Creating a list of labels to create the textfields
    ArrayList<String> assignStaffLabels =
        new ArrayList<String>(Arrays.asList("Request ID", "Employee Name"));
    ArrayList<JFXTextField> listOfFields = createFields(assignStaffLabels);
    listOfFields.get(0).setText(String.valueOf(reqid));
    // Creating the form with a VBox
    assignStaffVBox.getChildren().addAll(listOfFields.get(0), listOfFields.get(1), buttonBox);
    assignStaffLayout.setBody(assignStaffVBox);

    // Bringing the popup screen to the front and disabling the background
    popUpPane.toFront();
    JFXDialog assignStaffDialog =
        new JFXDialog(popUpPane, assignStaffLayout, JFXDialog.DialogTransition.BOTTOM);

    // Closing the popup
    closeButton.setOnAction(
        event -> {
          assignStaffDialog.close();
          popUpPane.toBack();
          displayServiceList(RequestHandling.getRequests("ALL"));
        });

    // Submits edit to the database
    submitButton.setOnAction(
        event -> {
          // If incomplete form, sends an error msg
          // Otherwise, sends to database and closes popup
          if (listOfFields.get(0).getText().isEmpty() || listOfFields.get(1).getText().isEmpty()) {
            //              incompletePopup();
            PopupMaker.incompletePopup(popUpPane);
          } else {
            RequestHandling.assignEmployee(
                Integer.parseInt(listOfFields.get(0).getText()), listOfFields.get(1).getText());

            assignStaffDialog.close();
            popUpPane.toBack();
            displayServiceList(RequestHandling.getRequests("ALL"));
          }
        });
    assignStaffDialog.show();
  }

  /**
   * Display all service requests of the specific type OR all.
   *
   * @param requests
   */
  public void displayServiceList(ObservableList<Request> requests) {
    // display all requests on page into fxml by adding hboxes dynamically. all should be sized: 100
    // in width besides summary and spacing: 10
    requestList.getChildren().clear();

    for (Request toDisplay : requests) {
      HBox oneRow = new HBox();
      oneRow.setSpacing(20);

      Label id = new Label(String.valueOf(toDisplay.getRequestID()));
      Label requestedOn = new Label(toDisplay.getDateRequested().toString());
      Label requestedBy = new Label(toDisplay.getRequestedBy());
      System.out.println("Print out reqby: " + requestedBy.getText());
      Label needBy = new Label(toDisplay.getDateNeeded().toString());
      Label assigned = new Label(toDisplay.getAssignedTo());
      System.out.println("Print out inside: " + toDisplay.getAssignedTo());
      Label rLocation = new Label(toDisplay.getRequestLocation());
      Label summary = new Label(toDisplay.getSummary());
      Label status = new Label(toDisplay.getStatus());
      JFXButton assign = new JFXButton("Assign Staff");
      JFXButton updateStatus = new JFXButton("Complete");
      JFXButton delete = new JFXButton("Delete");
      // JFXButton edit = new JFXButton(); todo maybe? @sadie

      assign.setOnAction(
          e -> {
            assignStaff(e, toDisplay.getRequestID());
          });

      updateStatus.setOnAction(
          e -> {
            try {
              RequestHandling.setStatus(toDisplay.getRequestID(), "Complete");
            } catch (SQLException throwables) {
              throwables.printStackTrace();
            }
            displayServiceList(RequestHandling.getRequests("ALL"));
          });

      delete.setOnAction(
          e -> {
            // mark the thing as done
            RequestHandling.deleteRequest(toDisplay.getRequestID());

            displayServiceList(RequestHandling.getRequests("ALL"));
          });

      id.setPrefWidth(40);
      requestedOn.setPrefWidth(80);
      requestedBy.setPrefWidth(100);
      needBy.setPrefWidth(80);
      assigned.setPrefWidth(100);
      rLocation.setPrefWidth(100);
      summary.setPrefWidth(250);
      summary.setWrapText(true);
      status.setPrefWidth(80);

      oneRow
          .getChildren()
          .addAll(
              id,
              requestedOn,
              requestedBy,
              needBy,
              assigned,
              rLocation,
              status,
              summary,
              assign,
              delete,
              updateStatus);
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

  public void toProfile(ActionEvent actionEvent) {
    SwitchScene.goToParent("/RevampedViews/DesktopApp/ProfilePage.fxml");
  }

  public void toHome(ActionEvent actionEvent) {
    String sideMenu = "/RevampedViews/DesktopApp/MainStaffScreen.fxml";
    if (!UserHandling.getEmployee()) sideMenu = "/RevampedViews/DesktopApp/MainPatientScreen.fxml";
    SwitchScene.goToParent(sideMenu);
  }

  public void toNav(ActionEvent actionEvent) {
    SwitchScene.goToParent("/RevampedViews/DesktopApp/Navigation.fxml");
  }

  public void toTrack(ActionEvent actionEvent) {
    SwitchScene.goToParent("/Views/CovidSurvey.fxml");
  }

  public void toReq(ActionEvent actionEvent) {
    SwitchScene.goToParent("/RevampedViews/DesktopApp/Services.fxml");
  }

  public void toEmployees(ActionEvent actionEvent) {
    SwitchScene.goToParent("/Views/ManageEmployees.fxml");
  }

  public void toLogin(ActionEvent actionEvent) {
    SwitchScene.goToParent("/RevampedViews/DesktopApp/SignInPage.fxml");
  }
}
