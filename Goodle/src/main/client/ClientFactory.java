package main.client;

import main.client.ui.CourseFormsView;
import main.client.ui.CourseGroupsView;
import main.client.ui.CourseInfoView;
import main.client.ui.CourseListView;
import main.client.ui.CourseMembersView;
import main.client.ui.CourseModulesView;
import main.client.ui.CourseView;
import main.client.ui.CreateCourseImportView;
import main.client.ui.CreateCourseView;
import main.client.ui.UserMainPageView;
import main.client.ui.UserProfileView;
import main.shared.GoodleRequestFactory;
import main.shared.proxy.GoodleUserProxy;

import com.google.gwt.place.shared.PlaceController;
import com.google.web.bindery.event.shared.SimpleEventBus;

public interface ClientFactory 
{
	SimpleEventBus getEventBus();
	PlaceController getPlaceController();
	void initializeRequestFactory();
	GoodleRequestFactory getRequestFactory();
	
	CourseListView getCourseListView();
	CourseView getCourseView();
	CourseInfoView getCourseInfoView();
	CourseModulesView getCourseModulesView();
	CourseGroupsView getCourseGroupsView();
	CourseMembersView getCourseMembersView();
	CourseFormsView getCourseFormsView();
	CreateCourseView getCreateCourseView();
	CreateCourseImportView getCreateCourseImportView();
	UserMainPageView getUserMainPageView();
	UserProfileView getUserProfileView();
	
	void setCurrentUser(GoodleUserProxy userProxy);
	GoodleUserProxy getCurrentUser();
}
