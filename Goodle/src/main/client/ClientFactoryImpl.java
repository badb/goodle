package main.client;

import main.client.ui.CourseFormsView;
import main.client.ui.CourseGroupsView;
import main.client.ui.CourseInfoView;
import main.client.ui.CourseListView;
import main.client.ui.CourseMembersView;
import main.client.ui.CourseModulesView;
import main.client.ui.CourseView;
import main.client.ui.CourseInfoView;
import main.client.ui.CourseGroupsView;
import main.client.ui.CreateCourseImportView;
import main.client.ui.CreateCourseView;
import main.client.ui.UserMainPageView;
import main.client.ui.UserProfileView;
import main.shared.GoodleRequestFactory;
import main.shared.proxy.GoodleUserProxy;

import com.google.gwt.core.client.GWT;
import com.google.gwt.place.shared.PlaceController;
import com.google.web.bindery.event.shared.SimpleEventBus;

public class ClientFactoryImpl implements ClientFactory 
{
	private static final SimpleEventBus eventBus = new SimpleEventBus();
	private static final PlaceController placeController = new PlaceController(eventBus);
	private static final GoodleRequestFactory requestFactory = GWT.create(GoodleRequestFactory.class);

	private static CourseListView courseListView;
	private static CourseView courseView;
	private static CourseInfoView courseInfoView;
	private static CourseModulesView courseModulesView;
	private static CourseGroupsView courseGroupsView;
	private static CourseMembersView courseMembersView;
	private static CourseFormsView courseFormsView;
	private static CreateCourseView createCourseView;
	private static CreateCourseImportView createCourseImportView;
	private static UserMainPageView userMainPageView;
	private static UserProfileView userProfileView;

	private static GoodleUserProxy currentUserProxy;

	@Override
	public SimpleEventBus getEventBus() { return eventBus; }

	@Override
	public PlaceController getPlaceController() { return placeController; }

	@Override
	public GoodleRequestFactory getRequestFactory() { return requestFactory; }

	@Override
	public CourseListView getCourseListView() 
	{
		if (courseListView == null) 
		{
			courseListView = new CourseListView();
			courseListView.setClientFactory(this);
		}
		return courseListView; 
	}

	@Override
	public CourseView getCourseView() 
	{
		if (courseView == null) 
		{
			courseView = new CourseView();
			courseView.setClientFactory(this);
		}
		return courseView; 
	}
	
	@Override
	public CreateCourseView getCreateCourseView() 
	{
		if (createCourseView == null) 
		{
			createCourseView = new CreateCourseView();
			createCourseView.setClientFactory(this);
		}
		return createCourseView; 
	}

	@Override
	public CourseInfoView getCourseInfoView() 
	{
		if (courseInfoView == null) 
		{
			courseInfoView = new CourseInfoView();
			courseInfoView.setClientFactory(this);
		}
		return courseInfoView; 
	}

	@Override
	public CourseModulesView getCourseModulesView() 
	{
		if (courseModulesView == null) 
		{
			courseModulesView = new CourseModulesView();
			courseModulesView.setClientFactory(this);
		}
		return courseModulesView; 
	}

	
	@Override
	public CourseGroupsView getCourseGroupsView() 
	{
		if (courseGroupsView == null) 
		{
			courseGroupsView = new CourseGroupsView();
			courseGroupsView.setClientFactory(this);
		}
		return courseGroupsView; 
	}
	
	public CourseMembersView getCourseMembersView() 
	{
		if (courseMembersView == null) 
		{
			courseMembersView = new CourseMembersView();
			courseMembersView.setClientFactory(this);
		}
		return courseMembersView; 
	}
	
	public CourseFormsView getCourseFormsView() 
	{
		if (courseFormsView == null) 
		{
			courseFormsView = new CourseFormsView();
			courseFormsView.setClientFactory(this);
		}
		return courseFormsView; 
	}
	@Override
	public CreateCourseImportView getCreateCourseImportView() 
	{
		if (createCourseImportView == null) 
		{
			createCourseImportView = new CreateCourseImportView();
			createCourseImportView.setClientFactory(this);
		}
		return createCourseImportView; 
	}
	
	@Override
	public UserMainPageView getUserMainPageView() 
	{
		if (userMainPageView == null) 
		{
			userMainPageView = new UserMainPageView();
			userMainPageView.setClientFactory(this);
		}
		return userMainPageView; 
	}

	@Override
	public UserProfileView getUserProfileView() 
	{
		if (userProfileView == null)
		{
			userProfileView = new UserProfileView();
			userProfileView.setClientFactory(this);
		}
		return userProfileView; 
	}

	@Override
	public void initializeRequestFactory() { requestFactory.initialize(eventBus); }

	@Override
	public void setCurrentUser(GoodleUserProxy userProxy) { currentUserProxy = userProxy; }

	@Override
	public GoodleUserProxy getCurrentUser() { return currentUserProxy; }
}
