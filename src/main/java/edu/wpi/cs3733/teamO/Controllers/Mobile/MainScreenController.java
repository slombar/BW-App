package edu.wpi.cs3733.teamO.Controllers.Mobile;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXNodesList;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class MainScreenController extends Application implements Initializable {

  private static Stage primaryStage;
  @FXML private JFXNodesList buttonList;
  private final JFXButton welcomeBtn = new JFXButton("Welcome to the B&W Faulkner Hospital");
  private final JFXButton navBtn = new JFXButton("Navigating to the hospital");
  private final JFXButton covidBtn = new JFXButton("Get the latest COVID-19 information");

  public static Stage getPrimaryStage() {
    return primaryStage;
  }

  @Override
  public void start(Stage ps) throws Exception {
    MainScreenController.primaryStage = ps;

    try {
      BorderPane root = FXMLLoader.load(getClass().getResource("/Views/MobileApp/MainScreen.fxml"));
      Scene scene = new Scene(root);
      Image icon =
              new Image(getClass().getResourceAsStream("/Brigham_and_Womens_Hospital_logo.png"));
      ps.getIcons().add(icon);
      ps.setScene(scene);
      ps.setFullScreen(true);
      ps.show();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    // style the buttons
    welcomeBtn.getStyleClass().addAll("animated-option-button");
    navBtn.getStyleClass().addAll("animated-option-button");
    covidBtn.getStyleClass().addAll("animated-option-button");

    // add them to be in an animated node list
    buttonList.addAnimatedNode(welcomeBtn);
    buttonList.addAnimatedNode(navBtn);
    buttonList.addAnimatedNode(covidBtn);
    buttonList.setSpacing(20);
  }

  private void buttonFunction() {
    navBtn.setOnAction(actionEvent -> {
//      goToParentMobile();
    });

    covidBtn.setOnAction(actionEvent -> {
//      goToParentMobile();
    });
  }


}
