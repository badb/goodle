package main.client.ui;

import java.util.logging.Level;
import java.util.logging.Logger;

import main.client.ClientFactory;
import main.shared.proxy.CourseProxy;
import main.shared.proxy.CourseRequest;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import com.google.web.bindery.requestfactory.shared.Receiver;


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
		if (!currentUserIsOwner()) {
			desc.setEnabled(false);
			biblio.setEnabled(false);
		} else {
			desc.setEnabled(true);
			biblio.setEnabled(true);
		}
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
		if (course != null && currentUserIsOwner()) {
			CourseRequest request = clientFactory.getRequestFactory().courseRequest();
			course = request.edit(course);
			request.addBiblio(event.getValue()).using(course).fire
			(
					new Receiver<Boolean>() 
					{
						@Override
						public void onSuccess(Boolean response)
						{
							Logger logger = Logger.getLogger("Goodle.Log");
							if (response) 
							{
								logger.log(Level.INFO, "Zmieniono bibliografię kursu");
							}
							else 
								logger.log(Level.INFO, "Nie udalo sie zmienić bibliografii");
						}
					}
			);
		}
    }

	
	@UiHandler("desc")
	public void onDescValueChange(ValueChangeEvent<String> event) {
		if (course != null && currentUserIsOwner()) {
			CourseRequest request = clientFactory.getRequestFactory().courseRequest();
			course = request.edit(course);
			request.addDescription(event.getValue()).using(course).fire
			(
					new Receiver<Boolean>() 
					{
						@Override
						public void onSuccess(Boolean response)
						{
							Logger logger = Logger.getLogger("Goodle.Log");
							if (response) 
							{
								logger.log(Level.INFO, "Zmieniono opis kursu");
							}
							else 
								logger.log(Level.INFO, "Nie udalo sie zmienic opisu");
						}
					}
			);
		}
    }
	
	private boolean currentUserIsOwner()
	{
		if (course != null)
		{
			return course.getCoordinators().contains(clientFactory.getCurrentUser().getId());
		}
		else return false;
	}
	
	
}
