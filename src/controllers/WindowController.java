package controllers;

import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import models.Event;
import models.State;
import utils.FileActions;
import views.LayoutGraph;
import views.Window;

public class WindowController extends WindowAbstractController {

	DialogAddController dialogAddController;
	DialogRemoveController dialogRemoveController;

	public WindowController(Window window, LayoutGraph layoutGraph) {
		super(window, layoutGraph);
		dialogAddController = new DialogAddController(window, layoutGraph);
		dialogRemoveController = new DialogRemoveController(window, layoutGraph);

	}

	public void init() {
		window.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		switch (e.getActionCommand()) {
		case "import":
			importFile();
			break;
		case "export":
			exportFile();
			break;
		case "test":
			test();
			break;
		case "cleanLog":
			cleanLog();
			break;
		default:
			break;
		}

	}

	public void importFile() {
		try {
			JFileChooser fileChooser = FileActions.initPath();
			int result = fileChooser.showOpenDialog(window.getContentPane());
			if (result == JFileChooser.APPROVE_OPTION) {
				FileReader file = new FileReader(fileChooser.getSelectedFile());
				BufferedReader bufferedReader = new BufferedReader(file);
				String line = bufferedReader.readLine();

				String states[] = line.split(",");
				for (String stateName : states) {
					State state = new State(stateName);
					addStateGraph(state);

				}
				line = bufferedReader.readLine();
				String events[] = line.split("],");
				for (String event : events) {
					String eventNane = event.substring(0, event.indexOf("["));
					String state1 = event.substring(event.indexOf("[") + 1, event.indexOf(","));
					String state2 = event.substring(event.indexOf(",") + 2);
					addEventGraph(new Event(eventNane), findStateByName(state1), findStateByName(state2));

				}
				refreshGraph("The file was imported!");
				bufferedReader.close();
				file.close();
			}
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "File not found!");
		}

	}

	public void exportFile() {
		try {
			JFileChooser fileChooser = FileActions.initPath();
			int result = fileChooser.showSaveDialog(window.getContentPane());
			if (result == JFileChooser.APPROVE_OPTION) {
				FileWriter file = new FileWriter(fileChooser.getSelectedFile() + ".txt");
				BufferedWriter bufferedWriter = new BufferedWriter(file);
				for (State state : getAllStates()) {
					bufferedWriter.write(state.toString() + ",");
				}

				bufferedWriter.newLine();
				for (Event event : getAllEvents()) {
					bufferedWriter.write(event.toString() + " ");
					bufferedWriter.write(getAllStatesByEvent(event) + ",");
				}
				bufferedWriter.close();
				file.close();
				messageLog(
						"File saved successfully on path: " + fileChooser.getSelectedFile().getAbsolutePath() + ".txt");
				JOptionPane.showMessageDialog(null, "File saved successfully!");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void test() {

		List<State> listStates = new ArrayList<State>();
		listStates.add(new State("A"));
		listStates.add(new State("B"));
		listStates.add(new State("C"));
		listStates.add(new State("D"));
		listStates.add(new State("E"));
		listStates.add(new State("F"));
		listStates.add(new State("G"));
		listStates.add(new State("H"));
		listStates.add(new State("I"));

		listStates.forEach(state -> {
			addStateGraph(state);

		});

		addEventGraph(new Event("A-B"), findStateByName("A"), findStateByName("B"));
		addEventGraph(new Event("A-C"), findStateByName("A"), findStateByName("C"));
		addEventGraph(new Event("A-D"), findStateByName("A"), findStateByName("D"));
		addEventGraph(new Event("A-E"), findStateByName("A"), findStateByName("E"));
		addEventGraph(new Event("B-F"), findStateByName("B"), findStateByName("F"));
		addEventGraph(new Event("F-H"), findStateByName("F"), findStateByName("H"));
		addEventGraph(new Event("D-G"), findStateByName("D"), findStateByName("G"));
		addEventGraph(new Event("G-I"), findStateByName("G"), findStateByName("I"));

		refreshGraph("Test case inserted!");

	}

	public void cleanLog() {
		window.getTextAreaLog().setText("");
	}

}
