package main.client.ui;

import main.shared.ModuleProxy;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DeckLayoutPanel;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.Widget;

public class ModuleEditWidget extends Composite {
	private ModuleProxy module;
	@UiField Label desc;
	@UiField TextArea descEdit;
	@UiField DeckLayoutPanel deckPanel;
	@UiField Button editButton;
	
	
	private static ModuleEditWidgetUiBinder uiBinder = GWT.create(ModuleEditWidgetUiBinder.class);

	interface ModuleEditWidgetUiBinder extends UiBinder<Widget, ModuleEditWidget> { }

	
	
	public ModuleEditWidget() {
		init();
		initWidget(uiBinder.createAndBindUi(this));
	}




	public void setModule(ModuleProxy module) {
		this.module = module;
	}

	public void init() {
		String description = module.getDescription();
		desc.setText(description);
		descEdit.setText(description);
		deckPanel.showWidget(0);

	}
	

	@UiHandler("editButton")
	void onClickHandler() {
		deckPanel.showWidget(1);
	}

}
