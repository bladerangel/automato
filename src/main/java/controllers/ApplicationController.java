package controllers;


import edu.uci.ics.jung.graph.Graph;
import javafx.embed.swing.SwingNode;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.scene.SubScene;
import javafx.scene.control.Alert;

import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import models.Event;
import models.State;
import utils.CreateNewWindow;
import views.LayoutGraph;

import java.io.*;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by Rangel on 20/11/2016.
 */
public class ApplicationController implements Initializable {

    @FXML
    private Pane pane;

    private SwingNode swingNode;

    private LayoutGraph layoutGraph;

    private CreateNewWindow createNewWindow;

    @FXML
    private Label xo;

    @FXML
    private Label x;

    @FXML
    private Label e;

    @FXML
    private Label xm;

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

    public void setXo() {
        xo.setText("Xo=" + layoutGraph.getStateStart());
    }

    public void setX() {
        x.setText("X=" + layoutGraph.getAllStates());
    }

    public void setE() {
        e.setText("E=" + layoutGraph.getAllEvents());
    }

    public void setXm() {
        xm.setText("Xm=" + layoutGraph.getAllStatesMarked());
    }

    public LayoutGraph getLayoutGraph() {
        return layoutGraph;
    }

    @FXML
    public void newProject() {
        layoutGraph.removeAllGraph();
        setXo();
        setX();
        setXm();
        setE();
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
                layoutGraph.removeAllGraph();
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
                    String events[] = line.split(">,");
                    for (String event : events) {
                        String eventName = event.substring(0, event.indexOf("<") - 1);
                        String state1 = event.substring(event.indexOf("<") + 1, event.indexOf(","));
                        String state2 = event.substring(event.indexOf(",") + 2);
                        Event eventSave = new Event(eventName, layoutGraph.findStateByName(state1), layoutGraph.findStateByName(state2));
                        addEventGraph(eventSave, eventSave.getStateInit(), eventSave.getStateFinal());

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
            if (save != null) {
                FileWriter file = new FileWriter(save);
                BufferedWriter bufferedWriter = new BufferedWriter(file);
                for (State state : layoutGraph.getAllStates()) {
                    bufferedWriter.write(state.toString() + ",");
                }

                bufferedWriter.newLine();
                for (Event event : layoutGraph.getAllEvents()) {
                    bufferedWriter.write(event.toString() + " ");
                    bufferedWriter.write(layoutGraph.getAllStatesByEvent(event) + ",");
                }
                bufferedWriter.close();
                file.close();
                alertMessage("File saved successfully!");
            }
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

    public void newWindow(String view) throws IOException {
        createNewWindow = new CreateNewWindow(view);
        createNewWindow.setStage(new Stage());
        createNewWindow.setBtnMin(false);
        createNewWindow.setScene();
        createNewWindow.setAbstractController(this);
        createNewWindow.show();
    }

    @FXML
    public void addState() throws IOException {
        newWindow("AddState");
    }

    @FXML
    void addEvent() throws IOException {
        newWindow("AddEvent");
    }


    @FXML
    void removeState() throws IOException {
        newWindow("RemoveState");
    }

    @FXML
    void removeEvent() throws IOException {
        newWindow("RemoveEvent");
    }

    @FXML
    void table() throws IOException {
        newWindow("TableView");
    }

    @FXML
    void testCase() throws IOException {
        /*
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
    */
        newWindow("Operations");
    }

    /*
        private boolean containsState(State state) {
            for (State s : layoutGraph.getGraph().getVertices()) {
                if (s.toString().equals(state.toString())) {
                    return true;
                }
            }
            return false;
        }
    */
    public boolean addStateGraph(State state) {
        if (!layoutGraph.getAllStates().contains(state)) {
            layoutGraph.addState(state);
            createAndSetSwingContent();
            setXo();
            setX();
            setXm();
            return true;
        }
        return false;
    }

    public boolean removeStateGraph(State state) {
        if (state != null) {
            layoutGraph.removeState(state);
            createAndSetSwingContent();
            setXo();
            setX();
            setXm();
            return true;
        }
        return false;
    }

    public boolean addEventGraph(Event event, State state1, State state2) {
        if (state1 != null && state2 != null) {
            layoutGraph.addEvent(event, state1, state2);
            createAndSetSwingContent();
            setE();
            return true;
        }
        return false;
    }

    public boolean removeEventGraph(Event event) {
        if (event != null) {
            layoutGraph.removeEvent(event);
            createAndSetSwingContent();
            setE();
            return true;
        }
        return false;
    }

}
