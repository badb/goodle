package edu.goodle.prototype.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;

public class UserNavPanel extends GoodlePanel {

	private HorizontalPanel panel = new HorizontalPanel();
	private Label name = new Label("Roman | ");
	private Button logoutButton = new Button(" Wyloguj");
	
	public UserNavPanel(GoodleServiceAsync goodleService, final Goodle goodle) {
		super(goodleService, goodle);
		panel.add(name);
		panel.add(logoutButton);
		
		logoutButton.getElement().setId("logout_button");
        logoutButton.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
            	goodle.logout();
            }
        });
	}

	public HorizontalPanel getPanel() {
		return panel;
	}
}
