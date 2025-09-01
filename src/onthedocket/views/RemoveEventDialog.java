package onthedocket.views;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.time.LocalDate;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

import onthedocket.models.Event;
import onthedocket.persistence.DataManager;

@SuppressWarnings("serial")
public class RemoveEventDialog extends JDialog {
	private final LocalDate date;
	private final JList<Event> eventJList;
	private boolean removed = false;

	/**
     * Constructs a modal dialog owned by the given frame, initializes UI components,
     * and positions the dialog relative to its owner.
     *
     * @param owner the parent frame to which this dialog is modal
     */
	public RemoveEventDialog(JFrame owner, LocalDate date) {
		super(owner, "Remove Events: " + date, true);
		this.date = date;
		
		List<Event> events = DataManager.getEventsOn(date);
		
		eventJList = new JList<>(events.toArray(new Event[0]));
        eventJList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		
		initComponents();
		pack();
		setLocationRelativeTo(owner);
	}
	
	public boolean wasRemoved() {
        return removed;
    }
	
	private void initComponents() {
		JPanel content = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 12, 8, 12);
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        content.add(new JLabel("Select event(s) to remove:"), gbc);

        gbc.gridy = 1;
        JScrollPane scroll = new JScrollPane(eventJList);
        content.add(scroll, gbc);

        JPanel buttons = new JPanel();
        JButton removeBtn = new JButton("Remove");
        JButton cancelBtn = new JButton("Cancel");

        removeBtn.addActionListener(e -> onRemove());
        cancelBtn.addActionListener(e -> dispose());

        buttons.add(removeBtn);
        buttons.add(cancelBtn);

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(content, BorderLayout.CENTER);
        getContentPane().add(buttons, BorderLayout.SOUTH);
	}
	
	private void onRemove() {
        List<Event> selected = eventJList.getSelectedValuesList();
        if (selected.isEmpty()) {
            JOptionPane.showMessageDialog(
                this,
                "Please select at least one event.",
                "No Selection",
                JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        int choice = JOptionPane.showConfirmDialog(
            this,
            "Remove " + selected.size() + " event(s)?",
            "Confirm Removal",
            JOptionPane.YES_NO_OPTION
        );
        if (choice != JOptionPane.YES_OPTION) {
            return;
        }

        for (Event e : selected) {
            DataManager.removeEvent(e);
        }
        removed = true;
        dispose();
    }

}
