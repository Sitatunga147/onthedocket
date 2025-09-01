package onthedocket.views;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.time.LocalDate;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

/**
 * A modal dialog displayed when a calendar cell is clicked, offering options
 * to add or remove events for a specific date. The dialog title reflects
 * the selected date and its actions delegate to AddEventDialog or event
 * removal logic.
 * 
 * @author Sitatunga147 (with moderate AI assistance)
 */
@SuppressWarnings("serial")
public class CellDialog extends JDialog {
	private LocalDate selectedDate;
	
	/**
     * Constructs a modal CellDialog for the given date.
     * Initializes UI components, sets the title to the selected date,
     * and positions the dialog relative to the owner frame.
     *
     * @param owner the parent frame for modality and positioning
     * @param selectedDate the calendar date this dialog represents
     */
	public CellDialog(JFrame owner, LocalDate selectedDate) {
		super(owner, selectedDate.toString(), true);
		this.selectedDate = selectedDate;
		initComponents();
		pack();
		setLocationRelativeTo(owner);
	}
	
	/**
     * Builds and arranges all dialog components in a GridBagLayout.
     * Adds buttons for adding, removing, or cancelling events and
     * wires their corresponding action listeners.
     */
	private void initComponents() {
		JPanel content = new JPanel(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(4, 8, 4, 8);
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.weightx = 1.0;
		
		gbc.gridx = 0;
		gbc.gridy = 0;
		content.add(new JLabel("What would you like to do?"), gbc);
		gbc.gridy = 1;
		JButton addButton = new JButton("ADD EVENT");
		addButton.addActionListener(e -> {
		    JFrame owner = (JFrame) SwingUtilities.getWindowAncestor(this);
		    AddEventDialog addDialog = new AddEventDialog(owner, selectedDate);
		    addDialog.setVisible(true);
		    if (addDialog.wasAdded()) {
		    	((MainView) owner).getCalendarComponent().updateWith(selectedDate);
		    }
		    dispose();
		});
		content.add(addButton, gbc);
		gbc.gridy = 2;
		JButton removeButton = new JButton("REMOVE EVENT");
		removeButton.addActionListener(e -> {
			JFrame owner = (JFrame) SwingUtilities.getWindowAncestor(this);
			RemoveEventDialog removeDialog = new RemoveEventDialog(owner, selectedDate);
			removeDialog.setVisible(true);
			if(removeDialog.wasRemoved()) {
				((MainView) owner).getCalendarComponent().updateWith(selectedDate);
			}
			dispose();
		});
		content.add(removeButton, gbc);
		gbc.gridy = 3;
		JButton cancelButton = new JButton("CANCEL");
		cancelButton.addActionListener(e -> dispose());
		content.add(cancelButton, gbc);
		
		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(content, BorderLayout.CENTER);
	}
}
