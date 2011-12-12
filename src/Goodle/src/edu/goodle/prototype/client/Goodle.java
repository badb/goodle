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
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.MultiWordSuggestOracle;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SuggestBox;

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
	private SearchPanel sp = new SearchPanel(goodleService, this);
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
		DOM.setElementAttribute(
			DOM.getElementById("courses"), "style", "visibility:visible");
		logger.info("SessionID: "+result);
		RootPanel.get("courses").add(cp.getPanel(sessionId));
		RootPanel.get("goodleLogout").add(logoutButton);
		RootPanel.get("goodleSearch").add(sp.getPanel());

		logoutButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				goodleService.logoutUser(sessionId, new AsyncCallback<Void>() {
					public void onFailure(Throwable caught) {
						logger.severe("Logout failed." + caught);
					}
					public void onSuccess(Void v) {
						RootPanel.get("goodleLogout").remove(logoutButton);
						DOM.setElementAttribute(DOM.getElementById(
							"main"), "style", "visibility:hidden");
						sessionId = "";
						cp.remove();
						sp.remove();
						onModuleLoad();
					}
				});
			}
		});
	}

	public String getSession() {
		return sessionId;
	}

	public void showSearchResultPanel(Panel searchResultPanel) {
		RootPanel.get("main").clear();
		RootPanel.get("main").add(searchResultPanel);
		DOM.setElementAttribute(
			DOM.getElementById("courses"), "style", "visibility:hidden");
		DOM.setElementAttribute(DOM.getElementById(
			"main"), "style", "visibility:visible");
	}
}
