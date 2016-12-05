package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import javafx.scene.control.cell.PropertyValueFactory;
import models.State;
import views.LayoutGraph;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Rangel on 25/11/2016.
 */
public class TableController extends AbstractController implements Initializable {


    @FXML
    private TableView table;

    private TableColumn<State, String> eventsColumn;

    private ObservableList<State> states;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    private <T> TableColumn<T, ?> getTableColumnByName(TableView<T> tableView, String name) {
        for (TableColumn<T, ?> col : tableView.getColumns())
            if (col.getText().equals(name)) return col;
        return null;
    }

    @Override
    public void init(ApplicationController applicationController, LayoutGraph layoutGraph) {
        super.init(applicationController, layoutGraph);
        eventsColumn = new TableColumn<>("States");
        eventsColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        table.getColumns().add(eventsColumn);
        layoutGraph.getAllEvents().forEach(event -> {
            eventsColumn = getTableColumnByName(table, event.getLinkName());
            if (eventsColumn == null) {
                eventsColumn = new TableColumn<>(event.getLinkName());
                eventsColumn.setSortable(false);
                table.getColumns().add(eventsColumn);
            }
            eventsColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
            eventsColumn.setCellFactory(param -> new TableCell<State, String>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (!empty) {
                        State state = layoutGraph.findStateTable(layoutGraph.findStateByName(item), event);
                        if (state != null)
                            setText(state.getName());
                        else
                            setText("-");

                    }
                }
            });
        });

        table.setItems(getStates());
    }


    public ObservableList<State> getStates() {
        states = FXCollections.observableArrayList();
        states.setAll(layoutGraph.getAllStates());
        return states;
    }


}
