package edu.goodle.prototype.client;

import java.util.logging.Logger;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class CourseListPanel extends GoodlePanel {
    private VerticalPanel coursePanel = new VerticalPanel();
    private static Logger logger = Logger.getLogger("");
    private Boolean loaded;


	public CourseListPanel(GoodleServiceAsync goodleService, Goodle goodle) {
		super(goodleService, goodle);
		loaded = false;
	}
	
    public void loadCourses (String sessionID) {
        getGoodleService().getCourses(sessionID, new AsyncCallback<String>() {
                public void onFailure(Throwable caught) {
                        logger.severe("getCourses: fail " + caught);
                }
                public void onSuccess(String result) {
                        logger.info("getCourses: ok");
                        Hyperlink h = new Hyperlink("Kurs1", "Kurs1");
                        h.addClickHandler(new ClickHandler() {
                        	public void onClick(ClickEvent event) {
                        		getGoodle().changeToCourse("kurs1");
                        	}
                        });
                        coursePanel.add(h);
                        h = new Hyperlink("Kurs2", "Kurs2");
                        coursePanel.add(h);
                }
        });
    }

    public VerticalPanel getPanel(String sessionId) {
        if (!loaded) {
                loadCourses(sessionId);
                loaded = true;
        }
        return coursePanel;
    }
}

