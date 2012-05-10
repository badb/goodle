package main.client.ui;

import main.client.ClientFactory;
import main.client.ui.CourseMembersView.CourseMembersViewUiBinder;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class CourseMembersView extends Composite
{
	private static CourseMembersViewUiBinder uiBinder = GWT.create(CourseMembersViewUiBinder.class);

	interface CourseMembersViewUiBinder extends UiBinder<Widget, CourseMembersView> { }
	
	private ClientFactory clientFactory;
	
	public CourseMembersView()
	{
		initWidget(uiBinder.createAndBindUi(this));
	}
	
	public void setClientFactory(ClientFactory clientFactory) { this.clientFactory = clientFactory; }
}
