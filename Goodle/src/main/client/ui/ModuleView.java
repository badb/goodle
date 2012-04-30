package main.client.ui;

import main.shared.proxy.ModuleProxy;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public class ModuleView extends Composite {

	private static ModuleWidgetUiBinder uiBinder = GWT.create(ModuleWidgetUiBinder.class);

	interface ModuleWidgetUiBinder extends UiBinder<Widget, ModuleView> { }
	
	private ModuleProxy module;
	
	@UiField Label titleView;
	@UiField TextBox titleEdit;
	@UiField Label descView;
	@UiField TextArea descEdit;
	
	public ModuleView() 
	{
		initWidget(uiBinder.createAndBindUi(this));
		titleView.setText("Nowy moduł");
		descView.setText("Opis");
		titleEdit.setVisible(false);
		descEdit.setVisible(false);
	}

	public ModuleProxy getModule() { return module; }

	public void setModule(ModuleProxy module) 
	{ 
		this.module = module; 
		titleView.setText(module.getTitle());
		descView.setText(module.getDesc());
	}
	
	@UiHandler("titleView")
	void onTitleViewClicked(ClickEvent event)
	{
		titleView.setVisible(false);
		titleEdit.setText(titleView.getText());
		titleEdit.setVisible(true);
	}
	
	@UiHandler("titleEdit")
	void onTitleEditClicked(KeyPressEvent event)
	{
		if (event.getCharCode() == KeyCodes.KEY_ENTER)
		{
			titleEdit.setVisible(false);
			titleView.setText(titleEdit.getText());
			titleView.setVisible(true);
		}
	}
	
	@UiHandler("descView")
	void onDescViewClicked(ClickEvent event)
	{
		descView.setVisible(false);
		descEdit.setText(descView.getText());
		descEdit.setVisible(true);
	}
	
	@UiHandler("descEdit")
	void onDescEditClicked(KeyPressEvent event)
	{
		if (event.getCharCode() == KeyCodes.KEY_ENTER)
		{
			descEdit.setVisible(false);
			descView.setText(descEdit.getText());
			descEdit.setVisible(true);
		}
	}

}
