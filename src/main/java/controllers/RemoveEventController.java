package controllers;

import services.ComboBoxService;
import com.jfoenix.controls.JFXComboBox;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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
public class RemoveEventController extends AbstractController implements Initializable {

    @FXML
    private AnchorPane pane;

    @FXML
    private JFXComboBox<Event> events;

    @FXML
    private JFXComboBox<State> states1;

    @FXML
    private JFXComboBox<State> states2;

    private ComboBoxService comboBoxService;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @Override
    public void init(ApplicationController applicationController, LayoutGraph layoutGraph) {
        super.init(applicationController, layoutGraph);
        comboBoxService = new ComboBoxService();
        states1.getItems().addAll(layoutGraph.getAllStates());
        states1.setCellFactory(param -> comboBoxService.newListState());
        states1.setConverter(comboBoxService.newConverterState());
        states2.setCellFactory(param -> comboBoxService.newListState());
        states2.setConverter(comboBoxService.newConverterState());
        events.setCellFactory(param -> comboBoxService.newListEvent());
        events.setConverter(comboBoxService.newConverterEvent());

    }

    @FXML
    public void changeEvent() {
        Event event = events.getSelectionModel().getSelectedItem();
        states2.getSelectionModel().select(layoutGraph.getStateDest(event));
    }


    @FXML
    public void changeState1() {
        states2.getItems().clear();
        events.getItems().clear();
        State state1 = states1.getSelectionModel().getSelectedItem();
        layoutGraph
                .getAllEventByStateOut(state1)
                .forEach(event -> {
                    events.getItems().add(event);
                    State state2 = layoutGraph.getStateDest(event);
                    if (!states2.getItems().contains(state2)) {
                        states2.getItems().add(state2);
                    }
                });

        states2.getSelectionModel().selectFirst();
        State state2 = states2.getSelectionModel().getSelectedItem();
        events.getSelectionModel().select(layoutGraph.findEvent(state1, state2));
    }

    @FXML
    public void changeState2() {
        State state1 = states1.getSelectionModel().getSelectedItem();
        State state2 = states2.getSelectionModel().getSelectedItem();
        events.getSelectionModel().select(layoutGraph.findEvent(state1, state2));
    }

    @FXML
    public void remove() {
        Event event = events.getSelectionModel().getSelectedItem();
        if (applicationController.removeEventGraph(event)) {
            Stage stage = (Stage) pane.getScene().getWindow();
            stage.close();
        }
    }


}
