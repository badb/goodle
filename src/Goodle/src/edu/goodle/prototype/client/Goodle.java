package edu.goodle.prototype.client;

import java.sql.Date;
import java.util.logging.Logger;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;

public class Goodle implements EntryPoint, ValueChangeHandler<String> {
	private final GoodleServiceAsync goodleService = GWT
			.create(GoodleService.class);
	private LoginPanel lp = new LoginPanel(goodleService, this);
	private MainStudentPanel mp = new MainStudentPanel(goodleService, this);
	private SearchPanel sp = new SearchPanel(goodleService, this);
	private UserNavPanel up = new UserNavPanel(goodleService, this); 
	private NavPathPanel np = new NavPathPanel(goodleService, this);
	private CourseInfoPanel cp = new CourseInfoPanel(goodleService, this);
	private CourseListPanel clp = new CourseListPanel(goodleService, this);

	private static Logger logger = Logger.getLogger("");
	private String initToken = History.getToken();
	
	public void onModuleLoad() {
		if (initToken.length() == 0) {
			History.newItem("main");
		}
		History.addValueChangeHandler(this);

        DOM.setElementAttribute(
                DOM.getElementById("goodleLogin"), "style", "visibility:hidden");
	    String sessionID = Cookies.getCookie("sessionID");
	    if ( sessionID != null && checkSessionID(sessionID)) {
	    	showNavBar();
	    	showCourseList();
	    } else {
	    	displayLoginBox();
	    }
	}
	
	public boolean checkSessionID(String sessionID) {
		return true;
	}
	
	public void clearPage() {
		RootPanel.get("tabs").clear();
		RootPanel.get("page").clear();
		np.clear();
		RootPanel.get("navpath").clear();
		RootPanel.get("info").clear();
		RootPanel.get("name").clear();
	}
	
	public void clearAll() {
		clearPage();
		RootPanel.get("search").clear();
		RootPanel.get("user_nav").clear();		
	}
	
	public void displayLoginBox() {
		clearAll();
		RootPanel.get("goodleLogin").add(lp.getPanel());
	    DOM.setElementAttribute(
	                DOM.getElementById("goodleLogin"), "style", "visibility:visible");
	}
	
	public void rememberLogin(String sessionID) {
		final long DURATION = 1000 * 60 * 30;
		Date expires = new Date(System.currentTimeMillis() + DURATION);
		Cookies.setCookie("sessionID", sessionID, expires, null, "/", false);
	}
	
	public void afterLogin(String result) {
		rememberLogin(result);
        DOM.setElementAttribute(
                DOM.getElementById("goodleLogin"), "style", "visibility:hidden");
        sp.clearTextBox();
        showNavBar();
		showCourseList();
	}
	
	public void showNavBar() {
		RootPanel.get("search").add(sp.getPanel());
		RootPanel.get("user_nav").add(up.getPanel());
		RootPanel.get("navpath").add(np.getPanel());
	}
	
	public void logout () {
		Cookies.removeCookie("sessionID");
		displayLoginBox();
	}
	
	public void showCourseList() {
		RootPanel.get("tabs").add(mp.getPanel());
	}

	public String getSession() {
		String sessionID = Cookies.getCookie("sessionID");
		return sessionID;
	}
	
	public void changeToCourse(String course) {
		clearPage();
		np.addNext(course);
		RootPanel.get("navpath").add(np.getPanel());
		RootPanel.get("name").add(new Label(course));
		RootPanel.get("info").add(cp.getPanel());
		MainCoursePanel mcp = new MainCoursePanel(goodleService, this);
		RootPanel.get("tabs").add(mcp.getPanel());
	}
	
	public void changeToCourseList(String text) {
		clearPage();
		showNavBar();
		RootPanel.get("tabs").add(mp.getPanel());
		mp.setNone();
		RootPanel.get("page").add(clp.getPanel(text));
	}

	public void searchForCourse(final String text) {
	       goodleService.searchCourse(getSession(), text,
	                new AsyncCallback<String>() {

	                public void onFailure(Throwable caught) {
	                        logger.severe("searchCourses failed." + caught);
	                }
	                public void onSuccess(String result) {
	                		changeToCourseList(result);
	                        logger.info("searchCourses:" + result);
	                }
	        });
	}

	@Override
	public void onValueChange(ValueChangeEvent<String> event) {
		logger.info("History changed: "+ event.getValue());
		changeToCourse(event.getValue());
	}

}
