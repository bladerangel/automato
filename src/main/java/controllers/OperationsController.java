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

    public void foundSuccessors(State state) {
        if (cloneLayoutGraph.getStatesSuccessors(state) != null) {
            cloneLayoutGraph
                    .getStatesSuccessors(state)
                    .forEach(stateSuccessors -> {
                        if (!statesAccessible.contains(stateSuccessors)) {
                            statesAccessible.add(stateSuccessors);
                            foundSuccessors(stateSuccessors);
                        }
                    });
        }
    }

    public void foundPredecessors(State state) {
        if (cloneLayoutGraph.getStatesPredecessors(state) != null) {
            cloneLayoutGraph
                    .getStatesPredecessors(state)
                    .forEach(statePredecessors -> {
                        if (!statesCoaccessible.contains(statePredecessors)) {
                            statesCoaccessible.add(statePredecessors);
                            foundPredecessors(statePredecessors);
                        }
                    });
        }
    }

    public void setStatesAccessible() {
        if (!statesAccessible.contains(cloneLayoutGraph.getStateStart()))
            statesAccessible.add(cloneLayoutGraph.getStateStart());
    }

    public void setStatesCoaccessible() {
        cloneLayoutGraph
                .getAllStatesMarked()
                .forEach(state -> {
                    if (!statesCoaccessible.contains(state)) {
                        statesCoaccessible.add(state);
                    }
                });
    }

    public void foundAndSetTrim() {
        cloneLayoutGraph
                .getAllStates()
                .stream()
                .filter(state -> (statesAccessible.contains(state) && statesCoaccessible.contains(state)))
                .forEach(state -> statesTrim.add(state));
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

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void createAndSetSwingContent() {

        swingNode.setContent(cloneLayoutGraph.changeBasicVisualizationServer());
        cloneLayoutGraph = LayoutGraph.cloneGraph(layoutGraph);
    }

    @Override
    public void init(ApplicationController applicationController, LayoutGraph layoutGraph) {
        super.init(applicationController, layoutGraph);
        statesAccessible = new ArrayList<>();
        statesCoaccessible = new ArrayList<>();
        statesTrim = new ArrayList<>();
        cloneLayoutGraph = LayoutGraph.cloneGraph(layoutGraph);

        foundSuccessors(cloneLayoutGraph.getStateStart());
        setStatesAccessible();

        cloneLayoutGraph
                .getAllStatesMarked()
                .forEach(state -> foundPredecessors(state));
        setStatesCoaccessible();

        foundAndSetTrim();
        swingNode = new SwingNode();
        pane.getChildren().add(swingNode);
        createAndSetSwingContent();
    }

}
