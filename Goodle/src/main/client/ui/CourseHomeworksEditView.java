 package main.client.ui;

import java.util.ArrayList;
import java.util.List;

import main.client.place.CoursePlace;
import main.shared.proxy.CourseProxy;
import main.shared.proxy.CourseRequest;
import main.shared.proxy.HomeworkProxy;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.google.web.bindery.requestfactory.shared.Receiver;

public class CourseHomeworksEditView extends AbstractCourseView 
{

	private static CourseHomeworksEditViewUiBinder uiBinder = GWT.create(CourseHomeworksEditViewUiBinder.class);

	interface CourseHomeworksEditViewUiBinder extends UiBinder<Widget, CourseHomeworksEditView> { }

	@UiField Label info;
	@UiField Button newHomework;
	@UiField FlexTable homeworks;
	
	private boolean mayStop;
	public boolean mayStop() { return mayStop; }
	
	@Override
	public void onCourseSet() 
	{ 
		request = cf.getRequestFactory().courseRequest();
		info.setText("");
		mayStop = false;
		getHomeworks();
	} 
	
	private CourseRequest request;
	
	public static String errorMsg = "Przepraszamy, wystąpił błąd i nie udało się załadować zadań. Spróbuj ponownie później.";

	public CourseHomeworksEditView()
	{
		initWidget(uiBinder.createAndBindUi(this));
	}
	
	private void getHomeworks() 
	{
		homeworks.clear();
		homeworks.removeAllRows();
		
		CourseRequest getRequest = cf.getRequestFactory().courseRequest();
		course = getRequest.edit(course);
				
		getRequest.getHomeworksSafe().using(course).fire
		(
			new Receiver<List<HomeworkProxy>>() 
			{
				@Override
				public void onSuccess(List<HomeworkProxy> result) 
				{
					for (HomeworkProxy h : result) 
					{
						HomeworkEditView view = new HomeworkEditView();
						view.setClientFactory(cf);
						view.setRequest(request);
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

	@UiHandler("newHomework")
	void onNewHomeworkClick(ClickEvent event)
	{	
		try {
		Integer rows = homeworks.getRowCount();
		
	    HomeworkEditView view = new HomeworkEditView();
	    
		view.setClientFactory(cf);
		view.setRequest(request);
	    view.newHomework(rows + 1);
	    
		homeworks.insertRow(rows);
		homeworks.insertCell(rows, 0);
	    homeworks.setWidget(rows, 0, view);
		} catch(Exception e) { info.setText("new " + e.getMessage()); }
	}
	
	@UiHandler("cancel")
	void onCancelHomeworksButtonClicked(ClickEvent event) 
	{
		mayStop = true;
		String courseId = course.getId().toString();
		cf.getPlaceController().goTo(new CoursePlace(courseId, "homeworks"));
	}

	@UiHandler("save")
	void onSaveHomeworksButtonClicked(ClickEvent event) 
	{
		mayStop = true;
		List<HomeworkProxy> updated = new ArrayList<HomeworkProxy>();
		
		for (int i = 0; i < homeworks.getRowCount(); ++i) 
		{
			HomeworkEditView view = (HomeworkEditView) homeworks.getWidget(i, 0);
			if (view != null) updated.add(view.getHomework());
		}
		
		course = request.edit(course);
		request.updateHomeworks(updated).using(course).fire
		(
			new Receiver<CourseProxy>()
			{
				@Override
				public void onSuccess(CourseProxy result)
				{
					if (result != null)
					{
						try {
						parent.setCourse(result);
						String courseId = result.getId().toString();
						cf.getPlaceController().goTo(new CoursePlace(courseId, "homeworks"));
						} catch(Exception e) { DialogBox db = new DialogBox(); db.setText(e.getMessage()); db.show(); }
					}

				}
			}
		);
		

	}

}
