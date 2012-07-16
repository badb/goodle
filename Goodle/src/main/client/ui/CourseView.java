package main.client.ui;

import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import main.client.ClientFactory;
import main.shared.JoinMethod;
import main.shared.proxy.CourseProxy;
import main.shared.proxy.CourseRequest;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.web.bindery.requestfactory.shared.Receiver;
import com.google.web.bindery.requestfactory.shared.ServerFailure;

public class CourseView extends Composite
{
	private static CourseViewUiBinder uiBinder = GWT.create(CourseViewUiBinder.class);

	interface CourseViewUiBinder extends UiBinder<Widget, CourseView> { }

	@UiField Label courseName;
	
	@UiField Button joinMethodAction;
	
	@UiField CourseMenuView courseMenu;
	@UiField SimplePanel currentView;

	private ClientFactory clientFactory;
	public void setClientFactory(ClientFactory clientFactory) 
	{ 
		this.clientFactory = clientFactory;
		courseMenu.setClientFactory(clientFactory);
	}

	private CourseProxy course;
	public CourseProxy getCourse() { return course; }	
	public void setCourse(CourseProxy course) 
	{ 
		this.course = course;
		if (course != null)
		{
			courseMenu.setVisible(true);
			currentView.setVisible(true);
			joinMethodAction.setVisible(true);

			courseName.setText(course.getName());
			
			if (currentUserIsOwner()) 
			{
				joinMethodAction.setEnabled(true);
				joinMethodAction.setText(course.getJoinMethod().toString());
			}
			else if (currentUserIsMember())
			{
				joinMethodAction.setText("Wypisz się");
				joinMethodAction.setEnabled(false);
			} 
			else 
			{
				joinMethodAction.setEnabled(true);
				joinMethodAction.setText("Dołącz");
			}
			courseMenu.setCourseId(course.getId().toString());
		}
	}

	public CourseView()
	{
		initWidget(uiBinder.createAndBindUi(this));
	}

	public void prepareView(String courseId, final String selectedView)
	{
		if (course != null && course.getId() != null && course.getId().toString().equals(courseId))
		{
			setSelectedView(selectedView);
			return;
		}
		clientFactory.getRequestFactory().courseRequest().findCourse(Long.parseLong(courseId)).fire
		(	
			new Receiver<CourseProxy>()
			{
				@Override
				public void onSuccess(CourseProxy response)
				{
					if (response == null) 
					{ 
						onCourseNotFound();
						return;
					}

					setCourse(response);			
					setSelectedView(selectedView);
				}
				
				@Override
				public void onFailure(ServerFailure error) {
					courseName.setText("error");
					Logger logger = Logger.getLogger("Goodle.Log");
					logger.log(Level.SEVERE, error.getMessage());
					logger.log(Level.SEVERE, error.getStackTraceString());
					logger.log(Level.SEVERE, error.getExceptionType());
				}
			}
		);
	}
	
	private void setSelectedView(String selectedView)
	{
		if (selectedView.equals("info")) 
		{ 
			CourseInfoView courseInfoView = clientFactory.getCourseInfoView();
			courseInfoView.setCourse(course);
			currentView.setWidget(courseInfoView); 
		}
		else if (selectedView.equals("members")) 
		{ 
			CourseMembersView courseMembersView = clientFactory.getCourseMembersView();
			courseMembersView.setCourse(course);
			currentView.setWidget(courseMembersView); 
		}
		else if (selectedView.equals("modules")) 
		{ 
			CourseModulesView courseModulesView = clientFactory.getCourseModulesView();
			if (course != null) {
				courseModulesView.setClientFactory(clientFactory);
				courseModulesView.setCourse(course);
			} else {
				Logger logger = Logger.getLogger("Goodle.Log");
				logger.log(Level.SEVERE, "CourseProxy is null");
			}
			currentView.setWidget(courseModulesView); 
		}
		else if (selectedView.equals("modulesEdit"))
		{
			CourseModulesEditView courseModulesEditView = clientFactory.getCourseModulesEditView();
			if (course != null) {
				courseModulesEditView.setClientFactory(clientFactory);
				courseModulesEditView.setCourse(course);
			} else {
				Logger logger = Logger.getLogger("Goodle.Log");
				logger.log(Level.SEVERE, "CourseProxy is null");
			}
			currentView.setWidget(courseModulesEditView);
		}
	}
	
	private boolean currentUserIsOwner()
	{
		if (course != null)
		{
			return course.getCoordinators().contains(clientFactory.getCurrentUser().getId());
		}
		else return false;
	}
	
	private boolean currentUserIsMember()
	{
		return course.getMembers().contains(clientFactory.getCurrentUser().getId());		
	}
	
	private void onCourseNotFound()
	{
		courseName.setText("Nie znaleziono kursu!");
		courseMenu.setVisible(false);
		currentView.setVisible(false);
		joinMethodAction.setVisible(false);
		// TODO Pokaż widok "strong nie została odnaleziona"
	}
	
	public void onUserRegistered()
	{
		com.google.gwt.user.client.Window.Location.reload();
	}
	
	@UiHandler("joinMethodAction")
	public void onJoinMethodActionClicked(ClickEvent click)
	{
		if (currentUserIsOwner())
		{
			CourseJoinMethodPopup popup = clientFactory.getCourseJoinMethodPopup();
			popup.setCourseProxy(course);
			popup.center();
		}
		else if (!currentUserIsMember())
		{
			if (course.getJoinMethod() == JoinMethod.OPEN)
			{
				CourseRequest request = clientFactory.getRequestFactory().courseRequest();
				course = request.edit(course);
				request.registerCurrentUser(null).using(course).fire();
				onUserRegistered();
			}
			else
			{
				CoursePasswordPopup popup = clientFactory.getCoursePasswordPopup();
				popup.setCourseProxy(course);
				popup.center();
			}
		}
	}
}

	
