package main.client;

import java.util.Date;

import main.client.ui.CalendarView;
import main.client.ui.CourseHomeworksEditView;
import main.client.ui.CourseHomeworksView;
import main.client.ui.CourseInfoView;
import main.client.ui.CourseJoinMethodPopup;
import main.client.ui.CourseListView;
import main.client.ui.CourseMembersView;
import main.client.ui.CourseModulesEditView;
import main.client.ui.CourseModulesView;
import main.client.ui.CourseNameTermPopup;
import main.client.ui.CoursePasswordPopup;
import main.client.ui.CourseSynchronizationPopup;
import main.client.ui.CourseView;
import main.client.ui.SynchronizationConfirmationPopup;
import main.client.ui.TopView;
import main.client.ui.UserCoursesView;
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
	CourseHomeworksView getCourseHomeworksView();
	UserMainPageView getUserMainPageView();
	
	CourseJoinMethodPopup getCourseJoinMethodPopup();
	CourseNameTermPopup getCourseNameTermPopup();
	CoursePasswordPopup getCoursePasswordPopup();
	CourseSynchronizationPopup getCourseSynchronizationPopup();
	SynchronizationConfirmationPopup getSynchronizationConfirmationPopup();
	void setCurrentUser(GoodleUserProxy userProxy);
	void setTopView(TopView topView);
	void setRightView(CalendarView calendarView);
	void setLeftView(UserCoursesView leftView);
	GoodleUserProxy getCurrentUser();
	CourseModulesEditView getCourseModulesEditView();
	BlobServiceAsync getBlobService();
	CourseHomeworksEditView getCourseHomeworksEditView();
	
	public String dateFormat  = "E, dd MMM yyyy, HH:mm:ss";
}
