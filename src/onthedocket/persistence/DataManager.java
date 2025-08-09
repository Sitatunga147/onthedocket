package onthedocket.persistence;

import java.util.ArrayList;

import onthedocket.models.Event;
import onthedocket.models.EventCategory;

public final class DataManager {
	private static ArrayList<Event> events;
	private static ArrayList<EventCategory> categories;

	private DataManager() {
		throw new AssertionError();
	}
	
	public static void addEvent(Event e) {
		events.add(e);
	}
	
	public static void addCategory(EventCategory c) {
		categories.add(c);
	}
	
	public static boolean removeEvent(Event e) {
		return events.remove(e);
	}
	
	public static boolean removeCategory(EventCategory c) {
		return categories.remove(c);
	}

	public static ArrayList<Event> getEvents() {
		return events;
	}

	public static ArrayList<EventCategory> getCategories() {
		return categories;
	}
}
