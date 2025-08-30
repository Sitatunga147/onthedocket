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
	
	public CalendarComponent() {
		this(LocalDate.now(), Theme.LIGHT);
	}
	
	public CalendarComponent(LocalDate dateOnCalendar) {
		this(dateOnCalendar, Theme.LIGHT);
	}

	public CalendarComponent(LocalDate dateOnCalendar, Theme theme) {
		this.sampleDate = dateOnCalendar;
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
			headerCell.setBorder(BorderFactory.createEtchedBorder());
			JLabel headerLabel = new JLabel(day.toString());
			headerLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
			headerCell.add(headerLabel);
			headerPanel.add(headerCell);
		}
		add(headerPanel, BorderLayout.NORTH);
	}
	
	private void initCalendar() {
		calendarPanel = new JPanel(new GridLayout(0, 7));
		int placeholders = sampleDate.withDayOfMonth(1).getDayOfWeek().getValue() % 7;
		for(int i = 1; i <= placeholders; i++) {
			JPanel cell = new JPanel();
			calendarPanel.add(cell);
		}
		
		for(int i = 1; i <= sampleDate.lengthOfMonth(); i++) {
			JPanel cell = new JPanel();
			cell.setLayout(new BoxLayout(cell, BoxLayout.Y_AXIS));
			cell.setBorder(BorderFactory.createEtchedBorder());
			JLabel day = new JLabel(String.valueOf(i));
			cell.add(day);
			calendarPanel.add(cell);
		}
		add(calendarPanel, BorderLayout.CENTER);
	}
	
	private void initBottom() {
		bottomPanel = new JPanel(new FlowLayout());
		JButton leftButton = new JButton("◄");
		leftButton.addActionListener(e -> {
			updateWith(sampleDate.minusMonths(1));
		});
		JButton rightButton = new JButton("►");
		rightButton.addActionListener(e -> {
			updateWith(sampleDate.plusMonths(1));
		});
		JLabel monthLabel = new JLabel(sampleDate.getMonth().toString());
		
		bottomPanel.add(leftButton);
		bottomPanel.add(monthLabel);
		bottomPanel.add(rightButton);
		add(bottomPanel, BorderLayout.SOUTH);
	}
}
