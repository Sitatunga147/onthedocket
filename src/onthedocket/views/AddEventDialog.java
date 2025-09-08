package onthedocket.views;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;

import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;

import onthedocket.models.Event;
import onthedocket.models.EventCategory;
import onthedocket.persistence.DataManager;

/**
 * Dialog for creating a new calendar event. Presents input fields for event name,
 * start date/time, end date/time, and category selection. Performs validation
 * and, on confirmation, adds the event to the DataManager.
 * 
 * @author Sitatunga147 (with moderate AI assistance)
 */
@SuppressWarnings("serial")
public class AddEventDialog extends JDialog {
	private JTextField nameField;
	private JSpinner startDateSpinner, startTimeSpinner, endDateSpinner, endTimeSpinner;
	private JComboBox<EventCategory> categoryCombo;
	private boolean added = false;
	
	/**
     * Constructs a modal dialog owned by the given frame, initializes UI components,
     * and positions the dialog relative to its owner.
     *
     * @param owner the parent frame to which this dialog is modal
     */
	public AddEventDialog(JFrame owner) {
		super(owner, "Add Event", true);
		initComponents();
		pack();
		setLocationRelativeTo(owner);
	}
	
	/**
     * Constructs a modal dialog owned by the given frame, initializes UI components,
     * and positions the dialog relative to its owner.
     * 
     * This dialog will use the passed date to default the JSpinner dates.
     *
     * @param owner the parent frame to which this dialog is modal
     * @param defaultDate the date for defaulting JSpinners
     */
	public AddEventDialog(JFrame owner, LocalDate defaultDate) {
		super(owner, "Add Event", true);
		initComponents();
		LocalDateTime startDateTime = defaultDate.atTime(LocalTime.now().withSecond(0).withNano(0));
		Date initial = Date.from(startDateTime.atZone(ZoneId.systemDefault()).toInstant());
		
		startDateSpinner.setValue(initial);
		startTimeSpinner.setValue(initial);
		updateEndSpinners();
		
		pack();
		setLocationRelativeTo(owner);
	}
	
	/**
     * Indicates whether the "Add" action resulted in a new event being stored.
     *
     * @return true if an event was added; false if the dialog was cancelled or validation failed
     */
	public boolean wasAdded() {
        return added;
    }
	
	/**
     * Builds and arranges all labels, fields, spinners, combo box, and buttons
     * within the dialog using a GridBagLayout and a FlowLayout for controls.
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
		content.add(new JLabel("Start Date:"), gbc);
		gbc.gridx = 1;
		startDateSpinner = buildDateSpinner();
		content.add(startDateSpinner, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 2;
		content.add(new JLabel("Start Time:"), gbc);
		gbc.gridx = 1;
		startTimeSpinner = buildTimeSpinner();
		content.add(startTimeSpinner, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 3;
		content.add(new JLabel("End Date:"), gbc);
		gbc.gridx = 1;
		endDateSpinner = buildDateSpinner();
		content.add(endDateSpinner, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 4;
		content.add(new JLabel("End Time:"), gbc);
		gbc.gridx = 1;
		endTimeSpinner = buildTimeSpinner();
		content.add(endTimeSpinner, gbc);
		
		updateEndSpinners();
		startDateSpinner.addChangeListener(e -> updateEndSpinners());
		startTimeSpinner.addChangeListener(e -> updateEndSpinners());
		
		gbc.gridx = 0; gbc.gridy = 5;
        content.add(new JLabel("Category:"), gbc);
        categoryCombo = new JComboBox<>(new DefaultComboBoxModel<>(DataManager.getCategories().toArray(new EventCategory[0])));
        for (EventCategory preset : new EventCategory[]{
                EventCategory.DEFAULT,
                EventCategory.WORK,
                EventCategory.SCHOOL,
                EventCategory.PERSONAL
        }) {
            if (!DataManager.getCategories().contains(preset)) {
                categoryCombo.addItem(preset);
            }
        }
        categoryCombo.setRenderer(new CategoryRenderer());
        gbc.gridx = 1;
        content.add(categoryCombo, gbc);

		
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
     * Creates a date spinner preconfigured to show and parse dates in "yyyy-MM-dd" format.
     *
     * @return a JSpinner for date selection
     */
	private JSpinner buildDateSpinner() {
		SpinnerDateModel model = new SpinnerDateModel(new Date(), null, null, Calendar.DAY_OF_MONTH);
	    JSpinner spinner = new JSpinner(model);
	    spinner.setEditor(new JSpinner.DateEditor(spinner, "yyyy-MM-dd"));
	    return spinner;
	}
	
