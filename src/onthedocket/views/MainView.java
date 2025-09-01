package onthedocket.views;

import java.awt.BorderLayout;
import java.awt.event.KeyEvent;
import java.time.LocalDate;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;

import onthedocket.persistence.DataManager;
import onthedocket.persistence.Serializer;
import onthedocket.utils.Theme;

/**
 * The main application window for OnTheDocket.
 * <p>
 * Extends {@link JFrame} to provide a full‐screen calendar interface,
 * theme selection, navigation controls, and help dialog.
 * </p>
 * 
 * @author Sitatunga147 (with moderate AI assistance)
 */
@SuppressWarnings("serial")
public class MainView extends JFrame {
	private Theme theme;
	private JMenuBar menubar;
	private CalendarComponent calendar;

	/**
     * Constructs the main view using the default light theme.
     */
	public MainView() {
		this(Theme.LIGHT);
	}
	
	/**
     * Constructs the main view with the specified theme.
     *
     * @param theme the initial {@link Theme} to apply to this window
     */
	public MainView(Theme theme) {
		setTheme(theme);
		
		setTitle("OnTheDocket");
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		initComponents();
		
		setVisible(true);
	}

	/**
     * Builds and lays out all UI components, including the menu bar
     * and calendar component, and sets up action listeners.
     */
	private void initComponents() {
		getContentPane().setForeground(theme.getBackgroundColor());
		setLayout(new BorderLayout());
		
		menubar = new JMenuBar();
		
		JMenu fileMenu = new JMenu("File");
		JMenuItem saveItem = new JMenuItem("Save");
		saveItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, KeyEvent.CTRL_DOWN_MASK));
		saveItem.addActionListener(e -> Serializer.serialize());
		fileMenu.add(saveItem);
		fileMenu.setMnemonic(KeyEvent.VK_F);
		
		JMenu addMenu = new JMenu("Add");
		JMenuItem addEventItem = new JMenuItem("Add Event");
		addEventItem.addActionListener(e -> {
			AddEventDialog dialog = new AddEventDialog(this);
			dialog.setVisible(true);
			if(dialog.wasAdded()) {
				calendar.updateWith(LocalDate.now());
			}
		});
		addMenu.add(addEventItem);
		addMenu.setMnemonic(KeyEvent.VK_A);
		
		JMenu themeMenu = new JMenu("Theme");
		JMenuItem lightItem = new JMenuItem("Light");
		lightItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L, KeyEvent.CTRL_DOWN_MASK));
		lightItem.addActionListener(e -> setTheme(Theme.LIGHT));
		JMenuItem darkItem = new JMenuItem("Dark");
		darkItem.addActionListener(e -> setTheme(Theme.DARK));
		darkItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_D, KeyEvent.CTRL_DOWN_MASK));
		JMenuItem sunriseItem = new JMenuItem("Sunrise");
		sunriseItem.addActionListener(e -> setTheme(Theme.SUNRISE));
		themeMenu.add(lightItem);
		themeMenu.add(darkItem);
		themeMenu.add(sunriseItem);
		themeMenu.setMnemonic(KeyEvent.VK_T);
		
		JMenu helpMenu = new JMenu("Help");
		JMenuItem todayItem = new JMenuItem("Jump To Today");
		todayItem.addActionListener(e -> calendar.updateWith(LocalDate.now()));
		JMenuItem aboutItem = new JMenuItem("About");
		aboutItem.addActionListener(e -> {
			JOptionPane.showMessageDialog(this, "OnTheDocket PRE-RELEASE Version by Sitatunga147", "About", JOptionPane.INFORMATION_MESSAGE);
		});
		helpMenu.add(todayItem);
		helpMenu.add(aboutItem);
		
		menubar.add(fileMenu);
		menubar.add(addMenu);
		menubar.add(themeMenu);
		menubar.add(helpMenu);
		
		setJMenuBar(menubar);
		
		calendar = new CalendarComponent(LocalDate.now(), theme);
		add(calendar, BorderLayout.CENTER);
	}
	
	public CalendarComponent getCalendarComponent() {
		return calendar;
	}

	public Theme getTheme() {
		return theme;
	}

	/**
     * Applies a new theme to this view, rebuilds the UI components,
     * and repaints the window.
     *
     * @param theme the new {@link Theme} to set
     */
	public void setTheme(Theme theme) {
		DataManager.setTheme(theme);
		this.theme = theme;
		getContentPane().removeAll();
		initComponents();
		
		revalidate();
		repaint();
	}
}
