package main.client.ui;

import java.util.logging.Level;
import java.util.logging.Logger;

import main.client.ClientFactory;
import main.client.place.CoursePlace;
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
import com.google.gwt.user.client.ui.Widget;
import com.google.web.bindery.requestfactory.shared.Receiver;
import com.google.web.bindery.requestfactory.shared.ServerFailure;

public class UserCoursesView extends Composite 
{

	private static UserCoursesViewUiBinder uiBinder = GWT.create(UserCoursesViewUiBinder.class);
	
	interface UserCoursesViewUiBinder extends UiBinder<Widget, UserCoursesView> { }
	
	@UiField Button createCourseButton;
	private ClientFactory clientFactory;
	
	public UserCoursesView()
	{
		initWidget(uiBinder.createAndBindUi(this));
	}
	
	public void setClientFactory(ClientFactory clientFactory) { this.clientFactory = clientFactory; }
	
	@UiHandler("createCourseButton")
	void onCreateCourseButtonClicked(ClickEvent event)
	{
		if (clientFactory != null)
		{
			createCourseButton.setEnabled(false);
			
			CourseRequest request = clientFactory.getRequestFactory().courseRequest();
			final CourseProxy course = request.create(CourseProxy.class);
			
			course.setName("Nowy kurs");
			course.setTerm("2012L");
			course.setJoinMethod(JoinMethod.OPEN);
	
			
			request.persist().using(course).fire(new Receiver<Long>()
			{
				@Override
				public void onSuccess(Long id)
				{
					createCourseButton.setEnabled(true);
					clientFactory.getCourseView().setCourse(course);
					clientFactory.getPlaceController().goTo(new CoursePlace(id.toString(), "-1", "1"));
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
	}

}
