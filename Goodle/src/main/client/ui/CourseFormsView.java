package main.client.ui;

import main.client.ClientFactory;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class CourseFormsView extends Composite
{
	private static CourseFormsViewUiBinder uiBinder = GWT.create(CourseFormsViewUiBinder.class);

	interface CourseFormsViewUiBinder extends UiBinder<Widget, CourseFormsView> { }
	
	private ClientFactory clientFactory;
	
	public CourseFormsView()
	{
		initWidget(uiBinder.createAndBindUi(this));
	}
	
	public void setClientFactory(ClientFactory clientFactory) { this.clientFactory = clientFactory; }
}
