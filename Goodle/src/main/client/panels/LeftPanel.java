package main.client.panels;

import main.client.services.ServicesManager;

import com.google.gwt.user.client.ui.VerticalPanel;


public class LeftPanel extends GoodlePanel
{
	private VerticalPanel mainPanel = new VerticalPanel();
	
	public LeftPanel(ServicesManager manager) 
	{ 
		super(manager); 
		initWidget(mainPanel);
	}
}
