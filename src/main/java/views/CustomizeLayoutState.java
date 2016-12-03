package views;

import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.visualization.RenderContext;
import edu.uci.ics.jung.visualization.renderers.Renderer;
import edu.uci.ics.jung.visualization.transform.shape.GraphicsDecorator;
import models.Event;
import models.State;

import java.awt.*;
import java.awt.geom.*;

/**
 * Created by Rangel on 02/12/2016.
 */
public class CustomizeLayoutState implements Renderer.Vertex<State, Event> {

    private Color color;
    private GraphicsDecorator graphicsContext;
    private Point2D center;

    @Override
    public void paintVertex(RenderContext<State, Event> renderContext, Layout<State, Event> layout, State state) {
        graphicsContext = renderContext.getGraphicsContext();
        center = layout.transform(state);

        color = Color.GREEN;
        if (state.isStart()) {
            color = Color.RED;
        }
        graphicsContext.setPaint(color);
        graphicsContext.fill(drawCircle(10));

        graphicsContext.setPaint(Color.BLACK);
        graphicsContext.draw(drawCircle(10));

        if (state.isMarked()) {
            graphicsContext.setPaint(Color.BLACK);
            graphicsContext.draw(drawCircle(15));
        }
    }

    private Ellipse2D drawCircle(double place) {
        return new Ellipse2D.Double(center.getX() - place, center.getY() - place, place * 2, place * 2);
    }

}
