package onthedocket.views;

import javax.swing.*;
import java.util.*;
import java.awt.*;

@SuppressWarnings("serial")
public class AddEventDialog extends JDialog {
	private JTextField nameField;
	private JSpinner dateSpinner;
	private JSpinner timeSpinner;
	
	public AddEventDialog(JFrame parent) {
		super(parent, "Add Event", true);
		
		setLayout(new BorderLayout());
		
		initializeComponents();
		pack();
		
		setLocationRelativeTo(parent);
		setVisible(true);
	}
	
	public void initializeComponents() {
		JPanel panel = new JPanel(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(10, 10, 10, 10);
		
		nameField = new JTextField(20);
		dateSpinner = new JSpinner(new SpinnerDateModel());
		dateSpinner.setEditor(new JSpinner.DateEditor(dateSpinner, "MM-dd-yyyy"));
		timeSpinner = new JSpinner(new SpinnerDateModel(new Date(), null, null, Calendar.MINUTE));
		timeSpinner.setEditor(new JSpinner.DateEditor(timeSpinner, "HH:mm"));
		
		JButton finishButton = new JButton("Finish");
		finishButton.addActionListener(e -> logAndDispose());
		JButton cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(e -> dispose());
		
		gbc.gridx = 0; gbc.gridy = 0;
		panel.add(new JLabel("Name: "), gbc);
		gbc.gridx = 1;
		panel.add(nameField, gbc);
		gbc.gridx = 0; gbc.gridy = 1;
		panel.add(new JLabel("Date: "), gbc);
		gbc.gridx = 1;
		panel.add(dateSpinner, gbc);
		gbc.gridx = 0; gbc.gridy = 2;
		panel.add(new JLabel("Time: "), gbc);
		gbc.gridx = 1;
		panel.add(timeSpinner, gbc);
		
		JPanel buttonsPanel = new JPanel();
		buttonsPanel.add(finishButton);
		buttonsPanel.add(cancelButton);
		
		add(panel, BorderLayout.CENTER);
		add(buttonsPanel, BorderLayout.SOUTH);
	}
	
	private void logAndDispose() {
		dispose();
	}
}
