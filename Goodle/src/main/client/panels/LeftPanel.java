package main.client.panels;

import main.client.services.ServicesManager;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.VerticalPanel;


public class LeftPanel extends GoodlePanel
{	
	private VerticalPanel mainPanel = new VerticalPanel();
	private Button createCourseButton = new Button("Utwórz kurs");
	
	public LeftPanel(final ServicesManager manager) 
	{ 
		super(manager); 
		createCourseButton.addClickHandler(new ClickHandler()
	    {
	    	public void onClick(ClickEvent event) { manager.showCreateCoursePanel(); }
	    });
		mainPanel.add(createCourseButton);
		initWidget(mainPanel);
	}
}
