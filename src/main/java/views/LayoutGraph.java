package views;

import java.awt.Color;
import java.awt.Dimension;

import edu.uci.ics.jung.algorithms.layout.CircleLayout;
import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.graph.DirectedSparseMultigraph;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.visualization.BasicVisualizationServer;
import edu.uci.ics.jung.visualization.decorators.ToStringLabeller;
import edu.uci.ics.jung.visualization.renderers.Renderer.VertexLabel.Position;
import models.Event;
import models.State;

public class LayoutGraph {

    private Graph<State, Event> graph;
    private Layout<State, Event> layout;
    private BasicVisualizationServer<State, Event> basicVisualizationServer;
    private CustomizeLayoutState customizeLayoutState;

    public LayoutGraph() {
        graph = new DirectedSparseMultigraph<>();
        customizeLayoutState = new CustomizeLayoutState();
    }

    public BasicVisualizationServer<State, Event> changeBasicVisualizationServer() {
        layout = new CircleLayout<>(graph);
        layout.setSize(new Dimension(500, 500));
        basicVisualizationServer = new BasicVisualizationServer<>(layout);
        basicVisualizationServer.setPreferredSize(new Dimension(500, 500));
        basicVisualizationServer.setBackground(Color.decode("#90A4AE"));

        basicVisualizationServer.getRenderer().setVertexRenderer(customizeLayoutState);
        basicVisualizationServer.getRenderContext().setVertexLabelTransformer(new ToStringLabeller<>());
        basicVisualizationServer.getRenderContext().setEdgeLabelTransformer(new ToStringLabeller<>());
        basicVisualizationServer.getRenderer().getVertexLabelRenderer().setPosition(Position.CNTR);
        return basicVisualizationServer;
    }

    public Graph<State, Event> getGraph() {
        return graph;
    }

}
