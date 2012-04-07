package main.client.widgets;

import main.client.services.ServicesManager;

import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;


public class TopPanel extends GoodleWidget
{
	private HorizontalPanel mainPanel = new HorizontalPanel();
	private CourseSearchWidget searchPanel;
	private Image userAvatar = new Image();
	private Label userLogin = new Label("Hermiona Granger");
	private Button logOutButton = new Button("X");
	
	public TopPanel(final ServicesManager manager, SimpleEventBus eventBus) 
	{ 
		super(manager, eventBus);
		searchPanel = new CourseSearchWidget(manager, eventBus);
		mainPanel.add(searchPanel);
		mainPanel.add(userAvatar);
		mainPanel.add(userLogin);
		mainPanel.add(logOutButton);
		initWidget(mainPanel);
	}
}