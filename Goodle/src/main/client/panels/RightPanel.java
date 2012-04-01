package main.client.panels;

import main.client.services.ServicesManager;

import com.google.gwt.user.client.ui.VerticalPanel;


public class RightPanel extends GoodlePanel
{
	private VerticalPanel mainPanel = new VerticalPanel();
	
	public RightPanel(ServicesManager manager) 
	{ 
		super(manager); 
		initWidget(mainPanel);
	}
}
