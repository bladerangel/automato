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

/**
 * Created by Rangel on 03/12/2016.
 */
public class OperationsController extends AbstractController implements Initializable {

    @FXML
    private Pane pane;

    private SwingNode swingNode;

    private LayoutGraph layoutGraph;

    public boolean containsEventsIn(State state) {
        for (Event event : layoutGraph.getAllEventByStateIn(state)) {
            if (layoutGraph.getStateSource(event) != state)
                return true;
        }
        return false;
    }

    @FXML
    void accessible() {
        for (State state : LayoutGraph.cloneGraph(layoutGraph).getAllStates()) {
            if (!state.isStart() && !containsEventsIn(state))
                layoutGraph.removeState(state);
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
        swingNode.setContent(layoutGraph.changeBasicVisualizationServer());
        //});
    }

    @Override
    public void init(ApplicationController applicationController) {
        super.init(applicationController);
        layoutGraph = LayoutGraph.cloneGraph(applicationController.getLayoutGraph());
        swingNode = new SwingNode();
        pane.getChildren().add(swingNode);
        createAndSetSwingContent();
    }

}
