package views;

import java.awt.event.ActionListener;
import java.awt.event.ItemListener;

import javax.swing.ComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;

import models.State;
import javax.swing.JComboBox;
import java.awt.Font;

public class DialogRemoveEvent extends JDialog {

	private static final long serialVersionUID = 1L;
	private static final String ImageRemoveEvent = "images/removeEvent.png";
	private static final String ImageLink = "images/link.png";
	private static final String ImageGraph = "images/graph.png";
	private JLabel lblEvent;
	private JTextField textFieldNameEvent;
	private JButton btnRemoveEvent;
	private JComboBox<State> comboBoxState2;
	private JComboBox<State> comboBoxState1;
	private JLabel lblLink;
	private JLabel lblImage;
	ComboBoxModel<State> comboBoxModel;

	public DialogRemoveEvent() {
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 400);
		getContentPane().setLayout(null);

		lblEvent = new JLabel("Name Event:");
		lblEvent.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblEvent.setBounds(138, 108, 90, 14);
		getContentPane().add(lblEvent);

		textFieldNameEvent = new JTextField();
		textFieldNameEvent.setBounds(138, 133, 158, 25);
		getContentPane().add(textFieldNameEvent);
		textFieldNameEvent.setColumns(10);

		btnRemoveEvent = new JButton("");
		btnRemoveEvent.setBounds(148, 283, 137, 40);
		btnRemoveEvent.setIcon(new ImageIcon(ImageRemoveEvent));
		getContentPane().add(btnRemoveEvent);

		comboBoxState1 = new JComboBox<State>();
		comboBoxState1.setBounds(42, 198, 107, 30);
		getContentPane().add(comboBoxState1);

		comboBoxState2 = new JComboBox<State>();
		comboBoxState2.setBounds(283, 198, 107, 30);
		getContentPane().add(comboBoxState2);

		lblLink = new JLabel("");
		lblLink.setBounds(191, 198, 50, 30);
		lblLink.setIcon(new ImageIcon(ImageLink));
		getContentPane().add(lblLink);

		lblImage = new JLabel("");
		lblImage.setBounds(176, 11, 81, 50);
		lblImage.setIcon(new ImageIcon(ImageGraph));
		getContentPane().add(lblImage);

	}

	public void actionListener(ActionListener actionListener, ItemListener itemListener) {
		btnRemoveEvent.setActionCommand("removeEvent");
		btnRemoveEvent.addActionListener(actionListener);
		comboBoxState1.addItemListener(itemListener);
	}

	public JTextField getTextFieldNameEvent() {
		return textFieldNameEvent;
	}

	public JComboBox<State> getComboBoxState1() {
		return comboBoxState1;
	}

	public JComboBox<State> getComboBoxState2() {
		return comboBoxState2;
	}
}
