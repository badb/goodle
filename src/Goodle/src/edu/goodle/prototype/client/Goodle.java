package edu.goodle.prototype.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.logging.client.ConsoleLogHandler;
import com.google.gwt.logging.client.FirebugLogHandler;
import com.google.gwt.user.client.ui.RootPanel;


import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.LogRecord;

public class Goodle implements EntryPoint {
	private static Logger logger = Logger.getLogger("");
	private final GoodleServiceAsync goodleService =
		GWT.create(GoodleService.class);

	private LoginPanel lp = new LoginPanel(goodleService);

	public void onModuleLoad() {
		RootPanel.get("goodleLogin").add(lp.getPanel());
	}
}
