package main.client.ui;

import main.client.ClientFactory;
import main.shared.proxy.CourseGroupProxy;
import main.shared.proxy.CourseProxy;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class CourseGroupsView extends Composite
{
	private static CourseGroupsViewUiBinder uiBinder = GWT.create(CourseGroupsViewUiBinder.class);

	interface CourseGroupsViewUiBinder extends UiBinder<Widget, CourseGroupsView> { }
	
	private CourseProxy course;
	public void setCourse(CourseProxy course)
	{
		this.course = course;
		// TODO show course groups;
	}
	
	private CourseGroupProxy group;
	public void setGroup(CourseGroupProxy group)
	{
		this.group = group;
		// TODO underline current group
	}
	
	private ClientFactory clientFactory;
	public void setClientFactory(ClientFactory clientFactory) { this.clientFactory = clientFactory; }
	
	public CourseGroupsView()
	{
		initWidget(uiBinder.createAndBindUi(this));
	}
	
}