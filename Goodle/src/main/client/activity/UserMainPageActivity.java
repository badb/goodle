package main.client.activity;

import main.client.ClientFactory;
import main.client.place.UserMainPagePlace;
import main.client.ui.UserMainPageView;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

public class UserMainPageActivity extends AbstractActivity
{
	private ClientFactory clientFactory;
	
	public UserMainPageActivity(ClientFactory clientFactory, UserMainPagePlace place)
	{
		this.clientFactory = clientFactory;
	}
	
	@Override
	public void start(AcceptsOneWidget panel, EventBus eventBus) 
	{
		UserMainPageView view = clientFactory.getUserMainPageView();
		panel.setWidget(view.asWidget());
	}
}

