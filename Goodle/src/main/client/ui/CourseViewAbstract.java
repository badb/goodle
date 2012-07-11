package main.client.ui;

import java.util.logging.Level;
import java.util.logging.Logger;

import main.client.ClientFactory;
import main.client.mapper.ContentPanelActivityMapper;
import main.client.place.CoursePlace;
import main.client.place.CreateCoursePlace;
import main.shared.proxy.CourseGroupProxy;
import main.shared.proxy.CourseProxy;
import main.shared.proxy.ModuleProxy;

import com.google.gwt.activity.shared.ActivityManager;
import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DeckPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.TabLayoutPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.web.bindery.event.shared.EventBus;
import com.google.web.bindery.requestfactory.shared.Receiver;
import com.google.web.bindery.requestfactory.shared.ServerFailure;


public class CourseViewAbstract extends Composite
{	
	Label courseName;
	Label courseDesc;
	@UiField Button editButton;
	@UiField Button saveButton;
	@UiField Button cancelButton;
	
	Label infoLabel;
	Label moduleLabel;
	Label groupLabel;
	Label membersLabel;
	Label formsLabel;
	
	private ClientFactory clientFactory;
	public void setClientFactory(ClientFactory clientFactory) 
	{ 
		this.clientFactory = clientFactory;
	}
	
	protected CourseProxy course;
	public CourseProxy getCourse() { return course; }	
	public void setCourse(CourseProxy course) { this.course = course; }
	
	protected CourseGroupProxy group;
	public CourseGroupProxy getGroup() { return group; }	
	public void setGroup(CourseGroupProxy group) { this.group = group; }	
		
	private String selectedTab;

	public void getCourse(String courseId)
 	{
		if (clientFactory != null)
		{
			clientFactory.getRequestFactory().courseRequest().findCourse(Long.parseLong(courseId)).fire
			(
				new Receiver<CourseProxy>()
				{
					@Override
					public void onSuccess(CourseProxy response)
					{
						course = response;
						if (course == null) {
							//TODO lepsza obsługa braku kursu
							Logger logger = Logger.getLogger("Goodle.Log");
						    logger.log(Level.SEVERE, "nie ma takiego kursu");
							return;
						}
						courseName.setText(course.getName());

						courseDesc.setText(course.getDescription());
						//String id = course.getModuleIds().get(0);
		//				courseModulesView.setClientFactory(clientFactory);
		//				courseInfoView.setClientFactory(clientFactory);


					}
					@Override
					public void onFailure(ServerFailure error){
						Logger logger = Logger.getLogger("Goodle.Log");
					    logger.log(Level.SEVERE, error.getMessage());
					    logger.log(Level.SEVERE, error.getStackTraceString());
					    logger.log(Level.SEVERE, error.getExceptionType());
					}
				}
			);
		}
	}
	
	public void getGroup(String groupId)
	{
		if (clientFactory != null)
		{
			clientFactory.getRequestFactory().courseGroupRequest().findCourseGroup(Long.parseLong(groupId)).fire
			(
				new Receiver<CourseGroupProxy>()
				{
					@Override
					public void onSuccess(CourseGroupProxy response)
					{
						group = response;
						if (group == null) {
							//TODO lepsza obsługa braku grupy
							Logger logger = Logger.getLogger("Goodle.Log");
						    logger.log(Level.SEVERE, "group null");
							return;
						}
					}
					@Override
					public void onFailure(ServerFailure error)
					{
						Logger logger = Logger.getLogger("Goodle.Log");
					    logger.log(Level.SEVERE, error.getMessage());
					    logger.log(Level.SEVERE, error.getStackTraceString());
					    logger.log(Level.SEVERE, error.getExceptionType());
					}
				}
			);
		}		
	}
		
/*	@UiHandler("moduleLabel")
	void showModules(ClickEvent event) {
		String courseId = (course == null ? "-1" : course.getId().toString());
		String groupId = (group == null ? "-1" : group.getId().toString());
		clientFactory.getPlaceController().goTo(new CoursePlace(courseId, groupsId, 2));
	}*/
	
	
	@UiHandler("editButton")
	void onEditButtonClick(ClickEvent event)
	{	
			editButton.setVisible(false);
			saveButton.setVisible(true);
			cancelButton.setVisible(true);
		
	}
	
	@UiHandler("saveButton")
	void onSaveButtonClick(ClickEvent event)
	{	
		//TODO zapisać zmiany w bazie danych
		
	}
	
	
	@UiHandler("cancelButton")
	void onCancelButtonClick(ClickEvent event)
	{	
		//TODO spytać o niezapisane dane
		

		editButton.setVisible(true);
		saveButton.setVisible(false);
		cancelButton.setVisible(false);
		
	}
		public void setSelectedTab(String tabId) {
			selectedTab = tabId;	
	}
}
