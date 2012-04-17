package main.client;

import main.client.ui.CourseInfoView;
import main.client.ui.CourseView;
import main.client.ui.CoursesListView;
import main.client.ui.CreateCourseView;
import main.client.ui.ModuleListView;
import main.shared.GoodleRequestFactory;

import com.google.gwt.place.shared.PlaceController;
import com.google.web.bindery.event.shared.SimpleEventBus;

public interface ClientFactory 
{
	SimpleEventBus getEventBus();
	PlaceController getPlaceController();
	void initializeRequestFactory();
	GoodleRequestFactory getRequestFactory();
	CoursesListView getCoursesListView();
	CourseView getCourseView();
	ModuleListView getModuleListView();
	CreateCourseView getCreateCourseView();
	CourseInfoView getCourseInfoView();
}
