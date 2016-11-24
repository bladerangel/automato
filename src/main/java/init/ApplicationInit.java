package init;/**
 * Created by Rangel on 20/11/2016.
 */

import com.jfoenix.controls.JFXDecorator;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ApplicationInit extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/views/ApplicationView.fxml"));
        JFXDecorator decorator = new JFXDecorator(primaryStage, root, false, true, true);
        Scene scene = new Scene(decorator);
        scene.getStylesheets().add(getClass().getResource("/assets/stylesheets/decorator.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }
}
