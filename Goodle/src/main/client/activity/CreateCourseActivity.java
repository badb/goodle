package main.client.activity;

import main.client.ClientFactory;
import main.client.ui.CreateCourseView;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

public class CreateCourseActivity extends AbstractActivity 
{
	private ClientFactory clientFactory;

	public CreateCourseActivity(ClientFactory clientFactory)
	{
		this.clientFactory = clientFactory;
	}

	@Override
	public void start(AcceptsOneWidget panel, EventBus eventBus) 
	{
		CreateCourseView view = clientFactory.getCreateCourseView();
		view.clear();
		panel.setWidget(view.asWidget());
	}
}
