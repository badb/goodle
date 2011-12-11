package edu.goodle.prototype.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.logging.client.ConsoleLogHandler;
import com.google.gwt.logging.client.FirebugLogHandler;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.RootPanel;

import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.LogRecord;

public class Goodle implements EntryPoint {
	private static Logger logger = Logger.getLogger("");
	private final GoodleServiceAsync goodleService =
		GWT.create(GoodleService.class);
	String sessionId = "";

	private LoginPanel lp = new LoginPanel(goodleService, this);
	private CourseListPanel cp = new CourseListPanel(goodleService, this);
	private Button logoutButton = new Button("Wyloguj");

	public void onModuleLoad() {
		if (sessionId == "") {
			RootPanel.get("goodleLogin").add(lp.getPanel());
			DOM.setElementAttribute(
				DOM.getElementById("goodleLogin"), "style", "visibility:visible");
		} else {
			afterLogin(sessionId);
		}
	}

	public void afterLogin(String result) {
		sessionId = result;
		DOM.setElementAttribute(
			DOM.getElementById("goodleLogin"), "style", "visibility:hidden");
		logger.info("SessionID: "+result);
		RootPanel.get("courses").add(cp.getPanel(sessionId));
		RootPanel.get("goodleLogout").add(logoutButton);

		logoutButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				goodleService.logoutUser(sessionId, new AsyncCallback<Void>() {
					public void onFailure(Throwable caught) {
						logger.severe("Logout failed." + caught);
					}
					public void onSuccess(Void v) {
						RootPanel.get("goodleLogout").remove(logoutButton);
						sessionId = "";
						cp.remove();
						onModuleLoad();
					}
				});
			}
		});
	}
}
