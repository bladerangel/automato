package views;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Paint;

import org.apache.commons.collections15.Transformer;

import edu.uci.ics.jung.algorithms.layout.CircleLayout;
import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.graph.DirectedSparseMultigraph;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.visualization.BasicVisualizationServer;
import edu.uci.ics.jung.visualization.decorators.ToStringLabeller;
import edu.uci.ics.jung.visualization.renderers.Renderer.VertexLabel.Position;
import models.Event;
import models.State;

import javax.swing.*;
import javax.swing.border.Border;


public class LayoutGraph {

    private Graph<State, Event> graph;
    private Layout<State, Event> layout;
    private BasicVisualizationServer<State, Event> basicVisualizationServer;
    private Transformer<State, Paint> statePaint;

    public LayoutGraph() {
        graph = new DirectedSparseMultigraph<>();
        statePaint = state -> Color.GREEN;
    }

    public BasicVisualizationServer<State, Event> changeBasicVisualizationServer() {
        layout = new CircleLayout<>(graph);
        layout.setSize(new Dimension(450, 450));
        basicVisualizationServer = new BasicVisualizationServer<>(layout);
        basicVisualizationServer.setPreferredSize(new Dimension(500, 500));
        basicVisualizationServer.setBackground(Color.decode("#90A4AE"));
		/*
         * float dash[] = { 10.0f }; final Stroke edgeStroke = new
		 * BasicStroke(1.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER,
		 * 10.0f, dash, 0.0f); Transformer<Event, Stroke> edgeStrokeTransformer
		 * = new Transformer<Event, Stroke>() { public Stroke transform(Event s)
		 * { return edgeStroke; } };
		 */

        basicVisualizationServer.getRenderContext().setVertexFillPaintTransformer(statePaint);
        // basicVisualizationServer.getRenderContext().setEdgeStrokeTransformer(edgeStrokeTransformer);
        basicVisualizationServer.getRenderContext().setVertexLabelTransformer(new ToStringLabeller<State>());
        basicVisualizationServer.getRenderContext().setEdgeLabelTransformer(new ToStringLabeller<Event>());
        basicVisualizationServer.getRenderer().getVertexLabelRenderer().setPosition(Position.CNTR);
        return basicVisualizationServer;
    }

    public Graph<State, Event> getGraph() {
        return graph;
    }

}
