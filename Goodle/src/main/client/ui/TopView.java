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
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.web.bindery.requestfactory.shared.Receiver;


public class TopView extends Composite
{
	private static TopViewUiBinder uiBinder = GWT.create(TopViewUiBinder.class);
	
	interface TopViewUiBinder extends UiBinder<Widget, TopView> { }
	@UiField Label goodleLogo;
	@UiField TextBox searchBox;
	@UiField Button searchButton;
	@UiField HorizontalPanel userBox;
	@UiField Image userImage;
	@UiField Label userName;
	@UiField Button logoutButton;
	@UiField HorizontalPanel horizontalPanel;
	private ClientFactory clientFactory;
	
	public TopView()
	{
		initWidget(uiBinder.createAndBindUi(this));
		horizontalPanel.setCellWidth(goodleLogo, "200px");
		horizontalPanel.setCellWidth(userBox, "250px");
		horizontalPanel.setCellWidth(logoutButton, "46px");
		Image img = new Image("http://picol.org/images/icons/files/png/64/logout_64.png");
		img.setPixelSize(20, 20);
		logoutButton.getElement().appendChild(img.getElement());
	}
	
	public void setClientFactory(ClientFactory clientFactory) { this.clientFactory = clientFactory; }
	
	public void setUserName(String name) { userName.setText(name); }
	
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
	
	@UiHandler("logoutButton")
	void onLogoutButtonClicked(ClickEvent event){
		clientFactory.getRequestFactory().goodleUserRequest().getLogoutUrl(Window.Location.getHref()).fire(
				new Receiver <String>(){
					@Override
					public void onSuccess(String response)
					{
						Window.Location.assign(response);
					}
					});
	}
}

