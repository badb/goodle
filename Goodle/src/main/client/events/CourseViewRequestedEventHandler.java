package main.client.events;

import com.google.gwt.event.shared.EventHandler;

public interface CourseViewRequestedEventHandler extends EventHandler 
{
	public void onEvent(CourseViewRequestedEvent event);
}
