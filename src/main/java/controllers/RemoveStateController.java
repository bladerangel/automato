package controllers;

import Services.ComboBoxService;
import com.jfoenix.controls.JFXComboBox;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListCell;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import models.State;
import views.LayoutGraph;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Rangel on 22/11/2016.
 */
public class RemoveStateController extends AbstractController implements Initializable {

    @FXML
    private AnchorPane pane;

    @FXML
    private JFXComboBox<State> states;

    private ComboBoxService comboBoxService;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    @Override
    public void init(ApplicationController applicationController, LayoutGraph layoutGraph) {
        super.init(applicationController, layoutGraph);
        comboBoxService = new ComboBoxService();
        states.getItems().addAll(layoutGraph.getAllStates());
        states.setCellFactory(param -> comboBoxService.newListState());
        states.setConverter(comboBoxService.newConverterState());
    }

    @FXML
    void remove() {
        State state = states.getSelectionModel().getSelectedItem();
        if (applicationController.removeStateGraph(state)) {
            Stage stage = (Stage) pane.getScene().getWindow();
            stage.close();
        }
    }


}