	/**
     * Creates a time spinner preconfigured to show and parse times in "HH:mm" format.
     *
     * @return a JSpinner for time selection
     */
	private JSpinner buildTimeSpinner() {
		SpinnerDateModel model = new SpinnerDateModel(new Date(), null, null, Calendar.MINUTE);
	    JSpinner spinner = new JSpinner(model);
	    spinner.setEditor(new JSpinner.DateEditor(spinner, "HH:mm"));
	    return spinner;
	}
	
	/**
     * Validates the name, ensures the end is not before the start,
     * and if valid, constructs a new Event and adds it to DataManager.
     * Sets the added flag and closes the dialog.
     */
	private void onAdd() {
        String name = nameField.getText().trim();
        if (name.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a name.", "Validation Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        LocalDateTime start = mergeDateAndTime((Date)startDateSpinner.getValue(), (Date)startTimeSpinner.getValue());
        LocalDateTime end = mergeDateAndTime((Date)endDateSpinner.getValue(), (Date)endTimeSpinner.getValue());
        if (end.isBefore(start)) {
            JOptionPane.showMessageDialog(this, "End must be at or after start.", "Validation Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        EventCategory category = (EventCategory) categoryCombo.getSelectedItem();
        DataManager.addEvent(new Event(name, start, end, category));
        added = true;
        dispose();
    }
	
	/**
     * Synchronizes the end-date and end-time spinners to default one hour
     * after the currently selected start date/time.
     */
	private void updateEndSpinners() {
	    Date datePart = (Date) startDateSpinner.getValue();
	    Date timePart = (Date) startTimeSpinner.getValue();

	    LocalDateTime start = mergeDateAndTime(datePart, timePart);
	    LocalDateTime endDefault = start.plusHours(1);

	    Date d = Date.from(endDefault.atZone(ZoneId.systemDefault()).toInstant());

	    endDateSpinner.setValue(d);
	    endTimeSpinner.setValue(d);
	}


	/**
     * Combines the given date and time into a single LocalDateTime,
     * discarding seconds and nanoseconds.
     *
     * @param datePart the Date representing the calendar date
     * @param timePart the Date representing the time of day
     * @return a LocalDateTime instance merging both inputs
     */
	private LocalDateTime mergeDateAndTime(Date datePart, Date timePart) {
	    LocalDate localDate = Instant.ofEpochMilli(datePart.getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
	    LocalTime localTime = Instant.ofEpochMilli(timePart.getTime()).atZone(ZoneId.systemDefault()).toLocalTime().withSecond(0).withNano(0);
	    return LocalDateTime.of(localDate, localTime);
	}

	/**
     * Custom renderer that displays each EventCategory by its name
     * and paints the label in the category's associated color.
     */
	private static class CategoryRenderer extends DefaultListCellRenderer {
		
		/**
         * Configures and returns the component used to render an item
         * in the category combo box.
         *
         * @param list         the JList to paint
         * @param value        the value to display (an EventCategory)
         * @param index        the cell index
         * @param isSelected   true if the cell is selected
         * @param cellHasFocus true if the cell has focus
         * @return a JLabel configured with the category's name and color
         */
        @Override
        public Component getListCellRendererComponent(
            JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {

            JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

            if (value instanceof EventCategory) {
                EventCategory cat = (EventCategory) value;
                label.setText(cat.getName());
                label.setForeground(cat.getColor());
            }
            return label;
        }
    }

}
