package main.client.ui;

import java.util.List;

import main.client.place.CoursePlace;
import main.shared.proxy.CourseRequest;
import main.shared.proxy.HomeworkProxy;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.google.web.bindery.requestfactory.shared.Receiver;

public class CourseHomeworksView  extends AbstractCourseView
{
	private static CourseHomeworksViewUiBinder uiBinder = GWT.create(CourseHomeworksViewUiBinder.class);

	interface CourseHomeworksViewUiBinder extends UiBinder<Widget, CourseHomeworksView> { }

	@UiField Label info;
	@UiField Button edit;
	@UiField FlexTable homeworks;
	
	public static String notRegistered = "Musisz być zarejestrowany na kurs, aby obejrzeć zawartość.";
	
	public CourseHomeworksView()
	{
		initWidget(uiBinder.createAndBindUi(this));
	}
	
	@Override
	public void onCourseSet()
	{
		info.setText("");
		edit.setEnabled(isCurrUserOwner());
		edit.setVisible(isCurrUserOwner());
		getHomeworks();
	}
	
	private void getHomeworks()
	{
		homeworks.clear();
		homeworks.removeAllRows();
		
		if (!isCurrUserMember() && !isCurrUserOwner())
		{
			info.setText(notRegistered);
			return;
		}
		
		CourseRequest request = cf.getRequestFactory().courseRequest();
		course = request.edit(course);
		
		request.getHomeworksSafe().using(course).fire
		(
			new Receiver<List<HomeworkProxy>>()
			{
				@Override
				public void onSuccess(List<HomeworkProxy> result)
				{
					for (HomeworkProxy h : result) 
					{
						HomeworkView view = new HomeworkView();
						view.setClientFactory(cf);
						view.setHomework(h);

						int rows = homeworks.getRowCount();
						homeworks.insertRow(rows);
						homeworks.insertCell(rows, 0);
						homeworks.setWidget(rows, 0, view);
					}
				}
			}
		);
	}
	
	@UiHandler("edit")
	public void onEditClick(ClickEvent event) 
	{
		if (isCurrUserOwner())
		{
			String courseId = course.getId().toString();
			cf.getPlaceController().goTo(new CoursePlace(courseId, "homeworksEdit"));
		}
	}
}
