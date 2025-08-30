package onthedocket.views;

import java.awt.BorderLayout;
import java.time.LocalDate;

import javax.swing.JFrame;

import onthedocket.utils.Theme;

@SuppressWarnings("serial")
public class MainView extends JFrame {
	private Theme theme;

	public MainView() {
		this(Theme.LIGHT);
	}
	
	public MainView(Theme theme) {
		setTheme(theme);
		
		setTitle("OnTheDocket");
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		initComponents();
		
		setVisible(true);
	}

	private void initComponents() {
		getContentPane().setForeground(theme.getBackgroundColor());
		setLayout(new BorderLayout());
		
		add(new CalendarComponent(LocalDate.now(), theme));
	}

	public Theme getTheme() {
		return theme;
	}

	public void setTheme(Theme theme) {
		this.theme = theme;
		initComponents();
	}
}
