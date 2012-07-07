package main.client.ui;

import main.client.place.CoursePlace;
import main.client.ui.CourseMembersView.CourseMembersViewUiBinder;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public class CourseMembersView extends CourseViewAbstract
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
	
	private static CourseMembersViewUiBinder uiBinder = GWT.create(CourseMembersViewUiBinder.class);

	interface CourseMembersViewUiBinder extends UiBinder<Widget, CourseMembersView> { }
	
	
	public CourseMembersView()
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
	
	@UiHandler("editButton")
	void onEditButtonClick(ClickEvent event)
	{	
			editButton.setVisible(false);
			saveButton.setVisible(true);
			cancelButton.setVisible(true);
		
	}
	
	@UiHandler("saveButton")
	void onSaveButtonClick(ClickEvent event)
	{	
		//TODO zapisać zmiany w bazie danych
		
	}
	
	
	@UiHandler("cancelButton")
	void onCancelButtonClick(ClickEvent event)
	{	
		//TODO spytać o niezapisane dane
		

		editButton.setVisible(true);
		saveButton.setVisible(false);
		cancelButton.setVisible(false);
		
	}
}
