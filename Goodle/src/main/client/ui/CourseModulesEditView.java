package main.client.ui;

import java.util.ArrayList;
import java.util.List;

import main.client.ClientFactory;
import main.client.place.CoursePlace;
import main.shared.proxy.CourseProxy;
import main.shared.proxy.CourseRequest;
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

public class CourseModulesEditView extends Composite {

	private static CourseModulesEditViewUiBinder uiBinder = GWT.create(CourseModulesEditViewUiBinder.class);

	interface CourseModulesEditViewUiBinder extends UiBinder<Widget, CourseModulesEditView> { }

	@UiField Label info;
	@UiField Button newModule;
	@UiField FlexTable modules;
	
	private boolean mayStop;
	public boolean mayStop() { return mayStop; }
	
	private CourseProxy course;
	public void setCourse(CourseProxy course) 
	{ 
		this.course = course;
		request = clientFactory.getRequestFactory().courseRequest();
		info.setText("");
		mayStop = false;
		getModules();
	} 
	
	private ClientFactory clientFactory;
	public void setClientFactory(ClientFactory clientFactory) { this.clientFactory = clientFactory; }
	
	private CourseView parent;
	public void setParent(CourseView parent) { this.parent = parent; }
	
	private CourseRequest request;
	
	public static String errorMsg = "Przepraszamy, wystąpił błąd i nie udało się załadować modułów. Spróbuj ponownie później.";

	public CourseModulesEditView()
	{
		initWidget(uiBinder.createAndBindUi(this));
	}
	
	private void getModules() 
	{
		modules.clear();
		modules.removeAllRows();
		
		CourseRequest getRequest = clientFactory.getRequestFactory().courseRequest();
		course = getRequest.edit(course);
				
		getRequest.getModulesSafe().using(course).fire
		(
			new Receiver<List<ModuleProxy>>() 
			{
				@Override
				public void onSuccess(List<ModuleProxy> result) 
				{
					for (ModuleProxy m : result) 
					{
						ModuleEditView view = new ModuleEditView();
						view.setClientFactory(clientFactory);
						view.setRequest(request);
						view.setModule(m);

						int rows = modules.getRowCount();
						modules.insertRow(rows);
						modules.insertCell(rows, 0);
						modules.setWidget(rows, 0, view);
					}
				}
			}
		);
	}

	@UiHandler("newModule")
	void onNewModuleClick(ClickEvent event)
	{	
		try {
		Integer rows = modules.getRowCount();
		
	    ModuleEditView view = new ModuleEditView();
	    
		view.setClientFactory(clientFactory);
		view.setRequest(request);
	    view.newModule(rows + 1);
	    
		modules.insertRow(rows);
		modules.insertCell(rows, 0);
	    modules.setWidget(rows, 0, view);
		} catch(Exception e) { info.setText("new " + e.getMessage()); }
	}
	
	@UiHandler("cancel")
	void onCancelModulesButtonClicked(ClickEvent event) 
	{
		mayStop = true;
		String courseId = course.getId().toString();
		clientFactory.getPlaceController().goTo(new CoursePlace(courseId, "modules"));
	}

	@UiHandler("save")
	void onSaveModulesButtonClicked(ClickEvent event) 
	{
		mayStop = true;
		List<ModuleProxy> updated = new ArrayList<ModuleProxy>();
		
		for (int i = 0; i < modules.getRowCount(); ++i) 
		{
			ModuleEditView view = (ModuleEditView) modules.getWidget(i, 0);
			if (view != null) updated.add(view.getModule());
		}
		
		course = request.edit(course);
		request.updateModules(updated).using(course).fire();
		
		String courseId = course.getId().toString();
		clientFactory.getPlaceController().goTo(new CoursePlace(courseId, "modules"));
	}

}
