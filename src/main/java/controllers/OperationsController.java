package controllers;

import javafx.embed.swing.SwingNode;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.Pane;
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

    private LayoutGraph cloneLayoutGraph;

    @FXML
    void accessible() {
        LayoutGraph.cloneGraph(layoutGraph).getAllStates().forEach(state -> {
            if (state.isAccessible()) {
                LayoutGraph.cloneGraph(layoutGraph).getStatesPredecessor(state).forEach(statePre -> {
                    if (!statePre.isAccessible()) {
                        cloneLayoutGraph.removeState(statePre);
                    }
                });
            } else {
                cloneLayoutGraph.removeState(state);
            }
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
