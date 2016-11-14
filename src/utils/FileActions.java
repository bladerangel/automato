package utils;

import java.io.File;

import javax.swing.JFileChooser;

public class FileActions {

	public static JFileChooser initPath() {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
		return fileChooser;
	}
}
