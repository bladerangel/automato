package controllers;

import services.ComboBoxService;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.RequiredFieldValidator;
import com.jfoenix.validation.base.ValidatorBase;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import models.Event;
import models.State;
import views.LayoutGraph;

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

    private ValidatorEvent validator;

    private RequiredFieldValidator requiredFieldValidator;

    private ComboBoxService comboBoxService;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        validator = new ValidatorEvent();
        requiredFieldValidator = new RequiredFieldValidator();
        validator.setIcon(new ImageView("assets/images/alert.png"));
        requiredFieldValidator.setIcon(new ImageView("assets/images/alert.png"));
        requiredFieldValidator.setMessage("Empty");
        validator.setMessage("Choose another name!");
        name.getValidators().add(requiredFieldValidator);
        name.getValidators().add(validator);
    }

    @Override
    public void init(ApplicationController applicationController, LayoutGraph graph) {
        super.init(applicationController, graph);
        comboBoxService = new ComboBoxService();
        states1.getItems().addAll(layoutGraph.getAllStates());
        states2.getItems().addAll(layoutGraph.getAllStates());
        states1.setCellFactory(param -> comboBoxService.newListState());
        states1.setConverter(comboBoxService.newConverterState());
        states2.setCellFactory(param -> comboBoxService.newListState());
        states2.setConverter(comboBoxService.newConverterState());
    }

    @FXML
    public void validate() {
        name.validate();
    }

    @FXML
    void save() {

        State state1 = states1.getSelectionModel().getSelectedItem();
        State state2 = states2.getSelectionModel().getSelectedItem();
        Event event = new Event(name.getText(), state1, state2);
        if (name.validate() && applicationController.addEventGraph(event, state1, state2)) {
            Stage stage = (Stage) pane.getScene().getWindow();
            stage.close();
        }
    }

    @FXML
    void changeState1() {
        name.validate();
    }

    private class ValidatorEvent extends ValidatorBase {

        @Override
        protected void eval() {
            State state1 = states1.getSelectionModel().getSelectedItem();
            if (state1 != null) {
                boolean contains = false;
                for (Event event : layoutGraph.findEventsByName(name.getText())) {
                    if (layoutGraph.getStateSource(event).equals(state1)) {
                        contains = true;
                        break;
                    }
                }
                if (contains) {
                    hasErrors.set(true);
                } else {
                    hasErrors.set(false);
                }

            }
        }
    }
}
