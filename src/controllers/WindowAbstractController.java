package controllers;

import java.awt.event.ActionListener;

import models.State;
import views.LayoutGraph;
import views.Window;

public abstract class WindowAbstractController implements ActionListener {

	protected Window window;
	protected LayoutGraph layoutGraph;

	public WindowAbstractController(Window window, LayoutGraph layoutGraph) {
		this.window = window;
		this.layoutGraph = layoutGraph;
		window.actionListeners(this);
	}

	public void refreshGraph() {
		window.getPanelGraph().removeAll();
		window.getPanelGraph().add(layoutGraph.changeBasicVisualizationServer());
		window.getPanelGraph().validate();
		window.getPanelGraph().repaint();
		// textArea.append(layout.getGrafo().printGrafo());
	}

	public boolean containsState(State state) {
		for (State s : layoutGraph.getGraph().getVertices()) {
			if (s.toString().equals(state.toString())) {
				return true;
			}
		}
		return false;
	}
}
