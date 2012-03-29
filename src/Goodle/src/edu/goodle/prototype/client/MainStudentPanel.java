package edu.goodle.prototype.client;

import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TabBar;
import com.google.gwt.user.client.ui.VerticalPanel;

public class MainStudentPanel extends GoodlePanel {

	private VerticalPanel mainPanel = new VerticalPanel();
	private TabBar bar = new TabBar();
	private CourseListPanel cp;
	
	public MainStudentPanel(GoodleServiceController controller, Goodle goodle) {
		super(controller, goodle);
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
	    cp = new CourseListPanel(controller, goodle);
		mainPanel.add(bar);
		

        //TODO tymczasowe:
        UploadPanel up = new UploadPanel(controller, goodle);
        mainPanel.add(up.getPanel());
        
        
		RootPanel.get("page").clear();
		RootPanel.get("page").add(cp.getPanel("kwa"));
	}
	
	public VerticalPanel getPanel() {
		return mainPanel;
	}
	
	public void setNone() {
		bar.selectTab(-1);
	}

}
