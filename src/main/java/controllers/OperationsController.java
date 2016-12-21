package controllers;

import javafx.embed.swing.SwingNode;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import models.Event;
import models.State;
import views.LayoutGraph;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.ResourceBundle;

/**
 * Created by Rangel on 03/12/2016.
 */
public class OperationsController extends AbstractController implements Initializable {

    @FXML
    private Pane pane1;

    private SwingNode swingNode;

    private LayoutGraph cloneLayoutGraph;

    private Collection<State> statesAccessible;

    private Collection<State> statesCoaccessible;

    private Collection<State> statesTrim;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @Override
    public void init(ApplicationController applicationController, LayoutGraph layoutGraph) {
        super.init(applicationController, layoutGraph);
        statesAccessible = new ArrayList<>();
        statesCoaccessible = new ArrayList<>();
        statesTrim = new ArrayList<>();
        cloneLayoutGraph = LayoutGraph.cloneGraph(layoutGraph);

        foundSuccessors(cloneLayoutGraph.getStateStart());
        cloneLayoutGraph
                .getAllStatesMarked()
                .forEach(state -> foundPredecessors(state));
        foundTrim();
        swingNode = new SwingNode();
        pane1.getChildren().add(swingNode);
        createAndSetSwingContent();
    }

    @FXML
    public void accessible() {
        LayoutGraph
                .cloneGraph(cloneLayoutGraph)
                .getAllStates()
                .stream()
                .filter(state -> !statesAccessible.contains(state))
                .forEach(state -> cloneLayoutGraph.removeState(state));

        createAndSetSwingContent();
    }

    @FXML
    public void coaccessible() {
        LayoutGraph
                .cloneGraph(cloneLayoutGraph)
                .getAllStates()
                .stream()
                .filter(state -> !statesCoaccessible.contains(state))
                .forEach(state -> cloneLayoutGraph.removeState(state));

        createAndSetSwingContent();
    }

    @FXML
    public void trim() {
        LayoutGraph
                .cloneGraph(cloneLayoutGraph)
                .getAllStates()
                .stream()
                .filter(state -> !statesTrim.contains(state))
                .forEach(state -> cloneLayoutGraph.removeState(state));

        createAndSetSwingContent();
    }

    @FXML
    public void complement() {
        LayoutGraph
                .cloneGraph(cloneLayoutGraph)
                .getAllStates()
                .stream()
                .filter(state -> !statesTrim.contains(state))
                .forEach(state -> cloneLayoutGraph.removeState(state));

        State state = new State("d");
        cloneLayoutGraph.addState(state);

        cloneLayoutGraph
                .getAllStates()
                .forEach(stateMarked -> {
                    stateMarked.setMarked(!stateMarked.isMarked());
                    Event event = new Event(stateMarked.getName() + "-" + state.getName(), stateMarked, state);
                    cloneLayoutGraph.addEvent(event, stateMarked, state);
                });

        createAndSetSwingContent();
    }

    @FXML
    public void original() {
        createAndSetSwingContent();
    }

    public void createAndSetSwingContent() {

        swingNode.setContent(cloneLayoutGraph.changeBasicVisualizationServer());
        cloneLayoutGraph = LayoutGraph.cloneGraph(layoutGraph);
    }

    public void foundSuccessors(State state) {
        if (!statesAccessible.contains(state) && cloneLayoutGraph.getStatesSuccessors(state) != null) {
            statesAccessible.add(state);
            cloneLayoutGraph
                    .getStatesSuccessors(state)
                    .forEach(stateSuccessors -> foundSuccessors(stateSuccessors));
        }
    }

    public void foundPredecessors(State state) {
        if (!statesCoaccessible.contains(state) && cloneLayoutGraph.getStatesPredecessors(state) != null) {
            statesCoaccessible.add(state);
            cloneLayoutGraph
                    .getStatesPredecessors(state)
                    .forEach(statePredecessors -> foundPredecessors(statePredecessors));
        }
    }

    public void foundTrim() {
        cloneLayoutGraph
                .getAllStates()
                .stream()
                .filter(state -> (statesAccessible.contains(state) && statesCoaccessible.contains(state)))
                .forEach(state -> statesTrim.add(state));
    }

}
