package main.client.activity;

import main.client.ClientFactory;
import main.client.ui.CreateCourseImportView;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

public class CreateCourseImportActivity extends AbstractActivity 
{
	private ClientFactory clientFactory;

	public CreateCourseImportActivity(ClientFactory clientFactory)
	{
		this.clientFactory = clientFactory;
	}

	@Override
	public void start(AcceptsOneWidget panel, EventBus eventBus) 
	{
		CreateCourseImportView view = clientFactory.getCreateCourseImportView();
		panel.setWidget(view.asWidget());
	}
}
