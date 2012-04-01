package main.client;

import main.client.panels.GoodlePanel;
import main.client.services.ServicesManager;

import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.Label;


public class NavPathPanel extends GoodlePanel {

	private HorizontalPanel panel = new HorizontalPanel();
	private Hyperlink link = new Hyperlink("Kursy", "Kursy");
	
	public NavPathPanel(ServicesManager controller) {
		super(controller);
		panel.add(link);
	}

	public HorizontalPanel getPanel() {
		return panel;
	}
	
	public void clear() {
		panel.clear();
	}
	
	public void addNext(String name) {
		Label l = new Label(" » ");
		Hyperlink h = new Hyperlink(name, name);
		panel.add(l);
		panel.add(h);
	}
}
