package main.client.ui;

import java.util.List;

import main.client.ClientFactory;
import main.shared.proxy.CourseProxy;
import main.shared.proxy.ModuleProxy;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
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

	private CourseProxy course;
	
	private ClientFactory clientFactory;

	public CourseModulesView()
	{
		initWidget(uiBinder.createAndBindUi(this));
	}

	public void setClientFactory(ClientFactory clientFactory) { this.clientFactory = clientFactory; }
	
	public void setCourse(CourseProxy course) 
	{
		if (!course.equals(this.course))
		{
			this.course = course;
			loadModules();
		}
	}
	
	private void loadModules()
	{
		modulesTable.clear();
		List<Long> ids = course.getModules();
		
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
	    ModuleProxy m = clientFactory.getRequestFactory().moduleRequest().create(ModuleProxy.class);
	    m.setTitle("Nowy moduł " + rows.toString());
	    m.setText("");
	    view.setModule(m);
	    
	    modulesTable.setWidget(rows, 0, view);
	}
}
