package main.client.ui;

import main.client.ClientFactory;
import main.client.place.CoursePlace;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public class CourseMenuView extends Composite
{
	private static CourseMenuViewUiBinder uiBinder = GWT.create(CourseMenuViewUiBinder.class);

	interface CourseMenuViewUiBinder extends UiBinder<Widget, CourseMenuView> { }
	
	@UiField Label infoLabel;
	@UiField Label modulesLabel;
	@UiField Label membersLabel;
	@UiField Label homeworksLabel;
	
	private String courseId;
	public void setCourseId(String courseId) { this.courseId = courseId; }
	
	private ClientFactory clientFactory;
	public void setClientFactory(ClientFactory clientFactory) { this.clientFactory = clientFactory; }
	
	public CourseMenuView()
	{
		initWidget(uiBinder.createAndBindUi(this));
	}
	
	@UiHandler("infoLabel")
	void onInfoLabelClicked(ClickEvent event) 
	{
		clientFactory.getPlaceController().goTo(new CoursePlace(courseId, "info"));
	}
	
	@UiHandler("modulesLabel")
	void onModulesLabelClicked(ClickEvent event) 
	{
		clientFactory.getPlaceController().goTo(new CoursePlace(courseId, "modules"));
	}
	
	@UiHandler("membersLabel")
	void onMembersLabelClicked(ClickEvent event) 
	{
		clientFactory.getPlaceController().goTo(new CoursePlace(courseId, "members"));
	}

	@UiHandler("homeworksLabel")
	void onHomeworksLabelClicked(ClickEvent event) 
	{
		clientFactory.getPlaceController().goTo(new CoursePlace(courseId, "homeworks"));
	}

}
