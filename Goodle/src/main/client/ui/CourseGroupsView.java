package main.client.ui;

import main.client.ClientFactory;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class CourseGroupsView extends Composite
{
	private static CourseGroupsViewUiBinder uiBinder = GWT.create(CourseGroupsViewUiBinder.class);

	interface CourseGroupsViewUiBinder extends UiBinder<Widget, CourseGroupsView> { }
	
	private ClientFactory clientFactory;
	
	public CourseGroupsView()
	{
		initWidget(uiBinder.createAndBindUi(this));
	}
	
	public void setClientFactory(ClientFactory clientFactory) { this.clientFactory = clientFactory; }
}