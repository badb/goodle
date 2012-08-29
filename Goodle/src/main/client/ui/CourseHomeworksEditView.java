 package main.client.ui;

import java.util.ArrayList;
import java.util.List;

import main.client.NewHomeworkEvent;
import main.client.NewHomeworkEventHandler;
import main.client.place.CoursePlace;
import main.shared.proxy.CourseProxy;
import main.shared.proxy.CourseRequest;
import main.shared.proxy.HomeworkProxy;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.event.shared.HasHandlers;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.google.web.bindery.requestfactory.shared.Receiver;

public class CourseHomeworksEditView extends AbstractCourseView implements HasHandlers
{
	private static CourseHomeworksEditViewUiBinder uiBinder = GWT.create(CourseHomeworksEditViewUiBinder.class);

	interface CourseHomeworksEditViewUiBinder extends UiBinder<Widget, CourseHomeworksEditView> { }

	@UiField Label info;
	@UiField Button newHomework;
	@UiField FlexTable homeworks;
	
	private boolean mayStop;
	public boolean mayStop() { return mayStop; }

	private HandlerManager handlerManager;
	
	@Override
	public void onCourseSet() 
	{ 
		request = cf.getRequestFactory().courseRequest();
		info.setText("");
		mayStop = false;
		getHomeworks();
	} 
	
	private CourseRequest request;
	
	public static String errorMsg = "Przepraszamy, wyst��pi�� b����d i nie uda��o si�� za��adowa�� zada��. Spr��buj ponownie p����niej.";

	public CourseHomeworksEditView()
	{
		handlerManager = new HandlerManager(this);
		initWidget(uiBinder.createAndBindUi(this));
	}
	
	@Override
	public void fireEvent(GwtEvent<?> event) {
		handlerManager.fireEvent(event);
	}
	
	public HandlerRegistration addNewHomeworkEventHandler(
			NewHomeworkEventHandler handler) {
	        	return handlerManager.addHandler(NewHomeworkEvent.TYPE, handler);
	}
	
	private void getHomeworks() 
	{
		homeworks.clear();
		homeworks.removeAllRows();
		
		CourseRequest getRequest = cf.getRequestFactory().courseRequest();
		course = getRequest.edit(course);
				
		getRequest.getHomeworksSafe().using(course).with("attachedFiles", "solutions").fire
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
						view.setCourse(course);
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
		Integer rows = homeworks.getRowCount();
		
	    HomeworkEditView view = new HomeworkEditView();
	    
		view.setClientFactory(cf);
		view.setRequest(request);
		view.setCourse(course);
	    view.newHomework(rows + 1);
	    
		homeworks.insertRow(rows);
		homeworks.insertCell(rows, 0);
	    homeworks.setWidget(rows, 0, view);
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
		try 
		{
			mayStop = true;
			List<HomeworkProxy> updated = new ArrayList<HomeworkProxy>();
			
			for (int i = 0; i < homeworks.getRowCount(); ++i) 
			{
				HomeworkEditView view = (HomeworkEditView) homeworks.getWidget(i, 0);
				if (view != null) updated.add(view.getHomework());
			}
			
			course = request.edit(course);
			request.updateHomeworks(updated).using(course).with("attachedFiles").fire
			(
				new Receiver<CourseProxy>()
				{
					@Override
					public void onSuccess(CourseProxy result)
					{
						if (result != null)
						{
							parent.setCourse(result);
							String courseId = result.getId().toString();
							cf.getPlaceController().goTo(new CoursePlace(courseId, "homeworks"));
							NewHomeworkEvent event = new NewHomeworkEvent();
							fireEvent(event);
						}
	
					}
				}
			); 
		} 
		catch (Exception e) { info.setText(e.getMessage()); }
	}

}
