package main.client.ui;

import main.client.ClientFactory;
import main.shared.proxy.ModuleProxy;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public class ModuleView extends Composite {

	private static ModuleWidgetUiBinder uiBinder = GWT.create(ModuleWidgetUiBinder.class);

	interface ModuleWidgetUiBinder extends UiBinder<Widget, ModuleView> { }

	@UiField Label visible;
	@UiField Label title;
	@UiField Label text;

	private ClientFactory clientFactory;
	public void setClientFactory(ClientFactory clientFactory) { this.clientFactory = clientFactory; }
	
	public void setModule(ModuleProxy module) 
	{ 
		title.setText(module.getTitle());
		text.setText(module.getText());
		if (module.getIsVisible())
		{
			visible.setText("Widoczny");
		}
		else visible.setText("Ukryty");
	}

	public ModuleView() 
	{
		initWidget(uiBinder.createAndBindUi(this));
	}

}
