package controllers;

import java.awt.event.ActionListener;
import java.util.Collection;

import models.Event;
import models.State;
import views.LayoutGraph;
import views.Window;

public abstract class WindowAbstractController implements ActionListener {

	protected Window window;
	private LayoutGraph layoutGraph;

	public WindowAbstractController(Window window, LayoutGraph layoutGraph) {
		this.window = window;
		this.layoutGraph = layoutGraph;
		window.actionListeners(this);
	}

	public void messageLog(String log) {
		window.getTextAreaLog().append(log + "\n");
	}

	public void refreshGraph(String log) {
		window.getPanelGraph().removeAll();
		window.getPanelGraph().add(layoutGraph.changeBasicVisualizationServer());
		window.getPanelGraph().validate();
		window.getPanelGraph().repaint();
		messageLog(log);
	}

	private boolean containsState(State state) {
		for (State s : layoutGraph.getGraph().getVertices()) {
			if (s.toString().equals(state.toString())) {
				return true;
			}
		}
		return false;
	}

	public boolean addStateGraph(State state) {
		if (!containsState(state)) {
			layoutGraph.getGraph().addVertex(state);
			return true;
		}
		return false;
	}

	public void removeStateGraph(State state) {
		layoutGraph.getGraph().removeVertex(state);
	}

	public void removeEventGraph(Event event) {
		layoutGraph.getGraph().removeEdge(event);
	}

	public void addEventGraph(Event event, State state1, State state2) {
		layoutGraph.getGraph().addEdge(event, state1, state2);
	}

	public Collection<State> getAllStates() {
		return layoutGraph.getGraph().getVertices();
	}

	public Collection<State> getAllStatesByEvent(Event event) {
		return layoutGraph.getGraph().getIncidentVertices(event);
	}

	public Collection<Event> getAllEvents() {
		return layoutGraph.getGraph().getEdges();
	}

	public Collection<Event> getAllEventByState(State state) {
		return layoutGraph.getGraph().getOutEdges(state);
	}

	public Event findEvent(State state1, State state2) {
		return layoutGraph.getGraph().findEdge(state1, state2);
	}

	public State findStateByName(String stateName) {
		for (State state : getAllStates()) {
			if (state.toString().equals(stateName)) {
				return state;
			}
		}
		return null;
	}
}
