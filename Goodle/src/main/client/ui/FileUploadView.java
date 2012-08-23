package main.client.ui;

import main.client.BlobServiceAsync;
import main.client.ClientFactory;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FileUpload;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.FormPanel.SubmitCompleteEvent;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public class FileUploadView extends Composite {

	private static FileUploadViewUiBinder uiBinder = GWT
			.create(FileUploadViewUiBinder.class);

	interface FileUploadViewUiBinder extends UiBinder<Widget, FileUploadView> {
	}

	public FileUploadView() {
		initWidget(uiBinder.createAndBindUi(this));
		form.setAction(GWT.getModuleBaseURL() +"uploadservice");
		form.setEncoding(FormPanel.ENCODING_MULTIPART);
		form.setMethod(FormPanel.METHOD_POST);
		 
	}

	private ClientFactory clientFactory;
	private BlobServiceAsync blobService;
	private FileContainerInterface parent;
	private String titleText;

	@UiField Button submitButton;
	@UiField FormPanel form;
	@UiField TextBox title;
	@UiField Label info;
	@UiField FileUpload uploadField;
	
	@UiHandler("submitButton")
	public void submitButtonClickHandler(ClickEvent event) {
		if (title.getText().equals("")) {
			titleText = uploadField.getFilename();
		} else {
			titleText = title.getText();
		}
		blobService.getBlobStoreUploadUrl(new AsyncCallback<String>() {
			@Override
			public void onSuccess(String result) {
				// Set the form action to the newly created
				// blobstore upload URL
				form.setAction(result.toString());
				// Submit the form to complete the upload
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
			//info.setText("Sukces!" + event.getResults().trim());

			String url = "/goodle/blobservice?blob-key=" + event.getResults().trim();
			parent.addFile(url, titleText);
			
		}

	public ClientFactory getClientFactory() {
		return clientFactory;
	}

	public void setClientFactory(ClientFactory clientFactory) {
		this.clientFactory = clientFactory;
		blobService = clientFactory.getBlobService();
	}
	public void setParent(FileContainerInterface parent) {
		this.parent = parent;
	}
	}
