package main.client.ui;

import main.client.ClientFactory;
import main.client.place.CreateCoursePlace;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.StackLayoutPanel;
import com.google.gwt.user.client.ui.Widget;

public class UserCoursesView extends Composite 
{

	private static UserCoursesViewUiBinder uiBinder = GWT.create(UserCoursesViewUiBinder.class);
	
	interface UserCoursesViewUiBinder extends UiBinder<Widget, UserCoursesView> { }
	
	@UiField StackLayoutPanel userPanel;
	@UiField Button createCourseButton;
	private ClientFactory clientFactory;
	
	public UserCoursesView()
	{
		initWidget(uiBinder.createAndBindUi(this));
	}
	
	public void setClientFactory(ClientFactory clientFactory) { this.clientFactory = clientFactory; }
	
	@UiHandler("createCourseButton")
	void onCreateCourseButtonClicked(ClickEvent event)
	{
		if (clientFactory != null)
		{
			clientFactory.getPlaceController().goTo(new CreateCoursePlace());
		}
	}

}
