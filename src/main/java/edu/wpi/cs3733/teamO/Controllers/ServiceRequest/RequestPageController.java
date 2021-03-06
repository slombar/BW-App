package edu.wpi.cs3733.teamO.Controllers.ServiceRequest;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.transitions.hamburger.HamburgerBackArrowBasicTransition;
import edu.wpi.cs3733.teamO.Database.UserHandling;
import edu.wpi.cs3733.teamO.HelperClasses.Effects;
import edu.wpi.cs3733.teamO.HelperClasses.SwitchScene;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

public class RequestPageController implements Initializable {
  @FXML private JFXDrawer drawer;
  @FXML private JFXHamburger hamburger;
  @FXML private JFXButton computerBtn;
  @FXML private JFXButton floralBtn;
  @FXML private JFXButton languageBtn;
  @FXML private JFXButton laundryBtn;
  @FXML private JFXButton giftBtn;
  @FXML private JFXButton transportBtn;
  @FXML private JFXButton maintenanceBtn;
  @FXML private JFXButton medicineBtn;
  @FXML private JFXButton laundryBtn2;
  @FXML private JFXButton securityBtn;
  @FXML private JFXButton sanitationBtn;
  @FXML private JFXButton moreBtn;
  public static String reqType;

  public static String getReqType() {
    return reqType;
  }

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    // Set drawer to SideMenu
    String sideMenu = "/Views/SideMenuStaff.fxml";
    if (UserHandling.getAdmin()) sideMenu = "/Views/SideMenuAdmin.fxml";
    try {
      VBox vbox = FXMLLoader.load(getClass().getResource(sideMenu));
      drawer.setSidePane(vbox);
    } catch (IOException e) {
      e.printStackTrace();
    }

    // transition animation of Hamburger icon
    HamburgerBackArrowBasicTransition transition = new HamburgerBackArrowBasicTransition(hamburger);
    transition.setRate(-1);

    // click event - mouse click
    hamburger.addEventHandler(
        MouseEvent.MOUSE_PRESSED,
        (e) -> {
          transition.setRate(transition.getRate() * -1);
          transition.play();
          if (drawer.isOpened()) {
            drawer.close(); // this will close slide pane
          } else {
            drawer.open(); // this will open slide pane
          }
        });
    hoverAllBtn();
  }

  /** hovering over the button will make the buttons darker */
  public void hoverAllBtn() {
    Effects.hoverEffect(computerBtn);
    Effects.hoverEffect(floralBtn);
    Effects.hoverEffect(languageBtn);
    Effects.hoverEffect(laundryBtn);
    Effects.hoverEffect(giftBtn);
    Effects.hoverEffect(transportBtn);
    Effects.hoverEffect(maintenanceBtn);
    Effects.hoverEffect(medicineBtn);
    Effects.hoverEffect(laundryBtn2);
    Effects.hoverEffect(securityBtn);
    Effects.hoverEffect(sanitationBtn);
    Effects.hoverEffect(moreBtn);
  }

  public void goToCheckReq(ActionEvent actionEvent) {
    SwitchScene.goToParent("/Views/RequestStatus.fxml");
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
    SwitchScene.goToParent("/Views/ServiceRequests/RequestList.fxml");
  }

  public void goToFloralReq(ActionEvent actionEvent) {
    reqType = "FLOR";
    SwitchScene.goToParent("/Views/ServiceRequests/RequestList.fxml");
  }

  public void goToLanguageReq(ActionEvent actionEvent) {
    reqType = "LANG";
    SwitchScene.goToParent("/Views/ServiceRequests/RequestList.fxml");
  }

  public void goToLaundryReq(ActionEvent actionEvent) {
    reqType = "LAUN";
    SwitchScene.goToParent("/Views/ServiceRequests/RequestList.fxml");
  }

  public void goToGiftReq(ActionEvent actionEvent) {
    reqType = "GIFT";
    SwitchScene.goToParent("/Views/ServiceRequests/RequestList.fxml");
  }

  public void goToTransportReq(ActionEvent actionEvent) {
    reqType = "TRAN";
    SwitchScene.goToParent("/Views/ServiceRequests/RequestList.fxml");
  }

  public void goToMaintenance(ActionEvent actionEvent) {
    reqType = "MAIT";
    SwitchScene.goToParent("/Views/ServiceRequests/RequestList.fxml");
  }

  public void goToMedicineReq(ActionEvent actionEvent) {
    reqType = "MEDI";
    SwitchScene.goToParent("/Views/ServiceRequests/RequestList.fxml");
  }

  public void goToSecurityReq(ActionEvent actionEvent) {
    reqType = "SECU";
    SwitchScene.goToParent("/Views/ServiceRequests/RequestList.fxml");
  }

  public void goToSanitationReq(ActionEvent actionEvent) {
    reqType = "SANA";
    SwitchScene.goToParent("/Views/ServiceRequests/RequestList.fxml");
  }
}
