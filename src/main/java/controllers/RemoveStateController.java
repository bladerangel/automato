package controllers;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.RequiredFieldValidator;
import com.jfoenix.validation.base.ValidatorBase;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import models.State;

import java.io.IOException;
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


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @Override
    public void init(ApplicationController applicationController) {
        super.init(applicationController);
        states.getItems().addAll(applicationController.getAllStates());
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
