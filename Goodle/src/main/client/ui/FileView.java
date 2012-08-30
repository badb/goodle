package main.client.ui;

import java.util.Date;

import main.shared.proxy.UploadedFileProxy;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.http.client.URL;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Frame;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public class FileView extends AbstractCourseView {

	private static FileViewUiBinder uiBinder = GWT.create(FileViewUiBinder.class);
	
	interface FileViewUiBinder extends UiBinder<Widget, FileView> { }

	private boolean viewFile;
	@UiField Button showButton;
	@UiField Button downloadButton;
	@UiField Frame frame;
	@UiField Label title;
	@UiField Label date;
	
	private UploadedFileProxy file;
	private String url = "";
	private final String dateText = "Dodany: ";
	
	private boolean authorNameAsTitle = false;
	public void setAuthorNameAsTitle(boolean authorNameAsTitle) 
	{
		if (file != null)
		{
			if (authorNameAsTitle && (file.getAuthorName() != null))
				title.setText(file.getAuthorName());
			else
				title.setText(file.getName());
		}
		this.authorNameAsTitle = authorNameAsTitle;
		
	}

	public FileView() { initWidget(uiBinder.createAndBindUi(this)); }
	
	public void setUploadedFile(UploadedFileProxy file) 
	{
		this.file = file;
		init();
	}
	
	private void init() {
		url = file.getUrl().substring(1);
		if (authorNameAsTitle && (file.getAuthorName() != null))
			title.setText(file.getAuthorName());
		else 
			title.setText(file.getName());
		
		if (file.getUploaded() == null) 
			date.setText(dateText + new Date());
		else
			date.setText(dateText+file.getUploaded().toString());
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
		String safeURL = URL.encode(GWT.getHostPageBaseURL()+ url);
		frame.setUrl("http://docs.google.com/viewer?url=" + safeURL + "&embedded=true");
		frame.setVisible(true);
	}

	private void hideViewer() {
		frame.setVisible(false);
	}

	@UiHandler("downloadButton")
	public void onDownloadButtonClick(ClickEvent event) {
		Window.open(GWT.getHostPageBaseURL() + url, "_self", "");
		//Window.Location.replace(GWT.getHostPageBaseURL() + url);
	}

}
