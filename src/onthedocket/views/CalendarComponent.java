package onthedocket.views;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import onthedocket.models.Event;
import onthedocket.persistence.DataManager;
import onthedocket.utils.Theme;

/**
 * Renders a monthly calendar view that displays day cells, headers, and navigation controls.
 * Fetches events from DataManager and displays them on their respective dates, or on each
 * date they span for multi-day events, using time labels and color-coded categories.
 * Supports theme changes and month navigation.
 *
 * @see onthedocket.persistence.DataManager
 * @see onthedocket.models.Event
 * @see onthedocket.utils.Theme
 * 
 * @author Sitatunga147 (with moderate AI assistance)
 */
@SuppressWarnings("serial")
public class CalendarComponent extends JComponent {
	private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("h:mm a");
	private LocalDate referenceDate;
	private JPanel headerPanel, calendarPanel, bottomPanel;
	private Theme theme;
	private ArrayList<Event> events;
	
	/**
     * Creates a CalendarComponent for the current date using the default LIGHT theme.
     */
	public CalendarComponent() {
		this(LocalDate.now(), Theme.LIGHT);
	}
	
	/**
     * Creates a CalendarComponent for the specified date using the default LIGHT theme.
     *
     * @param date the month reference date to display
     */
	public CalendarComponent(LocalDate date) {
		this(date, Theme.LIGHT);
	}

	/**
     * Creates a CalendarComponent for the specified date and theme.
     *
     * @param date the month reference date to display
     * @param theme the visual theme to apply
     */
	public CalendarComponent(LocalDate date, Theme theme) {
		this.referenceDate = date;
		
		setLayout(new BorderLayout());
		setTheme(theme);
	}
	
	/**
     * Updates the calendar to display the month containing the new reference date,
     * rebuilds header, day cells, and bottom navigation, then repaints the component.
     *
     * @param newReferenceDate the date to center the calendar view on
     */
	public void updateWith(LocalDate newReferenceDate) {
		this.referenceDate = newReferenceDate;
		removeAll();
		
		events = DataManager.getEvents();
		
		initHeader();
		initCalendar();
		initBottom();
		
		revalidate();
		repaint();
	}
	
	/**
     * Constructs a single day cell panel with vertical layout, background color,
     * and border styling from the current theme.
     *
     * @return a configured JPanel representing one calendar day cell
     */
	private JPanel buildDayCell() {
		JPanel cell = new JPanel();
		cell.setLayout(new BoxLayout(cell, BoxLayout.Y_AXIS));
		cell.setBackground(theme.getBackgroundColor());
		cell.setBorder(BorderFactory.createLineBorder(theme.getSecondaryColor()));
		return cell;
	}
	
	/**
     * Initializes and lays out the header row showing the names of the days of the week.
     */
	private void initHeader() {
		headerPanel = new JPanel(new GridLayout(1, 7));
		for(int i = 1; i <= 7; i++) {
			DayOfWeek day = DayOfWeek.of((i+5)%7+1);
			
			JPanel headerCell = new JPanel();
			headerCell.setLayout(new BoxLayout(headerCell, BoxLayout.Y_AXIS));
			headerCell.setBackground(theme.getSecondaryColor());
			headerCell.setBorder(BorderFactory.createEtchedBorder());
			JLabel headerLabel = new JLabel(day.toString());
			headerLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
			headerLabel.setForeground(theme.getPrimaryTextColor());
			headerCell.add(headerLabel);
			headerPanel.add(headerCell);
		}
		add(headerPanel, BorderLayout.NORTH);
	}
	
