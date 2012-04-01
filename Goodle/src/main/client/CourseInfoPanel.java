package main.client;

import main.client.panels.GoodlePanel;
import main.client.services.ServicesManager;

import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.Label;


public class CourseInfoPanel extends GoodlePanel {

	private HorizontalPanel panel = new HorizontalPanel();
	private Label text = new Label("Tu będzie opis przedmiotu pobrany z USOSa");
	private Hyperlink link = new Hyperlink("[Więcej]", "[Więcej]");
	
	public CourseInfoPanel(ServicesManager controller) {
		super(controller);
		
		panel.add(text);
		panel.add(link);
	}

	public HorizontalPanel getPanel() {
		return panel;
	}
}
