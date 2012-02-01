package edu.goodle.prototype.client;

import java.util.logging.Logger;

import com.google.gwt.event.dom.client.FocusEvent;
import com.google.gwt.event.dom.client.FocusHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.MultiWordSuggestOracle;
import com.google.gwt.user.client.ui.SuggestBox;
import com.google.gwt.user.client.ui.VerticalPanel;

public class SearchPanel extends GoodlePanel {
	
	private VerticalPanel panel = new VerticalPanel();
	private SuggestBox searchBox = new SuggestBox(createCoursesOracle());
	
	private static Logger logger = Logger.getLogger("");
	
	public SearchPanel(GoodleServiceAsync goodleService, Goodle goodle) {
		super(goodleService, goodle);
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
			        getGoodle().loadCourse(searchBox.getText());
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
        
        getGoodleService().getAllCourses(getGoodle().getSession(),
                new AsyncCallback<String>() {

                public void onFailure(Throwable caught) {
                        logger.severe("GetAllCourses failed." + caught);
                }
                public void onSuccess(String result) {
                        logger.info("GetAllCourses:" + result);
                }
        });
        return oracle;
    }
}
