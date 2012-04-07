package main.client.events;

import com.google.gwt.event.shared.EventHandler;

public interface CourseCreatedEventHandler extends EventHandler {
	
	void onEvent(CourseCreatedEvent event);

}
