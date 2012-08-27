package main.client;

import com.google.gwt.event.shared.GwtEvent;

public class NewHomeworkEvent extends GwtEvent<NewHomeworkEventHandler> {
	
	public static Type<NewHomeworkEventHandler> TYPE = new Type<NewHomeworkEventHandler>();

	public NewHomeworkEvent() {}
	
	@Override
	public Type<NewHomeworkEventHandler> getAssociatedType() {
		return TYPE;
	}
	
	@Override 
	protected void dispatch(NewHomeworkEventHandler handler) {
		handler.onNewHomework(this);
	}
	
}
