package edu.goodle.prototype.client;

import com.google.gwt.user.client.ui.FileUpload;
import com.google.gwt.user.client.ui.VerticalPanel;

public class UploadPanel extends GoodlePanel {
	private VerticalPanel panel = new VerticalPanel();
	
	public UploadPanel(GoodleServiceAsync goodleService, Goodle goodle) {
		super(goodleService, goodle);

	}
	
	public VerticalPanel getPanel() {
		FileUpload fu = new FileUpload();
		
		
		panel.add(fu);
        return panel;
	}
}
