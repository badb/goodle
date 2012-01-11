package edu.goodle.prototype.client;

import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.Label;

public class UserNavPanel extends GoodlePanel {

	private HorizontalPanel panel = new HorizontalPanel();
	private Label name = new Label("Roman | ");
	private Hyperlink logout = new Hyperlink(" Wyloguj", "Wyloguj");
	
	public UserNavPanel(GoodleServiceAsync goodleService, Goodle goodle) {
		super(goodleService, goodle);
		panel.add(name);
		panel.add(logout);
	}

	public HorizontalPanel getPanel() {
		return panel;
	}
}
