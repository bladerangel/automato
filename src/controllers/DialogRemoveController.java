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
		if (!dialogRemoveState.isActive()) {
			dialogRemoveState.getComboBoxStates().removeAllItems();
			getAllStates().forEach(state -> {
				dialogRemoveState.getComboBoxStates().addItem(state);
			});
			dialogRemoveState.setVisible(true);
		} else {
			State state = (State) dialogRemoveState.getComboBoxStates().getSelectedItem();
			if (removeStateGraph(state)) {
				dialogRemoveState.dispose();
				refreshGraph("This state " + state.toString() + " has been deleted!");
			}
		}
	}

	public void removeEvent() {
		if (!dialogRemoveEvent.isActive()) {
			refreshAll();
			dialogRemoveEvent.setVisible(true);
		} else {
			Event event = (Event) dialogRemoveEvent.getComboBoxEvent().getSelectedItem();
			if (removeEventGraph(event)) {
				refreshGraph("This event " + event.toString() + " has been deleted!");
				dialogRemoveEvent.dispose();
			}

		}
	}

	public void refreshAll() {
		dialogRemoveEvent.getComboBoxState1().removeAllItems();
		getAllStates().forEach(state -> {
			dialogRemoveEvent.getComboBoxState1().addItem(state);
		});

		if (dialogRemoveEvent.getComboBoxState1().getSelectedItem() != null) {
			refreshStates2AndEventByState1();
		} else {
			dialogRemoveEvent.getComboBoxEvent().removeAllItems();
			dialogRemoveEvent.getComboBoxState2().removeAllItems();
		}

	}

	public void selectEventsByState2() {
		State state1 = (State) dialogRemoveEvent.getComboBoxState1().getSelectedItem();
		State state2 = (State) dialogRemoveEvent.getComboBoxState2().getSelectedItem();
		dialogRemoveEvent.getComboBoxEvent().setSelectedItem(findEvent(state1, state2));
	}

	public void selectState2ByEvent() {
		Event event = (Event) dialogRemoveEvent.getComboBoxEvent().getSelectedItem();
		State state = (State) dialogRemoveEvent.getComboBoxState1().getSelectedItem();
		dialogRemoveEvent.getComboBoxState2().setSelectedItem(findStateByStateAndEvent(state, event));

	}

	public boolean containsState2(State state) {
		for (int i = 0; i < dialogRemoveEvent.getComboBoxState2().getItemCount(); i++) {
			if (dialogRemoveEvent.getComboBoxState2().getItemAt(i).equals(state)) {
				return true;
			}
		}
		return false;
	}

	public void refreshStates2AndEventByState1() {
		dialogRemoveEvent.getComboBoxEvent().removeAllItems();
		dialogRemoveEvent.getComboBoxState2().removeAllItems();
		State state1 = (State) dialogRemoveEvent.getComboBoxState1().getSelectedItem();
		getAllEventByState(state1).forEach(event -> {
			dialogRemoveEvent.getComboBoxEvent().addItem(event);
			State state2 = findStateByStateAndEvent(state1, event);
			if (!containsState2(state2)) {
				dialogRemoveEvent.getComboBoxState2().addItem(state2);
			}
		});
		selectEventsByState2();
	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		if (e.getStateChange() == ItemEvent.SELECTED) {
			if (e.getItem() instanceof State) {
				if (e.getSource().equals(dialogRemoveEvent.getComboBoxState1())) {
					refreshStates2AndEventByState1();
				} else {
					selectEventsByState2();
				}

			} else {
				selectState2ByEvent();
			}

		}
	}
}
