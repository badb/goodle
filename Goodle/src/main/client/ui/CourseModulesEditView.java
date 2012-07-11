package main.client.ui;

import java.util.List;

import main.client.ClientFactory;
import main.client.place.CoursePlace;
import main.shared.proxy.CourseGroupProxy;
import main.shared.proxy.ModuleProxy;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.google.web.bindery.requestfactory.shared.Receiver;

public class CourseModulesEditView extends CourseViewAbstract {

	private static CourseModulesEditViewUiBinder uiBinder = GWT
			.create(CourseModulesEditViewUiBinder.class);

	interface CourseModulesEditViewUiBinder extends
			UiBinder<Widget, CourseModulesEditView> {
	}

	@UiField Label infoLabel;
	@UiField Button addModuleButton;
	@UiField FlexTable modulesTable;
	private CourseGroupProxy group;
	private boolean click = false;
	private boolean changed = false;
	
	private ClientFactory clientFactory;
	
	/*private ModuleEditView currentEdited = null;*/

	public void setClientFactory(ClientFactory clientFactory) { this.clientFactory = clientFactory; }

	public CourseModulesEditView()
	{
		initWidget(uiBinder.createAndBindUi(this));
		
	}
	
	protected void onLoad() {
		click = false;
		changed = false;
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
	void onCancelButtonClicked(ClickEvent event) {
		/*hideEdit();*/
		//event.stopPropagation();
		click = true;
		String courseId = (course == null ? "-1" : course.getId().toString());
		String groupId = (group == null ? "-1" : group.getId().toString());
		clientFactory.getPlaceController().goTo(new CoursePlace(courseId, groupId, "1"));
	}

	@UiHandler("saveModulesButton")
	void onSaveButtonClicked(ClickEvent event) {
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
		String groupId = (group == null ? "-1" : group.getId().toString());
		clientFactory.getPlaceController().goTo(new CoursePlace(courseId, groupId, "1"));
	}
	
	
	
	@UiHandler("infoLabel")
	void showInfo(ClickEvent event) {
		String courseId = (course == null ? "-1" : course.getId().toString());
		String groupId = (group == null ? "-1" : group.getId().toString());
		clientFactory.getPlaceController().goTo(new CoursePlace(courseId, groupId, "0"));
	}
	
	@UiHandler("moduleLabel")
	void showModules(ClickEvent event) {
		String courseId = (course == null ? "-1" : course.getId().toString());
		String groupId = (group == null ? "-1" : group.getId().toString());
		clientFactory.getPlaceController().goTo(new CoursePlace(courseId, groupId, "1"));
	}
	
	@UiHandler("groupLabel")
	void showGroup(ClickEvent event) {
		String courseId = (course == null ? "-1" : course.getId().toString());
		String groupId = (group == null ? "-1" : group.getId().toString());
		clientFactory.getPlaceController().goTo(new CoursePlace(courseId, groupId, "2"));
	}
	
	@UiHandler("membersLabel")
	void showMembers(ClickEvent event) {
		String courseId = (course == null ? "-1" : course.getId().toString());
		String groupId = (group == null ? "-1" : group.getId().toString());
		clientFactory.getPlaceController().goTo(new CoursePlace(courseId, groupId, "3"));
	}
	
	@UiHandler("formsLabel")
	void showForms(ClickEvent event) {
		String courseId = (course == null ? "-1" : course.getId().toString());
		String groupId = (group == null ? "-1" : group.getId().toString());
		clientFactory.getPlaceController().goTo(new CoursePlace(courseId, groupId, "4"));
	}

	public boolean hasChanged() {
		if (click) return false;
		for (int i = 0; i < modulesTable.getRowCount(); i++) {
			//try {
				ModuleEditView view = (ModuleEditView) modulesTable.getWidget(i, 0);
				if (view.isChanged()) {
					changed = true;
				}
			
		}	
		return changed;
	}

}
