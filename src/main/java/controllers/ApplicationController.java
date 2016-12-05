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
import services.CreateWindowService;
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

    private CreateWindowService createWindowService;

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

    public void append(Label label, String text) {
        label.setText(label.getText() + text);
    }

    public void clean(Label label) {
        label.setText("");
    }

    public void cleanFinal(Label label) {
        label.setText(label.getText().substring(0, label.getText().lastIndexOf(",")));
    }

    public void setXo() {
        clean(xo);
        append(xo, "Xo=" + layoutGraph.getStateStart().getName());
    }

    public void setX() {
        clean(x);
        append(x, "X={");
        if (!layoutGraph.getAllStates().isEmpty()) {
            layoutGraph.getAllStates().forEach(state -> {
                append(x, state.getName() + ",");
            });
            cleanFinal(x);
        }
        append(x, "}");
    }

    public void setE() {
        clean(e);
        append(e, "E={");
        if (!layoutGraph.getAllEvents().isEmpty()) {
            layoutGraph.getAllEvents().forEach(event -> {
                append(e, event.getLinkName() + ",");
            });
            cleanFinal(e);
        }
        append(e, "}");
    }

    public void setXm() {
        clean(xm);
        append(xm, "Xm={");
        if (!layoutGraph.getAllStatesMarked().isEmpty()) {
            layoutGraph.getAllStatesMarked().forEach(state -> {
                append(xm, state.getName() + ",");
            });
            if (!layoutGraph.getAllStatesMarked().isEmpty())
                cleanFinal(xm);
        }
        append(xm, "}");
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
                    String events[] = line.split("],");
                    for (String event : events) {
                        String eventName = event.substring(0, event.indexOf("[") - 1);
                        String state1 = event.substring(event.indexOf("[") + 1, event.indexOf(","));
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
                    bufferedWriter.write(state.getName() + ",");
                }

                bufferedWriter.newLine();
                for (Event event : layoutGraph.getAllEvents()) {
                    bufferedWriter.write(event.getLinkName() + " ");
                    bufferedWriter.write(layoutGraph.getAllStatesByEvent(event).stream().map(State::getName).collect(Collectors.toList()) + ",");
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
        createWindowService = new CreateWindowService(view);
        createWindowService.setStage(new Stage());
        createWindowService.setBtnMin(false);
        createWindowService.setScene();
        createWindowService.setAbstractController(this, layoutGraph);
        createWindowService.show();
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


    private boolean containsState(State state) {
        for (State s : layoutGraph.getAllStates()) {
            if (s.getName().equals(state.getName())) {
                return true;
            }
        }
        return false;
    }

    public boolean addStateGraph(State state) {
        if (!containsState(state)) {
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
