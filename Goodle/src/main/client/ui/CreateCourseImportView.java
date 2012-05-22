package main.client.ui;

import java.util.logging.Level;
import java.util.logging.Logger;

import main.client.ClientFactory;
import main.client.place.CoursePlace;
import main.client.place.CreateCourseImportPlace;
import main.client.place.CreateCoursePlace;
import main.shared.JoinMethod;
import main.shared.proxy.CourseProxy;
import main.shared.proxy.CourseRequest;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.place.shared.Place;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.TabLayoutPanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.web.bindery.requestfactory.shared.Receiver;
import com.google.web.bindery.requestfactory.shared.ServerFailure;


public class CreateCourseImportView extends Composite 
{
	
	private static CreateCourseImportViewUiBinder uiBinder = GWT.create(CreateCourseImportViewUiBinder.class);
	private Place createPlace = new CreateCoursePlace();
	
	interface CreateCourseImportViewUiBinder extends UiBinder<Widget, CreateCourseImportView> { }

	@UiField Label importLabel;
	@UiField Label createLabel;
	
	private ClientFactory clientFactory;
	
	public CreateCourseImportView() 
	{
		initWidget(uiBinder.createAndBindUi(this));
	}

	public void setClientFactory(ClientFactory clientFactory) { this.clientFactory = clientFactory; }	

	@UiHandler("createLabel")
	void showCreate(ClickEvent event) {clientFactory.getPlaceController().goTo(new CreateCoursePlace());}
}
