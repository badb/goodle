package main.client;

import com.google.gwt.event.shared.GwtEvent;

public class NameChangedEvent extends GwtEvent<NameChangedEventHandler> {
	
	public static Type<NameChangedEventHandler> TYPE = new Type<NameChangedEventHandler>();
	
	private final String name;
	
	public NameChangedEvent(String name) {
		this.name = name;
	}
	
	@Override
	public Type<NameChangedEventHandler> getAssociatedType() {
		return TYPE;
	}
	
	@Override 
	protected void dispatch(NameChangedEventHandler handler) {
		handler.onNameChanged(this);
	}
	
	public String getName() {
		return name;
	}
}
