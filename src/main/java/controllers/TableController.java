package controllers;

import com.jfoenix.controls.JFXTreeTableColumn;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;

import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import models.State;
import models.Event;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Created by Rangel on 25/11/2016.
 */
public class TableController extends AbstractController implements Initializable {


    @FXML
    private TableView table;

    private TableColumn<State, String> eventsColumn;

    private TableRow<State> rowState;
    ObservableList<State> states;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @Override
    public void init(ApplicationController applicationController) {
        super.init(applicationController);
        eventsColumn = new TableColumn<>("States");


        eventsColumn.setCellValueFactory(new PropertyValueFactory<>("name"));


        table.getColumns().add(eventsColumn);
        applicationController.getAllEvents().forEach(event -> {
            eventsColumn = new TableColumn<>(event.toString());
            eventsColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
            //eventsColumn.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue().getName()));
            eventsColumn.setCellFactory(param -> {
                return new TableCell<State, String>() {
                    @Override
                    protected void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (this.getTableRow() != null && item != null) {
                            System.out.println("item" + event);
                            State state = applicationController.find(applicationController.findStateByName(item), event);
                            if (state != null)
                                setText(state.getName());
                            else
                                setText("-");

                        }
                    }
                };
            });
            eventsColumn.setSortable(false);
            table.getColumns().add(eventsColumn);
        });

        table.setItems(getStates());
    }


    public ObservableList<State> getStates() {
        states = FXCollections.observableArrayList();
        states.setAll(applicationController.getAllStates());
        return states;
    }


}
