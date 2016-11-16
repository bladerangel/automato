package controllers;

import java.awt.event.ActionEvent;

import javax.swing.JOptionPane;

import models.Event;
import models.State;
import views.DialogAddEvent;
import views.DialogAddState;
import views.LayoutGraph;
import views.Window;

public class DialogAddController extends WindowAbstractController {

	DialogAddState dialogAddState;
	DialogAddEvent dialogAddEvent;

	public DialogAddController(Window window, LayoutGraph layoutGraph) {
		super(window, layoutGraph);
		dialogAddState = new DialogAddState();
		dialogAddEvent = new DialogAddEvent();
		dialogAddEvent.actionListener(this);
		dialogAddState.actionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		switch (e.getActionCommand()) {
		case "addState":
			addState();
			break;
		case "addEvent":
			addEvent();
			break;
		default:
			break;
		}

	}

	public void addState() {
		if (!dialogAddState.isActive()) {
			dialogAddState.setVisible(true);
		} else {
			String stateName = dialogAddState.getTextFieldNameState().getText();
			if (!stateName.trim().equals("")) {
				State state = new State(stateName);
				if (addStateGraph(state)) {
					dialogAddState.getTextFieldNameState().setText("");
					dialogAddState.dispose();
					refreshGraph("This state " + state.toString() + " has been insert!");
				} else {
					JOptionPane.showMessageDialog(null, "This state is already saved!");
				}
			}

		}

	}

	public void addEvent() {
		if (!dialogAddEvent.isActive()) {
			refreshComboboxStates();
			dialogAddEvent.setVisible(true);

		} else {
			State state1 = (State) dialogAddEvent.getComboBoxState1().getSelectedItem();
			State state2 = (State) dialogAddEvent.getComboBoxState2().getSelectedItem();
			Event event = new Event(dialogAddEvent.getTextFieldNameState().getText());
			if (addEventGraph(event, state1, state2)) {
				dialogAddEvent.getTextFieldNameState().setText("");
				dialogAddEvent.dispose();
				refreshGraph("This event " + event.toString() + " with state " + state1.toString() + " and "
						+ state2.toString() + " has been insert!");
			}

		}
	}

	public void refreshComboboxStates() {
		dialogAddEvent.getComboBoxState1().removeAllItems();
		dialogAddEvent.getComboBoxState2().removeAllItems();
		getAllStates().forEach(state -> {
			dialogAddEvent.getComboBoxState1().addItem(state);
			dialogAddEvent.getComboBoxState2().addItem(state);
		});
	}

}
