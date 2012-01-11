package edu.goodle.prototype.client;

import java.sql.Date;
import java.util.logging.Logger;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.RootPanel;

public class Goodle implements EntryPoint {
	private final GoodleServiceAsync goodleService = GWT
			.create(GoodleService.class);
	private LoginPanel lp = new LoginPanel(goodleService, this);
	private MainStudentPanel mp = new MainStudentPanel(goodleService, this);
	private SearchPanel sp = new SearchPanel(goodleService, this);
	private UserNavPanel up = new UserNavPanel(goodleService, this); 

	public void onModuleLoad() {
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
	
	public void displayLoginBox() {
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
        showNavBar();
		showCourseList();
	}
	
	public void showNavBar() {
		RootPanel.get("search").add(sp.getPanel());
		RootPanel.get("user_nav").add(up.getPanel());
	}
	
	public void showCourseList() {
		RootPanel.get("tabs").add(mp.getPanel());
	}

	public String getSession() {
		String sessionID = Cookies.getCookie("sessionID");
		return sessionID;
	}
}
