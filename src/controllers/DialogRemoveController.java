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
			layoutGraph.getGraph().removeVertex(state);
			dialogRemoveState.dispose();
			refreshGraph();

		}

	}

	public void removeEvent() {
		if (!dialogRemoveEvent.isVisible()) {
			findEvents();
			dialogRemoveEvent.setVisible(true);

		} else {
			if (dialogRemoveEvent.getTextFieldNameEvent().getText().equals(null)) {
				for (Event event : layoutGraph.getGraph().getEdges()) {
					if (event.toString().equals(dialogRemoveEvent.getTextFieldNameEvent().getText())) {
						layoutGraph.getGraph().removeEdge(event);
						break;
					}
				}
			} else {

				State state1 = (State) dialogRemoveEvent.getComboBoxState1().getSelectedItem();
				State state2 = (State) dialogRemoveEvent.getComboBoxState2().getSelectedItem();
				layoutGraph.getGraph().removeEdge(layoutGraph.getGraph().findEdge(state1, state2));
			}
			dialogRemoveEvent.dispose();
			refreshGraph();
		}
	}

	public void findEvents() {
		dialogRemoveEvent.getComboBoxState1().removeAllItems();
		layoutGraph.getGraph().getVertices().forEach(state -> {
			dialogRemoveEvent.getComboBoxState1().addItem(state);
		});
	}

	public void findStates() {
		dialogRemoveEvent.getComboBoxState2().removeAllItems();
		layoutGraph.getGraph().getOutEdges((State) dialogRemoveEvent.getComboBoxState1().getSelectedItem())
				.forEach(event -> {
					boolean stateEqual = false;
					for (State state : layoutGraph.getGraph().getIncidentVertices(event)) {
						if (!dialogRemoveEvent.getComboBoxState1().getSelectedItem().toString().equals(state.toString())
								|| stateEqual) {
							dialogRemoveEvent.getComboBoxState2().addItem(state);
						} else {
							stateEqual = true;
						}
					}
				});
	}

	public void refreshComboboxStates() {
		dialogRemoveState.getComboBoxStates().removeAllItems();
		layoutGraph.getGraph().getVertices().forEach(state -> {
			dialogRemoveState.getComboBoxStates().addItem(state);
		});
	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		if (e.getStateChange() == ItemEvent.SELECTED) {
			findStates();
		}

	}

}
