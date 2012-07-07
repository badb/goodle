package main.client.ui;

import java.util.logging.Level;
import java.util.logging.Logger;

import main.client.ClientFactory;
import main.shared.proxy.CourseProxy;

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
	public void setCourse(CourseProxy course) { this.course = course; }

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

					course = response;
					courseName.setText(course.getName());
					courseMenu.setCourseId(course.getId().toString());
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
			currentView.setWidget(clientFactory.getCourseInfoView()); 
		}
		else if (selectedView.equals("members")) 
		{ 
			currentView.setWidget(clientFactory.getCourseMembersView()); 
		}
		else 
		{ 
			currentView.setWidget(clientFactory.getCourseModulesView()); 
		}
	}
	
	private void onCourseNotFound()
	{
		courseName.setText("Nie znaleziono");
		// TODO Pokaż widok "strona nie została odnaleziona"
	}
}

	
