package onthedocket.views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import onthedocket.models.EventCategory;
import onthedocket.persistence.DataManager;

/**
 * Dialog for creating a new event category. Presents input field for category name
 * and JColorChooser for category color. Adds the category to the DataManager after name
 * validation.
 * 
 * @author Sitatunga147 (with moderate AI assistance)
 */
@SuppressWarnings("serial")
public class AddCategoryDialog extends JDialog {
	private JTextField nameField;
	private JColorChooser colorChooser;
	private boolean added = false;
	
	/**
     * Constructs a modal dialog owned by the given frame, initializes UI components,
     * and positions the dialog relative to its owner.
     *
     * @param owner the parent frame to which this dialog is modal
     */
	public AddCategoryDialog(JFrame owner) {
		super(owner, "Add Category", true);
		initComponents();
		pack();
		setLocationRelativeTo(owner);
	}
	
	/**
     * Indicates whether the "Add" action resulted in a new category being stored.
     *
     * @return true if an event category was added; false if the dialog was cancelled or validation failed
     */
	public boolean wasAdded() {
        return added;
    }
	
	/**
     * Builds and arranges all fields, JColorChooser, and buttons
     * within the dialog using a GridBagLayout.
     */
	private void initComponents() {
		JPanel content = new JPanel(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(4, 8, 4, 8);
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.weightx = 1.0;
		
		gbc.gridx = 0;
		gbc.gridy = 0;
		content.add(new JLabel("Name:"), gbc);
		gbc.gridx = 1;
		nameField = new JTextField(20);
		content.add(nameField, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 1;
		colorChooser = new JColorChooser();
		content.add(colorChooser, gbc);
		
		JPanel buttons = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		JButton addButton = new JButton("ADD");
		addButton.addActionListener(e -> onAdd());
		JButton cancelButton = new JButton("CANCEL");
		cancelButton.addActionListener(e -> dispose());
		buttons.add(addButton);
		buttons.add(cancelButton);
		
		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(content, BorderLayout.CENTER);
		getContentPane().add(buttons, BorderLayout.SOUTH);
	}
	
	/**
     * Validates the name, and if valid,
     * constructs a new EventCategory and adds it to DataManager.
     * Sets the added flag and closes the dialog.
     */
	private void onAdd() {
		String name = nameField.getText().trim();
        if (name.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a name.", "Validation Error", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        Color color = colorChooser.getColor();
        
        DataManager.addCategory(new EventCategory(name, color));
        added = true;
        dispose();
	}
}
