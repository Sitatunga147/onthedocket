package onthedocket.models;

import java.awt.Color;
import java.io.Serializable;

/**
 * Represents a classification of calendar events, providing a name
 * and an associated {@link Color} for visual styling in the calendar UI.
 * <p>
 * Four common categories are provided out-of-the-box:
 * {@link #DEFAULT}, {@link #WORK}, {@link #SCHOOL}, and {@link #PERSONAL}.
 * Developers may also instantiate custom categories as needed.
 * </p>
 * 
 * @author Sitatunga147 (with moderate AI assistance)
 */
public class EventCategory implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * A catch-all category for events without a specific type, rendered in gray.
	 */
	public static final EventCategory DEFAULT = new EventCategory("Default", Color.GRAY);
	/**
	 * A category for work-related events, rendered in red.
	 */
	public static final EventCategory WORK = new EventCategory("Work", Color.RED);
	/**
	 * A category for school or academic events, rendered in green.
	 */
	public static final EventCategory SCHOOL = new EventCategory("School", Color.GREEN);
	/**
	 * A category for personal events, rendered in blue.
	 */
	public static final EventCategory PERSONAL = new EventCategory("Personal", Color.BLUE);
	
	private String name;
	private Color color;

	/**
	 * Constructs an {@code EventCategory} with the given display name and color.
	 * 
	 * @param name the display name for this category
	 * @param color the {@link Color} used to distinguish events of this category
	 * @throws NullPointerException if {@code name} or {@code color} is {@code null}
	 */
	public EventCategory(String name, Color color) {
		setName(name);
		setColor(color);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}
}
