package main.client.ui;

import main.client.ClientFactory;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;



public class ModuleListView  extends Composite
{
	private static ModuleListViewUiBinder uiBinder = GWT.create(ModuleListViewUiBinder.class);

	interface ModuleListViewUiBinder extends UiBinder<Widget, ModuleListView> { }

	//@UiField(provided=true) 
	//CellList<ModuleProxy> moduleList;
	private ClientFactory clientFactory;

	public ModuleListView()
	{
		//initList();
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
	}

	public void clear()
	{
		
	}

}
