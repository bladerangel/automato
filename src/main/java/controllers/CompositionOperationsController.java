package controllers;

import javafx.embed.swing.SwingNode;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.Pane;
import models.Event;
import models.State;
import views.LayoutGraph;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.ResourceBundle;

/**
 * Created by Rangel on 03/12/2016.
 */
public class CompositionOperationsController extends AbstractController implements Initializable {

    @FXML
    private Pane pane1;

    private SwingNode swingNode;

    private LayoutGraph cloneLayoutGraph;

    private LayoutGraph cloneLayoutGraph1;
    private LayoutGraph cloneLayoutGraph2;

    private Collection<State> statesAccessible1;
    private Collection<State> statesAccessible2;

    private Collection<Event> eventsAccessible1;
    private Collection<Event> eventsAccessible2;

    private Collection<Event> notConnect1;
    private Collection<Event> notConnect2;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @Override
    public void init(ApplicationController applicationController, LayoutGraph layoutGraph1, LayoutGraph layoutGraph2) {
        super.init(applicationController, layoutGraph1, layoutGraph2);
        statesAccessible1 = new ArrayList<>();
        statesAccessible2 = new ArrayList<>();
        notConnect1 = new ArrayList<>();
        notConnect2 = new ArrayList<>();
        eventsAccessible1 = new ArrayList<>();
        eventsAccessible2 = new ArrayList<>();

        cloneLayoutGraph = new LayoutGraph();

        cloneLayoutGraph1 = LayoutGraph.cloneGraph(layoutGraph1);
        cloneLayoutGraph2 = LayoutGraph.cloneGraph(layoutGraph2);

        swingNode = new SwingNode();
        pane1.getChildren().add(swingNode);
        foundSuccessors1(cloneLayoutGraph1.getStateStart());
        foundSuccessors2(cloneLayoutGraph2.getStateStart());
        multiplication();

    }

    @FXML
    public void multiplication() {
        addStatesInit();

        State stateAnterior1 = cloneLayoutGraph1.getStateStart();
        State stateAnterior2 = cloneLayoutGraph2.getStateStart();

        for (Event event1 : eventsAccessible1) {
            for (Event event2 : eventsAccessible2) {
                if (event1.getLinkName().equals(event2.getLinkName()) && event1.getStateInit().equals(stateAnterior1) && event2.getStateInit().equals(stateAnterior2)) {
                    State state1 = cloneLayoutGraph.findStateByName(event1.getStateInit().getName() + "," + event2.getStateInit().getName());
                    State state2 = cloneLayoutGraph.findStateByName(event1.getStateFinal().getName() + "," + event2.getStateFinal().getName());
                    Event event = new Event(event1.getLinkName(), state1, state2);
                    cloneLayoutGraph.addEvent(event, state1, state2);
                    stateAnterior1 = event1.getStateFinal();
                    stateAnterior2 = event2.getStateFinal();
                }
            }
        }

        LayoutGraph.cloneGraph(cloneLayoutGraph).getAllStates().forEach(state -> {
            if (cloneLayoutGraph.getAllEventByStateOut(state).isEmpty() && cloneLayoutGraph.getAllEventByStateIn(state).isEmpty()) {
                cloneLayoutGraph.removeState(state);
            }
        });

        createAndSetSwingContent();
    }

    @FXML
    public void parallel() {
        addStatesInit();

        cloneLayoutGraph1.getAllEvents().forEach(event1 ->
                cloneLayoutGraph2.getAllEvents().forEach(event2 -> {
                    if (event1.getLinkName().equals(event2.getLinkName())) {
                        State state1 = cloneLayoutGraph.findStateByName(event1.getStateInit().getName() + "," + event2.getStateInit().getName());
                        State state2 = cloneLayoutGraph.findStateByName(event1.getStateFinal().getName() + "," + event2.getStateFinal().getName());
                        if (state1 != null && state2 != null) {
                            Event event = new Event(event1.getLinkName(), state1, state2);
                            cloneLayoutGraph.addEvent(event, state1, state2);
                        }

                    }
                })
        );

        setNotConnect();
        createAndSetSwingContent();

    }

    public void createAndSetSwingContent() {
        swingNode.setContent(cloneLayoutGraph.changeBasicVisualizationServer());
        cloneLayoutGraph = new LayoutGraph();
        notConnect1 = new ArrayList<>();
        notConnect2 = new ArrayList<>();
    }

    public void foundSuccessors1(State state) {
        if (!statesAccessible1.contains(state) && cloneLayoutGraph1.getStatesSuccessors(state) != null) {
            statesAccessible1.add(state);
            cloneLayoutGraph1
                    .getStatesSuccessors(state)
                    .forEach(stateSuccessors -> {
                        eventsAccessible1.add(cloneLayoutGraph1.findEvent(state, stateSuccessors));
                        foundSuccessors1(stateSuccessors);
                    });
        }
    }

    public void foundSuccessors2(State state) {
        if (!statesAccessible2.contains(state) && cloneLayoutGraph2.getStatesSuccessors(state) != null) {
            statesAccessible2.add(state);
            cloneLayoutGraph2
                    .getStatesSuccessors(state)
                    .forEach(stateSuccessors -> {
                        eventsAccessible2.add(cloneLayoutGraph2.findEvent(state, stateSuccessors));
                        foundSuccessors2(stateSuccessors);
                    });
        }
    }

    public void addStatesInit() {
        statesAccessible1.forEach(state1 ->
                statesAccessible2.forEach(state2 -> {
                    State state = new State(state1.getName() + "," + state2.getName());
                    if (state1.isStart() && state2.isStart()) {
                        state.setStart(true);
                    }
                    cloneLayoutGraph.addState(state);
                })
        );
    }

    public void setNotConnect() {
        notConnect1.addAll(cloneLayoutGraph1.getAllEvents());
        cloneLayoutGraph1.getAllEvents().forEach(event1 ->
                cloneLayoutGraph2.getAllEvents().forEach(event2 -> {
                    if (event1.getLinkName().equals(event2.getLinkName())) {
                        notConnect1.remove(event1);
                    }
                })
        );

        notConnect1.forEach(event ->
                cloneLayoutGraph2.getAllStates().forEach(state -> {
                    State state1 = cloneLayoutGraph.findStateByName(event.getStateInit().getName() + "," + state.getName());
                    State state2 = cloneLayoutGraph.findStateByName(event.getStateFinal().getName() + "," + state.getName());
                    if (state1 != null && state2 != null)
                        cloneLayoutGraph.addEvent(new Event(event.getLinkName(), state1, state2), state1, state2);
                })
        );

        notConnect2.addAll(cloneLayoutGraph2.getAllEvents());
        cloneLayoutGraph2.getAllEvents().forEach(event2 ->
                cloneLayoutGraph1.getAllEvents().forEach(event1 -> {
                    if (event1.getLinkName().equals(event2.getLinkName())) {
                        notConnect2.remove(event2);
                    }
                })
        );

        notConnect2.forEach(event ->
                cloneLayoutGraph1.getAllStates().forEach(state -> {
                    State state1 = cloneLayoutGraph.findStateByName(state.getName() + "," + event.getStateInit().getName());
                    State state2 = cloneLayoutGraph.findStateByName(state.getName() + "," + event.getStateFinal().getName());
                    if (state1 != null && state2 != null)
                        cloneLayoutGraph.addEvent(new Event(event.getLinkName(), state1, state2), state1, state2);
                })
        );
    }

}
