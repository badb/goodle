package main.client.ui;

import main.client.ClientFactory;
import main.client.resources.GoodleResources;
import main.shared.proxy.UploadedFileProxy;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Frame;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public class FileEditView extends Composite {

	private static FileEditViewUiBinder uiBinder = GWT
			.create(FileEditViewUiBinder.class);

	private boolean viewFile;
	@UiField
	Button showButton;
	@UiField
	Button deleteButton;
	@UiField
	Frame frame;
	@UiField
	Label title;
	private UploadedFileProxy file;
	private String url = "";

	private ClientFactory clientFactory;
	private FileContainerInterface parent;
	public void setParent(FileContainerInterface parent) { this.parent = parent; }
	
	interface FileEditViewUiBinder extends UiBinder<Widget, FileEditView> {
	}

	public FileEditView() {
		initWidget(uiBinder.createAndBindUi(this));
	}
	
	public void setUploadedFile(UploadedFileProxy file) {
		this.file = file;
		init();
	}
	
	private void init() {
		url = file.getUrl().substring(1);
		title.setText(file.getName());

		Image deleteIcon = new Image(GoodleResources.INSTANCE.removeIcon());
		deleteIcon.getElement().setAttribute("title", "Usuń plik");
		deleteButton.getElement().appendChild(deleteIcon.getElement());
	}
	

	@UiHandler("showButton")
	public void onShowButtonClick(ClickEvent event) {
		if (!viewFile) {
			showViewer();
			viewFile = true;
		} else {
			hideViewer();
			viewFile = false;
		}

	}

	private void showViewer() {
		frame.setUrl("http://docs.google.com/viewer?url=" + GWT.getHostPageBaseURL()+ url
				+ "&embedded=true");
		frame.setVisible(true);
	}

	private void hideViewer() {
		frame.setVisible(false);
	}

	/*@UiHandler("downloadButton")
	public void onDownloadButtonClick(ClickEvent event) {
		Window.open(GWT.getHostPageBaseURL() + url, "_self", "");
		//Window.Location.replace(GWT.getHostPageBaseURL() + url);
	}*/

	public void setClientFactory(ClientFactory clientFactory) {
		this.clientFactory = clientFactory;
	}
	
	@UiHandler("deleteButton")
	public void onDeleteButtonClick(ClickEvent event) {
		parent.removeFile(file);
	}
}

