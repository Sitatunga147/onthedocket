package onthedocket.views;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.*;
import java.time.format.*;

@SuppressWarnings("serial")
public class CalendarComponent extends Grid {
	private ArrayList<LocalDate> dateArray;

	public CalendarComponent(LocalDate date, int rows) {
		super(rows, 7);
		resetWith(date);
	}
	
	public void resetWith(LocalDate date) {
		clear();
		dateArray = new ArrayList<LocalDate>();
		LocalDate sunday = date.with(TemporalAdjusters.previousOrSame(DayOfWeek.SUNDAY));
		for(int d = 0; d < 7; d++) {
			dateArray.add(sunday.plusDays(d));
		}
		
		initializeHeaderRow();
		initializeRemainingCells();
		
		revalidate();
		repaint();
	}
	
	private void initializeHeaderRow() {
		DateTimeFormatter monthDayFormatter = DateTimeFormatter.ofPattern("MMMM d", Locale.US);
		
		for(int c = 0; c < getColumns(); c++) {
			LocalDate date = dateArray.get(c);
			
			String dayOfWeek = date.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.US);
			String calendarDate = date.format(monthDayFormatter);
			
			JPanel cell = new JPanel(new GridBagLayout());
			cell.setBackground(Color.BLACK);
			
			JLabel dayOfWeekLabel = new JLabel(dayOfWeek, SwingConstants.CENTER);
			dayOfWeekLabel.setForeground(Color.WHITE);
			dayOfWeekLabel.setFont(new Font(Font.SERIF, Font.BOLD, 25));
			
			JLabel dateLabel = new JLabel(calendarDate, SwingConstants.CENTER);
			dateLabel.setForeground(Color.LIGHT_GRAY);
			dateLabel.setFont(new Font(Font.SERIF, Font.BOLD, 20));
			
			GridBagConstraints gbc = new GridBagConstraints();
			gbc.gridx = 0;
			gbc.weightx = 1;
			gbc.weighty = 0;
			gbc.fill = GridBagConstraints.NONE;
			gbc.anchor = GridBagConstraints.CENTER;
			
			gbc.gridy = 0;
			cell.add(dayOfWeekLabel, gbc);
			gbc.gridy = 1;
			cell.add(dateLabel, gbc);
			
			insertComponent(cell, 0, c);
		}
	}
	
	private void initializeRemainingCells() {
		for(int r = 1; r < getRows(); r++) {
			for(int c = 0; c < getColumns(); c++) {
				JPanel cell = new JPanel();
				cell.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
				insertComponent(cell, r, c);
			}
		}
	}
	
	public ArrayList<LocalDate> getDateArray() {
		return dateArray;
	}
}