	/**
     * Initializes and lays out the main calendar grid for the current reference month,
     * including placeholder cells, daily event entries, and multi-day events.
     * Multi-day events appear on each day they span with adjusted time labels
     * and panels rendered with an alternate color.
     */
	private void initCalendar() {
		calendarPanel = new JPanel(new GridLayout(0, 7));
		calendarPanel.setBackground(theme.getBackgroundColor());
		int placeholders = referenceDate.withDayOfMonth(1).getDayOfWeek().getValue() % 7;
		for(int i = 1; i <= placeholders; i++) {
			JPanel cell = new JPanel();
			cell.setBackground(theme.getBackgroundColor());
			calendarPanel.add(cell);
		}
		
		for(int i = 1; i <= referenceDate.lengthOfMonth(); i++) {
			LocalDate date = referenceDate.withDayOfMonth(i);
			ArrayList<Event> todayEvents = new ArrayList<Event>();
			for(Event e : events) {
				LocalDate start = e.getStart().toLocalDate();
				LocalDate end = e.getEnd().toLocalDate();
				if(!date.isBefore(start) && !date.isAfter(end)) {
					todayEvents.add(e);
				}
			}
			JPanel cell = buildDayCell();
			JLabel day = new JLabel(String.valueOf(i));
			day.setForeground(theme.getSecondaryTextColor());
			cell.add(day);
			
			for(Event e : todayEvents) {
				JPanel eventPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
				LocalDate start = e.getStart().toLocalDate();
				LocalDate end = e.getEnd().toLocalDate();
				boolean isMultiDay = start.isBefore(end);
				eventPanel.setBackground(isMultiDay ? theme.getSecondaryColor() : theme.getBackgroundColor());
				
				LocalDateTime cellStart = date.isEqual(start) ? e.getStart() : LocalDateTime.of(date, LocalTime.MIN);
				LocalDateTime cellEnd = date.isEqual(end) ? e.getEnd() : LocalDateTime.of(date, LocalTime.of(23, 59));
				JLabel timeLabel = new JLabel(cellStart.format(TIME_FORMATTER) + " - " + cellEnd.format(TIME_FORMATTER));
				timeLabel.setForeground(theme.getPrimaryTextColor());
				JLabel eventLabel = new JLabel(e.getName());
				eventLabel.setForeground(e.getCategory().getColor());
				eventPanel.add(timeLabel);
				eventPanel.add(eventLabel);
				cell.add(eventPanel);
			}
			
			final LocalDate cellDate = date;
			cell.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent me) {
					JFrame owner = (JFrame) SwingUtilities.getWindowAncestor(CalendarComponent.this);
					CellDialog dialog = new CellDialog(owner, cellDate);
					dialog.setVisible(true);
				}
			});
			
			calendarPanel.add(cell);
		}
		add(calendarPanel, BorderLayout.CENTER);
	}
	
	/**
     * Initializes and lays out the bottom navigation panel, including
     * month‐prev/next buttons and the current month label.
     */
	private void initBottom() {
		bottomPanel = new JPanel(new FlowLayout());
		bottomPanel.setBackground(theme.getBackgroundColor());
		
		JButton leftButton = new JButton("◄");
		leftButton.setBackground(theme.getAccentColor());
		leftButton.setForeground(theme.getPrimaryTextColor());
		leftButton.addActionListener(e -> {
			updateWith(referenceDate.minusMonths(1));
		});
		JButton rightButton = new JButton("►");
		rightButton.setBackground(theme.getAccentColor());
		rightButton.setForeground(theme.getPrimaryTextColor());
		rightButton.addActionListener(e -> {
			updateWith(referenceDate.plusMonths(1));
		});
		JLabel monthLabel = new JLabel(referenceDate.getMonth().toString() + " " + referenceDate.getYear());
		monthLabel.setForeground(theme.getPrimaryTextColor());
		
		bottomPanel.add(leftButton);
		bottomPanel.add(monthLabel);
		bottomPanel.add(rightButton);
		add(bottomPanel, BorderLayout.SOUTH);
	}

	public Theme getTheme() {
		return theme;
	}

	/**
     * Applies a new theme to this component and refreshes its contents
     * to reflect updated styling.
     *
     * @param theme the new Theme to set
     */
	public void setTheme(Theme theme) {
		this.theme = theme;
		updateWith(referenceDate);
	}
}
