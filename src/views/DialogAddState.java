package views;

import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.Font;

public class DialogAddState extends JDialog {

	private static final long serialVersionUID = 1L;
	private static final String ImageSaveState = "images/saveState.png";
	private static final String ImageGraph = "images/graph.png";
	private JLabel lblState;
	private JTextField textFieldNameState;
	private JButton btnSaveState;
	private JLabel lblImage;

	public DialogAddState() {
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 313);
		getContentPane().setLayout(null);

		lblState = new JLabel("State Name:");
		lblState.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblState.setBounds(138, 116, 90, 14);
		getContentPane().add(lblState);

		textFieldNameState = new JTextField();
		textFieldNameState.setBounds(138, 141, 158, 25);
		getContentPane().add(textFieldNameState);
		textFieldNameState.setColumns(10);

		btnSaveState = new JButton("");
		btnSaveState.setBounds(148, 210, 137, 40);
		btnSaveState.setIcon(new ImageIcon(ImageSaveState));
		getContentPane().add(btnSaveState);

		lblImage = new JLabel("");
		lblImage.setBounds(176, 11, 81, 50);
		lblImage.setIcon(new ImageIcon(ImageGraph));
		getContentPane().add(lblImage);

	}

	public void actionListener(ActionListener actionListener) {
		btnSaveState.setActionCommand("addState");
		btnSaveState.addActionListener(actionListener);
	}

	public JTextField getTextFieldNameState() {
		return textFieldNameState;
	}

}
