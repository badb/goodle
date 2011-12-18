package edu.goodle.prototype.client;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.RootPanel;

import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.LogRecord;

public class CourseListPanel extends GoodlePanel{
	private VerticalPanel coursePanel = new VerticalPanel();
	private static Logger logger = Logger.getLogger("");
	private Boolean loaded;

	public CourseListPanel(GoodleServiceAsync goodleService, Goodle goodle) {
		super(goodleService, goodle);
		this.loaded = false;
	}

	public void loadCourses (String sessionID) {
		getGoodleService().getCourses(sessionID, new AsyncCallback<String>() {
			public void onFailure(Throwable caught) {
				logger.severe("getCourses: fail " + caught);
			}
			public void onSuccess(String result) {
				logger.info("getCourses: ok");
				Hyperlink h = new Hyperlink(result, "");

				h.addClickHandler(new ClickHandler(){
					@Override
					public void onClick(ClickEvent event) {
						RootPanel.get("courses").remove(coursePanel);
						CoursePanel cp = new CoursePanel(getGoodleService(), getGoodle(), "Blablaliza fetoryczna");
						RootPanel.get("course_info").add(cp.getPanel(getGoodle().getSession()));
					}
				});
				coursePanel.add(h);
			}
		});
	}

	public VerticalPanel getPanel(String sessionId) {
		if (!loaded) {
			loadCourses(sessionId);
			loaded = true;
		}
		return coursePanel;
	}

	public void remove() {
		RootPanel.get("courses").remove(coursePanel);
		loaded = false;
		coursePanel.clear();
	}
}