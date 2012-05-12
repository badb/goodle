package main.client.ui;

import main.client.ClientFactory;
import main.shared.proxy.CourseGroupProxy;
import main.shared.proxy.CourseProxy;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DeckLayoutPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.Widget;
import com.google.web.bindery.requestfactory.shared.Receiver;


public class CourseInfoView extends Composite
{
	private static CourseInfoViewUiBinder uiBinder = GWT.create(CourseInfoViewUiBinder.class);

	interface CourseInfoViewUiBinder extends UiBinder<Widget, CourseInfoView> { }
	
	@UiField Label courseInfo;
	@UiField DeckLayoutPanel deckPanel;
	@UiField TextArea courseInfoEdit;
	
	
	private ClientFactory clientFactory;
	public void setClientFactory(ClientFactory clientFactory) { this.clientFactory = clientFactory; }
	
	private CourseProxy course;

	public void setCourse(CourseProxy course) 
	{
		this.course = course;
		// TODO set course description
	}

	private CourseGroupProxy group;
	public void setGroup(CourseGroupProxy group) 
	{
		this.group = group;
		// TODO set group description

	}


	public CourseInfoView()
	{
		
		initWidget(uiBinder.createAndBindUi(this));
	}	
}
