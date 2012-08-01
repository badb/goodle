package main.client.ui;

import java.util.Date;

import main.shared.proxy.CourseProxy;
import main.shared.proxy.CourseRequest;
import main.shared.proxy.HomeworkProxy;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.google.web.bindery.requestfactory.shared.Receiver;


public class HomeworkView extends AbstractCourseView
{
	private static HomeworkViewUiBinder uiBinder = GWT.create(HomeworkViewUiBinder.class);

	interface HomeworkViewUiBinder extends UiBinder<Widget, HomeworkView> { }
	
	@UiField Label visible;
	@UiField Label title;
	@UiField Label text;
	@UiField Label deadline;
	
	public HomeworkView()
	{
		initWidget(uiBinder.createAndBindUi(this));
	}	
	
	private void updateCourse(CourseRequest request)
	{
		parent.changeCourse();
		request.update().using(course).fire
		(
			new Receiver<CourseProxy>()
			{
				@Override
				public void onSuccess(CourseProxy response) 
				{
					if (parent != null) parent.setCourse(response);
				}
			}
		);
	}
	
	public void setHomework(HomeworkProxy homework) 
	{ 
		title.setText(homework.getName());
		text.setText(homework.getText());
		Date d = homework.getDeadline();
		if (d != null)
			deadline.setText(d.toString());
		else
			deadline.setText("brak terminu");
		if (homework.getIsVisible())
		{
			visible.setText("Widoczny");
		}
		else visible.setText("Ukryty");
	}

}
