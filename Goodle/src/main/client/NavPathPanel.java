package main.client;

import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.Label;

public class NavPathPanel extends GoodlePanel {

	private HorizontalPanel panel = new HorizontalPanel();
	private Hyperlink link = new Hyperlink("Kursy", "Kursy");
	
	public NavPathPanel(GoodleServiceController controller, Goodle goodle) {
		super(controller, goodle);
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
