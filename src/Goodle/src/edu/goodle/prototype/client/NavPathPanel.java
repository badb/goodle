package edu.goodle.prototype.client;

import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;

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
	
	public void addNext(String name) {
		Label l = new Label(" » ");
		Hyperlink h = new Hyperlink(name, name);
		panel.add(l);
		panel.add(h);
	}
}
