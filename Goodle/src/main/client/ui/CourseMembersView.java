package main.client.ui;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import main.client.ClientFactory;
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
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.DefaultSelectionEventManager;
import com.google.gwt.view.client.MultiSelectionModel;
import com.google.gwt.view.client.SelectionModel;
import com.google.web.bindery.requestfactory.shared.Receiver;
import com.google.web.bindery.requestfactory.shared.ServerFailure;

public class CourseMembersView extends Composite
{
	private static CourseMembersViewUiBinder uiBinder = GWT.create(CourseMembersViewUiBinder.class);
	
	interface CourseMembersViewUiBinder extends UiBinder<Widget, CourseMembersView> { }
		
	private ClientFactory clientFactory;
	public void setClientFactory(ClientFactory clientFactory) { this.clientFactory = clientFactory; }
	
	private CourseProxy course;
	public void setCourse(CourseProxy course) 
	{ 
		this.course = course;
		prepareView();
	}
	
	private List <GoodleUserProxy> membersProxies = new ArrayList<GoodleUserProxy>();
	
	@UiField(provided=true) 
	CellTable<GoodleUserProxy> membersList;

	@UiField Button removeButton;
	SelectionModel<GoodleUserProxy> selectionModel;
	
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
			
		Column <GoodleUserProxy, Boolean> checkColumn = 
			new Column <GoodleUserProxy, Boolean> (new CheckboxCell(true, false))	
		{
			@Override
			public Boolean getValue(GoodleUserProxy object) 
			{
				return selectionModel.isSelected(object);
			}
		};
		
		TextColumn<GoodleUserProxy> nameColumn = new TextColumn<GoodleUserProxy>() 
		{
			@Override
			public String getValue(GoodleUserProxy object) 
			{
				return object.getFirstName() + " " + object.getLastName() + " (" + object.getEmail() + ")";
			}
		};
		nameColumn.setSortable(true);

		if (currentUserIsOwner())
			membersList.addColumn(checkColumn);
		membersList.addColumn(nameColumn);
	}
	
	public void prepareView()
	{
		membersProxies.clear();
		message.setText("");
		if (course != null)
		{
			if (currentUserIsOwner()) 
			{
				removeButton.setEnabled(true);
				removeButton.setVisible(true);
			}
			else 
			{
				removeButton.setEnabled(false);
				removeButton.setVisible(false);
			}
			getCourseMembers();
		}
	}

	public void getCourseMembers() 
	{	
		Set<Long> ids = course.getMembers();
				
		clientFactory.getRequestFactory().goodleUserRequest().findGoodleUsers(ids).fire
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
		
		if (ids.isEmpty()) {
			message.setText(markToRemove);
			return;
		}
		
		CourseRequest request = clientFactory.getRequestFactory().courseRequest();
		course = request.edit(course);
		request.unregisterUsers(ids).using(course).fire
		(
			new Receiver<Boolean>() 
			{			
				
				@Override
				public void onSuccess(Boolean response) 
				{
					if (response) 
					{
						message.setText(usersRemoved);
						Iterator<GoodleUserProxy> i = membersProxies.iterator();
						while(i.hasNext())
						{
							GoodleUserProxy u = i.next();							
							if (selectionModel.isSelected(u)) i.remove();
						}
						membersList.setRowData(membersProxies);
					}
					else message.setText(failure);
				}
				
				@Override
				public void onFailure(ServerFailure error) {
					Logger logger = Logger.getLogger("Goodle.Log");
					logger.log(Level.SEVERE, error.getMessage());
					logger.log(Level.SEVERE, error.getStackTraceString());
					logger.log(Level.SEVERE, error.getExceptionType());
				}
			}
		);
	}
	
	private boolean currentUserIsOwner()
	{
		if (course != null)
		{
			return course.getCoordinators().contains(clientFactory.getCurrentUser().getId());
		}
		else return false;
	}
}
