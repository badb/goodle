package main.client.ui;

import main.client.ClientFactory;
import main.client.ui.CommentView.CommentViewUiBinder;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class CommentView extends Composite
{
	private static CommentViewUiBinder uiBinder = GWT.create(CommentViewUiBinder.class);

	interface CommentViewUiBinder extends UiBinder<Widget, CommentView> { }
	
	private ClientFactory clientFactory;
	
	public CommentView()
	{
		initWidget(uiBinder.createAndBindUi(this));
	}
	
	public void setClientFactory(ClientFactory clientFactory) { this.clientFactory = clientFactory; }
}
