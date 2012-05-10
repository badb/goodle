package main.client.ui;

import main.client.ClientFactory;
import main.client.ui.CommentListView.CommentListViewUiBinder;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class CommentListView extends Composite
{
	private static CommentListViewUiBinder uiBinder = GWT.create(CommentListViewUiBinder.class);

	interface CommentListViewUiBinder extends UiBinder<Widget, CommentListView> { }
	
	private ClientFactory clientFactory;
	
	public CommentListView()
	{
		initWidget(uiBinder.createAndBindUi(this));
	}
	
	public void setClientFactory(ClientFactory clientFactory) { this.clientFactory = clientFactory; }
}
