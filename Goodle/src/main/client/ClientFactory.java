package main.client;

import main.client.ui.CourseListView;
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
	CreateCourseView getCreateCourseView();
	CreateCourseImportView getCreateCourseImportView();
	UserMainPageView getUserMainPageView();
	UserProfileView getUserProfileView();
	
	void setCurrentUser(GoodleUserProxy userProxy);
	GoodleUserProxy getCurrentUser();
}
