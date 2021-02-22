import Controllers.DatabaseFunctionality;
import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Opp extends Application {

  public void init() {
    // This happens every time a scene starts up. Kinda cool but not currently useful
    System.out.println("Starting Up");
  }

  public void start(Stage primaryStage) throws IOException {
    System.out.println("Starting Method");
    DatabaseFunctionality.establishConnection();
    primaryStage.setTitle("Main Page");
    primaryStage.setScene(
        new Scene(FXMLLoader.load(getClass().getResource("/Views/Index.fxml")), 1000, 750));
    primaryStage.show();
  }

  public void stop() {
    DatabaseFunctionality.shutDownDB();
    System.out.println("Shutting Down");
  }
}
