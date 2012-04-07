package main.client.events;

import com.google.gwt.event.shared.EventHandler;

public interface CoursesFoundEventHandler extends EventHandler 
{
	public void onEvent(CoursesFoundEvent event);
}
