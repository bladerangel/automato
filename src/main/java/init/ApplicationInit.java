package init;/**
 * Created by Rangel on 20/11/2016.
 */

import com.jfoenix.controls.JFXDecorator;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import utils.CreateNewWindow;

public class ApplicationInit extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        CreateNewWindow createNewWindow = new CreateNewWindow("ApplicationView");
        createNewWindow.setStage(primaryStage);
        createNewWindow.setBtnMin(true);
        createNewWindow.setScene();
        createNewWindow.show();
    }
}
