package main.client.ui;

import main.client.place.CoursePlace;
import main.shared.proxy.CourseGroupProxy;
import main.shared.proxy.CourseProxy;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public class CourseGroupsView extends CourseViewAbstract
{
	@UiField Label courseName;
	@UiField Label courseDesc;
	@UiField Button editButton;
	@UiField Button saveButton;
	@UiField Button cancelButton;

	
	@UiField Label infoLabel;
	@UiField Label moduleLabel;
	@UiField Label groupLabel;
	@UiField Label membersLabel;
	@UiField Label formsLabel;

	@UiField Label infoGroupLabel;
	@UiField(provided=true) 
	CellList<CourseGroupProxy> groupList;

	
	private static CourseGroupsViewUiBinder uiBinder = GWT.create(CourseGroupsViewUiBinder.class);

	interface CourseGroupsViewUiBinder extends UiBinder<Widget, CourseGroupsView> { }
	
	private CourseProxy course;
	public void setCourse(CourseProxy course)
	{
		this.course = course;
		// TODO show course groups;
	}
	
	private CourseGroupProxy group;
	public void setGroup(CourseGroupProxy group)
	{
		this.group = group;
		// TODO underline current group
	}
		
	public CourseGroupsView()
	{
		initWidget(uiBinder.createAndBindUi(this));
	}
	
	@UiHandler("infoLabel")
	void showInfo(ClickEvent event) {
		String courseId = (course == null ? "-1" : course.getId().toString());
		String groupId = (group == null ? "-1" : group.getId().toString());
		getClientFactory().getPlaceController().goTo(new CoursePlace(courseId, groupId, "0"));
	}
	
	@UiHandler("moduleLabel")
	void showModules(ClickEvent event) {
		String courseId = (course == null ? "-1" : course.getId().toString());
		String groupId = (group == null ? "-1" : group.getId().toString());
		getClientFactory().getPlaceController().goTo(new CoursePlace(courseId, groupId, "1"));
	}
	
	@UiHandler("groupLabel")
	void showGroup(ClickEvent event) {
		String courseId = (course == null ? "-1" : course.getId().toString());
		String groupId = (group == null ? "-1" : group.getId().toString());
		getClientFactory().getPlaceController().goTo(new CoursePlace(courseId, groupId, "2"));
	}
	
	@UiHandler("membersLabel")
	void showMembers(ClickEvent event) {
		String courseId = (course == null ? "-1" : course.getId().toString());
		String groupId = (group == null ? "-1" : group.getId().toString());
		getClientFactory().getPlaceController().goTo(new CoursePlace(courseId, groupId, "3"));
	}
	
	@UiHandler("formsLabel")
	void showForms(ClickEvent event) {
		String courseId = (course == null ? "-1" : course.getId().toString());
		String groupId = (group == null ? "-1" : group.getId().toString());
		getClientFactory().getPlaceController().goTo(new CoursePlace(courseId, groupId, "4"));
	}
	
	protected void setCourseName(String name) {
		courseName.setText(name);
	}
	
	protected void setCourseDesc(String desc) {
		courseDesc.setText(desc);
	}
}