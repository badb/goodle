package main.client.ui;

import main.client.ClientFactory;
import main.shared.proxy.CourseProxy;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;


public class CourseInfoView extends Composite
{
	private static CourseInfoViewUiBinder uiBinder = GWT.create(CourseInfoViewUiBinder.class);

	interface CourseInfoViewUiBinder extends UiBinder<Widget, CourseInfoView> { }
	
	@UiField EditLabel desc;
	@UiField EditLabel biblio;
		
	private CourseProxy course;
	public void setCourse(CourseProxy course) 
	{
		this.course = course;
		desc.setValue(course.getDescription());
		biblio.setValue(course.getBibliography());
	}
	
	private ClientFactory clientFactory;
	public void setClientFactory(ClientFactory clientFactory) { this.clientFactory = clientFactory; }

	public CourseInfoView()
	{
		initWidget(uiBinder.createAndBindUi(this));
	}	
	
	@UiHandler("biblio")
	public void onBiblioValueChange(ValueChangeEvent<String> event) {
		if (course != null) course.setBibliography(event.getValue());
    }
	
	@UiHandler("desc")
	public void onDescValueChange(ValueChangeEvent<String> event) {
		if (course != null) course.setDescription(event.getValue());
    }
}
