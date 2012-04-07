package main.client.widgets;

import main.client.services.ServicesManager;
import main.shared.models.Course;

import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;


public class CourseViewPanel extends GoodleWidget
{
	private Course course;
	
	private VerticalPanel mainPanel = new VerticalPanel();
	private Label courseName;
	private Label courseDesc;
	
	public CourseViewPanel(ServicesManager manager, SimpleEventBus eventBus, Course course) 
	{ 
		super(manager, eventBus);
		this.course = course;
		courseName = new Label(course.getName() + " (" + course.getTerm() + ")");
		courseDesc = new Label(course.getDesc());
		mainPanel.add(courseName);
		mainPanel.add(courseDesc);
		initWidget(mainPanel);
	}
}
