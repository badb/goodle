package edu.goodle.prototype.client;

import java.util.logging.Logger;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class GoodleServiceController {
	
	private final GoodleServiceAsync goodleService = GWT.create(GoodleService.class);
	private static Logger logger = Logger.getLogger("");
	private Goodle goodle;
	
	public void GoodleServiceContorller(Goodle goodle) {
		this.goodle = goodle;
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
		                	goodle.addCoursePanels(result);
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
	            		goodle.changeToCourseList(result);
	                    logger.info("searchCourses:" + result);
	            }
	    });
	}
	
	void loginUser (String name, String password) {
	    goodleService.loginUser(name, password, new AsyncCallback<String>() {
		    public void onFailure(Throwable caught) {
		    		goodle.loginFail();
		            logger.severe("Login failed." + caught);
		    }
		    public void onSuccess(String result) {
		            goodle.afterLogin(result);
		    }
	    });
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
