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
import main.shared.proxy.MessageProxy;

import com.google.gwt.cell.client.CheckboxCell;
import com.google.gwt.cell.client.TextCell;
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
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.DefaultSelectionEventManager;
import com.google.gwt.view.client.MultiSelectionModel;
import com.google.gwt.view.client.ProvidesKey;
import com.google.gwt.view.client.SelectionModel;
import com.google.web.bindery.requestfactory.shared.Receiver;
import com.google.web.bindery.requestfactory.shared.RequestContext;
import com.google.web.bindery.requestfactory.shared.ServerFailure;

public class CourseMembersView extends Composite
{
	private static CourseMembersViewUiBinder uiBinder = GWT.create(CourseMembersViewUiBinder.class);
	
	interface CourseMembersViewUiBinder extends UiBinder<Widget, CourseMembersView> { }
		
	private ClientFactory clientFactory;
	public void setClientFactory(ClientFactory clientFactory) { this.clientFactory = clientFactory; }
	private CourseProxy course;
	public void setCourse(CourseProxy course) {this.course = course;}
	
	
	@UiField(provided=true) 
	CellTable<GoodleUserProxy> membersList;
	List <GoodleUserProxy> membersUserProxies = new ArrayList<GoodleUserProxy>();
	@UiField Button removeButton;
	SelectionModel<GoodleUserProxy> selectionModel;
	
	public CourseMembersView()
	{
		initList();
		initWidget(uiBinder.createAndBindUi(this));
	}
	
	private void initList() {
		membersList = new CellTable<GoodleUserProxy>();
		selectionModel = new MultiSelectionModel<GoodleUserProxy>();
	}

	public void addMembers(Set<Long> members) {
		membersList.setSelectionModel(selectionModel,
		       DefaultSelectionEventManager.<GoodleUserProxy> createCheckboxManager());
		Column <GoodleUserProxy, Boolean> checkColumn = 
				new Column <GoodleUserProxy, Boolean> (new CheckboxCell(true, false))	{
			@Override
			public Boolean getValue(GoodleUserProxy object) {
				return selectionModel.isSelected(object);
			}};
		TextColumn<GoodleUserProxy> nameColumn = new TextColumn<GoodleUserProxy>() {
			@Override
			public String getValue(GoodleUserProxy object) {
				return object.getFirstName() + " " + object.getLastName() + " (" + object.getEmail() + ")";
			}};
			
		membersList.addColumn(checkColumn);
		membersList.addColumn(nameColumn);
		
		int size = members.size();
		membersList.setRowCount(size);
		for(Iterator<Long> it = members.iterator(); it.hasNext();)
				this.getUser(it.next(), size);
	}
	
	public void getUser(Long id, final int size) 
	{
		if (clientFactory != null)
		{
				clientFactory.getRequestFactory().goodleUserRequest().findGoodleUser(id).fire
			(
					new Receiver<GoodleUserProxy>()
					{
						@Override
						public void onSuccess(GoodleUserProxy response)
						{
							if (response != null)
							{
								membersUserProxies.add(response);
								if (membersUserProxies.size() == size)
									membersList.setRowData(0, membersUserProxies);
							}
						}
					}
			);
		}
	}

	@UiHandler("removeButton")
	public void onRemoveButtonClick(ClickEvent event) {
		final Logger logger = Logger.getLogger("Goodle.Log");
		if (clientFactory == null)
			return;
		CourseRequest request = clientFactory.getRequestFactory().courseRequest();
		for(Iterator<GoodleUserProxy> it = membersUserProxies.iterator(); it.hasNext();) {
			final GoodleUserProxy user = it.next();
			if (selectionModel.isSelected(user)) {
				request.removeMember(user.getId()).using(course).fire(
						new Receiver<Void>() {			
							@Override
							public void onFailure(ServerFailure error) {
								Logger logger = Logger.getLogger("Goodle.Log");
								logger.log(Level.SEVERE, error.getMessage());
								logger.log(Level.SEVERE, error.getStackTraceString());
								logger.log(Level.SEVERE, error.getExceptionType());
							}

							@Override
							public void onSuccess(Void response) {
								Logger logger = Logger.getLogger("Goodle.Log");
								logger.log(Level.INFO, "usunieto uzytkownika " + user.getId() + " z kursu");
							}
				});
			}
		}
	}

}
