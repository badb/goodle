package edu.goodle.prototype.client;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.RootPanel;

import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.LogRecord;

public class CoursePanel extends GoodlePanel{
	private VerticalPanel coursePanel = new VerticalPanel();
	private static Logger logger = Logger.getLogger("");
	private Boolean loaded;
	private String courseID;

	private Label mesgLabel = new Label("Nie jesteś zapisany na kurs.");
	private Button registerButton = new Button("Zapisz się");

	public CoursePanel(GoodleServiceAsync goodleService, Goodle goodle, String courseID) {
		super(goodleService, goodle);
		this.courseID = courseID;
		this.loaded = false;
	}

	public void loadCourseInfo (String sessionID) {
		
		getGoodleService().getCourseInfo(sessionID, courseID, new AsyncCallback<String>() {
			public void onFailure(Throwable caught) {
				logger.severe("loadCourseInfo fail: " + caught);
			}
			public void onSuccess(String result) {
				logger.info("loadCourseInfo: ok");
				Label l = new Label(result);
				DOM.setElementAttribute(l.getElement(), "id", "course_name");
				DOM.setElementAttribute(DOM.getElementById("course_info"), "style", "visibility:visible");
				coursePanel.insert(l, 0);
			}
		});
	}

	private boolean registered() {
		return false;
	}

	public VerticalPanel getPanel(String sessionID) {
		if (!loaded) {
			loadCourseInfo(sessionID);
			loaded = true;

			if (!registered()) {
				coursePanel.add(mesgLabel);
				coursePanel.add(registerButton);
			}
		}
		return coursePanel;
	}

	public void remove() {
		RootPanel.get("courses").remove(coursePanel);
		loaded = false;
		coursePanel.clear();
	}
}