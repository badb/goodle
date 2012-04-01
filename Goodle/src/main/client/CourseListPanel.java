package main.client;

import main.client.panels.GoodlePanel;
import main.client.services.ServicesManager;

import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.VerticalPanel;


public class CourseListPanel extends GoodlePanel {
    private VerticalPanel coursePanel = new VerticalPanel();
    public CourseListPanel(ServicesManager controller) {
		super(controller);
	}

    public VerticalPanel getPanel(String text) {
    	coursePanel.clear();
    	String delimiter = "\\|";
    	String[] courses = text.split(delimiter);
    	for (int i = 0; i < courses.length; ++i) {
    		Hyperlink h = new Hyperlink(courses[i], courses[i]);
        	coursePanel.add(h);
    	}
        return coursePanel;
    }
}

