package edu.goodle.prototype.client;

import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Hyperlink;

public class NavPathPanel extends GoodlePanel {

	private HorizontalPanel panel = new HorizontalPanel();
	private Hyperlink link = new Hyperlink("Kursy", "Kursy");
	
	public NavPathPanel(GoodleServiceAsync goodleService, Goodle goodle) {
		super(goodleService, goodle);
		panel.add(link);
	}

	public HorizontalPanel getPanel() {
		return panel;
	}
}
