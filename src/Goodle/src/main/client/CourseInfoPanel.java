package main.client;

import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.Label;

public class CourseInfoPanel extends GoodlePanel {

	private HorizontalPanel panel = new HorizontalPanel();
	private Label text = new Label("Tu będzie opis przedmiotu pobrany z USOSa");
	private Hyperlink link = new Hyperlink("[Więcej]", "[Więcej]");
	
	public CourseInfoPanel(GoodleServiceController controller, Goodle goodle) {
		super(controller, goodle);
		
		panel.add(text);
		panel.add(link);
	}

	public HorizontalPanel getPanel() {
		return panel;
	}
}
