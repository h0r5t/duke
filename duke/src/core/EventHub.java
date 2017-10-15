package core;

import java.util.List;

public class EventHub {

	private MultiMap<EventType, IEventSubscriber> subscriberMap;

	public EventHub() {
		subscriberMap = new MultiMap<>();
	}

	public void subscribe(EventType eventType, IEventSubscriber subscriber) {
		subscriberMap.putOne(eventType, subscriber);
	}

	public void unsubscribe(EventType eventType, IEventSubscriber subscriber) {
		subscriberMap.removeOne(eventType, subscriber);
	}

	public void fireEvent(AEvent event) {
		List<IEventSubscriber> list = subscriberMap.get(event.getEventType());
		if (list != null) {
			for (IEventSubscriber sub : list)
				sub.eventOcurred(event);
		}
	}
}
