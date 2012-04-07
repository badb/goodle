package main.client;

import main.client.services.ServicesManager;
import main.client.widgets.GoodleWidget;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.VerticalPanel;


public class RegisterPanel extends GoodleWidget {

	private HorizontalPanel panel = new HorizontalPanel();
	private VerticalPanel formPanel = new VerticalPanel();
	private Label registerLabel = new Label("Nie jesteś zapisany.");
	private Button registerButton = new Button("Zapisz się");
	private Label formLabel = new Label("Prośba o zapisanie na zajęcia");
	private Label reasonLabel = new Label("Komentarz / uzasadnienie:");
	private Button sendButton = new Button("Wyślij");
	private TextArea reasonTextArea = new TextArea();
	
	public RegisterPanel(ServicesManager manager, SimpleEventBus eventBus) {
		super(manager, eventBus);
		registerButton.getElement().setId("register_button");
		panel.add(registerLabel);
		panel.add(registerButton);
		formPanel.add(formLabel);
		formPanel.add(reasonLabel);
		formPanel.add(reasonTextArea);
		formPanel.add(sendButton);
		registerButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				panel.clear();
				panel.add(formPanel);
			}
			
		});
	}
	
	public HorizontalPanel getPanel() {

		return panel;
	}

}
