package main.client.ui;

import main.client.ClientFactory;
import main.client.place.FindCoursesByNamePlace;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;


public class TopView extends Composite
{
	private static TopViewUiBinder uiBinder = GWT.create(TopViewUiBinder.class);
	
	interface TopViewUiBinder extends UiBinder<Widget, TopView> { }
	@UiField Image goodleLogo;
	@UiField TextBox searchBox;
	@UiField Button searchButton;
	@UiField Image userImage;
	@UiField Button logoutButton;
	private ClientFactory clientFactory;
	
	public TopView()
	{
		initWidget(uiBinder.createAndBindUi(this));
	}
	
	public void setClientFactory(ClientFactory clientFactory) { this.clientFactory = clientFactory; }
	
	@UiHandler("searchBox")
	void onEnterPressed(KeyPressEvent event)
	{
		if (event.getCharCode() == KeyCodes.KEY_ENTER)
		{
			String token = searchBox.getText();
			clientFactory.getPlaceController().goTo(new FindCoursesByNamePlace(token));
		}
	}
	
	@UiHandler("searchButton")
	void onSearchButtonClicked(ClickEvent event)
	{
		if (clientFactory != null)
		{
			String token = searchBox.getText();
			clientFactory.getPlaceController().goTo(new FindCoursesByNamePlace(token));
		}
	}
}

