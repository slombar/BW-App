package edu.wpi.cs3733.teamO.Controllers.RevampedMobile;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXNodesList;
import edu.wpi.cs3733.teamO.HelperClasses.SwitchScene;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class MobileAboutController implements Initializable {
  @FXML private JFXNodesList buttonsList;

  Image addIcon = new Image(getClass().getResourceAsStream("/Icons/addBlack.png"));
  ImageView addIconView = new ImageView(addIcon);
  Image exitIcon = new Image(getClass().getResourceAsStream("/Icons/exitBlack.png"));
  ImageView exitIconView = new ImageView(exitIcon);
  Image navIcon = new Image(getClass().getResourceAsStream("/Icons/navIconBlack.png"));
  ImageView navIconView = new ImageView(navIcon);
  Image googleIcon = new Image(getClass().getResourceAsStream("/Icons/navIconBlack.png"));
  ImageView googleIconView = new ImageView(googleIcon);

  private final JFXButton addBtn = new JFXButton(null, addIconView);
  private final JFXButton exitBtn = new JFXButton("Exit About Us", exitIconView);
  private final JFXButton hospitalBtn = new JFXButton("Google Nav", googleIconView);
  private final JFXButton googleBtn = new JFXButton("Hospital Nav", navIconView);

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    // set the icons sizes
    addIconView.setFitWidth(15);
    addIconView.setFitHeight(15);
    exitIconView.setFitWidth(20);
    exitIconView.setFitHeight(20);
    navIconView.setFitWidth(15);
    navIconView.setFitHeight(15);
    googleIconView.setFitWidth(15);
    googleIconView.setFitHeight(15);

    // style the buttons
    addBtn.getStyleClass().addAll("nav-menu-button");
    addBtn.setButtonType(JFXButton.ButtonType.RAISED);
    exitBtn.getStyleClass().addAll("nav-buttons");
    exitBtn.setButtonType(JFXButton.ButtonType.RAISED);
    hospitalBtn.getStyleClass().addAll("nav-buttons");
    hospitalBtn.setButtonType(JFXButton.ButtonType.RAISED);
    googleBtn.getStyleClass().addAll("nav-buttons");
    googleBtn.setButtonType(JFXButton.ButtonType.RAISED);

    /** Add to the floor selection* */
    buttonsList.addAnimatedNode(addBtn);
    buttonsList.addAnimatedNode(hospitalBtn);
    buttonsList.addAnimatedNode(googleBtn);
    buttonsList.addAnimatedNode(exitBtn);
    buttonsList.setSpacing(20);
    buttonsList.setRotate(180);
    buttonsList.setAlignment(Pos.CENTER_RIGHT);

    buttonFunction(); // adds on action functionality to buttons
    buttonsList.toFront();
  }

  /** adding on action functionality to the buttons in the JFXNodeslist */
  private void buttonFunction() {
    hospitalBtn.setOnAction(
        actionEvent -> {
          SwitchScene.goToParentMobile("/Views/MobileApp/MobileHospitalNav.fxml", actionEvent);
        });

    googleBtn.setOnAction(
        actionEvent -> {
          SwitchScene.goToParentMobile("/Views/MobileApp/MobileGoogleNav.fxml", actionEvent);
        });

    exitBtn.setOnAction(
        actionEvent -> {
          SwitchScene.goToParentMobile(
              "/RevampedViews/MobileApp/MainMobileScreen.fxml", actionEvent);
        });
  }
}
