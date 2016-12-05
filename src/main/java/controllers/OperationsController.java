package controllers;

import javafx.embed.swing.SwingNode;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.Pane;
import models.Event;
import models.State;
import views.LayoutGraph;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * Created by Rangel on 03/12/2016.
 */
public class OperationsController extends AbstractController implements Initializable {

    @FXML
    private Pane pane;

    private SwingNode swingNode;

    private LayoutGraph cloneLayoutGraph;

    public boolean containsEventsIn(State state) {
        for (Event event : cloneLayoutGraph.getAllEventByStateIn(state)) {
            if (cloneLayoutGraph.getStateSource(event) != state)
                return true;
        }
        return false;
    }

    @FXML
    void accessible() {
        for (State state : LayoutGraph.cloneGraph(cloneLayoutGraph).getAllStates()) {
            if (!state.isStart() && !containsEventsIn(state))
                cloneLayoutGraph.removeState(state);
        }
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
        cloneLayoutGraph.getAllStates().forEach(state -> {
            System.out.println(state.getName()+cloneLayoutGraph.getStatesPredecessor(state).stream().map(State::getName).collect(Collectors.toList()));
            System.out.println(state.getName()+cloneLayoutGraph.getStatesSuccessors(state).stream().map(State::getName).collect(Collectors.toList()));
        });

        swingNode = new SwingNode();
        pane.getChildren().add(swingNode);
        createAndSetSwingContent();
    }

}
