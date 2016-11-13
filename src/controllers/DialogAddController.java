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
		if (!dialogAddState.isVisible()) {
			dialogAddState.setVisible(true);
		} else {
			State state = new State(dialogAddState.getTextFieldNameState().getText());
			if (!containsState(state)) {
				layoutGraph.getGraph().addVertex(state);
				dialogAddState.dispose();
				refreshGraph();
			} else {
				JOptionPane.showMessageDialog(null, "This state is already saved!");
			}

		}

	}

	public void addEvent() {
		if (!dialogAddEvent.isVisible()) {
			refreshComboboxStates();
			dialogAddEvent.setVisible(true);

		} else {
			State state1 = (State) dialogAddEvent.getComboBoxState1().getSelectedItem();
			State state2 = (State) dialogAddEvent.getComboBoxState2().getSelectedItem();
			Event event = new Event(dialogAddEvent.getTextFieldNameState().getText());
			layoutGraph.getGraph().addEdge(event, state1, state2);
			dialogAddEvent.dispose();
			refreshGraph();
		}
	}

	public void refreshComboboxStates() {
		dialogAddEvent.getComboBoxState1().removeAllItems();
		dialogAddEvent.getComboBoxState2().removeAllItems();
		layoutGraph.getGraph().getVertices().forEach(state -> {
			dialogAddEvent.getComboBoxState1().addItem(state);
			dialogAddEvent.getComboBoxState2().addItem(state);
		});
	}

}
