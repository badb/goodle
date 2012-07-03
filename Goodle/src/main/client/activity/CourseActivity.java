package main.client.activity;

import main.client.ClientFactory;
import main.client.place.CoursePlace;
import main.client.ui.CourseFormsView;
import main.client.ui.CourseGroupsView;
import main.client.ui.CourseInfoView;
import main.client.ui.CourseMembersView;
import main.client.ui.CourseModulesView;
import main.client.ui.CourseView;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

public class CourseActivity extends AbstractActivity
{
	private ClientFactory clientFactory;
	private String courseId;
	private String groupId;
	private String tabId;
	
	public CourseActivity(ClientFactory clientFactory, CoursePlace place)
	{
		this.clientFactory = clientFactory;
		this.courseId = place.getCourseId();
		this.groupId = place.getGroupId();
		this.tabId = place.getTabId();
	}

	
	@Override
	public void start(AcceptsOneWidget panel, EventBus eventBus) 
	{
		if (tabId.equals("0")) {
			CourseInfoView infoView = clientFactory.getCourseInfoView();
			infoView.getCourse(courseId);
			infoView.getGroup(groupId);
			infoView.setSelectedTab(tabId);
			panel.setWidget(infoView.asWidget());
		} else if (tabId.equals("1")) {
			CourseModulesView modulesView = clientFactory.getCourseModulesView();
			modulesView.getCourse(courseId);
			modulesView.getGroup(groupId);
			modulesView.setSelectedTab(tabId);
			panel.setWidget(modulesView.asWidget());
		} else if (tabId.equals("2")) {
			CourseGroupsView groupsView = clientFactory.getCourseGroupsView();
			groupsView.getCourse(courseId);
			groupsView.getGroup(groupId);
			groupsView.setSelectedTab(tabId);
			panel.setWidget(groupsView.asWidget());
		} else if (tabId.equals("3")) {
			CourseMembersView membersView = clientFactory.getCourseMembersView();
			membersView.getCourse(courseId);
			membersView.getGroup(groupId);
			membersView.setSelectedTab(tabId);
			panel.setWidget(membersView.asWidget());
		} else if (tabId.equals("4")) {
			CourseFormsView formsView = clientFactory.getCourseFormsView();
			formsView.getCourse(courseId);
			formsView.getGroup(groupId);
			formsView.setSelectedTab(tabId);
			panel.setWidget(formsView.asWidget());
		} else {
			//[TODO]
			//Brak strony	
		}
	}
}
