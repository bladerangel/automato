package views;

import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import models.State;

import java.awt.Font;
import javax.swing.JComboBox;

public class DialogRemoveState extends JDialog {

	private static final long serialVersionUID = 1L;
	private static final String ImageRemoveState = "images/removeState.png";
	private static final String ImageGraph = "images/graph.png";
	private JLabel lblState;
	private JButton btnRemoveState;
	private JLabel lblImage;
	private JComboBox<State> comboBoxStates;

	public DialogRemoveState() {
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 313);
		getContentPane().setLayout(null);

		lblState = new JLabel("Name State:");
		lblState.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblState.setBounds(153, 116, 90, 14);
		getContentPane().add(lblState);

		btnRemoveState = new JButton("");
		btnRemoveState.setBounds(148, 210, 137, 40);
		btnRemoveState.setIcon(new ImageIcon(ImageRemoveState));
		getContentPane().add(btnRemoveState);
		
		lblImage = new JLabel("");
		lblImage.setBounds(176, 11, 81, 50);
		lblImage.setIcon(new ImageIcon(ImageGraph));
		getContentPane().add(lblImage);
		
		comboBoxStates = new JComboBox<State>();
		comboBoxStates.setBounds(151, 141, 132, 30);
		getContentPane().add(comboBoxStates);

	}

	public void actionListener(ActionListener actionListener) {
		btnRemoveState.setActionCommand("removeState");
		btnRemoveState.addActionListener(actionListener);
	}

	public JComboBox<State> getComboBoxStates() {
		return comboBoxStates;
	}
}
