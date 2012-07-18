package main.client.ui;

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

public class CourseModulesView  extends Composite
{
	private static CourseModulesViewUiBinder uiBinder = GWT.create(CourseModulesViewUiBinder.class);

	interface CourseModulesViewUiBinder extends UiBinder<Widget, CourseModulesView> { }

	@UiField Label info;
	@UiField Button edit;
	@UiField FlexTable modules;
	
	private CourseProxy course;
	public void setCourse(CourseProxy course) 
	{
		if (!course.equals(this.course) || isEdited)
		{
			this.course = course;
			getModules();
		}
		isEdited = false;
	}
	
	private ClientFactory clientFactory;
	public void setClientFactory(ClientFactory clientFactory) { this.clientFactory = clientFactory; }
	
	private boolean isEdited;
	
	public CourseModulesView()
	{
		initWidget(uiBinder.createAndBindUi(this));
	}
	
	private void getModules()
	{
		try {
		modules.clear();
		
		CourseRequest request = clientFactory.getRequestFactory().courseRequest();
		course = request.edit(course);
		
		request.getModulesSafe().using(course).fire
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
						view.setModule(m);

						int rows = modules.getRowCount();
						modules.insertRow(rows);
						modules.insertCell(rows, 0);
						modules.setWidget(rows, 0, view);
					}
				}
			}
		);
		} catch(Exception e) { info.setText(e.getMessage()); }
	}
	
	@UiHandler("edit")
	public void onEditClick(ClickEvent event) 
	{
		isEdited = true;
		String courseId = course.getId().toString();
		clientFactory.getPlaceController().goTo(new CoursePlace(courseId, "modulesEdit"));
	}
}
