package edu.goodle.prototype.client;

import java.util.logging.Logger;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.RootPanel;

public class Goodle implements EntryPoint {
	private static final String SERVER_ERROR = "An error occurred while "
			+ "attempting to contact the server. Please check your network "
			+ "connection and try again.";
	private final GoodleServiceAsync goodleService = GWT
			.create(GoodleService.class);
	private static Logger logger = Logger.getLogger("");
	private String sessionID = "";
	
	private LoginPanel lp = new LoginPanel(goodleService, this);
	private MainStudentPanel mp = new MainStudentPanel(goodleService, this);

	public void onModuleLoad() {
		if (sessionID.compareTo("") == 0) {
			RootPanel.get("goodleLogin").add(lp.getPanel());
            DOM.setElementAttribute(
                    DOM.getElementById("goodleLogin"), "style", "visibility:visible");
		} else {
			
		}
	}
	
	public void afterLogin(String result) {
        DOM.setElementAttribute(
                DOM.getElementById("goodleLogin"), "style", "visibility:hidden");
		showCourseList();
	}
	
	public void showCourseList() {
		RootPanel.get("main").add(mp.getPanel());
	}
}
