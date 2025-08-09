package onthedocket.models;

import java.time.Duration;
import java.time.LocalDateTime;

public class Event {
	private String name;
	private LocalDateTime start;
	private LocalDateTime end;
	private EventCategory category;

	public Event(String name, LocalDateTime start, LocalDateTime end) {
		this(name, start, end, EventCategory.DEFAULT);
	}
	
	public Event(String name, LocalDateTime start, LocalDateTime end, EventCategory category) {
		setName(name);
		setStart(start);
		setEnd(end);
	}
	
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
