package main.client.activity;

import main.client.ClientFactory;
import main.client.place.UserProfilePlace;
import main.client.ui.UserProfileView;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

public class UserProfileActivity extends AbstractActivity
{
	private ClientFactory clientFactory;
	private String userId;
	
	public UserProfileActivity(ClientFactory clientFactory, UserProfilePlace place)
	{
		this.clientFactory = clientFactory;
		userId = place.getUserId();
	}
	
	@Override
	public void start(AcceptsOneWidget panel, EventBus eventBus) 
	{
		UserProfileView view = clientFactory.getUserProfileView();
		panel.setWidget(view.asWidget());
	}
}
