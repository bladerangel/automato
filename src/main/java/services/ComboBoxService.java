package services;

import javafx.scene.control.ListCell;
import javafx.util.StringConverter;
import models.Event;
import models.State;

/**
 * Created by Rangel on 04/12/2016.
 */
public class ComboBoxService {
    public ListCell<State> newListState() {
        return new ListCell<State>() {
            @Override
            protected void updateItem(State item, boolean empty) {
                super.updateItem(item, empty);
                if (!empty)
                    setText(item.getName());
            }
        };
    }

    public StringConverter<State> newConverterState() {
        return new StringConverter<State>() {
            @Override
            public String toString(State state) {
                return state.getName();
            }

            @Override
            public State fromString(String string) {
                return null;
            }
        };
    }

    public ListCell<Event> newListEvent() {
        return new ListCell<Event>() {
            @Override
            protected void updateItem(Event item, boolean empty) {
                super.updateItem(item, empty);
                if (!empty)
                    setText(item.getLinkName());

            }
        };
    }

    public StringConverter<Event> newConverterEvent() {
        return new StringConverter<Event>() {
            @Override
            public String toString(Event state) {
                return state.getLinkName();
            }

            @Override
            public Event fromString(String string) {
                return null;
            }
        };
    }

}
