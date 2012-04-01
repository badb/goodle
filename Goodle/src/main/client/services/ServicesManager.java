package main.client.services;

import java.util.Collection;
import java.util.logging.Logger;

import main.client.Goodle;
import main.client.GoodleService;
import main.client.GoodleServiceAsync;
import main.shared.models.Course;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.rpc.AsyncCallback;


public class ServicesManager {
	
	private final GoodleServiceAsync goodleService = GWT.create(GoodleService.class);
	private static Logger logger = Logger.getLogger("");
	private Goodle goodle;
	
	private final CourseServiceAsync courseService = GWT.create(CourseService.class);
	
	public ServicesManager() { };
	public ServicesManager(Goodle goodle) { this.goodle = goodle; }
	
	public void findCoursesByName(String name)
	{
		AsyncCallback<Collection<Course>> callback = new AsyncCallback<Collection<Course>>()
		{
			public void onFailure(Throwable caught) 
			{
	            Logger.getLogger("").severe("Wyszukiwanie nie powiodło się: " + caught);
			}
			public void onSuccess(Collection<Course> result) 
			{	
				Logger.getLogger("").severe("Wyszukiwanie zakończone sukcesem.");
	            goodle.showCoursesFound(result);
			}
		};
	    courseService.findCoursesByName(name, callback);
		
	}
	
	void getCourseInfo (String course) {	
		goodleService.getCourseInfo(getSession(), course, new AsyncCallback<String>() {
		        public void onFailure(Throwable caught) {
		        	logger.severe("loadCourseInfo fail: " + caught);
		        }
		        public void onSuccess(String result) {
		        	logger.info("loadCourseInfo: ok" + result);
		        	/* tu tak na prawde jakies konkretne pole sprawdza */
		//                if (result.equals("yes")) {
		                	//goodle.addCoursePanels(result);
		                	logger.info("after goodle.addCoursepanels");
		 //               } else {
		   //             	goodle.addRegisterPanel();
		    //            }
		        }
		});
	}
	
	void searchCourse (String text) {
	    goodleService.searchCourse(getSession(), text,
	            new AsyncCallback<String>() {
	
	            public void onFailure(Throwable caught) {
	                    logger.severe("searchCourses failed." + caught);
	            }
	            public void onSuccess(String result) {
	            		//goodle.changeToCourseList(result);
	                    logger.info("searchCourses:" + result);
	            }
	    });
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
	                    logger.severe("GetAllCourses failed." + caught);
	            }
	            public void onSuccess(String result) {
	                    logger.info("GetAllCourses:" + result);
	            }
	    });
	}

	public String getSession() {
		String sessionID = Cookies.getCookie("sessionID");
		return sessionID;
	}
	
}
