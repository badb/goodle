package edu.goodle.prototype.client;


import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Frame;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;


public class FileViewPanel {

	private VerticalPanel panel = new VerticalPanel();
	private HorizontalPanel hpanel = new HorizontalPanel();
	private ClientFile file;

	private Button downloadButton = new Button("Zapisz na dysku");
	private Label name = new Label();
	private Button showButton = new Button("Podgląd");
	private Button hideButton = new Button("Ukryj podgląd");
	private Frame frame;
	
	public FileViewPanel(ClientFile file) {
		this.file = file;
	}

	public VerticalPanel getPanel() {

		// BlobServiceAsync blobService = GWT.create(BlobService.class);
		name.setText(file.getName());
		hpanel.add(name);

		showButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				
				String url = GWT.getHostPageBaseURL() + file.getUrl().substring(1);
				frame = new Frame("http://docs.google.com/viewer?url=" + url + "&embedded=true");
		
				frame.setVisible(true);

				panel.add(frame);
				
				showButton.setVisible(false);
				hideButton.setVisible(true);
			}

		});
		hpanel.add(showButton);
		
		hideButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				showButton.setVisible(true);
				hideButton.setVisible(false);
				frame.setVisible(false);
			}
		});
		hideButton.setVisible(false);
		hpanel.add(hideButton);
		
		downloadButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				
				Window.open(file.getUrl(), "_self", "");
				
			}

		});
		hpanel.add(downloadButton);
		panel.add(hpanel);
		return panel;

	}

}