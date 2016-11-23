package controllers;


import javafx.embed.swing.SwingNode;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.scene.control.Alert;

import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import models.Event;
import models.State;
import utils.CreateNewState;
import views.LayoutGraph;

import java.io.*;
import java.net.URL;
import java.util.*;

/**
 * Created by Rangel on 20/11/2016.
 */
public class ApplicationController implements Initializable {

    @FXML
    private Pane pane;

    private SwingNode swingNode;

    private LayoutGraph layoutGraph;

    @FXML
    private Label x;

    @FXML
    private Label e;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        layoutGraph = new LayoutGraph();
        swingNode = new SwingNode();
        pane.getChildren().add(swingNode);
    }

    public void createAndSetSwingContent() {
        //SwingUtilities.invokeLater(() -> {
        swingNode.setContent(layoutGraph.changeBasicVisualizationServer());
        //});
    }

    public void setX(){
        x.setText("X="+ getAllStates() +"");
    }

    public void setE(){
        e.setText("E="+ getAllEvents() +"");
    }

    @FXML
    public void newProject() {
        removeAllGraph();
        createAndSetSwingContent();
    }

    @FXML
    public void importFile() {
        try {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Import File");
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("Text Files", "*.txt"));
            Stage stage = (Stage) pane.getScene().getWindow();
            File selectedFile = fileChooser.showOpenDialog(stage);
            if (selectedFile != null) {
                removeAllGraph();
                FileReader file = new FileReader(selectedFile);
                BufferedReader bufferedReader = new BufferedReader(file);
                String line = bufferedReader.readLine();

                String states[] = line.split(",");
                for (String stateName : states) {
                    State state = new State(stateName);
                    addStateGraph(state);

                }
                line = bufferedReader.readLine();
                if (line != null) {
                    String events[] = line.split("],");
                    for (String event : events) {
                        String eventName = event.substring(0, event.indexOf("["));
                        String state1 = event.substring(event.indexOf("[") + 1, event.indexOf(","));
                        String state2 = event.substring(event.indexOf(",") + 2);
                        addEventGraph(new Event(eventName), findStateByName(state1), findStateByName(state2));

                    }
                    createAndSetSwingContent();
                    bufferedReader.close();
                    file.close();
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void exportFile() {
        try {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Save File");
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("Text Files", "*.txt"));
            Stage stage = (Stage) pane.getScene().getWindow();
            File save = fileChooser.showSaveDialog(stage);

            FileWriter file = new FileWriter(save);
            BufferedWriter bufferedWriter = new BufferedWriter(file);
            for (State state : getAllStates()) {
                bufferedWriter.write(state.toString() + ",");
            }

            bufferedWriter.newLine();
            for (Event event : getAllEvents()) {
                bufferedWriter.write(event.toString() + " ");
                bufferedWriter.write(getAllStatesByEvent(event) + ",");
            }
            bufferedWriter.close();
            file.close();
            alertMessage("File saved successfully!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void alertMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.show();
    }


    @FXML
    public void addState() throws IOException {
        new CreateNewState("/views/AddState.fxml", this);
    }

    @FXML
    void addEvent() throws IOException {
        new CreateNewState("/views/AddEvent.fxml", this);
    }


    @FXML
    void removeState() throws IOException {
        new CreateNewState("/views/RemoveState.fxml", this);
    }

    @FXML
    void removeEvent() throws IOException {
        new CreateNewState("/views/RemoveEvent.fxml", this);
    }

    @FXML
    void testCase() {
        removeAllGraph();

        List<State> listStates = new ArrayList<>();
        listStates.add(new State("A"));
        listStates.add(new State("B"));
        listStates.add(new State("C"));
        listStates.add(new State("D"));
        listStates.add(new State("E"));
        listStates.add(new State("F"));
        listStates.add(new State("G"));
        listStates.add(new State("H"));
        listStates.add(new State("I"));

        listStates.forEach(this::addStateGraph);

        addEventGraph(new Event("A-B"), findStateByName("A"), findStateByName("B"));
        addEventGraph(new Event("A-C"), findStateByName("A"), findStateByName("C"));
        addEventGraph(new Event("A-D"), findStateByName("A"), findStateByName("D"));
        addEventGraph(new Event("A-E"), findStateByName("A"), findStateByName("E"));
        addEventGraph(new Event("B-F"), findStateByName("B"), findStateByName("F"));
        addEventGraph(new Event("F-H"), findStateByName("F"), findStateByName("H"));
        addEventGraph(new Event("D-G"), findStateByName("D"), findStateByName("G"));
        addEventGraph(new Event("G-I"), findStateByName("G"), findStateByName("I"));

        createAndSetSwingContent();

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
            createAndSetSwingContent();
            setX();
            return true;
        }
        return false;
    }

    public boolean removeStateGraph(State state) {
        if (state != null) {
            layoutGraph.getGraph().removeVertex(state);
            createAndSetSwingContent();
            return true;
        }
        return false;
    }

    public boolean removeEventGraph(Event event) {
        if (event != null) {
            layoutGraph.getGraph().removeEdge(event);
            createAndSetSwingContent();
            return true;
        }
        return false;
    }

    public void removeAllGraph() {
        layoutGraph = new LayoutGraph();
    }

    public boolean addEventGraph(Event event, State state1, State state2) {
        if (state1 != null && state2 != null) {
            layoutGraph.getGraph().addEdge(event, state1, state2);
            createAndSetSwingContent();
            setE();
            return true;
        }
        return false;
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

    public State findStateByStateAndEvent(State state, Event event) {
        return layoutGraph.getGraph().getOpposite(state, event);
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
