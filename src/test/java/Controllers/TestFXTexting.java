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

public class TestFXTexting extends ApplicationTest {
  @Override
  public void start(Stage stage) throws IOException {
    // replace "HomeView.fxml" with whatever .fxml file you're testing
    Parent sceneRoot = FXMLLoader.load(getClass().getResource("HomeView.fxml"));

    // I'm pretty sure this stuff can stay the same:
    Scene scene = new Scene(sceneRoot);
    stage.setScene(scene);
    stage.show();
  }

  @Test
  public void testSingleClick() {
    clickOn("Click Me"); // this would be the name of a button that's a part of the scene
    verifyThat("#text", Node::isVisible); // this is essentially similar to assertTrue(...)
  }
}
