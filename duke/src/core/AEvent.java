package core;

public abstract class AEvent {

	private EventType eventType;

	public AEvent() {
		this.eventType = EventType.getEventType(this);
	}

	public EventType getEventType() {
		return eventType;
	}

}
