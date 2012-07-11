package main.client.activity;



import main.client.ClientFactory;
import main.client.place.CourseModulesEditPlace;
import main.client.place.CoursePlace;
import main.client.ui.CourseModulesEditView;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

public class CourseModulesEditActivity extends AbstractActivity
{

	private ClientFactory clientFactory;
	private String courseId;
	private String groupId;
	private String tabId;
	private CourseModulesEditView view;
	
	public CourseModulesEditActivity(ClientFactory clientFactory, CourseModulesEditPlace place)
	{
		this.clientFactory = clientFactory;
		this.courseId = place.getCourseId();
		this.groupId = place.getGroupId();
		this.tabId = place.getTabId();
	}

	@Override
	public void start(AcceptsOneWidget panel, EventBus eventBus) {
		view = clientFactory.getCourseModulesEditView();
		view.getCourse(courseId);
		view.getGroup(groupId);
		panel.setWidget(view.asWidget());
	}
	
	public String mayStop() {
		if (view.hasChanged()) return "Nie zapisałeś zmian!";
		return null;
	}
	
	

}