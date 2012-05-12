package main.client.ui;

import java.util.logging.Level;
import java.util.logging.Logger;

import main.client.ClientFactory;
import main.client.place.CoursePlace;
import main.shared.proxy.CourseProxy;
import main.shared.proxy.CourseRequest;
import main.shared.proxy.ModuleProxy;
import main.shared.proxy.ModuleRequest;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DeckLayoutPanel;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.web.bindery.requestfactory.shared.Receiver;
import com.google.web.bindery.requestfactory.shared.ServerFailure;

public class ModuleView extends Composite {

	private static ModuleWidgetUiBinder uiBinder = GWT.create(ModuleWidgetUiBinder.class);

	interface ModuleWidgetUiBinder extends UiBinder<Widget, ModuleView> { }
	
	private ModuleProxy module;
	private boolean visible;
	
	@UiField Label titleView;
	@UiField TextBox titleEdit;
	@UiField Label descView;
	@UiField TextArea descEdit;
	
	@UiField Button editButton;
	@UiField HorizontalPanel editPanel;
	@UiField DeckLayoutPanel titleDeckPanel;
	@UiField DeckLayoutPanel descDeckPanel;
	@UiField CheckBox showHideBox;
	NotSavedPopup popup;

	private ClientFactory clientFactory;
	
	public ModuleView() 
	{
		initWidget(uiBinder.createAndBindUi(this));
		titleView.setText("Nowy moduł");
		descView.setText("Opis");
		titleDeckPanel.showWidget(0);
		titleDeckPanel.setVisible(true);
		descDeckPanel.showWidget(0);
	}

	public ModuleProxy getModule() { return module; }
	
	public void setClientFactory(ClientFactory clientFactory) { this.clientFactory = clientFactory; }

	public void setModule(ModuleProxy module) 
	{ 
		this.module = module; 
		titleView.setText(module.getTitle());
		descView.setText(module.getText());
		visible = module.getIsVisible();
	}

	
	@UiHandler("editButton")
	void onEditButtonClicked(ClickEvent event)
	{
		editButton.setVisible(false);
		editPanel.setVisible(true);

		titleEdit.setText(titleView.getText());
		descEdit.setText(descView.getText());
		titleDeckPanel.showWidget(1);
		descDeckPanel.showWidget(1);
	}
	
	
	private boolean isChanged() {
		return (visible != showHideBox.getValue()) 
				|| (titleEdit.getText() != titleView.getText()) || (descEdit.getText() != descView.getText()); 
	}
	
	@UiHandler("cancelButton")
	void onCancelButtonClicked(ClickEvent event)
	{
		if (isChanged()) {
			if (popup == null) {
				popup = new NotSavedPopup();
			}
			popup.show();
			//TODO wykonanie akcji po zamknięciu popupa
			
  			//dialogBox.setAutoHideEnabled(true);
		}
		editButton.setVisible(true);
		editPanel.setVisible(false);
		
		titleDeckPanel.showWidget(0);
		descDeckPanel.showWidget(0);
		
	}
	
	@UiHandler("saveButton")
	void onSaveButtonClicked(ClickEvent event)
	{
		saveData();
	}
	
	
	private void saveData() {
		
		ModuleRequest request = clientFactory.getRequestFactory().moduleRequest();

		titleView.setText(titleEdit.getText());
		descView.setText(descEdit.getText());
		module.setTitle(titleEdit.getText());
		module.setText(descEdit.getText());
		visible = showHideBox.getValue();
		module.setIsVisible(visible);
		
		request.persist().using(module).fire();
	}

}
