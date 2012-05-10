package main.client.ui;

import main.client.ClientFactory;
import main.client.ui.UserProfileView.UserProfileViewUiBinder;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class UserMainPageView extends Composite
{
	private static UserMainPageViewUiBinder uiBinder = GWT.create(UserMainPageViewUiBinder.class);

	interface UserMainPageViewUiBinder extends UiBinder<Widget, UserMainPageView> { }
	
	private ClientFactory clientFactory;
	
	public UserMainPageView()
	{
		initWidget(uiBinder.createAndBindUi(this));
	}
	
	public void setClientFactory(ClientFactory clientFactory) { this.clientFactory = clientFactory; }
}
