package main.client.ui;

import java.util.logging.Level;
import java.util.logging.Logger;

import main.client.ClientFactory;
import main.client.place.CoursePlace;
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
	@UiField Label courseDesc;
	@UiField ModulesTabView modulesTabView;

	@UiField CourseInfoView courseInfoView;
	@UiField TabLayoutPanel tabPanel;
	//@UiField Label infoLabel;
	
	private ClientFactory clientFactory;
	private CourseProxy course;
	private String selectedTab;

	public CourseView()
	{
		initWidget(uiBinder.createAndBindUi(this));
	}

	public void setClientFactory(ClientFactory clientFactory) 
	{ 
		this.clientFactory = clientFactory; 
		modulesTabView.setClientFactory(clientFactory);
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
						courseDesc.setText(course.getDesc());
						tabPanel.getTabWidget(0).setVisible(true);
						tabPanel.getTabWidget(1).setVisible(true);
						//String id = course.getModuleIds().get(0);
						modulesTabView = clientFactory.getModulesTabView();
						modulesTabView.setCourse(course);		
						courseInfoView = clientFactory.getCourseInfoView();
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

	public String getSelectedTab() {
		return selectedTab;
	}

	public void setSelectedTab(String tabId) {
		this.selectedTab = tabId;		
		tabPanel.selectTab(new Integer(selectedTab).intValue());
	}
	
	@UiHandler("tabPanel")
	public void onSelection(SelectionEvent<Integer> event) {
		Integer tabNumber = event.getSelectedItem();
		selectedTab = tabNumber.toString();

		String courseId = course.getId().toString();
		clientFactory.getPlaceController().goTo(new CoursePlace(courseId, selectedTab));
	}

    public void onModuleLoad() {
        tabPanel.selectTab(new Integer(selectedTab).intValue());
        tabPanel.getTabWidget(new Integer(selectedTab)).getParent().setVisible(true);
		tabPanel.getTabWidget(0).setVisible(true);
		tabPanel.getTabWidget(1).setVisible(true);
    }

	
}
