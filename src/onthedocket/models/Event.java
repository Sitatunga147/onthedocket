package onthedocket.models;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

/**
 * The {@code Event} class models a calendar event, with a name, start, end, and category.
 * <p>
 * Two {@code Event} instances are considered equal if they share the same name, start, end, and category.
 * </p>
 * 
 * @author Sitatunga147 (with moderate AI assistance)
 */
public class Event {
	private String name;
	private LocalDateTime start;
	private LocalDateTime end;
	private EventCategory category;

	/**
	 * Constructs an event with the given name and start/end times, using the default category.
	 * 
	 * @param name a title for the event
	 * @param start the date-time when this event begins
	 * @param end the date-time when this event ends
	 */
	public Event(String name, LocalDateTime start, LocalDateTime end) {
		this(name, start, end, EventCategory.DEFAULT);
	}
	
	/**
	 * Constructs an event with the given name, start/end times, and category.
	 * 
	 * @param name a title for the event
	 * @param start the date-time when this event begins
	 * @param end the date-time when this event ends
	 * @param category the category used for classification and styling (e.g., WORK, PERSONAL)
	 */
	public Event(String name, LocalDateTime start, LocalDateTime end, EventCategory category) {
		setName(name);
		setStart(start);
		setEnd(end);
		setCategory(category);
	}
	
	/**
	 * Indicates whether some other object is "equal to" this one.
	 * Two events are equal if they have the same name, start, end, and category.
	 * 
	 * @param o the reference object with which to compare
	 * @return {@code true} if this event is equal to {@code o}; {@code false} otherwise
	 */
	@Override
	public boolean equals(Object o) {
		if(this == o) return true;
		if(!(o instanceof Event)) return false;
		
		Event other = (Event)o;
		return name.equals(other.name) && start.equals(other.start) && end.equals(other.end) && category.equals(other.category);
	}
	
	/**
	 * Returns a has code value for the event, consistent with {@link #equals(Object)}.
	 * 
	 * @return a hash code based on name, start, end, and category
	 */
	@Override
	public int hashCode() {
		return Objects.hash(name, start, end, category);
	}
	
	/**
	 * Returns a String representation of this event in the format:
	 * <pre>
	 * Event[Name: yyyy-MM-dd HH:mm -> yyyy-MM-dd HH:mm (Category Name)]
	 * </pre>
	 * 
	 * @return a formatted String describing this event
	 */
	@Override
	public String toString() {
		DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
		return String.format("Event[%s: %s -> %s (%s)]", name, start.format(format), end.format(format), category.getName());
	}
	
	/**
	 * Determines whether this event overlaps in time with another event.
	 * OVerlap is defined inclusive of start and end instants.
	 * 
	 * @param other the event to compare against
	 * @return {@code true} if the time intervals intersect; {@code false} otherwise
	 */
	public boolean overlaps(Event other) {
		return !start.isAfter(other.end) && !other.start.isAfter(end);
	}

	/**
	 * Calculates the duration of this event as the difference between end and start.
	 * 
	 * @return a {@link Duration} representing the length of this event
	 */
	public Duration getDuration() {
		return Duration.between(start, end);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public EventCategory getCategory() {
		return category;
	}

	public void setCategory(EventCategory category) {
		this.category = category;
	}

	public LocalDateTime getStart() {
		return start;
	}

	public void setStart(LocalDateTime start) {
		this.start = start;
	}

	public LocalDateTime getEnd() {
		return end;
	}

	public void setEnd(LocalDateTime end) {
		this.end = end;
	}
}
