package edu.goodle.prototype.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FileUpload;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.FormPanel.SubmitCompleteEvent;

import edu.goodle.prototype.db.UploadedFile;

public class UploadPanel extends GoodlePanel {
	private VerticalPanel panel = new VerticalPanel();
	final FormPanel uploadForm = new FormPanel();
	Label info = new Label();
	FileUpload upload = new FileUpload();

	// Use an RPC call to the Blob Service to get the blobstore upload url
	BlobServiceAsync blobService = GWT.create(BlobService.class);

	public UploadPanel(GoodleServiceAsync goodleService, Goodle goodle) {
		super(goodleService, goodle);

	}

	public VerticalPanel getPanel() {
		// FileUpload fu = new FileUpload();

		// panel.add(fu);

		uploadForm.setEncoding(FormPanel.ENCODING_MULTIPART);
		uploadForm.setMethod(FormPanel.METHOD_POST);
		
		upload.setName("upload");
		panel.add(upload);

		Button submit = new Button("Wyślij plik");
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
		

		panel.add(submit);

		panel.add(info);

		uploadForm
				.addSubmitCompleteHandler(new FormPanel.SubmitCompleteHandler() {
					@Override
					public void onSubmitComplete(SubmitCompleteEvent event) {
						info.setText("Sukces!");

						// pokaż wysłany plik

						getFile(event.getResults().trim());

					}

				});

		panel.add(uploadForm);
		return panel;
	}

	private void getFile(String id) {

		// Make another call to the Blob Service to retrieve the meta-data
		blobService.getUploadedFile(id, new AsyncCallback<ClientFile>() {

			public void onSuccess(ClientFile result) {

				// TODO: pokazanie pliku
				FileViewPanel fileView = new FileViewPanel(result);
				panel.add(fileView);

			}

			@Override
			public void onFailure(Throwable caught) {
				caught.printStackTrace();
			}
		});

	}
}
