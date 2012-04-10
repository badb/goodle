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
	
	public CourseActivity(ClientFactory clientFactory, CoursePlace place)
	{
		this.clientFactory = clientFactory;
		this.courseId = place.getCourseId();
	}

	@Override
	public void start(AcceptsOneWidget panel, EventBus eventBus) 
	{
		CourseView view = clientFactory.getCourseView();
		view.setClientFactory(clientFactory);
		view.getCourse(courseId);
		panel.setWidget(view.asWidget());
	}
}
