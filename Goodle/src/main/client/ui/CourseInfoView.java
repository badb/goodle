package main.client.ui;

import main.client.ClientFactory;
import main.shared.CourseProxy;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.google.web.bindery.requestfactory.shared.Receiver;


public class CourseInfoView extends Composite
{
	private static CourseViewUiBinder uiBinder = GWT.create(CourseViewUiBinder.class);

	interface CourseViewUiBinder extends UiBinder<Widget, CourseInfoView> { }
	
	@UiField Label courseInfo;
	
	
	private ClientFactory clientFactory;
	private CourseProxy course;

	public CourseInfoView()
	{
		
		initWidget(uiBinder.createAndBindUi(this));
	}

	public void setClientFactory(ClientFactory clientFactory) { this.clientFactory = clientFactory; }
	
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
						//courseInfo.setText(course.getDesc());
					}
				}
			);
		}
	}

	
}
