package main.client.ui;

import main.client.ClientFactory;
import main.shared.proxy.CourseGroupProxy;
import main.shared.proxy.CourseProxy;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.HasValueChangeHandlers;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DeckLayoutPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.Widget;


public class CourseInfoView extends Composite 
{
	private static CourseInfoViewUiBinder uiBinder = GWT.create(CourseInfoViewUiBinder.class);

	interface CourseInfoViewUiBinder extends UiBinder<Widget, CourseInfoView> { }
	
	@UiField EditLabel desc;
	@UiField EditLabel biblio;
	
	private ClientFactory clientFactory;
	public void setClientFactory(ClientFactory clientFactory) { this.clientFactory = clientFactory; }
	
	private CourseProxy course;

	public void setCourse(CourseProxy course) 
	{
		this.course = course;
		biblio.setValue(course.getBibliography());
	}
	
	@UiHandler("biblio")
	public void onBiblioValueChange(ValueChangeEvent<String> event) {
		course.setBibliography(event.getValue());
    }
	
	@UiHandler("desc")
	public void onDescValueChange(ValueChangeEvent<String> event) {
		course.setDescription(event.getValue());
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
		biblio.setValue("biblio");
	}	
	


}
