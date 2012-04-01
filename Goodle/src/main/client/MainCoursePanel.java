package main.client;

import main.client.panels.GoodlePanel;
import main.client.services.ServicesManager;

import com.google.gwt.user.client.ui.TabBar;
import com.google.gwt.user.client.ui.VerticalPanel;


public class MainCoursePanel extends GoodlePanel {

	private VerticalPanel mainPanel = new VerticalPanel();
	private TabBar bar = new TabBar();
	
	public MainCoursePanel(ServicesManager controller) {
		super(controller);
		bar.addTab("Zajęcia");
		bar.addTab("Prace domowe");
		bar.addTab("Ankiety");
		bar.selectTab(0);
		mainPanel.add(bar);
	}
	
	public VerticalPanel getPanel() {
		return mainPanel;
	}

}
