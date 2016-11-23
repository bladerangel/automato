package views;

import java.awt.Font;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;

import models.State;
import utils.ImagePath;

public class DialogAddEvent extends JDialog {

	private static final long serialVersionUID = 1L;
	private static final ImageIcon ImageSaveEvent = ImagePath.setImage("/saveEvent.png");
	private static final ImageIcon ImageLink = ImagePath.setImage("/link.png");
	private static final ImageIcon ImageGraph = ImagePath.setImage("/graph.png");
	private JLabel lblEvent;
	private JTextField textFieldNameEvent;
	private JButton btnSaveEvent;
	private JComboBox<State> comboBoxState2;
	private JComboBox<State> comboBoxState1;
	private JLabel lblLink;
	private JLabel lblImage;

	public DialogAddEvent() {
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 400);
		getContentPane().setLayout(null);

		lblEvent = new JLabel("Event Name:");
		lblEvent.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblEvent.setBounds(138, 108, 90, 14);
		getContentPane().add(lblEvent);

		textFieldNameEvent = new JTextField();
		textFieldNameEvent.setBounds(138, 133, 158, 25);
		getContentPane().add(textFieldNameEvent);
		textFieldNameEvent.setColumns(10);

		btnSaveEvent = new JButton("");
		btnSaveEvent.setBounds(148, 283, 137, 40);
		btnSaveEvent.setIcon(ImageSaveEvent);
		getContentPane().add(btnSaveEvent);

		comboBoxState1 = new JComboBox<State>();
		comboBoxState1.setBounds(42, 198, 107, 30);
		getContentPane().add(comboBoxState1);

		comboBoxState2 = new JComboBox<State>();
		comboBoxState2.setBounds(283, 198, 107, 30);
		getContentPane().add(comboBoxState2);

		lblLink = new JLabel("");
		lblLink.setBounds(191, 198, 50, 30);
		lblLink.setIcon(ImageLink);
		getContentPane().add(lblLink);

		lblImage = new JLabel("");
		lblImage.setBounds(176, 11, 81, 50);
		lblImage.setIcon(ImageGraph);
		getContentPane().add(lblImage);

	}

	public void actionListener(ActionListener actionListener) {
		btnSaveEvent.setActionCommand("addEvent");
		btnSaveEvent.addActionListener(actionListener);
	}

	public JTextField getTextFieldNameState() {
		return textFieldNameEvent;
	}

	public JComboBox<State> getComboBoxState1() {
		return comboBoxState1;
	}

	public JComboBox<State> getComboBoxState2() {
		return comboBoxState2;
	}
}
