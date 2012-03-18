package edu.goodle.prototype.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;


public class FileViewPanel {

	private VerticalPanel panel = new VerticalPanel();
	private ClientFile file;

	private Button downloadButton = new Button("Zapisz na dysku");
	private Label name = new Label();

	public FileViewPanel(ClientFile file) {
		this.file = file;
	}

	public VerticalPanel getPanel() {

		// BlobServiceAsync blobService = GWT.create(BlobService.class);
		name.setText(file.getName());
		panel.add(name);

		//TODO właściwe pokazywanie plików
		
		downloadButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				//TODO właściwe pobieranie plików
				//to działa dla pdfów na chrome, ale trzeba zmienić tytuł
				Window.open(file.getUrl(), "_self", "");
	        
			}

		});
		panel.add(downloadButton);

		return panel;

	}

}
