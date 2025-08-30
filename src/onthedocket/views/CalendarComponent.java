package onthedocket.views;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.time.DayOfWeek;
import java.time.LocalDate;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

import onthedocket.utils.Theme;

@SuppressWarnings("serial")
public class CalendarComponent extends JComponent {
	private LocalDate sampleDate;
	private JPanel headerPanel;
	private JPanel calendarPanel;
	private JPanel bottomPanel;
	private Theme theme;
	
	public CalendarComponent() {
		this(LocalDate.now(), Theme.LIGHT);
	}
	
	public CalendarComponent(LocalDate dateOnCalendar) {
		this(dateOnCalendar, Theme.LIGHT);
	}

	public CalendarComponent(LocalDate dateOnCalendar, Theme theme) {
		this.sampleDate = dateOnCalendar;
		setTheme(theme);
		
		setLayout(new BorderLayout());
		initHeader();
		initCalendar();
		initBottom();
	}
	
	private void updateWith(LocalDate dateOnCalendar) {
		this.sampleDate = dateOnCalendar;
		removeAll();
		
		initHeader();
		initCalendar();
		initBottom();
		
		revalidate();
		repaint();
	}
	
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
			headerLabel.setForeground(theme.getTextColor());
			headerCell.add(headerLabel);
			headerPanel.add(headerCell);
		}
		add(headerPanel, BorderLayout.NORTH);
	}
	
	private void initCalendar() {
		calendarPanel = new JPanel(new GridLayout(0, 7));
		calendarPanel.setBackground(theme.getBackgroundColor());
		int placeholders = sampleDate.withDayOfMonth(1).getDayOfWeek().getValue() % 7;
		for(int i = 1; i <= placeholders; i++) {
			JPanel cell = new JPanel();
			cell.setBackground(theme.getBackgroundColor());
			calendarPanel.add(cell);
		}
		
		for(int i = 1; i <= sampleDate.lengthOfMonth(); i++) {
			JPanel cell = new JPanel();
			cell.setLayout(new BoxLayout(cell, BoxLayout.Y_AXIS));
			cell.setBackground(theme.getBackgroundColor());
			cell.setBorder(BorderFactory.createLineBorder(theme.getSecondaryColor()));
			JLabel day = new JLabel(String.valueOf(i));
			day.setForeground(theme.getTextColor2());
			cell.add(day);
			calendarPanel.add(cell);
		}
		add(calendarPanel, BorderLayout.CENTER);
	}
	
	private void initBottom() {
		bottomPanel = new JPanel(new FlowLayout());
		bottomPanel.setBackground(theme.getBackgroundColor());
		
		JButton leftButton = new JButton("◄");
		leftButton.setBackground(theme.getAccentColor());
		leftButton.setForeground(theme.getTextColor());
		leftButton.addActionListener(e -> {
			updateWith(sampleDate.minusMonths(1));
		});
		JButton rightButton = new JButton("►");
		rightButton.setBackground(theme.getAccentColor());
		rightButton.setForeground(theme.getTextColor());
		rightButton.addActionListener(e -> {
			updateWith(sampleDate.plusMonths(1));
		});
		JLabel monthLabel = new JLabel(sampleDate.getMonth().toString());
		monthLabel.setForeground(theme.getTextColor());
		
		bottomPanel.add(leftButton);
		bottomPanel.add(monthLabel);
		bottomPanel.add(rightButton);
		add(bottomPanel, BorderLayout.SOUTH);
	}

	public Theme getTheme() {
		return theme;
	}

	public void setTheme(Theme theme) {
		this.theme = theme;
		updateWith(sampleDate);
	}
}
