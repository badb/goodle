package main.client.widgets;

import main.client.services.ServicesManager;

import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.user.client.ui.VerticalPanel;


public class LeftPanel extends GoodleWidget
{	
	private VerticalPanel mainPanel = new VerticalPanel();
	private CreateCourseCaller createCourseCaller;
	
	public LeftPanel(final ServicesManager manager, SimpleEventBus eventBus) 
	{ 
		super(manager, eventBus); 
		createCourseCaller = new CreateCourseCaller(manager, eventBus);
		mainPanel.add(createCourseCaller);
		initWidget(mainPanel);
	}
}
