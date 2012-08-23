package main.client.ui;

import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import main.client.ClientFactory;
import main.shared.proxy.CourseRequest;
import main.shared.proxy.HomeworkProxy;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.datepicker.client.DatePicker;

public class HomeworkEditView extends Composite {

	private static HomeworkWidgetUiBinder uiBinder = GWT.create(HomeworkWidgetUiBinder.class);

	interface HomeworkWidgetUiBinder extends UiBinder<Widget, HomeworkEditView> { }

	@UiField EditLabel title;
	@UiField EditLabel text;

	@UiField CheckBox isVisible;
	@UiField DatePicker deadline;
	
	private String previousTitle;
	private String previousText;
	private boolean previousIsVisible;
	private Date previousDeadline;

	private ClientFactory clientFactory;
	public void setClientFactory(ClientFactory clientFactory) { this.clientFactory = clientFactory; }
	
	private CourseRequest request;
	public void setRequest(CourseRequest request) { this.request = request; }
	
	private HomeworkProxy homework;
	public HomeworkProxy getHomework()
	{
		homework.setTitle(title.getText());
		homework.setText(text.getText());
		homework.setDeadline(deadline.getValue());
		homework.setIsVisible(isVisible.getValue());
		return homework;
	}
	public void setHomework(HomeworkProxy homework) 
	{
		this.homework = request.edit(homework);
		prepareView();
	}
	public void newHomework(Integer n)
	{
		if (n == null) return;
		
		homework = request.create(HomeworkProxy.class);
		homework.setTitle("Zadanie " + n.toString());
		homework.setText("Edytuj treść");
		homework.setIsVisible(false);
		prepareView();
	}
	
	public void prepareView()
	{
		previousTitle = homework.getTitle();
		previousText = homework.getText();
		previousIsVisible = homework.getIsVisible();
		previousDeadline = homework.getDeadline();
		title.setText(homework.getTitle());
		text.setText(homework.getText());
		if (previousDeadline != null)
			deadline.setValue(homework.getDeadline());
		isVisible.setValue(homework.getIsVisible());
	}
	
	public boolean isChanged() 
	{ 
		return (!title.getText().equals(previousTitle) ||
				!text.getText().equals(previousText) ||
				isVisible.getValue() != previousIsVisible); 
	}
	
	public HomeworkEditView() 
	{
		initWidget(uiBinder.createAndBindUi(this));
	}
	
	@UiHandler("isVisible")
	public void onVisibilityChanged(ClickEvent click)
	{
		if (isVisible.getText().equals("Widoczny"))
		{
			isVisible.setText("Ukryty");
		}
		else isVisible.setText("Widoczny");
	}
}