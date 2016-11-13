package controllers;

import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import models.Event;
import models.State;
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
			JFileChooser fileChooser = new JFileChooser();
			fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
			int result = fileChooser.showOpenDialog(window.getContentPane());
			if (result == JFileChooser.APPROVE_OPTION) {
				FileReader file = new FileReader(fileChooser.getSelectedFile());
				BufferedReader bufferedReader = new BufferedReader(file);
				String line = bufferedReader.readLine();
				while (line != null) {
					System.out.printf("%s\n", line);

					line = bufferedReader.readLine();
				}
				bufferedReader.close();
				file.close();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void exportFile() {
		try {
			JFileChooser fileChooser = new JFileChooser();
			fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
			int result = fileChooser.showSaveDialog(window.getContentPane());
			if (result == JFileChooser.APPROVE_OPTION) {
				FileWriter file = new FileWriter(fileChooser.getSelectedFile() + ".txt");
				BufferedWriter bufferedWriter = new BufferedWriter(file);
				bufferedWriter.write("aew");
				bufferedWriter.newLine();
				bufferedWriter.write("br");
				bufferedWriter.close();
				file.close();
				JOptionPane.showMessageDialog(null, "File saved successfully!");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
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
			if (!containsState(state)) {
				layoutGraph.getGraph().addVertex(state);
			}

		});

		layoutGraph.getGraph().addEdge(new Event("A-B"), listStates.get(0), listStates.get(1));
		layoutGraph.getGraph().addEdge(new Event("A-C"), listStates.get(0), listStates.get(2));
		layoutGraph.getGraph().addEdge(new Event("A-D"), listStates.get(0), listStates.get(3));
		layoutGraph.getGraph().addEdge(new Event("A-E"), listStates.get(0), listStates.get(4));
		layoutGraph.getGraph().addEdge(new Event("B-F"), listStates.get(1), listStates.get(5));
		layoutGraph.getGraph().addEdge(new Event("F-H"), listStates.get(6), listStates.get(7));
		layoutGraph.getGraph().addEdge(new Event("D-G"), listStates.get(3), listStates.get(6));
		layoutGraph.getGraph().addEdge(new Event("G-I"), listStates.get(6), listStates.get(8));

		refreshGraph();

	}

	public void cleanLog() {
		window.getTextAreaLog().setText("");
	}

}
