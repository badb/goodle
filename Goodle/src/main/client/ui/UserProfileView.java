package main.client.ui;

import main.client.ClientFactory;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class UserProfileView extends Composite
{
	private static UserProfileViewUiBinder uiBinder = GWT.create(UserProfileViewUiBinder.class);

	interface UserProfileViewUiBinder extends UiBinder<Widget, UserProfileView> { }
	
	private ClientFactory clientFactory;
	
	public UserProfileView()
	{
		initWidget(uiBinder.createAndBindUi(this));
	}
	
	public void setClientFactory(ClientFactory clientFactory) { this.clientFactory = clientFactory; }
}
