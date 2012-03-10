package edu.goodle.prototype.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

import edu.goodle.prototype.db.UploadedFile;


public class FileViewPanel extends Widget {

	private VerticalPanel panel = new VerticalPanel();
	private ClientFile file;

    private Button downloadButton = new Button("Zapisz na dysku");
    private Label name = new Label(file.getName());
	
	
	public FileViewPanel(ClientFile file) {
		this.file = file;

	}
	
	public VerticalPanel getPanel() {

		  BlobServiceAsync blobService = GWT.create(BlobService.class);

		  panel.add(name);
		  
		  downloadButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
					//TODO
		      }
				
			});
		  panel.add(downloadButton);
		  
		  return panel;
		  
	}

}
