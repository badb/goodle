package main.client.ui;

import java.util.List;

import main.client.ClientFactory;
import main.shared.proxy.CourseGroupProxy;
import main.shared.proxy.ModuleProxy;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.google.web.bindery.requestfactory.shared.Receiver;

public class CourseModulesView  extends Composite
{
	private static CourseModulesViewUiBinder uiBinder = GWT.create(CourseModulesViewUiBinder.class);

	interface CourseModulesViewUiBinder extends UiBinder<Widget, CourseModulesView> { }

	@UiField Label infoLabel;
	@UiField Button addModuleButton;
	@UiField FlexTable modulesTable;
	private CourseGroupProxy group;
	
	private ClientFactory clientFactory;
	
	private ModuleView currentEdited = null;

	public void setClientFactory(ClientFactory clientFactory) { this.clientFactory = clientFactory; }

	public CourseModulesView()
	{
		initWidget(uiBinder.createAndBindUi(this));

	}
	
	public void setGroup(CourseGroupProxy group) 
	{
		if (!group.equals(this.group))
		{
			this.group = group;
			loadModules();
		}
	}
	
	private void loadModules()
	{
		modulesTable.clear();
		List<Long> ids = group.getModules();
		
		if(!ids.isEmpty()){
		
		clientFactory.getRequestFactory().moduleRequest().findModules(ids).fire
		(
			new Receiver<List<ModuleProxy>>()
			{
				@Override
				public void onSuccess(List<ModuleProxy> result)
				{
					for (ModuleProxy m : result)
					{
						ModuleView view = new ModuleView();
						view.setClientFactory(clientFactory);
					    view.setModule(m);
					    
						int rows = modulesTable.getRowCount();
						modulesTable.insertRow(rows);
						modulesTable.insertCell(rows, 0);					
					    modulesTable.setWidget(rows, 0, view);
					}
				}
			}
		);
		}
	}
	
	@UiHandler("addModuleButton")
	void onAddModuleButtonClick(ClickEvent event)
	{	
		Integer rows = modulesTable.getRowCount();
		modulesTable.insertRow(rows);
		modulesTable.insertCell(rows, 0);
		
	    ModuleView view = new ModuleView();
		view.setClientFactory(clientFactory);

	    ModuleProxy m = clientFactory.getRequestFactory().moduleRequest().create(ModuleProxy.class);
	    m.setTitle("Nowy moduł " + rows.toString());
	    m.setText("");
	    m.setAuthor(clientFactory.getCurrentUser());
	    view.setModule(m);
	    
	    modulesTable.setWidget(rows, 0, view);
	    onModuleClick(view);
	    
	}
	
	private void onModuleClick(ModuleView view) {

	    if (view != null) {
	    	if (!view.equals(currentEdited) || (!view.getIsEdited())) {
	    		//jeśli zamknięto edycję modułu, to currentEdited dalej jest ustawiony
	    		if (currentEdited != null) {
	    			currentEdited.hideEdit();
	    		}
	    		view.showEdit();
	    	} 
			currentEdited = view;
	    }
	}
	
	@UiHandler("modulesTable")
	void onModulesTableClick(ClickEvent event) {
		int row = modulesTable.getCellForEvent(event).getRowIndex();

	    ModuleView view = (ModuleView) modulesTable.getWidget(row, 0);
	    onModuleClick(view);
	}
	
	void onModuleLoad() {
		//nie wiem, czy to dobrze działa...
		Window.addWindowClosingHandler(new Window.ClosingHandler() {
		      public void onWindowClosing(Window.ClosingEvent closingEvent) {
		    	  stopEditing();
		        closingEvent.setMessage("Do you really want to leave the page?");
		      }
		    });
		History.addValueChangeHandler(new ValueChangeHandler<String>() {

			public void onValueChange(ValueChangeEvent<String> event) {
				stopEditing();
				
			}
		});
	}

	void stopEditing() {
		if (currentEdited != null) {
			currentEdited.hideEdit();
		}

		currentEdited = null;
	}
	
	
}
