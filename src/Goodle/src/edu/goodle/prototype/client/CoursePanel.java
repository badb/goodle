package edu.goodle.prototype.client;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.RootPanel;

public class CoursePanel extends GoodlePanel{
	private VerticalPanel coursePanel = new VerticalPanel();
	private String courseID;

	public CoursePanel(GoodleServiceController controller, Goodle goodle, String courseID) {
		super(controller, goodle);
		this.courseID = courseID;
	}

	public void load(String result) {
		Label l = new Label(result);
		DOM.setElementAttribute(l.getElement(), "id", "course_name");
		DOM.setElementAttribute(DOM.getElementById("course_info"), "style", "visibility:visible");
		coursePanel.insert(l, 0);
	}

	public VerticalPanel getPanel(String sessionID) {
		return coursePanel;
	}

	public void remove() {
		RootPanel.get("courses").remove(coursePanel);
		coursePanel.clear();
	}
	
	public VerticalPanel getPanel(){
		return coursePanel;
	}
}