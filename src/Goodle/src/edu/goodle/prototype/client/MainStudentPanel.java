package edu.goodle.prototype.client;

import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TabBar;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

@SuppressWarnings({ "deprecation", "unused" })
public class MainStudentPanel extends GoodlePanel {

	private VerticalPanel mainPanel = new VerticalPanel();
	private TabBar bar = new TabBar();
	private CourseListPanel cp;
	
	public MainStudentPanel(GoodleServiceAsync goodleService, Goodle goodle) {
		super(goodleService, goodle);
		bar.addTab("Kursy");
		bar.addTab("Ustawienia");
		bar.addTab("Pomoc");
	    bar.selectTab(0);
	    
	    bar.addSelectionHandler(new SelectionHandler<Integer>() {
	    	public void onSelection(SelectionEvent<Integer> event) {
	    		RootPanel.get("page").clear();
				if (event.getSelectedItem() == 0) {
					RootPanel.get("page").add(cp.getPanel("kwa"));
				}
	    	}
	    });
	    cp = new CourseListPanel(goodleService, goodle);
		mainPanel.add(bar);
		RootPanel.get("page").clear();
		RootPanel.get("page").add(cp.getPanel("kwa"));
	}
	
	public VerticalPanel getPanel() {
		return mainPanel;
	}

}
