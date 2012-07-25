package main.client;

import main.client.ui.CourseInfoView;
import main.client.ui.CourseJoinMethodPopup;
import main.client.ui.CourseListView;
import main.client.ui.CourseMembersView;
import main.client.ui.CourseModulesEditView;
import main.client.ui.CourseModulesView;
import main.client.ui.CoursePasswordPopup;
import main.client.ui.CourseView;
import main.client.ui.UserMainPageView;
import main.shared.GoodleRequestFactory;
import main.shared.proxy.GoodleUserProxy;

import com.google.gwt.core.client.GWT;
import com.google.gwt.place.shared.PlaceController;
import com.google.web.bindery.event.shared.SimpleEventBus;

public class ClientFactoryImpl implements ClientFactory 
{
	private final SimpleEventBus eventBus = new SimpleEventBus();
	private final PlaceController placeController = new PlaceController(eventBus);
	private final GoodleRequestFactory requestFactory = GWT.create(GoodleRequestFactory.class);

	private CourseListView courseListView;
	private CourseView courseView;
	private CourseInfoView courseInfoView;
	private CourseModulesView courseModulesView;
	private CourseMembersView courseMembersView;
	private UserMainPageView userMainPageView;
	
	private CourseJoinMethodPopup courseJoinMethodPopup;
	private CoursePasswordPopup coursePasswordPopup;

	private GoodleUserProxy currentUserProxy;
	private CourseModulesEditView courseModulesEditView;

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
	public CourseInfoView getCourseInfoView() 
	{
		if (courseInfoView == null) 
		{
			courseInfoView = new CourseInfoView();
			courseInfoView.setParent(getCourseView());
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
			courseModulesView.setParent(getCourseView());
			courseModulesView.setClientFactory(this);
		}
		return courseModulesView; 
	}
	
	public CourseMembersView getCourseMembersView() 
	{
		if (courseMembersView == null) 
		{
			courseMembersView = new CourseMembersView();
			courseMembersView.setParent(getCourseView());
			courseMembersView.setClientFactory(this);
		}
		return courseMembersView; 
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
	public CourseJoinMethodPopup getCourseJoinMethodPopup()
	{
		if (courseJoinMethodPopup == null)
		{
			courseJoinMethodPopup = new CourseJoinMethodPopup();
			courseJoinMethodPopup.setClientFactory(this);
			courseJoinMethodPopup.setParent(getCourseView());
		}
		return courseJoinMethodPopup;
	}
	
	@Override
	public CoursePasswordPopup getCoursePasswordPopup()
	{
		if (coursePasswordPopup == null)
		{
			coursePasswordPopup = new CoursePasswordPopup();
			coursePasswordPopup.setClientFactory(this);
			coursePasswordPopup.setParent(getCourseView());
		}
		return coursePasswordPopup;
	}

	@Override
	public void initializeRequestFactory() { requestFactory.initialize(eventBus); }

	@Override
	public void setCurrentUser(GoodleUserProxy userProxy) { currentUserProxy = userProxy; }

	@Override
	public GoodleUserProxy getCurrentUser() { return currentUserProxy; }

	@Override
	public CourseModulesEditView getCourseModulesEditView() {
		if (courseModulesEditView == null)
		{
			courseModulesEditView = new CourseModulesEditView();
			courseModulesEditView.setClientFactory(this);
			courseModulesEditView.setParent(getCourseView());
		}
		return courseModulesEditView;
	}
}
