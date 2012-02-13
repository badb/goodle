package edu.goodle.prototype.client;

import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

public class CourseListPanel extends GoodlePanel {
    private VerticalPanel coursePanel = new VerticalPanel();
    public CourseListPanel(GoodleServiceAsync goodleService, Goodle goodle) {
		super(goodleService, goodle);
	}

    public VerticalPanel getPanel(String text) {
    	coursePanel.clear();
    	String delimiter = "\\|";
    	String[] courses = text.split(delimiter);
    	for (int i = 0; i < courses.length; ++i) {
        	Label l = new Label(courses[i]);
        	coursePanel.add(l);
    	}
        return coursePanel;
    }
}

