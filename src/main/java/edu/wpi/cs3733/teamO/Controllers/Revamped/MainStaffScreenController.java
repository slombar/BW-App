package edu.wpi.cs3733.teamO.Controllers.Revamped;

import com.jfoenix.controls.JFXButton;
import edu.wpi.cs3733.teamO.Database.UserHandling;
import edu.wpi.cs3733.teamO.HelperClasses.Effects;
import edu.wpi.cs3733.teamO.HelperClasses.SwitchScene;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Circle;

public class MainStaffScreenController implements Initializable {
  @FXML private ImageView navImg;
  @FXML private ImageView servReqImg;
  @FXML private ImageView googleNavImg;

  @FXML private JFXButton navBtn;
  @FXML private JFXButton servReqBtn;
  @FXML private JFXButton googleNavBtn;
  @FXML private JFXButton covidBtn;
  @FXML private JFXButton entryReqBtn;
  @FXML private JFXButton parkingBtn;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    // determines employee/staff
    System.out.println("Employee " + UserHandling.getEmployee());
    System.out.println("Admin " + UserHandling.getAdmin());

    String sideMenuUrl;
    if (UserHandling.getEmployee()) {
      System.out.println("EMPLOYEE");
      sideMenuUrl = "/Views/SideMenuStaff.fxml";
      if (UserHandling.getAdmin()) {
        sideMenuUrl = "/Views/SideMenuAdmin.fxml";
        System.out.println("ADMIN");
      }
    } else {
      sideMenuUrl = "/Views/SideMenu.fxml";
    }
    // TODO: add side menu stuff

    // customization
    hoverAllBtn();
    clipImages();
  }

  /** hovering over the button will make the buttons darker */
  public void hoverAllBtn() {
    Effects.hoverEffect(navBtn);
    Effects.hoverEffect(servReqBtn);
    Effects.hoverEffect(googleNavBtn);
    Effects.hoverEffectGray(covidBtn);
    Effects.hoverEffectGray(entryReqBtn);
    Effects.hoverEffectGray(parkingBtn);
  }

  public void clipImages() {
    Circle clip1 = new Circle(150, 150, 150);
    Circle clip2 = new Circle(150, 150, 150);
    Circle clip3 = new Circle(150, 150, 150);

    navImg.setClip(clip1);
    servReqImg.setClip(clip2);
    googleNavImg.setClip(clip3);
  }

  public void goToNav(ActionEvent actionEvent) {
    SwitchScene.goToParent("/RevampedViews/DesktopApp/Navigation.fxml");
  }

  public void goToServReq(ActionEvent actionEvent) {
    SwitchScene.goToParent("/Views/ServiceRequests/RequestPage.fxml");
  }

  public void goToGoogleNav(ActionEvent actionEvent) {
    SwitchScene.goToParent("/Views/GoogleMaps/GoogleMapPage.fxml");
  }

  public void goToCovid(ActionEvent actionEvent) {
    SwitchScene.goToParent("/Views/CovidSurvey.fxml");
  }

  public void goToEntryReq(ActionEvent actionEvent) {
    SwitchScene.goToParent("/Views/ServiceRequests/RequestList.fxml");
  }

  public void goToParking(ActionEvent actionEvent) {}

  public void goToInfo(MouseEvent mouseEvent) {}

  public void goToAccount(ActionEvent actionEvent) {}
}
