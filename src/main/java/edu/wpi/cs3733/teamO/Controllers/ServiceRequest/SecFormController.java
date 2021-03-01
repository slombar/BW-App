package edu.wpi.cs3733.teamO.Controllers.ServiceRequest;

import com.jfoenix.controls.*;
import edu.wpi.cs3733.teamO.Opp;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class SecFormController {

  @FXML private StackPane stackPane;
  @FXML private JFXTextField secThreat;
  @FXML private JFXSlider secSlider;
  @FXML private JFXTextArea secDesc;
  @FXML private JFXTextField secLocation;
  @FXML private JFXCheckBox secCheck;
  private boolean popUp = false;
  private boolean goHome = false;

  /**
   * Scene switching
   *
   * @param actionEvent
   * @throws IOException
   */
  @FXML
  public void goToHomePage(ActionEvent actionEvent) throws IOException {
    AnchorPane root = FXMLLoader.load(getClass().getResource("/Views/Archive/Index.fxml"));
    Opp.getPrimaryStage().setFullScreen(true);
    Opp.getPrimaryStage().getScene().setRoot(root);
  }

  public void submitForm(ActionEvent actionEvent) throws IOException {
    if (submissionPopup()) {
      AnchorPane root = FXMLLoader.load(getClass().getResource("/Views/Archive/Index.fxml"));
      Opp.getPrimaryStage().setFullScreen(true);
      Opp.getPrimaryStage().getScene().setRoot(root);
    }
  }

  public void clearPage(ActionEvent actionEvent) {
    secThreat.clear();
    secSlider.setValue(1);
    secCheck.setSelected(false);
    secDesc.clear();
    secLocation.clear();
  }

  public boolean submissionPopup() {

    if (!popUp) {
      popUp = true;
      goHome = false;

      // dialogContent has the conetnt of the popup
      JFXDialogLayout dialogContent = new JFXDialogLayout();
      dialogContent.setHeading(new Text("Success!"));
      VBox dialogVBox = new VBox(12);

      // Creating an HBox of buttons
      HBox buttonBox = new HBox(20);
      JFXButton closeButton = new JFXButton("Close");
      JFXButton homeButton = new JFXButton("Return to Homepage");
      buttonBox.getChildren().addAll(closeButton, homeButton);

      // Creating the format
      dialogVBox
          .getChildren()
          .addAll(new Text("The form has been submitted successfully."), buttonBox);
      dialogContent.setBody(dialogVBox);

      // Bringing the popup screen to the front and disabling the background
      stackPane.toFront();
      JFXDialog submissionDialog =
          new JFXDialog(stackPane, dialogContent, JFXDialog.DialogTransition.BOTTOM);
      submissionDialog.setOverlayClose(false);

      // Closing the popup
      closeButton.setOnAction(
          event -> {
            submissionDialog.close();
            stackPane.toBack();
            popUp = false;
            goHome = false;
          });

      // go to Index/Homepage
      homeButton.setOnAction(
          event -> {
            submissionDialog.close();
            stackPane.toBack();
            AnchorPane root = null;
            try {
              root = FXMLLoader.load(getClass().getResource("/Views/Archive/Index.fxml"));
            } catch (IOException e) {
              e.printStackTrace();
            }
            Opp.getPrimaryStage().setFullScreen(true);
            Opp.getPrimaryStage().getScene().setRoot(root);
            popUp = false;
            goHome = true;
          });
      submissionDialog.show();
    }
    return goHome;
  }
}
