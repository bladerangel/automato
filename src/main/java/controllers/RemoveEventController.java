package controllers;

import com.jfoenix.controls.JFXComboBox;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import models.Event;
import models.State;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Rangel on 22/11/2016.
 */
public class RemoveEventController extends AbstractController implements Initializable {

    @FXML
    private AnchorPane pane;

    @FXML
    private JFXComboBox<Event> events;

    @FXML
    private JFXComboBox<State> states1;

    @FXML
    private JFXComboBox<State> states2;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @Override
    public void init(ApplicationController applicationController) {
        super.init(applicationController);
        states1.getItems().addAll(applicationController.getAllStates());

    }

    @FXML
    void changeEvent() {
        Event event = events.getSelectionModel().getSelectedItem();
        State state1 = states1.getSelectionModel().getSelectedItem();
        states2.getSelectionModel().select(applicationController.findStateByStateAndEvent(state1, event));
    }


    @FXML
    void changeState1() {
        states2.getItems().clear();
        events.getItems().clear();
        State state1 = states1.getSelectionModel().getSelectedItem();
        applicationController.getAllEventByState(state1).forEach(event -> {
            events.getItems().add(event);
            State state2 = applicationController.findStateByStateAndEvent(state1, event);
            if (!states2.getItems().contains(state2)) {
                states2.getItems().add(state2);
            }
        });

        states2.getSelectionModel().selectFirst();
        State state2 = states2.getSelectionModel().getSelectedItem();
        events.getSelectionModel().select(applicationController.findEvent(state1, state2));
    }

    @FXML
    void changeState2() {
        State state1 = states1.getSelectionModel().getSelectedItem();
        State state2 = states2.getSelectionModel().getSelectedItem();
        events.getSelectionModel().select(applicationController.findEvent(state1, state2));
    }

    @FXML
    void remove() {
        Event event = events.getSelectionModel().getSelectedItem();
        if (applicationController.removeEventGraph(event)) {
            Stage stage = (Stage) pane.getScene().getWindow();
            stage.close();
        }
    }


}
