package main.client.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import main.client.ClientFactory;
import main.client.place.CoursePlace;
import main.shared.proxy.CourseProxy;
import main.shared.proxy.CourseRequest;
import main.shared.proxy.MessageProxy;
import main.shared.proxy.ModuleProxy;
import main.shared.proxy.ModuleRequest;
import main.shared.proxy.UploadedFileProxy;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.google.web.bindery.requestfactory.shared.Receiver;
import com.google.web.bindery.requestfactory.shared.ServerFailure;

public class ModuleEditView extends Composite {

	private static ModuleWidgetUiBinder uiBinder = GWT.create(ModuleWidgetUiBinder.class);

	interface ModuleWidgetUiBinder extends UiBinder<Widget, ModuleEditView> { }

	@UiField EditLabel title;
	@UiField EditLabel text;
	@UiField FlexTable filesTable;
	@UiField CheckBox isVisible;
	@UiField FileUploadView upload;
	
	private String previousTitle;
	private String previousText;
	private boolean previousIsVisible;
	private List <UploadedFileProxy> previousFiles;
	private List <UploadedFileProxy> currentFiles;
	//private List <MessageProxy> currentMessages;

	private ClientFactory clientFactory;
	public void setClientFactory(ClientFactory clientFactory) { this.clientFactory = clientFactory; 
		upload.setClientFactory(clientFactory);}
	
	private CourseRequest request;
	public void setRequest(CourseRequest request) { this.request = request; }
	
	private ModuleProxy module;
	public ModuleProxy getModule()
	{
		module.setTitle(title.getText());
		module.setText(text.getText());
		module.setIsVisible(isVisible.getValue());
		module.setMaterials(currentFiles);
		return module;
	}
	public void setModule(ModuleProxy module) 
	{
		this.module = request.edit(module);
		prepareView();
	}
	public void newModule(Integer n)
	{
		if (n == null) return;
		
		module = request.create(ModuleProxy.class);
		module.setTitle("Moduł " + n.toString());
		module.setText("Edytuj treść");
		module.setIsVisible(false);
		//module.setComments(new ArrayList<MessageProxy>());
		module.setMaterials(new ArrayList<UploadedFileProxy>());
		prepareView();
	}
	
	public void prepareView()
	{
		previousTitle = module.getTitle();
		previousText = module.getText();
		previousIsVisible = module.getIsVisible();
		previousFiles = module.getMaterials();
		currentFiles = previousFiles;
		//currentMessages = module.getComments();
		title.setText(previousTitle);
		text.setText(previousText);
		isVisible.setValue(previousIsVisible);
		if (previousIsVisible) 
		{
			isVisible.setText("Widoczny");
		}
		else isVisible.setText("Ukryty");
		
		upload.setParent(this);
		refreshFiles();
	}
	
	public boolean isChanged() 
	{
		return (!title.getText().equals(previousTitle) ||
				!text.getText().equals(previousText) ||
				isVisible.getValue() != previousIsVisible || 
				!currentFiles.equals(previousFiles));
	}
	
	public ModuleEditView() 
	{
		initWidget(uiBinder.createAndBindUi(this));
	}
	
	@UiHandler("isVisible")
	public void onVisibilityChanged(ClickEvent click)
	{
		if (isVisible.getText().equals("Widoczny"))
		{
			isVisible.setText("Ukryty");
		}
		else isVisible.setText("Widoczny");
	}
	public void addFile(String url, String title) {
		UploadedFileProxy file = request.create(UploadedFileProxy.class);
		file.setName(title);
		file.setUrl(url);
		file.setModule(module);
		currentFiles.add(file);
		//module.setMaterials(currentFiles);
		addFileView(file);
	}
	
	private void addFileView(UploadedFileProxy file) {
		int rows = filesTable.getRowCount();
		filesTable.insertRow(rows);
		filesTable.insertCell(rows, 0);
		FileEditView view = new FileEditView();
		view.setClientFactory(clientFactory);
		view.setUploadedFile(file);
		view.setParent(this);
		filesTable.setWidget(rows, 0, view);
	}
	
	private void refreshFiles() {
		filesTable.removeAllRows();
		for (UploadedFileProxy file : currentFiles) {
			addFileView(file);
		}
	}
	public void removeFile(UploadedFileProxy file) {
		int index = currentFiles.lastIndexOf(file);
		if (index == -1) {
			return;
		}
		filesTable.removeRow(index);
		currentFiles.remove(index);
	}
}