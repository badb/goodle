package main.client.ui;

import main.client.BlobService;
import main.client.BlobServiceAsync;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.FormPanel.SubmitCompleteEvent;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public class FileUploadView extends Composite {

	private static FileUploadViewUiBinder uiBinder = GWT
			.create(FileUploadViewUiBinder.class);

	interface FileUploadViewUiBinder extends UiBinder<Widget, FileUploadView> {
	}

	public FileUploadView() {
		initWidget(uiBinder.createAndBindUi(this));
		form.setAction(GWT.getModuleBaseURL() +"upload");
		form.setEncoding(FormPanel.ENCODING_MULTIPART);
		form.setMethod(FormPanel.METHOD_POST);
		 
	}

	@UiField Button submitButton;
	@UiField FormPanel form;
	@UiField Label info;
	BlobServiceAsync blobService = GWT.create(BlobService.class);

	@UiHandler("submitButton")
	public void submitButtonClickHandler(ClickEvent event) {
		blobService.getBlobStoreUploadUrl(new AsyncCallback<String>() {
			@Override
			public void onSuccess(String result) {
				// Set the form action to the newly created
				// blobstore upload URL
				form.setAction(result.toString());
				// Submit the form to complete the upload
				info.setText(result);
				form.submit();
				form.reset();
			}

			@Override
			public void onFailure(Throwable caught) {
				caught.printStackTrace();
			}
		});
	
	}
	
	@UiHandler("form")
	public void formSubmitComplete(SubmitCompleteEvent event) {
			info.setText("Sukces!" + event.getResults());

			// pokaż wysłany plik

			//getFile(event.getResults().trim());

		}
	}
