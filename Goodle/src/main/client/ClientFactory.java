package main.client;

import main.client.ui.CourseInfoView;
import main.client.ui.CourseListView;
import main.client.ui.CourseMembersView;
import main.client.ui.CourseModulesView;
import main.client.ui.CourseView;
import main.client.ui.UserMainPageView;
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
	CourseMembersView getCourseMembersView();
	UserMainPageView getUserMainPageView();
	
	void setCurrentUser(GoodleUserProxy userProxy);
	GoodleUserProxy getCurrentUser();
}
