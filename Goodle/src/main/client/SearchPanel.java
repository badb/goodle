package main.client;

import java.util.logging.Logger;

import main.client.panels.GoodlePanel;
import main.client.services.ServicesManager;

import com.google.gwt.event.dom.client.FocusEvent;
import com.google.gwt.event.dom.client.FocusHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.user.client.ui.MultiWordSuggestOracle;
import com.google.gwt.user.client.ui.SuggestBox;
import com.google.gwt.user.client.ui.VerticalPanel;


public class SearchPanel extends GoodlePanel {
	
	private VerticalPanel panel = new VerticalPanel();
	private SuggestBox searchBox = new SuggestBox(createCoursesOracle());
	
	private static Logger logger = Logger.getLogger("");
	
	public SearchPanel(ServicesManager controller) {
		super(controller);
		final String defaultText = "Szukaj przedmiotu...";
		searchBox.setText(defaultText);
		panel.add(searchBox);
		
		searchBox.getTextBox().addFocusHandler(new FocusHandler() {
			@Override
			public void onFocus(FocusEvent event) {
				if (searchBox.getText().compareTo(defaultText) == 0) { 
					searchBox.setText("");
					searchBox.getTextBox().getElement().setAttribute("style", "color:black");
				}
			}
		});
		searchBox.setLimit(20);
		
		searchBox.addKeyPressHandler(new KeyPressHandler() {
			public void onKeyPress(KeyPressEvent event) {
			    if (event.getNativeEvent().getKeyCode() == KeyCodes.KEY_ENTER) {
			       logger.info("Search for: " + searchBox.getText());
			       // getGoodle().searchForCourse(searchBox.getText());
			      }
			}
		});
	}
	
	 public VerticalPanel getPanel() {
         return panel;
	 }

	 public void clearTextBox() {
		 searchBox.getTextBox().getElement().setAttribute("style", "color:#C9C9C9");
		 searchBox.setText("Szukaj przedmiotu...");
	 }
	
    private MultiWordSuggestOracle createCoursesOracle() {
        MultiWordSuggestOracle oracle = new MultiWordSuggestOracle();
        /* to tylko testowe */
        oracle.add("Kurs1Kurs1Kurs1Kurs1Kurs1Kurs1Kurs1Kurs1Kurs1Kurs1Kurs1");
        oracle.add("Kurs2");
        oracle.add("Kurs3");  
        /* koniec testowych danych */
        
        //getGoodleServiceController().getAllCourses();
        return oracle;
    }
}
