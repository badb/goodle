package main.client.panels;

import main.client.services.ServicesManager;
import main.shared.AsyncSuggestOracle;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SuggestBox;


public class TopPanel extends GoodlePanel
{
	private HorizontalPanel mainPanel = new HorizontalPanel();
	private SuggestBox searchBox;
	private Button searchButton = new Button("Szukaj");
	private Image userAvatar = new Image();
	private Label userLogin = new Label();
	private Button logOutButton = new Button("X");
	
	public TopPanel(final ServicesManager manager) 
	{ 
		super(manager);
		
		AsyncSuggestOracle oracle = new AsyncSuggestOracle();
		searchBox = new SuggestBox(oracle);
		
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
		mainPanel.add(userAvatar);
		mainPanel.add(userLogin);
		mainPanel.add(logOutButton);
		initWidget(mainPanel);
	}
}