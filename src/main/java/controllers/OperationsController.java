package controllers;

import javafx.embed.swing.SwingNode;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.Pane;
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
    private Pane pane;

    private SwingNode swingNode;

    private LayoutGraph cloneLayoutGraph;

    private Collection<State> statesAccessible;

    private Collection<State> statesCoaccessible;

    private Collection<State> statesTrim;

    public void foundSuccessors(Collection<State> states, State state) {
        if (cloneLayoutGraph.getStatesSuccessors(state) != null) {
            cloneLayoutGraph.getStatesSuccessors(state).forEach(stateSuccessors -> {
                if (!states.contains(stateSuccessors)) {
                    states.add(stateSuccessors);
                    foundSuccessors(states, stateSuccessors);
                }
            });
        }
    }

    public void foundPredecessors(Collection<State> states, State state) {
        if (cloneLayoutGraph.getStatesPredecessors(state) != null) {
            cloneLayoutGraph.getStatesPredecessors(state).forEach(statePredecessors -> {
                if (!states.contains(statePredecessors)) {
                    states.add(statePredecessors);
                    foundPredecessors(states, statePredecessors);
                }
            });
        }
    }

    public void foundTrim(Collection<State> statesTrim, Collection<State> statesAccessible, Collection<State> statesCoaccessible) {
        LayoutGraph.cloneGraph(cloneLayoutGraph).getAllStates().stream().filter(state -> (statesAccessible.contains(state) && statesCoaccessible.contains(state))).forEach(state -> {
            statesTrim.add(state);
        });
    }

    @FXML
    void accessible() {
        LayoutGraph.cloneGraph(cloneLayoutGraph).getAllStates().stream().filter(state -> !statesAccessible.contains(state) && !state.isStart()).forEach(state -> {
            cloneLayoutGraph.removeState(state);
        });
        createAndSetSwingContent();
    }

    @FXML
    void coaccessible() {
        LayoutGraph.cloneGraph(cloneLayoutGraph).getAllStates().stream().filter(state -> !statesCoaccessible.contains(state)).forEach(state -> {
            cloneLayoutGraph.removeState(state);
        });
        createAndSetSwingContent();
    }

    @FXML
    void trim() {
        LayoutGraph.cloneGraph(cloneLayoutGraph).getAllStates().stream().filter(state -> !statesTrim.contains(state)).forEach(state -> {
            cloneLayoutGraph.removeState(state);
        });
        createAndSetSwingContent();
    }

    @FXML
    void complement() {
        LayoutGraph.cloneGraph(cloneLayoutGraph).getAllStates().stream().filter(state -> !statesTrim.contains(state)).forEach(state -> {
            cloneLayoutGraph.removeState(state);
        });
        State state = new State("d");
        cloneLayoutGraph.addState(state);
        cloneLayoutGraph.getAllStates().forEach(state1 -> {
            state1.setMarked(!state1.isMarked());
            Event event = new Event(state1.getName() + "-" + state.getName(), state1, state);
            cloneLayoutGraph.addEvent(event, state1, state);
        });
        createAndSetSwingContent();
    }

    @FXML
    void testCase() {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void createAndSetSwingContent() {
        //SwingUtilities.invokeLater(() -> {
        swingNode.setContent(cloneLayoutGraph.changeBasicVisualizationServer());
        cloneLayoutGraph = LayoutGraph.cloneGraph(layoutGraph);
        //});
    }

    @Override
    public void init(ApplicationController applicationController, LayoutGraph layoutGraph) {
        super.init(applicationController, layoutGraph);
        statesAccessible = new ArrayList<>();
        statesCoaccessible = new ArrayList<>();
        statesTrim = new ArrayList<>();
        cloneLayoutGraph = LayoutGraph.cloneGraph(layoutGraph);
        foundSuccessors(statesAccessible, cloneLayoutGraph.getStateStart());
        cloneLayoutGraph.getAllStatesMarked().forEach(state -> {
            foundPredecessors(statesCoaccessible, state);
        });
        foundTrim(statesTrim, statesAccessible, statesCoaccessible);
        swingNode = new SwingNode();
        pane.getChildren().add(swingNode);
        createAndSetSwingContent();
    }

}
