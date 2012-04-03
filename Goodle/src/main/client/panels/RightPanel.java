package main.client.panels;

import main.client.services.ServicesManager;

import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;


public class RightPanel extends GoodlePanel
{
	private VerticalPanel mainPanel = new VerticalPanel();
	
	private Label tempLabel = new Label("To jest prawy panel");
	
	public RightPanel(ServicesManager manager) 
	{ 
		super(manager); 
		mainPanel.add(tempLabel);
		initWidget(mainPanel);
	}
}
