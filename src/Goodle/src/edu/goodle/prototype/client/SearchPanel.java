package edu.goodle.prototype.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.MultiWordSuggestOracle;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SuggestBox;
import com.google.gwt.user.client.ui.VerticalPanel;

import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.LogRecord;

public class SearchPanel extends GoodlePanel {

	private static Logger logger = Logger.getLogger("");

	private HorizontalPanel searchPanel = new HorizontalPanel();
	private VerticalPanel searchMainPanel = new VerticalPanel();

	private SuggestBox searchBox = new SuggestBox(createCoursesOracle());
	private Button searchButton = new Button("OK");
	private Label searchLabel = new Label("Szukaj:");

	private VerticalPanel searchResultPanel = new VerticalPanel();

	public SearchPanel(GoodleServiceAsync goodleService, Goodle goodle) {
		super(goodleService, goodle);

		searchMainPanel.add(searchLabel);
		searchPanel.add(searchBox);
		searchPanel.add(searchButton);
		searchMainPanel.add(searchPanel);

		searchButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				searchResultPanel.clear();
				getGoodleService().searchCourse(getGoodle().getSession(),
					searchBox.getText(), new AsyncCallback<String>() {

					public void onFailure(Throwable caught) {
						logger.severe("Search failed." + caught);
					}
					public void onSuccess(String result) {
						logger.info("Search ok: " + result);
						Hyperlink h = new Hyperlink(result, result);
						searchResultPanel.add(h);

						h.addClickHandler(new ClickHandler(){
							@Override
							public void onClick(ClickEvent event) {
								RootPanel.get("main").remove(searchResultPanel);
								CoursePanel cp = new CoursePanel(getGoodleService(), getGoodle(), "Blablaliza fetoryczna");
								RootPanel.get("course_info").add(cp.getPanel(getGoodle().getSession()));
							}
						});
						getGoodle().showSearchResultPanel(searchResultPanel);
					}
				});
			}
		});
	}

	public VerticalPanel getPanel() {
		return searchMainPanel;
	}

	public void remove() {
		RootPanel.get("goodleSearch").remove(searchMainPanel);
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