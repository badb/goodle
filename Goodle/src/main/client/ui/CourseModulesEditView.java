package main.client.ui;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import main.client.ClientFactory;
import main.client.place.CoursePlace;
import main.shared.proxy.CourseProxy;
import main.shared.proxy.ModuleProxy;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Widget;
import com.google.web.bindery.requestfactory.shared.Receiver;
import com.google.web.bindery.requestfactory.shared.ServerFailure;

public class CourseModulesEditView extends CourseViewAbstract {

	private static CourseModulesEditViewUiBinder uiBinder = GWT
			.create(CourseModulesEditViewUiBinder.class);

	interface CourseModulesEditViewUiBinder extends
			UiBinder<Widget, CourseModulesEditView> {
	}

	@UiField Button addModuleButton;
	@UiField FlexTable modulesTable;
	private boolean click = false;
	private boolean changed = false;
	private CourseProxy course;
	
	private ClientFactory clientFactory;
	

	public void setClientFactory(ClientFactory clientFactory) { this.clientFactory = clientFactory; }

	public CourseModulesEditView()
	{
		initWidget(uiBinder.createAndBindUi(this));
		
	}
	
	protected void onLoad() {
		click = false;
		changed = false;
	}
	
	public void setCourse(CourseProxy course) { 
		this.course = course; 
		loadModules();
	}

	
	private void loadModules()
	{
		modulesTable.clear();
		if (course == null) {
			Logger logger = Logger.getLogger("Goodle.Log");
			logger.log(Level.SEVERE, "CourseProxy jest nullem!");
			return;
		}
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
						ModuleEditView view = new ModuleEditView();
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
		
	    ModuleEditView view = new ModuleEditView();
		view.setClientFactory(clientFactory);

	    ModuleProxy m = clientFactory.getRequestFactory().moduleRequest().create(ModuleProxy.class);
	    m.setTitle("Nowy moduł " + rows.toString());
	    m.setText("");
	    m.setAuthor(clientFactory.getCurrentUser());
	    view.setModule(m);
	    
	    modulesTable.setWidget(rows, 0, view);
	    changed = true;
	    
	}
	
	@UiHandler("cancelModulesButton")
	void onCancelModulesButtonClicked(ClickEvent event) {
		/*hideEdit();*/
		//event.stopPropagation();
		click = true;
		String courseId = (course == null ? "-1" : course.getId().toString());
		clientFactory.getPlaceController().goTo(new CoursePlace(courseId, "modules"));
	}

	@UiHandler("saveModulesButton")
	void onSaveModulesButtonClicked(ClickEvent event) {
		Logger logger = Logger.getLogger("Goodle.Log");
		logger.log(Level.SEVERE, ""+modulesTable.getRowCount() + course.getModules().size());
		
		for (int i = 0; i < modulesTable.getRowCount(); i++) {
			//try {
				ModuleEditView view = (ModuleEditView) modulesTable.getWidget(i, 0);
				if (view.isChanged()) {
					view.saveData();
				}
			//} catch ()
			/*hideEdit();*/
			//event.stopPropagation();
		}	
		click = true;
		
		String courseId = (course == null ? "-1" : course.getId().toString());
		clientFactory.getPlaceController().goTo(new CoursePlace(courseId, "modules"));
	}
	

	public boolean hasChanged() {
		if (click) return false;
		for (int i = 0; i < modulesTable.getRowCount(); i++) {
			ModuleEditView view = (ModuleEditView) modulesTable.getWidget(i, 0);
			if (view.isChanged()) {
				changed = true;
			}
			
		}	
		return changed;
	}

	public void setCourse(String courseId) {

		clientFactory.getRequestFactory().courseRequest().findCourse(Long.parseLong(courseId)).fire
		(	
			new Receiver<CourseProxy>()
			{
				@Override
				public void onSuccess(CourseProxy response)
				{
					if (response == null) 
					{ 
						//TODO: onCourseNotFound();

						Logger logger = Logger.getLogger("Goodle.Log");
						logger.log(Level.SEVERE, "pusta odpowiedź");
						
						return;
					}

					setCourse(response);
				}
				
				@Override
				public void onFailure(ServerFailure error) {
					courseName.setText("error");
					Logger logger = Logger.getLogger("Goodle.Log");
					logger.log(Level.SEVERE, error.getMessage());
					logger.log(Level.SEVERE, error.getStackTraceString());
					logger.log(Level.SEVERE, error.getExceptionType());
				}
			}
		);
	}

}
