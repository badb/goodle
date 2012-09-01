package main.client.ui;

import java.util.ArrayList;
import java.util.List;

import main.client.ClientFactory;
import main.client.resources.GoodleResources;
import main.shared.proxy.Converter;
import main.shared.proxy.CourseRequest;
import main.shared.proxy.ModuleProxy;
import main.shared.proxy.UploadedFileProxy;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public class ModuleEditView extends Composite implements FileContainerInterface {

	private static ModuleWidgetUiBinder uiBinder = GWT.create(ModuleWidgetUiBinder.class);

	interface ModuleWidgetUiBinder extends UiBinder<Widget, ModuleEditView> { }

	@UiField EditLabel title;
	@UiField EditLabel text;
	@UiField FlexTable filesTable;
	@UiField CheckBox isVisible;
	@UiField FileUploadView upload;
	@UiField Button deleteButton;
	
	
	private String previousTitle;
	private String previousText;
	private boolean previousIsVisible;
	
	private List <UploadedFileProxy> files = new ArrayList<UploadedFileProxy>();
	private List <UploadedFileProxy> newFiles = new ArrayList<UploadedFileProxy>();
	private boolean filesChanged = false;

	private ClientFactory clientFactory;
	public void setClientFactory(ClientFactory clientFactory) 
	{ 
		this.clientFactory = clientFactory; 
		upload.setClientFactory(clientFactory);
	}
	
	private CourseRequest request;
	public void setRequest(CourseRequest request) { this.request = request; }
	
	private ModuleProxy module;
	public ModuleProxy getModule()
	{
		module.setTitle(title.getText());
		module.setText(Converter.getList(text.getText()));
		module.setIsVisible(isVisible.getValue());
		module.setAttachedFiles(newFiles);
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
		module.setText(Converter.getList("Edytuj treść"));
		module.setIsVisible(false);
		module.setAttachedFiles(new ArrayList<UploadedFileProxy>());
		prepareView();
	}
	
	public void prepareView()
	{
		previousTitle = module.getTitle();
		previousText = Converter.getString( module.getText());
		previousIsVisible = module.getIsVisible();
		files = module.getAttachedFiles();
		title.setText(previousTitle);
		text.setText(previousText);
		isVisible.setValue(previousIsVisible);
		if (previousIsVisible) 
		{
			isVisible.setText("Widoczny");
		}
		else {
			this.addStyleName("hidden");
			isVisible.setText("Ukryty");
		}
		
		upload.setParent(this);
		refreshFiles();
		
		Image deleteIcon = new Image(GoodleResources.INSTANCE.removeIcon());
		deleteIcon.getElement().setAttribute("title", "Usuń moduł");
		deleteButton.getElement().appendChild(deleteIcon.getElement());
	}
	
	public boolean isChanged() 
	{
		return (!title.getText().equals(previousTitle) ||
				!text.getText().equals(previousText) ||
				isVisible.getValue() != previousIsVisible || 
				filesChanged);
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
			this.addStyleName("hidden");
			isVisible.setText("Ukryty");
		}
		else {
			this.removeStyleName("hidden");
			isVisible.setText("Widoczny");
		}
	}
	
	public void addFile(String url, String title) 
	{
		UploadedFileProxy f = request.create(UploadedFileProxy.class);
		f.setName(title);
		f.setUrl(url);
		files.add(f);
		newFiles.add(f);
		filesChanged = true;
		addFileView(f);
	}
	
	private void addFileView(UploadedFileProxy file) 
	{
		int rows = filesTable.getRowCount();
		filesTable.insertRow(rows);
		filesTable.insertCell(rows, 0);
		FileEditView view = new FileEditView();
		view.setClientFactory(clientFactory);
		view.setUploadedFile(file);
		view.setParent(this);
		filesTable.setWidget(rows, 0, view);
	}
	
	private void refreshFiles() 
	{
		filesTable.removeAllRows();
		for (UploadedFileProxy f : files) { addFileView(f); }
	}
	
	public void removeFile(UploadedFileProxy file) 
	{
		int index = files.lastIndexOf(file);
		
		if (index == -1)
			return;
		
		filesTable.removeRow(index);
		files.remove(file);
		newFiles.remove(file);
	}
	
	@UiHandler("deleteButton")
	public void onDeleteButtonClick(ClickEvent event) {
		this.removeFromParent();
	}
}