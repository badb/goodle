package main.client.ui;

import main.client.ClientFactory;
import main.client.ui.MessageListView.MessageListViewUiBinder;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class MessageListView extends Composite
{
	private static MessageListViewUiBinder uiBinder = GWT.create(MessageListViewUiBinder.class);

	interface MessageListViewUiBinder extends UiBinder<Widget, MessageListView> { }
	
	private ClientFactory clientFactory;
	
	public MessageListView()
	{
		initWidget(uiBinder.createAndBindUi(this));
	}
	
	public void setClientFactory(ClientFactory clientFactory) { this.clientFactory = clientFactory; }
}
