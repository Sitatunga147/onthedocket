package onthedocket.models;

import java.awt.Color;

public class EventCategory {
	public static final EventCategory DEFAULT = new EventCategory("Default", Color.GRAY);
	public static final EventCategory WORK = new EventCategory("Work", Color.RED);
	public static final EventCategory SCHOOL = new EventCategory("School", Color.GREEN);
	public static final EventCategory PERSONAL = new EventCategory("Personal", Color.BLUE);
	
	private String name;
	private Color color;

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
