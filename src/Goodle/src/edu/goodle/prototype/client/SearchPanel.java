package edu.goodle.prototype.client;

import java.util.logging.Logger;

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
		searchBox.setText("Szukaj przedmiotu...");
		panel.add(searchBox);
		
	}
	
	 public VerticalPanel getPanel() {
         return panel;
	 }


	
    private MultiWordSuggestOracle createCoursesOracle() {
        MultiWordSuggestOracle oracle = new MultiWordSuggestOracle();
        oracle.add("AWERT");
        oracle.add("BLA");
        oracle.add("QWERTY");
        
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
