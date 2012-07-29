package main.client.ui;

import main.client.ClientFactory;
import main.shared.proxy.CourseRequest;
import main.shared.proxy.ModuleProxy;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Widget;

public class ModuleEditView extends Composite {

	private static ModuleWidgetUiBinder uiBinder = GWT.create(ModuleWidgetUiBinder.class);

	interface ModuleWidgetUiBinder extends UiBinder<Widget, ModuleEditView> { }

	@UiField EditLabel title;
	@UiField EditLabel text;
	@UiField FlexTable filesTable;
	@UiField CheckBox isVisible;
	
	private String previousTitle;
	private String previousText;
	private boolean previousIsVisible;

	private ClientFactory clientFactory;
	public void setClientFactory(ClientFactory clientFactory) { this.clientFactory = clientFactory; }
	
	private CourseRequest request;
	public void setRequest(CourseRequest request) { this.request = request; }
	
	private ModuleProxy module;
	public ModuleProxy getModule()
	{
		module.setTitle(title.getText());
		module.setText(text.getText());
		module.setIsVisible(isVisible.getValue());
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
		prepareView();
	}
	
	public void prepareView()
	{
		
		previousTitle = module.getTitle();
		previousText = module.getText();
		previousIsVisible = module.getIsVisible();
		title.setText(module.getTitle());
		text.setText(module.getText());
		isVisible.setValue(module.getIsVisible());
		if (module.getIsVisible())
		{
			isVisible.setText("Widoczny");
		}
		else isVisible.setText("Ukryty");
		
		
		/*request.getFiles().using(module).fire
		(
			new Receiver<List<UploadedFileProxy>>()
			{
				@Override
				public void onSuccess(List<UploadedFileProxy> result)
				{
					for (UploadedFileProxy m : result) 
					{
						int rows = filesTable.getRowCount();
						filesTable.insertRow(rows);
						filesTable.insertCell(rows, 0);
						FileView view = new FileView();
						view.setClientFactory(clientFactory);
						view.setUploadedFile(m);
						filesTable.setWidget(rows, 0, view);
					}
				}
			}
		);*/
		/* stara wersja:
		 * for (MaterialProxy m : module.getFiles()) {
			int rows = filesTable.getRowCount();
			filesTable.insertRow(rows);
			filesTable.insertCell(rows, 0);
			if (m.getClass() == UploadedFileProxy.class){
				//TODO zmiana na edycję pliku
				FileView view = new FileView();
				view.setClientFactory(clientFactory);
				view.setUploadedFile((UploadedFileProxy) m);
				filesTable.setWidget(rows, 0, view);
			}
			else {
				//TODO: co jeśli to nie jest plik?
				
				filesTable.setWidget(rows, 0, new Label("Podgląd jeszcze nieobsługiwany"));
			}
		}*/
	}
	
	public boolean isChanged() 
	{ 
		//TODO sprawdzenie czy nie dodano/zmieniono pliku
		return (!title.getText().equals(previousTitle) ||
				!text.getText().equals(previousText) ||
				isVisible.getValue() != previousIsVisible); 
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
}