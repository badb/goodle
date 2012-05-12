package main.client.ui;

import java.util.logging.Level;
import java.util.logging.Logger;

import main.client.ClientFactory;
import main.client.place.CoursePlace;
import main.shared.proxy.CourseGroupProxy;
import main.shared.proxy.CourseProxy;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TabLayoutPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.web.bindery.requestfactory.shared.Receiver;
import com.google.web.bindery.requestfactory.shared.ServerFailure;


public class CourseView extends Composite
{
	private static CourseViewUiBinder uiBinder = GWT.create(CourseViewUiBinder.class);

	interface CourseViewUiBinder extends UiBinder<Widget, CourseView> { }
	
	@UiField Label courseName;
	
	@UiField TabLayoutPanel tabPanel;
	
	@UiField CourseInfoView courseInfoView;
	@UiField CourseModulesView courseModulesView;	
	@UiField CourseGroupsView courseGroupsView;
	@UiField CourseMembersView courseMembersView;
	@UiField CourseFormsView courseFormsView;
	//@UiField Label infoLabel;
	
	private ClientFactory clientFactory;
	public void setClientFactory(ClientFactory clientFactory) 
	{ 
		this.clientFactory = clientFactory;
		courseInfoView.setClientFactory(clientFactory);
		courseModulesView.setClientFactory(clientFactory);
		courseGroupsView.setClientFactory(clientFactory);
		courseMembersView.setClientFactory(clientFactory);
		courseFormsView.setClientFactory(clientFactory);
	}
	
	private CourseProxy course;
	public CourseProxy getCourse() { return course; }	
	public void setCourse(CourseProxy course) { this.course = course; }
	
	private CourseGroupProxy group;
	public CourseGroupProxy getGroup() { return group; }	
	public void setGroup(CourseGroupProxy group) { this.group = group; }	
	
	private String selectedTab;
	public String getSelectedTab() { return selectedTab; }
	public void setSelectedTab(String tabId) 
	{
		this.selectedTab = tabId;		
		tabPanel.selectTab(new Integer(selectedTab).intValue());
	}

	public CourseView()
	{
		initWidget(uiBinder.createAndBindUi(this));
	}

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
						courseName.setText(course.getName());
						courseInfoView.setCourse(course);
						courseGroupsView.setCourse(course);
					}
					@Override
					public void onFailure(ServerFailure error){
						Logger logger = Logger.getLogger("Goodle.Log");
					    logger.log(Level.SEVERE, error.getMessage());
					    logger.log(Level.SEVERE, error.getStackTraceString());
					    logger.log(Level.SEVERE, error.getExceptionType());
					}
				}
			);
		}
	}
	
	public void getGroup(String groupId)
	{
		if (clientFactory != null)
		{
			clientFactory.getRequestFactory().courseGroupRequest().findCourseGroup(Long.parseLong(groupId)).fire
			(
				new Receiver<CourseGroupProxy>()
				{
					@Override
					public void onSuccess(CourseGroupProxy response)
					{
						group = response;
						courseInfoView.setGroup(group);
						courseModulesView.setGroup(group);
						courseGroupsView.setGroup(group);
					}
					@Override
					public void onFailure(ServerFailure error)
					{
						Logger logger = Logger.getLogger("Goodle.Log");
					    logger.log(Level.SEVERE, error.getMessage());
					    logger.log(Level.SEVERE, error.getStackTraceString());
					    logger.log(Level.SEVERE, error.getExceptionType());
					}
				}
			);
		}		
	}
	
	@UiHandler("tabPanel")
	public void onSelection(SelectionEvent<Integer> event) {
		Integer tabNumber = event.getSelectedItem();
		selectedTab = tabNumber.toString();

		String courseId = course.getId().toString();
		String groupId = ( group == null ? "-1" : group.getId().toString() );
		clientFactory.getPlaceController().goTo(new CoursePlace(courseId, groupId, selectedTab));
	}

    public void onModuleLoad() {
        tabPanel.selectTab(new Integer(selectedTab).intValue());
        tabPanel.getTabWidget(new Integer(selectedTab)).getParent().setVisible(true);
		tabPanel.getTabWidget(0).setVisible(true);
		tabPanel.getTabWidget(1).setVisible(true);
    }

	
}
