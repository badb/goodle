package edu.goodle.prototype.client;

import com.google.gwt.user.client.ui.TabBar;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class MainStudentPanel extends GoodlePanel {

	private VerticalPanel mainPanel = new VerticalPanel();
	private TabBar bar = new TabBar();
	
	public MainStudentPanel(GoodleServiceAsync goodleService, Goodle goodle) {
		super(goodleService, goodle);
		bar.addTab("Zajęcia");
		bar.addTab("Prace domowe");
		bar.addTab("Ankiety");
		mainPanel.add(bar);
	}
	
	public VerticalPanel getPanel() {
		return mainPanel;
	}

}
