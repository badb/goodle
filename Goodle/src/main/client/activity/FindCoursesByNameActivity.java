package main.client.activity;

import main.client.ClientFactory;
import main.client.place.FindCoursesByNamePlace;
import main.client.ui.CourseListView;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

public class FindCoursesByNameActivity extends AbstractActivity
{
	private ClientFactory clientFactory;
	private String name;
	
	public FindCoursesByNameActivity(ClientFactory clientFactory, FindCoursesByNamePlace place)
	{
		this.clientFactory = clientFactory;
		this.name = place.getName();
	}

	@Override
	public void start(final AcceptsOneWidget panel, EventBus eventBus) 
	{
		CourseListView view = clientFactory.getCourseListView();
		view.clear();
		view.findCoursesByName(name);
		panel.setWidget(view.asWidget());
	}
}
