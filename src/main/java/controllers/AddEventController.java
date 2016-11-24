package controllers;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.RequiredFieldValidator;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import models.Event;
import models.State;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Rangel on 22/11/2016.
 */
public class AddEventController extends AbstractController implements Initializable {

    @FXML
    private AnchorPane pane;

    @FXML
    private JFXTextField name;

    @FXML
    private JFXComboBox<State> states1;

    @FXML
    private JFXComboBox<State> states2;

    RequiredFieldValidator requiredFieldValidator;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        requiredFieldValidator = new RequiredFieldValidator();
        requiredFieldValidator.setIcon(new ImageView("assets/images/alert.png"));
        requiredFieldValidator.setMessage("Empty");
        name.getValidators().add(requiredFieldValidator);
    }

    @Override
    public void init(ApplicationController applicationController) {
        super.init(applicationController);
        states1.getItems().addAll(applicationController.getAllStates());
        states2.getItems().addAll(applicationController.getAllStates());
    }

    @FXML
    public void validate() {
        name.validate();
    }

    @FXML
    void save() {
        Event event = new Event(name.getText());
        State state1 = states1.getSelectionModel().getSelectedItem();
        State state2 = states2.getSelectionModel().getSelectedItem();
        if (name.validate() && applicationController.addEventGraph(event, state1, state2)) {
            Stage stage = (Stage) pane.getScene().getWindow();
            stage.close();
        }
    }


}
