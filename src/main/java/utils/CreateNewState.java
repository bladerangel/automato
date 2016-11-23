package utils;/**
 * Created by Rangel on 22/11/2016.
 */

import controllers.AbstractController;
import controllers.ApplicationController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class CreateNewState extends Stage {
    private FXMLLoader root;

    public CreateNewState(String view, ApplicationController applicationController) throws IOException {
        root = new FXMLLoader(getClass().getResource(view));
        initModality(Modality.APPLICATION_MODAL);
        setScene(new Scene(root.load()));
        AbstractController abstractController = root.getController();
        abstractController.init(applicationController);
        show();

    }

    public Object getRoot() {
        return root.getController();
    }
}
