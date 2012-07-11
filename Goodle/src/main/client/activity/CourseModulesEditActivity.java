package main.client.activity;



import main.client.ClientFactory;
import main.client.place.CourseModulesEditPlace;
import main.client.ui.CourseModulesEditView;
import main.client.ui.CourseView;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

public class CourseModulesEditActivity extends AbstractActivity
{

	private ClientFactory clientFactory;
	private String courseId;
	private CourseModulesEditView view;
	
	public CourseModulesEditActivity(ClientFactory clientFactory, CourseModulesEditPlace place)
	{
		this.clientFactory = clientFactory;
		this.courseId = place.getCourseId();
	}

	@Override
	public void start(AcceptsOneWidget panel, EventBus eventBus) {
		view = clientFactory.getCourseModulesEditView();
		view.setCourse(courseId);
		panel.setWidget(view.asWidget());
	}
	
	public String mayStop() {
		if (view.hasChanged()) return "Nie zapisałeś zmian!";
		return null;
	}
	
	

}