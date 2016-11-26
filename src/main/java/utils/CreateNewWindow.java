package utils;/**
 * Created by Rangel on 22/11/2016.
 */

import com.jfoenix.controls.JFXDecorator;
import controllers.AbstractController;
import controllers.ApplicationController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class CreateNewWindow {
    private FXMLLoader fxmlLoader;
    private Stage stage;
    private Scene scene;
    private JFXDecorator decorator;
    private AbstractController abstractController;

    public CreateNewWindow(String view, boolean btnMin) throws IOException {
        fxmlLoader = new FXMLLoader(getClass().getResource(view));
        stage = new Stage();
        decorator = new JFXDecorator(stage, fxmlLoader.load(), false, true, btnMin);
        scene = new Scene(decorator);

        scene.getStylesheets().add(getClass().getResource("/assets/stylesheets/decorator.css").toExternalForm());
        stage.setScene(scene);
        stage.setResizable(false);
        if (btnMin)
            stage.show();
    }

    public CreateNewWindow(String view, ApplicationController applicationController) throws IOException {
        this(view, false);
        abstractController = fxmlLoader.getController();
        abstractController.init(applicationController);
        decorator.setOnCloseButtonAction(() -> stage.close());
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
    }
}
