package views;

import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.visualization.RenderContext;
import edu.uci.ics.jung.visualization.renderers.Renderer;
import edu.uci.ics.jung.visualization.transform.shape.GraphicsDecorator;
import models.Event;
import models.State;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;

/**
 * Created by Rangel on 02/12/2016.
 */
public class CustomizeLayoutState implements Renderer.Vertex<State, Event> {


    @Override
    public void paintVertex(RenderContext<State, Event> renderContext, Layout<State, Event> layout, State state) {
        GraphicsDecorator graphicsContext = renderContext.getGraphicsContext();
        Point2D center = layout.transform(state);
        Shape shape = new Ellipse2D.Double((int) center.getX() - 10, (int) center.getY() - 10, 20, 20);
        Color color = Color.GREEN;
        graphicsContext.setPaint(color);
        graphicsContext.fill(shape);

        shape = new Ellipse2D.Double(center.getX() - 10, center.getY() - 10, 20, 20);
        graphicsContext.setStroke(new BasicStroke(1));
        graphicsContext.setPaint(Color.BLACK);
        graphicsContext.draw(shape);

        if (state.isMarked()) {
            shape = new Ellipse2D.Double(center.getX() - 15, center.getY() - 15, 30, 30);
            graphicsContext.setStroke(new BasicStroke(1));
            graphicsContext.setPaint(Color.BLACK);
            graphicsContext.draw(shape);
        }

    }
}
