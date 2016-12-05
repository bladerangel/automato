package views;

import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

import edu.uci.ics.jung.algorithms.layout.CircleLayout;
import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.graph.DirectedSparseMultigraph;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.util.Pair;
import edu.uci.ics.jung.visualization.BasicVisualizationServer;
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
        basicVisualizationServer.getRenderContext().setVertexLabelTransformer(state -> state.getName());
        basicVisualizationServer.getRenderContext().setEdgeLabelTransformer(event -> event.getLinkName());
        basicVisualizationServer.getRenderer().getVertexLabelRenderer().setPosition(Position.CNTR);
        return basicVisualizationServer;
    }

    public void addState(State state) {
        graph.addVertex(state);
    }

    public void removeState(State state) {
        graph.removeVertex(state);
    }

    public Collection<State> getAllStates() {
        return graph.getVertices();
    }

    public State getStateStart() {
        return getAllStates().stream().filter(state -> state.isStart()).findFirst().orElse(new State(""));
    }

    public Collection<State> getAllStatesMarked() {
        return getAllStates().stream().filter(state -> state.isMarked()).collect(Collectors.toList());
    }

    public State findStateByName(String stateName) {
        for (State state : getAllStates()) {
            if (state.getName().equals(stateName)) {
                return state;
            }
        }

        return null;
    }

    public Collection<Event> findEventsByName(String eventName) {
        Collection<Event> events = new ArrayList<>();
        for (Event event : getAllEvents()) {
            if (event.getLinkName().equals(eventName)) {
                events.add(event);
            }
        }
        return events;
    }

    public void addEvent(Event event, State state1, State state2) {
        graph.addEdge(event, state1, state2);
    }

    public void removeEvent(Event event) {
        graph.removeEdge(event);
    }

    public Collection<Event> getAllEvents() {
        return graph.getEdges();
    }

    public Event findEvent(State state1, State state2) {
        return graph.findEdge(state1, state2);
    }

    public Collection<Event> getAllEventByStateIn(State state) {
        return graph.getInEdges(state);
    }

    public Collection<Event> getAllEventByStateOut(State state) {
        return graph.getOutEdges(state);
    }

    public State findStateByStateAndEvent(State state, Event event) {
        return graph.getOpposite(state, event);
    }

    public State getStateSource(Event event) {
        return graph.getSource(event);
    }

    public State getStateDest(Event event) {
        return graph.getDest(event);
    }

    public Pair<State> getAllStatesByEvent(Event event) {
        //return graph.getIncidentVertices(event);
        return graph.getEndpoints(event);

    }

    public static LayoutGraph cloneGraph(LayoutGraph layoutGraph) {
        LayoutGraph cloneLayoutGraph = new LayoutGraph();
        layoutGraph.getAllStates().forEach(state -> cloneLayoutGraph.addState(state));
        layoutGraph.getAllEvents().forEach(event -> {
            cloneLayoutGraph.addEvent(event, event.getStateInit(), event.getStateFinal());
        });
        return cloneLayoutGraph;
    }

    public void removeAllGraph() {
        graph = new DirectedSparseMultigraph<>();
    }

    public State findStateTable(State state, Event event) {
        for (Event eventFind : findEventsByName(event.getLinkName())) {
            if (graph.isIncident(state, eventFind)) {
                State des = getStateDest(eventFind);
                if (findStateByStateAndEvent(state, eventFind).equals(des)) {
                    return des;
                }
            }
        }
        return null;
    }

    public Collection<State> getStatesPredecessor(State state) {
        return graph.getPredecessors(state);
    }

    public Collection<State> getStatesSuccessors(State state) {
        return graph.getSuccessors(state);
    }
}
