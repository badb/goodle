package main.client.ui;

import main.client.ClientFactory;
import main.client.ui.MessageView.MessageViewUiBinder;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class MessageView extends Composite
{
	private static MessageViewUiBinder uiBinder = GWT.create(MessageViewUiBinder.class);

	interface MessageViewUiBinder extends UiBinder<Widget, MessageView> { }
	
	private ClientFactory clientFactory;
	
	public MessageView()
	{
		initWidget(uiBinder.createAndBindUi(this));
	}
	
	public void setClientFactory(ClientFactory clientFactory) { this.clientFactory = clientFactory; }
}
