package onthedocket.persistence;

import java.time.LocalDate;
import java.util.ArrayList;

import onthedocket.models.Event;
import onthedocket.models.EventCategory;
import onthedocket.utils.Theme;

/**
 * The DataManager is the central in-memory repository for calendar events, event categories,
 * and the application theme. Provides static methods to add, remove, and query {@link Event} and {@link EventCategory} instances,
 * as well as to get or set the current {@link Theme}. This class is non-instantiable
 * and all data is held in static collections.
 * 
 * @author Sitatunga147 (with moderate AI assistance)
 */
public final class DataManager {
	private static ArrayList<Event> events = new ArrayList<Event>();
	private static ArrayList<EventCategory> categories = new ArrayList<EventCategory>();
	private static Theme theme;

	/**
	 * Private constructor to prevent external instantiation.
	 * 
	 * @throws AssertionError always thrown to enforce non-instantiability
	 */
	private DataManager() {
		throw new AssertionError();
	}
	
	/**
	 * Adds the given event to the repository.
	 * 
	 * @param e the {@link Event} to add; must not be null
	 */
	public static void addEvent(Event e) {
		events.add(e);
	}
	
	/**
	 * Adds the given category to the repository.
	 * 
	 * @param c the {@link EventCategory} to add; must not be null
	 */
	public static void addCategory(EventCategory c) {
		categories.add(c);
	}
	
	/**
     * Removes the given event from the repository.
     *
     * @param e the {@link Event} to remove
     * @return {@code true} if the event was present and removed; {@code false} otherwise
     */
	public static boolean removeEvent(Event e) {
		return events.remove(e);
	}
	
	/**
     * Removes the given category from the repository.
     *
     * @param c the {@link EventCategory} to remove
     * @return {@code true} if the category was present and removed; {@code false} otherwise
     */
	public static boolean removeCategory(EventCategory c) {
		return categories.remove(c);
	}
	
	/**
     * Retrieves all events that start on the specified date.
     *
     * @param date the {@link LocalDate} on which to filter events; must not be null
     * @return an {@link ArrayList} of {@link Event} instances whose start date equals the given date;
     *         never null but possibly empty
     */
	public static ArrayList<Event> getEventsOn(LocalDate date) {
		ArrayList<Event> eventsOnDate = new ArrayList<Event>();
		for(Event e : events) {
			if(e.getStart().toLocalDate().equals(date)) {
				eventsOnDate.add(e);
			}
		}
		return eventsOnDate;
	}

	public static ArrayList<Event> getEvents() {
		return events;
	}

	public static ArrayList<EventCategory> getCategories() {
		return categories;
	}
	
	public static Theme getTheme() {
		return theme;
	}
	
	public static void setTheme(Theme theme) {
		DataManager.theme = theme;
	}
}
