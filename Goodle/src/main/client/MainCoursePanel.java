package main.client;

import com.google.gwt.user.client.ui.TabBar;
import com.google.gwt.user.client.ui.VerticalPanel;

public class MainCoursePanel extends GoodlePanel {

	private VerticalPanel mainPanel = new VerticalPanel();
	private TabBar bar = new TabBar();
	
	public MainCoursePanel(GoodleServiceController controller, Goodle goodle) {
		super(controller, goodle);
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
