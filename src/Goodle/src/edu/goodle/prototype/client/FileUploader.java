package edu.goodle.prototype.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.*;
import com.google.gwt.user.client.ui.FormPanel.SubmitCompleteEvent;
import com.google.gwt.user.client.rpc.AsyncCallback;

import edu.goodle.prototype.db.UploadedFile;


public class FileUploader implements EntryPoint {
	// You must use a FormPanel to create a blobstore upload form
	final FormPanel uploadForm = new FormPanel();

	// Use an RPC call to the Blob Service to get the blobstore upload url
	BlobServiceAsync blobService = GWT.create(BlobService.class);

	VerticalPanel mainVerticalPanel = new VerticalPanel();
	HorizontalPanel hp1 = new HorizontalPanel();
	HorizontalPanel hp2 = new HorizontalPanel();
	HTML titleLabel = new HTML("Title");
	HTML descriptionLabel = new HTML("Description");
	TextBox titleTextBox = new TextBox();
	TextBox descriptionTextBox = new TextBox();
	FileUpload upload = new FileUpload();
	Button submitButton = new Button("Submit");

	FlexTable resultsTable = new FlexTable();

	@Override
	public void onModuleLoad() {

		hp1.add(titleLabel);
		hp1.add(titleTextBox);
		hp2.add(descriptionLabel);
		hp2.add(descriptionTextBox);

		mainVerticalPanel.add(hp1);
		mainVerticalPanel.add(hp2);
		mainVerticalPanel.add(upload);

		mainVerticalPanel.add(submitButton);
		mainVerticalPanel.add(resultsTable);

		hp1.setSpacing(5);
		hp2.setSpacing(5);
		mainVerticalPanel.setSpacing(5);

		uploadForm.setWidget(mainVerticalPanel);

		// The upload form, when submitted, will trigger an HTTP call to the
		// servlet. The following parameters must be set
		uploadForm.setEncoding(FormPanel.ENCODING_MULTIPART);
		uploadForm.setMethod(FormPanel.METHOD_POST);

		// Set Names for the text boxes so that they can be retrieved from the
		// HTTP call as parameters
		titleTextBox.setName("titleTextBox");
		descriptionTextBox.setName("descriptionTextBox");
		upload.setName("upload");

		RootPanel.get("container").add(uploadForm);

		submitButton.addClickHandler(new ClickHandler() {
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

						// The submit complete Event Results will contain the
						// unique
						// identifier for the picture's meta-data. Trim it to
						// remove
						// trailing spaces and line breaks

					}

				});

	}


}
