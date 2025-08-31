package onthedocket.views;

import java.awt.BorderLayout;
import java.time.LocalDate;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import onthedocket.utils.Theme;

@SuppressWarnings("serial")
public class MainView extends JFrame {
	private Theme theme;
	private JMenuBar menubar;

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
		
		menubar = new JMenuBar();
		JMenu themeMenu = new JMenu("Theme");
		JMenuItem lightItem = new JMenuItem("Light");
		lightItem.addActionListener(e -> {
			setTheme(Theme.LIGHT);
		});
		JMenuItem darkItem = new JMenuItem("Dark");
		darkItem.addActionListener(e -> {
			setTheme(Theme.DARK);
		});
		JMenuItem sunriseItem = new JMenuItem("Sunrise");
		sunriseItem.addActionListener(e -> {
			setTheme(Theme.SUNRISE);
		});
		themeMenu.add(lightItem);
		themeMenu.add(darkItem);
		themeMenu.add(sunriseItem);
		menubar.add(themeMenu);
		
		setJMenuBar(menubar);
		add(new CalendarComponent(LocalDate.now(), theme));
	}

	public Theme getTheme() {
		return theme;
	}

	public void setTheme(Theme theme) {
		this.theme = theme;
		getContentPane().removeAll();
		initComponents();
		
		revalidate();
		repaint();
	}
}
