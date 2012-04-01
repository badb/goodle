package main.client.panels;

import main.client.services.ServicesManager;
import main.shared.models.Course;

import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;


public class CoursePanel extends GoodlePanel
{
	private Course course;
	
	private VerticalPanel mainPanel = new VerticalPanel();
	private Label courseName;
	private Label courseDesc;
	private HorizontalPanel courseMenu = new HorizontalPanel();
	
	public CoursePanel(ServicesManager manager, Course course) 
	{ 
		super(manager); 
		courseName = new Label(course.getName());
		courseDesc = new Label(course.getDesc().toString());
		mainPanel.add(courseName);
		mainPanel.add(courseDesc);
		mainPanel.add(courseMenu);
		initWidget(mainPanel);
	}
}
