package main;

import controllers.WindowController;
import views.LayoutGraph;
import views.Window;

public class Main {

	public static void main(String[] args) {

		Window window = new Window();
		LayoutGraph layoutGraph = new LayoutGraph();
		WindowController windowController = new WindowController(window, layoutGraph);
		windowController.init();
	}

}
