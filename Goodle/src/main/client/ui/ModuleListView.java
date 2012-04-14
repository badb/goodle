package main.client.ui;

import java.util.List;

import main.client.ClientFactory;
import main.client.place.CoursePlace;
import main.client.util.ModuleCell;
import main.shared.CourseProxy;
import main.shared.ModuleProxy;

import com.google.gwt.cell.client.ValueUpdater;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import com.google.web.bindery.requestfactory.shared.Receiver;



public class ModuleListView  extends Composite
{
	private static ModuleListViewUiBinder uiBinder = GWT.create(ModuleListViewUiBinder.class);

	interface ModuleListViewUiBinder extends UiBinder<Widget, ModuleListView> { }

	@UiField(provided=true) 
	CellList<ModuleProxy> moduleList;
	private ClientFactory clientFactory;

	public ModuleListView()
	{
		initList();
		initWidget(uiBinder.createAndBindUi(this));
	}

	public void setClientFactory(ClientFactory clientFactory) { this.clientFactory = clientFactory; }

	private void initList()
	{
		moduleList = new CellList<ModuleProxy>(new ModuleCell());
		ValueUpdater<ModuleProxy> updater = new ValueUpdater<ModuleProxy>()
				{
			public void update(ModuleProxy value) 
			{
				clientFactory.getPlaceController().goTo(new CoursePlace(value.getId().toString()));
			}
				};
				moduleList.setValueUpdater(updater);
	}

	public void clear()
	{
		
	}

	public void findCoursesByName(String name) 
	{
		if (clientFactory != null)
		{
			clientFactory.getRequestFactory().courseRequest().findCoursesByName(name).fire
			(
					new Receiver<List<CourseProxy>>()
					{
						@Override
						public void onSuccess(List<CourseProxy> response)
						{
							moduleList.setLayoutData(response);
						}
					}
			);
		}
	}


}
