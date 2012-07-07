package main.client.ui;

import main.client.ClientFactory;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class CalendarView extends Composite 
{

	private static CalendarViewUiBinder uiBinder = GWT.create(CalendarViewUiBinder.class);
	
	interface CalendarViewUiBinder extends UiBinder<Widget, CalendarView> { }
	 
	@UiField Button createEventButton;
	@UiField Button hideRightButton;
	private ClientFactory clientFactory;
	
	public CalendarView()
	{
		initWidget(uiBinder.createAndBindUi(this));
	}
	
	public void setClientFactory(ClientFactory clientFactory) { this.clientFactory = clientFactory; }
	
}