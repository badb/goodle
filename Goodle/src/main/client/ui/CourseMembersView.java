package main.client.ui;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import main.shared.proxy.CourseProxy;
import main.shared.proxy.CourseRequest;
import main.shared.proxy.GoodleUserProxy;

import com.google.gwt.cell.client.CheckboxCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.DefaultSelectionEventManager;
import com.google.gwt.view.client.MultiSelectionModel;
import com.google.gwt.view.client.SelectionModel;
import com.google.web.bindery.requestfactory.shared.Receiver;

public class CourseMembersView extends AbstractCourseView
{
	private static CourseMembersViewUiBinder uiBinder = GWT.create(CourseMembersViewUiBinder.class);
	
	interface CourseMembersViewUiBinder extends UiBinder<Widget, CourseMembersView> { }
		
	private List <GoodleUserProxy> membersProxies = new ArrayList<GoodleUserProxy>();
	
	@UiField(provided=true) 
	CellTable<GoodleUserProxy> membersList;

	@UiField Button removeButton;
	SelectionModel<GoodleUserProxy> selectionModel;
	
	private TextColumn<GoodleUserProxy> nameColumn;
	private Column <GoodleUserProxy, Boolean> checkColumn;
	
	@UiField Label message;
	
	private static String failure = "Operacja nie powiodła się. Spróbuj ponownie.";
	private static String usersRemoved = "Użytkownicy zostali wyrejestrowani z kursu.";
	private static String markToRemove = "Musisz najpierw zaznaczyć użytkowników do wyrejestrowania";
	
	public CourseMembersView()
	{
		initList();
		initWidget(uiBinder.createAndBindUi(this));
	}
	
	private void initList() 
	{
		selectionModel = new MultiSelectionModel<GoodleUserProxy>();
		
		membersList = new CellTable<GoodleUserProxy>();
		membersList.setSelectionModel
		(
			selectionModel,
			DefaultSelectionEventManager.<GoodleUserProxy>createCheckboxManager()
		);
			
		checkColumn = new Column <GoodleUserProxy, Boolean> (new CheckboxCell(true, false))	
		{
			@Override
			public Boolean getValue(GoodleUserProxy object) 
			{
				return selectionModel.isSelected(object);
			}
		};
		
		nameColumn = new TextColumn<GoodleUserProxy>() 
		{
			@Override
			public String getValue(GoodleUserProxy object) 
			{
				String value = object.getFirstName() + " " + object.getLastName();
				if (object.getStudentId() == null)
					value += " (" + object.getStudentId() + ")";
				else
					value += " (" + object.getEmail() + ")";
				return value;
			}
		};
		nameColumn.setSortable(true);

		membersList.addColumn(nameColumn);
		membersList.addColumn(checkColumn);
	}
	
	public void onCourseSet()
	{
		membersProxies.clear();
		message.setText("");
		if (isCurrUserOwner()) insertCheckBoxColumn(checkColumn);
		else removeCheckBoxColumn(checkColumn);
		removeButton.setEnabled(isCurrUserOwner());
		removeButton.setVisible(isCurrUserOwner());
		getCourseMembers();
	}

	public void getCourseMembers() 
	{	
		Set<Long> ids = course.getMembers();
				
		cf.getRequestFactory().goodleUserRequest().findGoodleUsers(ids).fire
		(
			new Receiver<Set<GoodleUserProxy>>()
			{
				@Override
				public void onSuccess(Set<GoodleUserProxy> response)
				{
					membersProxies.addAll(response);
					membersList.setRowData(membersProxies);
				}
			}
		);
	}

	@UiHandler("removeButton")
	public void onRemoveButtonClick(ClickEvent event) 
	{
		List<Long> ids = new ArrayList<Long>();
		
		for(GoodleUserProxy i : membersProxies)
		{
			if (selectionModel.isSelected(i)) 
			{
				ids.add(i.getId());
			}
		}
		
		if (ids.isEmpty()) 
		{
			message.setText(markToRemove);
			return;
		}
		
		parent.changeCourse();
		
		CourseRequest request = cf.getRequestFactory().courseRequest();
		course = request.edit(course);
		request.unregisterUsers(ids).using(course).fire
		(
			new Receiver<CourseProxy>() 
			{			
				@Override
				public void onSuccess(CourseProxy course) 
				{
					if (course != null) 
					{
						message.setText(usersRemoved);
						Iterator<GoodleUserProxy> i = membersProxies.iterator();
						while(i.hasNext())
						{
							GoodleUserProxy u = i.next();							
							if (selectionModel.isSelected(u)) i.remove();
						}
						membersList.setRowData(membersProxies);
						parent.setCourse(course);
					}
					else message.setText(failure);
				}
			}
		);
	}
	
	public void insertCheckBoxColumn(Column<GoodleUserProxy,Boolean> column) {
	    if (membersList.getColumnIndex(column) == -1)
	        membersList.addColumn(column);
	}
	

	public void removeCheckBoxColumn(Column<GoodleUserProxy, Boolean> column) {
	    int index = membersList.getColumnIndex(column);
	    if (index != -1)
	         membersList.removeColumn(index);
	}

}