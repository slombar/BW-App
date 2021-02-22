package Controllers;

import static org.testfx.api.FxAssert.verifyThat;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.junit.Test;
import org.testfx.framework.junit5.ApplicationTest;

public class TestEditPageController extends ApplicationTest {
  @Override
  public void start(Stage stage) throws IOException {
    Parent sceneRoot = FXMLLoader.load(getClass().getResource("EditPageController.fxml"));
    Scene scene = new Scene(sceneRoot);
    stage.setScene(scene);
    stage.show();
  }

  @Test
  public void testSingleClick() {
    clickOn("Back"); // this would be the name of a button that's a part of the scene
    verifyThat("#text", Node::isVisible); // this is essentially similar to assertTrue(...)
  }
}
