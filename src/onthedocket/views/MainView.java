package onthedocket.views;

import javax.swing.*;
import java.awt.*;
import java.time.*;

@SuppressWarnings("serial")
public class MainView extends JFrame {
	private JMenuBar menuBar;
	private JTabbedPane tabbedPane;
	private JLabel statusBar;
	
	public MainView() {
		setTitle("OnTheDocket");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setLayout(new BorderLayout());
		
		initializeMenuBar();
		initializePane();
		initializeStatusBar();
		
		add(tabbedPane, BorderLayout.CENTER);
		add(statusBar, BorderLayout.SOUTH);
		
		setVisible(true);
	}
	
	private void initializeMenuBar() {
		menuBar = new JMenuBar();
		
		JMenu file = new JMenu("File");
		JMenu edit = new JMenu("Edit");
		JMenu view = new JMenu("View");
		
		menuBar.add(file);
		menuBar.add(edit);
		menuBar.add(view);
		setJMenuBar(menuBar);
	}
	
	private void initializeStatusBar() {
		statusBar = new JLabel("Ready.");
		statusBar.setBorder(BorderFactory.createEtchedBorder());
	}
	
	private void initializePane() {
		tabbedPane = new JTabbedPane();
		
		tabbedPane.addTab("Calendar", buildCalendarTab());
		tabbedPane.addTab("Analytics", buildAnalyticsTab());
	}
	
	private JPanel buildCalendarTab() {
		CalendarComponent calendar = new CalendarComponent(LocalDate.now(), 15);
		JButton leftButton = new JButton("◄");
		leftButton.setFont(leftButton.getFont().deriveFont(20.0f));
		leftButton.addActionListener(e -> {
			calendar.resetWith(calendar.getDateArray().get(0).minusWeeks(1));
		});
		JButton rightButton = new JButton("►");
		rightButton.setFont(rightButton.getFont().deriveFont(20.0f));
		rightButton.addActionListener(e -> {
			calendar.resetWith(calendar.getDateArray().get(0).plusWeeks(1));
		});
		
		JPanel calendarPanel = new JPanel(new BorderLayout());
		calendarPanel.add(calendar, BorderLayout.CENTER);
		calendarPanel.add(leftButton, BorderLayout.WEST);
		calendarPanel.add(rightButton, BorderLayout.EAST);
		
		return calendarPanel;
	}
	
	private JPanel buildAnalyticsTab() {
		JPanel analyticsPanel = new JPanel();
		
		return analyticsPanel;
	}
}
