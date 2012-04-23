package main.client.ui;

import java.util.List;

import main.client.ClientFactory;
import main.shared.proxy.CourseProxy;
import main.shared.proxy.ModuleProxy;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;



public class ModulesTabView  extends Composite
{
	private static ModulesTabUiBinder uiBinder = GWT.create(ModulesTabUiBinder.class);

	interface ModulesTabUiBinder extends UiBinder<Widget, ModulesTabView> { }

	//@UiField(provided=true) 
	//CellList<ModuleProxy> moduleList;
	@UiField  (provided=true) FlexTable modulesTable;
	@UiField Label infoLabel;
	private CourseProxy course;
	private List<ModuleProxy> modules;
	
	
	private ClientFactory clientFactory;

	public ModulesTabView()
	{
		initList();
		initWidget(uiBinder.createAndBindUi(this));
	}

	public void setClientFactory(ClientFactory clientFactory) { this.clientFactory = clientFactory; }
	


	private void initList()
	{
		/*moduleList = new CellList<ModuleProxy>(new ModuleCell());
		ValueUpdater<ModuleProxy> updater = new ValueUpdater<ModuleProxy>()
				{
			public void update(ModuleProxy value) 
			{
				clientFactory.getPlaceController().goTo(new CoursePlace(value.getId().toString()));
			}
				};
				moduleList.setValueUpdater(updater);*/
		
		
		
		
		modulesTable = new FlexTable();
	    modulesTable.setText(0, 0, "Lista modułów");
	    //modules.setText(0,1, "Ukryj/pokaż moduł");

	    

	    modulesTable.insertRow(1);
	    modulesTable.insertCell(1, 0);
	    modulesTable.setWidget(1, 0, new Label("test1"));
	    
	    if (course == null) {
	    	//infoLabel.setText("Nie ma takiego kursu");
	    	return;
	    }
	    

	    modules = course.getModules();
	    for (ModuleProxy mp:modules) {
	    	int row = modulesTable.getRowCount();
		    modulesTable.insertRow(row);
		    modulesTable.insertCell(row, 0);
		    ModuleWidget mw = new ModuleWidget();
		    mw.setModule(mp);
		    modulesTable.setWidget(row, 0, mw);
	    	mp.getDescription();
	    }
	    
	    
	    
	    
	}

	public void clear()
	{
		modulesTable.clear();
	}

	public CourseProxy getCourse() {
		return course;
	}

	public void setCourse(CourseProxy course) {
		this.course = course;
	}



}
