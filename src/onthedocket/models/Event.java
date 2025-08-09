package onthedocket.models;

import java.time.Duration;
import java.time.LocalDateTime;

public class Event {
	private String name;
	private LocalDateTime start;
	private LocalDateTime end;

	public Event(String name, LocalDateTime start, LocalDateTime end) {
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
