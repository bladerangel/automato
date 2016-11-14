package views;

import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ItemListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;

import models.Event;
import models.State;

public class DialogRemoveEvent extends JDialog {

	private static final long serialVersionUID = 1L;
	private static final String ImageRemoveEvent = "images/removeEvent.png";
	private static final String ImageLink = "images/link.png";
	private static final String ImageGraph = "images/graph.png";
	private JLabel lblEvent;
	private JButton btnRemoveEvent;
	private JComboBox<State> comboBoxState2;
	private JComboBox<State> comboBoxState1;
	private JLabel lblLink;
	private JLabel lblImage;
	private JComboBox<Event> comboBoxEvent;

	public DialogRemoveEvent() {
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 400);
		getContentPane().setLayout(null);

		lblEvent = new JLabel("Event Name:");
		lblEvent.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblEvent.setBounds(163, 108, 90, 14);
		getContentPane().add(lblEvent);

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

		comboBoxEvent = new JComboBox<Event>();
		comboBoxEvent.setBounds(163, 133, 107, 30);
		getContentPane().add(comboBoxEvent);

	}

	public void actionListener(ActionListener actionListener, ItemListener itemListener) {
		btnRemoveEvent.setActionCommand("removeEvent");
		btnRemoveEvent.addActionListener(actionListener);
		comboBoxState1.addItemListener(itemListener);
		comboBoxState2.addItemListener(itemListener);
		comboBoxEvent.addItemListener(itemListener);
	}

	public JComboBox<Event> getComboBoxEvent() {
		return comboBoxEvent;
	}

	public JComboBox<State> getComboBoxState1() {
		return comboBoxState1;
	}

	public JComboBox<State> getComboBoxState2() {
		return comboBoxState2;
	}
}
