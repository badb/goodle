package main.client;

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

	private TopView topView;
	private CalendarView rightView;
	private CourseListView courseListView;
	private CourseView courseView;
	private CourseInfoView courseInfoView;
	private CourseModulesView courseModulesView;
	private CourseMembersView courseMembersView;
	private CourseHomeworksView courseHomeworksView;
	private UserMainPageView userMainPageView;
	
	private CourseJoinMethodPopup courseJoinMethodPopup;
	private CourseNameTermPopup courseNameTermPopup;
	private CoursePasswordPopup coursePasswordPopup;
	private CourseSynchronizationPopup courseSynchronizationPopup;
	private SynchronizationConfirmationPopup synchronizationConfirmationPopup;

	private GoodleUserProxy currentUserProxy;
	private CourseModulesEditView courseModulesEditView;
	private BlobServiceAsync blobService;
	private CourseHomeworksEditView courseHomeworksEditView;
	
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
	public CourseNameTermPopup getCourseNameTermPopup()
	{
		if (courseNameTermPopup == null)
		{
			courseNameTermPopup = new CourseNameTermPopup();
			courseNameTermPopup.setClientFactory(this);
			courseNameTermPopup.setParent(getCourseView());
			if (topView != null) {
				courseNameTermPopup.addNameChangedEventHandler(topView);
			}
		}
		return courseNameTermPopup;
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
	public CourseSynchronizationPopup getCourseSynchronizationPopup()
	{
		if (courseSynchronizationPopup == null)
		{
			courseSynchronizationPopup = new CourseSynchronizationPopup();
			courseSynchronizationPopup.setClientFactory(this);
			courseSynchronizationPopup.setParent(getCourseView());
			
		}
		return courseSynchronizationPopup;
	}

	@Override
	public void initializeRequestFactory() { requestFactory.initialize(eventBus); }

	@Override
	public void setCurrentUser(GoodleUserProxy userProxy) { currentUserProxy = userProxy; }

	@Override
	public void setTopView(TopView topView) { this.topView = topView; }
	
	@Override
	public void setRightView(CalendarView rightView) { this.rightView = rightView;}

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

	@Override
	public BlobServiceAsync getBlobService() {
		if (blobService == null) {
			blobService = GWT.create(BlobService.class);
		}
		return blobService;
	}
	@Override
	public CourseHomeworksView getCourseHomeworksView() {
		if (courseHomeworksView == null)
		{
			courseHomeworksView = new CourseHomeworksView();
			courseHomeworksView.setClientFactory(this);
			courseHomeworksView.setParent(getCourseView());
		}
		return courseHomeworksView;
	}

	@Override
	public CourseHomeworksEditView getCourseHomeworksEditView() {
		if (courseHomeworksEditView == null)
		{
			courseHomeworksEditView = new CourseHomeworksEditView();
			courseHomeworksEditView.setClientFactory(this);
			courseHomeworksEditView.setParent(getCourseView());
			courseHomeworksEditView.addNewHomeworkEventHandler(rightView);
		}
		return courseHomeworksEditView;
	}

	@Override
	public SynchronizationConfirmationPopup getSynchronizationConfirmationPopup() {
		if (synchronizationConfirmationPopup == null)
		{
			synchronizationConfirmationPopup = new SynchronizationConfirmationPopup();
			synchronizationConfirmationPopup.setClientFactory(this);
			synchronizationConfirmationPopup.setParent(getCourseView());
		}
		return synchronizationConfirmationPopup;
	}


}
