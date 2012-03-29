package edu.goodle.prototype.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FileUpload;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.FormPanel.SubmitCompleteEvent;


public class UploadPanel extends GoodlePanel {
	private VerticalPanel panel = new VerticalPanel();
	private HorizontalPanel formPanel = new HorizontalPanel();
	final FormPanel uploadForm = new FormPanel();
	Label info = new Label();
	FileUpload upload = new FileUpload();
	Label titleLabel = new Label();
	final TextBox titleBox = new TextBox();


	Button submit = new Button("Submit");

	FlexTable resultsTable = new FlexTable();

	// Use an RPC call to the Blob Service to get the blobstore upload url
	BlobServiceAsync blobService = GWT.create(BlobService.class);

	public UploadPanel(GoodleServiceController goodleService, Goodle goodle) {
		super(goodleService, goodle);
	}

	public VerticalPanel getPanel() {

		upload.setName("uploadFile");
		formPanel.add(upload);
		titleLabel.setText("Podaj tytuł pliku:");
		formPanel.add(titleLabel);
		titleBox.setName("title");
		formPanel.add(titleBox);
		formPanel.add(submit);
		panel.add(formPanel);
		
		panel.add(info);
		panel.add(resultsTable);
		
		uploadForm.setEncoding(FormPanel.ENCODING_MULTIPART);
		uploadForm.setMethod(FormPanel.METHOD_POST);

	    uploadForm.setWidget(formPanel);
	    
		
		submit.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				blobService.getBlobStoreUploadUrl(new AsyncCallback<String>() {
					@Override
					public void onSuccess(String result) {
						// Set the form action to the newly created
						// blobstore upload URL
						uploadForm.setAction(result.toString());
						// Submit the form to complete the upload
						uploadForm.submit();
						uploadForm.reset();
					}

					@Override
					public void onFailure(Throwable caught) {
						caught.printStackTrace();
					}
				});
			}
		});

		uploadForm
				.addSubmitCompleteHandler(new FormPanel.SubmitCompleteHandler() {
					@Override
					public void onSubmitComplete(SubmitCompleteEvent event) {
						info.setText("Sukces!");

						// pokaż wysłany plik

						getFile(event.getResults().trim());

					}

				});

		RootPanel.get().add(uploadForm);
		return panel;
	}

	private void getFile(String id) {

		// Make another call to the Blob Service to retrieve the meta-data
		blobService.getUploadedFile(id, new AsyncCallback<ClientFile>() {
			public void onSuccess(ClientFile result) {

				FileViewPanel fileView = new FileViewPanel(result);
				panel.add(fileView.getPanel());
			}

			@Override
			public void onFailure(Throwable caught) {
				caught.printStackTrace();
			}
		});

	}
}
