package controllers;

import javafx.embed.swing.SwingNode;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.Pane;
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

    @FXML
    void accessible() {
        Collection<State> states = new ArrayList<>();
        foundSuccessors(states, cloneLayoutGraph.getStateStart());
        LayoutGraph.cloneGraph(cloneLayoutGraph).getAllStates().stream().filter(state -> !states.contains(state) && !state.isStart()).forEach(state -> {
            cloneLayoutGraph.removeState(state);
        });
        createAndSetSwingContent();
    }

    @FXML
    void coaccessible() {

    }

    @FXML
    void trim() {

    }

    @FXML
    void complement() {

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
        //});
    }

    @Override
    public void init(ApplicationController applicationController, LayoutGraph layoutGraph) {
        super.init(applicationController, layoutGraph);
        cloneLayoutGraph = LayoutGraph.cloneGraph(layoutGraph);
        swingNode = new SwingNode();
        pane.getChildren().add(swingNode);
        createAndSetSwingContent();
    }

}
