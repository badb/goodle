package main.client;

import main.client.ui.CourseInfoView;
import main.client.ui.CourseView;
import main.client.ui.CoursesListView;
import main.client.ui.CreateCourseView;
import main.client.ui.ModuleWidget;
import main.client.ui.ModulesTabView;
import main.shared.GoodleRequestFactory;

import com.google.gwt.core.client.GWT;
import com.google.gwt.place.shared.PlaceController;
import com.google.web.bindery.event.shared.SimpleEventBus;

public class ClientFactoryImpl implements ClientFactory 
{
	private static final SimpleEventBus eventBus = new SimpleEventBus();
	private static final PlaceController placeController = new PlaceController(eventBus);
	private static final GoodleRequestFactory requestFactory = GWT.create(GoodleRequestFactory.class);
	private static final CoursesListView coursesListView = new CoursesListView();
	private static final CourseView courseView = new CourseView();
	private static final CourseInfoView courseInfoView = new CourseInfoView();
	private static final CreateCourseView createCourseView = new CreateCourseView();
	private static final ModulesTabView modulesTabView = new ModulesTabView();
	//private static final ModuleWidget moduleWidget = new ModuleWidget();
	
	@Override
	public SimpleEventBus getEventBus() { return eventBus; }

	@Override
	public PlaceController getPlaceController() { return placeController; }
	
	@Override
	public GoodleRequestFactory getRequestFactory() { return requestFactory; }

	@Override
	public CoursesListView getCoursesListView() { return coursesListView; }

	@Override
	public CourseView getCourseView() { return courseView; }

	@Override
	public CreateCourseView getCreateCourseView() { return createCourseView; }
	
	@Override
	public void initializeRequestFactory() { requestFactory.initialize(eventBus); }

	@Override
	public ModulesTabView getModulesTabView() {
		return modulesTabView;
	}
	
	@Override
	public CourseInfoView getCourseInfoView() {
		return courseInfoView;
	}

	/*@Override
	public ModuleWidget getModuleWidget() {
		return moduleWidget;
	}*/
}
