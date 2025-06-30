package onthedocket.views;

import javax.swing.*;
import java.awt.*;

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
		JPanel calendarPanel = new JPanel();
		
		return calendarPanel;
	}
	
	private JPanel buildAnalyticsTab() {
		JPanel analyticsPanel = new JPanel();
		
		return analyticsPanel;
	}
}
