package main.client.ui;

import main.client.ClientFactory;
import main.client.ui.FooterView.FooterViewUiBinder;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class FooterView extends Composite
{
	private static FooterViewUiBinder uiBinder = GWT.create(FooterViewUiBinder.class);

	interface FooterViewUiBinder extends UiBinder<Widget, FooterView> { }
	
	private ClientFactory clientFactory;
	
	public FooterView()
	{
		initWidget(uiBinder.createAndBindUi(this));
	}
	
	public void setClientFactory(ClientFactory clientFactory) { this.clientFactory = clientFactory; }
}
