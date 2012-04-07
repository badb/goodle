package main.client.widgets;

import main.client.events.CourseCreatedEvent;
import main.client.events.CourseCreatedEventHandler;
import main.client.events.CourseViewRequestedEvent;
import main.client.events.CourseViewRequestedEventHandler;
import main.client.events.CoursesFoundEvent;
import main.client.events.CoursesFoundEventHandler;
import main.client.events.CreateCourseViewRequestedEvent;
import main.client.events.CreateCourseViewRequestedEventHandler;
import main.client.services.ServicesManager;

import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.user.client.ui.HorizontalPanel;

public class ContentPanel extends GoodleWidget 
{
	private HorizontalPanel mainPanel = new HorizontalPanel();
	
	public ContentPanel(final ServicesManager manager, final SimpleEventBus eventBus) 
	{
		super(manager, eventBus);
		listenForEvents();
		initWidget(mainPanel);
	}
	
	private void listenForEvents()
	{
		eventBus.addHandler(CourseCreatedEvent.TYPE, new CourseCreatedEventHandler()
		{
			public void onEvent(CourseCreatedEvent event) 
			{
				mainPanel.clear();
				mainPanel.add(new CourseViewPanel(manager, eventBus, event.getCourse()));
			}
		});		
		eventBus.addHandler(CourseViewRequestedEvent.TYPE, new CourseViewRequestedEventHandler()
		{
			public void onEvent(CourseViewRequestedEvent event) 
			{
				mainPanel.clear();
				mainPanel.add(new CourseViewPanel(manager, eventBus, event.getCourse()));
			}		
		});
		eventBus.addHandler(CreateCourseViewRequestedEvent.TYPE, new CreateCourseViewRequestedEventHandler()
		{
			public void onEvent(CreateCourseViewRequestedEvent event) 
			{
				mainPanel.clear();
				mainPanel.add(new CreateCourseForm(manager, eventBus));
			}		
		});
		eventBus.addHandler(CoursesFoundEvent.TYPE, new CoursesFoundEventHandler()
		{
			public void onEvent(CoursesFoundEvent event) 
			{
				mainPanel.clear();
				mainPanel.add(new CoursesListPanel(manager, eventBus, event.getCourses()));
			}		
		});
	}
}
