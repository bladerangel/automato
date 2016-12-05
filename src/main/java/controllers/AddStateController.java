package controllers;

import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.RequiredFieldValidator;
import com.jfoenix.validation.base.ValidatorBase;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import models.State;
import views.LayoutGraph;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Rangel on 22/11/2016.
 */
public class AddStateController extends AbstractController implements Initializable {

    @FXML
    private AnchorPane pane;

    @FXML
    private JFXTextField name;

    @FXML
    private CheckBox start;

    @FXML
    private CheckBox marked;

    private ValidatorState validator;

    private RequiredFieldValidator requiredFieldValidator;

    @FXML
    public void save() throws IOException {
        State state = new State(name.getText());
        if (name.validate() && applicationController.addStateGraph(state)) {
            if (marked.isSelected()) {
                state.setMarked(true);
            }
            if (start.isSelected()) {
                state.setStart(true);
                state.setAccessible(true);
            }
            Stage stage = (Stage) pane.getScene().getWindow();
            stage.close();
        }
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        validator = new ValidatorState();
        requiredFieldValidator = new RequiredFieldValidator();
        validator.setIcon(new ImageView("assets/images/alert.png"));
        requiredFieldValidator.setIcon(new ImageView("assets/images/alert.png"));
        requiredFieldValidator.setMessage("Empty!");
        validator.setMessage("Choose another name!");
        name.getValidators().add(validator);
        name.getValidators().add(requiredFieldValidator);
    }

    @Override
    public void init(ApplicationController applicationController, LayoutGraph layoutGraph) {
        super.init(applicationController, layoutGraph);
        if (!layoutGraph.getStateStart().getName().equals("")) {
            start.setDisable(true);
        }
    }

    @FXML
    public void validate() {
        name.validate();
    }

    private class ValidatorState extends ValidatorBase {

        @Override
        protected void eval() {
            if (layoutGraph.findStateByName(name.getText()) != null) {
                hasErrors.set(true);
            } else {
                hasErrors.set(false);
            }
        }
    }
}
