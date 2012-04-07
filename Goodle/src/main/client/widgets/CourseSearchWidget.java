package main.client.widgets;

import main.client.services.ServicesManager;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.TextBox;

public class CourseSearchWidget extends GoodleWidget 
{
	private HorizontalPanel mainPanel = new HorizontalPanel();
	private TextBox searchBox = new TextBox();
	private Button searchButton = new Button("Szukaj");
	
	public CourseSearchWidget(final ServicesManager manager, SimpleEventBus eventBus) 
	{
		super(manager, eventBus);
		searchBox.getElement().setAttribute("placeholder", "Szukaj kursu");
	    searchBox.addKeyPressHandler(new KeyPressHandler() 
	    {
	        public void onKeyPress(KeyPressEvent event)
	        {
	            if (event.getCharCode() == KeyCodes.KEY_ENTER) 
	            { 
	            	manager.findCoursesByName(searchBox.getText()); 
	            }
	        }
	    });
	    searchButton.addClickHandler(new ClickHandler() 
	    {
	        public void onClick(ClickEvent event) 
	        { 
	        	manager.findCoursesByName(searchBox.getText()); 
	        }
	    });
		mainPanel.add(searchBox);
		mainPanel.add(searchButton);		
		initWidget(mainPanel);
	}

}
