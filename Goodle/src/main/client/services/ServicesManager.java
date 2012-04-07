package main.client.services;

import java.util.ArrayList;
import java.util.logging.Logger;

import main.client.GoodleService;
import main.client.GoodleServiceAsync;
import main.client.events.CourseCreatedEvent;
import main.client.events.CourseViewRequestedEvent;
import main.client.events.CoursesFoundEvent;
import main.client.utils.CourseShortDesc;
import main.shared.models.Course;

import com.google.appengine.api.datastore.Key;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.rpc.AsyncCallback;


public class ServicesManager {
	
	private final GoodleServiceAsync goodleService = GWT.create(GoodleService.class);
	
	private final CourseServiceAsync courseService = GWT.create(CourseService.class);
	private SimpleEventBus eventBus;
	
	public ServicesManager() { }
	public ServicesManager(SimpleEventBus eventBus) { this.eventBus = eventBus; };	
		
	public void createCourse(String name, String desc)
	{
		AsyncCallback<Course> callback = new AsyncCallback<Course>()
		{
			public void onFailure(Throwable caught) { }
			public void onSuccess(Course c) 
			{	
				eventBus.fireEvent(new CourseCreatedEvent(c));
			}
		};
	    courseService.createCourse(name, "2012L", desc, null, callback);		
	}
	
	public void findCourseByKey(Key key) 
	{
		AsyncCallback<Course> callback = new AsyncCallback<Course>()
		{
			public void onFailure(Throwable caught) { }
			public void onSuccess(Course c) 
			{	
				eventBus.fireEvent(new CourseViewRequestedEvent(c));
			}
		};
	    courseService.findCourseByKey(key, callback);			
	}
	
	public void findCoursesByName(String name)
	{
		AsyncCallback<ArrayList<CourseShortDesc>> callback = 
				new AsyncCallback<ArrayList<CourseShortDesc>>()
		{
			public void onFailure(Throwable caught) { }
			public void onSuccess(ArrayList<CourseShortDesc> result) 
			{	
	            eventBus.fireEvent(new CoursesFoundEvent(result));
			}
		};
	    courseService.findCoursesDescByName(name, callback);
		
	}
	
	
	void loginUser (String name, String password) {
		Logger.getLogger("").severe("Service controller wchodzi.");
		AsyncCallback<String> callback = new AsyncCallback<String>()
		{
			public void onFailure(Throwable caught) 
			{
	            Logger.getLogger("").severe("Login failed." + caught);
	    		//goodle.loginFail();

			}
			public void onSuccess(String result) 
			{	
				Logger.getLogger("").severe("Success.");
	            //goodle.afterLogin(result);
			}
		};
	    goodleService.loginUser(name, password, callback);

	}
	
	void getAllCourses() {
	    goodleService.getAllCourses(getSession(),
	            new AsyncCallback<String>() {
	            public void onFailure(Throwable caught) {
	            }
	            public void onSuccess(String result) {
	            }
	    });
	}

	public String getSession() {
		String sessionID = Cookies.getCookie("sessionID");
		return sessionID;
	}
	
}
