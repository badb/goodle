package main.client.ui;

import main.shared.proxy.CourseProxy;
import main.shared.proxy.CourseRequest;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Widget;
import com.google.web.bindery.requestfactory.shared.Receiver;


public class CourseInfoView extends AbstractCourseView
{
	private static CourseInfoViewUiBinder uiBinder = GWT.create(CourseInfoViewUiBinder.class);

	interface CourseInfoViewUiBinder extends UiBinder<Widget, CourseInfoView> { }
	
	@UiField EditLabel description;
	@UiField EditLabel bibliography;
		
	@Override
	public void onCourseSet() 
	{
		description.setEditable(isCurrUserOwner());
		bibliography.setEditable(isCurrUserOwner());
		description.setValue(course.getDescription());
		bibliography.setValue(course.getBibliography());
	}
	
	public CourseInfoView()
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
	
	
	@UiHandler("description")
	public void onDescriptionChange(ValueChangeEvent<String> event) 
	{
		if (course == null || !isCurrUserOwner()) return;
		
		CourseRequest request = cf.getRequestFactory().courseRequest();
		course = request.edit(course);
		course.setDescription(event.getValue());
		updateCourse(request);
	}
	
	@UiHandler("bibliography")
	public void onBibliographyChange(ValueChangeEvent<String> event) 
	{
		if (course == null || !isCurrUserOwner()) return;
		
		CourseRequest request = cf.getRequestFactory().courseRequest();
		course = request.edit(course);
		course.setBibliography(event.getValue());
		updateCourse(request);
    }
}
