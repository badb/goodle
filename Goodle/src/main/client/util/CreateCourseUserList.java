package main.client.util;

import java.util.ArrayList;

import main.shared.GoodleUserProxy;

import com.google.gwt.cell.client.ButtonCell;
import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.cell.client.SelectionCell;
import com.google.gwt.cell.client.TextCell;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.ProvidesKey;

public class CreateCourseUserList extends Composite
{

	private final ProvidesKey<GoodleUserProxy> keyProvider = 
			new ProvidesKey<GoodleUserProxy>() 
	{
		@Override
		public Object getKey(GoodleUserProxy item) { return item.getId(); }
	};
	private final ListDataProvider<GoodleUserProxy> dataProvider =
			new ListDataProvider<GoodleUserProxy>();
	private CellTable<GoodleUserProxy> userTable;
	
	CreateCourseUserList()
	{
		initUserTable();
		initWidget(this);
	}
	
	private void initUserTable()
	{
		userTable = new CellTable<GoodleUserProxy>(keyProvider);
		
		TextCell fullNameCell = new TextCell();
		Column<GoodleUserProxy, String> fullNameColumn = 
				new Column<GoodleUserProxy, String>(fullNameCell)
		{
			@Override
			public String getValue(GoodleUserProxy object)
			{
				return object.getFirstName() + " " + object.getLastName();
			};
		};
		
		ArrayList<String> rights = new ArrayList<String>();
		rights.add("Prowadzący");
		rights.add("Uczestnik");
		SelectionCell rightsCell = new SelectionCell(rights);
		Column<GoodleUserProxy, String> rightsColumn =
				new Column<GoodleUserProxy, String>(rightsCell)
		{
			@Override
			public String getValue(GoodleUserProxy object)
			{
				return "Uczestnik";
			}
		};
		
		ButtonCell removeCell = new ButtonCell();
		Column<GoodleUserProxy, String> removeColumn =
				new Column<GoodleUserProxy, String>(removeCell)
		{
			@Override
			public String getValue(GoodleUserProxy object)
			{
				return "Usuń";
			}
		};
		removeColumn.setFieldUpdater(new FieldUpdater<GoodleUserProxy, String>()
		{
			@Override
			public void update(int index, GoodleUserProxy object, String value) 
			{
				dataProvider.getList().remove(index);
			}
		});
		
		userTable.addColumn(fullNameColumn);
		userTable.addColumn(rightsColumn);
		userTable.addColumn(removeColumn);
	}
	
	public void addUser(GoodleUserProxy user) { dataProvider.getList().add(user); }
}
