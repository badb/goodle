package main.client.panels;

import main.client.services.ServicesManager;

import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;


public class ResultListPanel extends GoodlePanel
{
	private VerticalPanel mainPanel = new VerticalPanel();
	private Label testLabel = new Label("Ala ma kota");
	
	public ResultListPanel(ServicesManager manager) 
	{ 
		super(manager); 
		mainPanel.add(testLabel);
		initWidget(mainPanel);
	}
}
