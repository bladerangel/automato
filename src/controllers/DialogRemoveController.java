package controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import models.Event;
import models.State;
import views.DialogRemoveEvent;
import views.DialogRemoveState;
import views.LayoutGraph;
import views.Window;

public class DialogRemoveController extends WindowAbstractController implements ItemListener {

	DialogRemoveState dialogRemoveState;
	DialogRemoveEvent dialogRemoveEvent;

	public DialogRemoveController(Window window, LayoutGraph layoutGraph) {
		super(window, layoutGraph);
		dialogRemoveState = new DialogRemoveState();
		dialogRemoveEvent = new DialogRemoveEvent();
		dialogRemoveState.actionListener(this);
		dialogRemoveEvent.actionListener(this, this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		switch (e.getActionCommand()) {
		case "removeState":
			removeState();
			break;
		case "removeEvent":
			removeEvent();
			break;
		default:
			break;
		}

	}

	public void removeState() {
		if (!dialogRemoveState.isVisible()) {
			refreshComboboxStates();
			dialogRemoveState.setVisible(true);
		} else {
			State state = (State) dialogRemoveState.getComboBoxStates().getSelectedItem();
			removeStateGraph(state);
			dialogRemoveState.dispose();
			refreshGraph("This state " + state.toString() + " has been deleted!");
		}
	}

	public void removeEvent() {
		if (!dialogRemoveEvent.isVisible()) {
			refreshEvents();
			dialogRemoveEvent.setVisible(true);

		} else {
			if (!dialogRemoveEvent.getTextFieldNameEvent().getText().equals("")) {
				for (Event event : getAllEvents()) {
					if (event.toString().equals(dialogRemoveEvent.getTextFieldNameEvent().getText())) {
						removeEventGraph(event);
						refreshGraph("This event " + event.toString() + " has been deleted!");
						break;
					}
				}
			} else {

				State state1 = (State) dialogRemoveEvent.getComboBoxState1().getSelectedItem();
				State state2 = (State) dialogRemoveEvent.getComboBoxState2().getSelectedItem();
				if (state2 != null) {
					Event event = findEvent(state1, state2);
					removeEventGraph(event);
					refreshGraph("This event " + event.toString() + " with state " + state1.toString() + " and "
							+ state2.toString() + " has been deleted!");
				}
			}
			dialogRemoveEvent.dispose();
		}
	}

	public void refreshEvents() {
		dialogRemoveEvent.getComboBoxState1().removeAllItems();
		getAllStates().forEach(state -> {
			dialogRemoveEvent.getComboBoxState1().addItem(state);
		});
	}

	public void refreshStates() {
		dialogRemoveEvent.getComboBoxState2().removeAllItems();
		State stateRemove = (State) dialogRemoveEvent.getComboBoxState1().getSelectedItem();
		getAllEventByState(stateRemove).forEach(event -> {
			getAllStatesByEvent(event).forEach(state -> {
				dialogRemoveEvent.getComboBoxState2().addItem(state);
			});
			dialogRemoveEvent.getComboBoxState2().removeItem(stateRemove);
		});

	}

	public void refreshComboboxStates() {
		dialogRemoveState.getComboBoxStates().removeAllItems();
		getAllStates().forEach(state -> {
			dialogRemoveState.getComboBoxStates().addItem(state);
		});
	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		if (e.getStateChange() == ItemEvent.SELECTED) {
			refreshStates();
		}

	}

}
