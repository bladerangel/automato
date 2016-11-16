package utils;

import javax.swing.ImageIcon;

public class ImagePath {
	
	public static ImageIcon setImage(String path) {
		return new ImageIcon(ImagePath.class.getResource("/images/"+path));
	}
}
