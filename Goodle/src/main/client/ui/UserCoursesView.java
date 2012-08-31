package main.client.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import main.client.ClientFactory;
import main.client.NameChangedEvent;
import main.client.NameChangedEventHandler;
import main.client.place.CoursePlace;
import main.client.util.CourseCell;
import main.shared.proxy.CourseProxy;

import com.google.gwt.cell.client.ValueUpdater;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.web.bindery.requestfactory.shared.Receiver;
import com.google.web.bindery.requestfactory.shared.ServerFailure;

public class UserCoursesView extends Composite implements NameChangedEventHandler
{

	private static UserCoursesViewUiBinder uiBinder = GWT.create(UserCoursesViewUiBinder.class);
	
	interface UserCoursesViewUiBinder extends UiBinder<Widget, UserCoursesView> { }
	
	@UiField VerticalPanel userCoursesPanel;
	@UiField Button createCourseButton;
	CellList<CourseProxy> coordinatedList;
	CellList<CourseProxy> attendedList;
	@UiField Label coordinatedLabel;
	@UiField ScrollPanel coordinatedPanel;
	@UiField Label attendedLabel;
	@UiField ScrollPanel attendedPanel;
	private ClientFactory clientFactory;
	
	public UserCoursesView()
	{
		initLists();
		initWidget(uiBinder.createAndBindUi(this));
		userCoursesPanel.setCellHeight(createCourseButton, "50px");
		userCoursesPanel.setCellHeight(coordinatedLabel, "50px");
		userCoursesPanel.setCellHeight(attendedLabel, "50px");
		userCoursesPanel.setCellHeight(coordinatedList, "0px");
	}
	
	public void setClientFactory(ClientFactory clientFactory) { this.clientFactory = clientFactory;
	initLists();}
	
	@UiHandler("createCourseButton")
	void onCreateCourseButtonClicked(ClickEvent event)
	{
		createCourseButton.setEnabled(false);
		
		clientFactory.getRequestFactory().courseRequest().newCourse().fire(new Receiver<CourseProxy>()
		{
			@Override
			public void onSuccess(CourseProxy c)
			{
				createCourseButton.setEnabled(true);
				List<CourseProxy> list = new ArrayList<CourseProxy>();
				list.addAll(coordinatedList.getVisibleItems());
				list.add(c);
				coordinatedList.setRowData(list);
				coordinatedLabel.setVisible(true);
				clientFactory.getCourseView().setCourse(c);
				clientFactory.getPlaceController().goTo(new CoursePlace(c.getId().toString(), "modules"));
			}
			@Override
			public void onFailure(ServerFailure error){
				Logger logger = Logger.getLogger("Goodle.Log");
				logger.log(Level.SEVERE, error.getMessage());
				logger.log(Level.SEVERE, error.getStackTraceString());
				logger.log(Level.SEVERE, error.getExceptionType());
			}
		});
	}
	
	public void initLists() {
		coordinatedList = new CellList<CourseProxy>(new CourseCell());
		attendedList = new CellList<CourseProxy>(new CourseCell());
		ValueUpdater<CourseProxy> updater = new ValueUpdater<CourseProxy>()
			{
				public void update(CourseProxy value) 
				{
					clientFactory.getPlaceController().goTo(new CoursePlace(value.getId().toString(), "modules"));
				}
			};
		coordinatedList.setValueUpdater(updater);
		coordinatedList.setRowData(new ArrayList<CourseProxy>());
				
		attendedList.setValueUpdater(updater);
		attendedList.setRowData(new ArrayList<CourseProxy>());
		
		if (clientFactory != null)
		{
			refreshCoordinatedList();
			refreshAttendedList();
		}
	}
	
	public void refreshCoordinatedList() {
		coordinatedLabel.setVisible(false);
		clientFactory.getRequestFactory().goodleUserRequest().getLedCourseProxies()
			.using(clientFactory.getCurrentUser()).fire
		(
				new Receiver<List<CourseProxy>>()
				{
					@Override
					public void onSuccess(List<CourseProxy> response)
					{
						coordinatedList.setRowData(response);
					    coordinatedPanel.setWidget(coordinatedList);
					    if (coordinatedList.getRowCount() > 0) {
					    	coordinatedLabel.setVisible(true);					    }
					}
				}
		);
	}	
	
	public void refreshAttendedList() {
		attendedLabel.setVisible(false);
		clientFactory.getRequestFactory().goodleUserRequest()
			.getAttendedCourseProxies().using(clientFactory.getCurrentUser()).fire
		(
				new Receiver<List<CourseProxy>>()
				{
					@Override
					public void onSuccess(List<CourseProxy> response)
					{
						attendedList.setRowData(response);
						attendedPanel.setWidget(attendedList);
					    if (attendedList.getRowCount() > 0) {
					    	attendedLabel.setVisible(true);
					    }
					}
				}
		);
	}

	@Override
	public void onNameChanged(NameChangedEvent event) {
		Logger logger = Logger.getLogger("Goodle.Log");
		logger.log(Level.INFO, "change!");
		refreshCoordinatedList();
		refreshAttendedList();
	}
}
