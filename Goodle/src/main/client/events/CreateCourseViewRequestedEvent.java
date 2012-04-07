package main.client.events;

import com.google.gwt.event.shared.GwtEvent;

public class CreateCourseViewRequestedEvent extends GwtEvent<CreateCourseViewRequestedEventHandler> 
{
	public static Type<CreateCourseViewRequestedEventHandler> TYPE = new Type<CreateCourseViewRequestedEventHandler>();
	
	@Override
	public Type<CreateCourseViewRequestedEventHandler> getAssociatedType() { return TYPE; }

	@Override
	protected void dispatch(CreateCourseViewRequestedEventHandler handler) { handler.onEvent(this);	}
}
