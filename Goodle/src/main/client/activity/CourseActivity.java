package main.client.activity;

import main.client.ClientFactory;
import main.client.place.CoursePlace;
import main.client.ui.CourseView;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

public class CourseActivity extends AbstractActivity
{
	private ClientFactory clientFactory;
	private String courseId;
	private String tabId;
	
	public CourseActivity(ClientFactory clientFactory, CoursePlace place)
	{
		this.clientFactory = clientFactory;
		this.courseId = place.getCourseId();
		this.tabId = place.getTabId();
	}
	
	@Override
	public void start(AcceptsOneWidget panel, EventBus eventBus) 
	{
		CourseView view = clientFactory.getCourseView();
		view.getCourse(courseId);
		view.setSelectedTab(tabId);	
		panel.setWidget(view.asWidget());
	}
}
