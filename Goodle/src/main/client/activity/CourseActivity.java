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
	private String viewName;
	
	public CourseActivity(ClientFactory clientFactory, CoursePlace place)
	{
		this.clientFactory = clientFactory;
		this.courseId = place.getCourseId();
		this.viewName = place.getViewName();
	}
	
	@Override
	public void start(AcceptsOneWidget panel, EventBus eventBus) 
	{
		CourseView courseView = clientFactory.getCourseView();
		panel.setWidget(courseView.asWidget());
		courseView.prepareView(courseId, viewName);
	}
	
	@Override
	public String mayStop()
	{
		if (viewName.equals("modulesEdit") && !clientFactory.getCourseModulesEditView().mayStop())
		{
			return "Jesteś w trakcie edycji zajęć. Czy na pewno chcesz zakończyć?";
		}
		return null;
	}
}
