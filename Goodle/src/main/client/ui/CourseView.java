package main.client.ui;

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
	@UiField Button synchronizationButton;
	
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
		changed = false;
		if (course != null)
		{
			courseMenu.setVisible(true);
			currentView.setVisible(true);
			joinMethodAction.setVisible(true);
			synchronizationButton.setVisible(false);

			setCourseName();
			 
			if (currentUserIsOwner()) 
			{
				joinMethodAction.setEnabled(true);
				joinMethodAction.setText(course.getJoinMethod().toString());
				synchronizationButton.setVisible(true);
				synchronizationButton.setEnabled(true);
				synchronizationButton.setText("Synchronizuj z Usos");
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
	
	boolean changed = false;
	public void changeCourse() { changed = true; }

	public CourseView()
	{
		initWidget(uiBinder.createAndBindUi(this));
	}

	public void prepareView(String courseId, final String selectedView)
	{
		if (!changed && course != null && course.getId() != null && course.getId().toString().equals(courseId))
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
			courseModulesView.setClientFactory(clientFactory);
			courseModulesView.setCourse(course);
			currentView.setWidget(courseModulesView); 
		}
		else if (selectedView.equals("modulesEdit"))
		{
			CourseModulesEditView courseModulesEditView = clientFactory.getCourseModulesEditView();
			courseModulesEditView.setClientFactory(clientFactory);
			courseModulesEditView.setCourse(course);
			currentView.setWidget(courseModulesEditView);
		}
		else if (selectedView.equals("homeworks"))
		{
			CourseHomeworksView courseHomeworksView = clientFactory.getCourseHomeworksView();
			courseHomeworksView.setClientFactory(clientFactory);
			courseHomeworksView.setCourse(course);
			currentView.setWidget(courseHomeworksView);
		}
		else if (selectedView.equals("homeworksEdit"))
		{
			CourseHomeworksEditView courseHomeworksEditView = clientFactory.getCourseHomeworksEditView();
			courseHomeworksEditView.setClientFactory(clientFactory);
			courseHomeworksEditView.setCourse(course);
			currentView.setWidget(courseHomeworksEditView);
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
	
	private void setCourseName()
	{
		if (course != null) courseName.setText(course.getName() + " (" + course.getTerm() + ")");
	}
	
	@UiHandler("courseName")
	public void onCourseNameClicked(ClickEvent click)
	{
		if (currentUserIsOwner())
		{
			 CourseNameTermPopup popup = clientFactory.getCourseNameTermPopup();
			 popup.setCourse(course);
			 popup.center();
		}
	}
	
	@UiHandler("joinMethodAction")
	public void onJoinMethodActionClicked(ClickEvent click)
	{
		if (currentUserIsOwner())
		{
			CourseJoinMethodPopup popup = clientFactory.getCourseJoinMethodPopup();
			popup.setCourse(course);
			popup.center();
		}
		else if (!currentUserIsMember())
		{
			if (course.getJoinMethod() == JoinMethod.OPEN)
			{
				CourseRequest request = clientFactory.getRequestFactory().courseRequest();
				course = request.edit(course);
				request.registerCurrentUser(null).using(course).fire(
					new Receiver<Boolean>() 
								{
									@Override
									public void onSuccess(Boolean response)
									{
										if (response) 
										{
											onUserRegistered();
										} else {
											Logger logger = Logger.getLogger("Goodle.Log");
											logger.log(Level.SEVERE, "nie udalo sie zarejestrowac");
										}
									}
								}
						);
				;
//				onUserRegistered();
			}
			else
			{
				CoursePasswordPopup popup = clientFactory.getCoursePasswordPopup();
				popup.setCourse(course);
				popup.center();
			}
		}
	}
	@UiHandler("synchronizationButton")
	public void onSynchronizationButtonClicked(ClickEvent click)
	{
		CourseSynchronizationPopup popup = clientFactory.getCourseSynchronizationPopup();
		popup.setClientFactory(clientFactory);
		popup.setCourse(course);
		popup.center();
	}
}

	
