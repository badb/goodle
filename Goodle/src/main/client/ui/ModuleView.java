package main.client.ui;


import main.client.ClientFactory;
import main.shared.proxy.ModuleProxy;
import main.shared.proxy.ModuleRequest;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.FocusEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DeckLayoutPanel;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public class ModuleView extends Composite {

	private static ModuleWidgetUiBinder uiBinder = GWT
			.create(ModuleWidgetUiBinder.class);

	interface ModuleWidgetUiBinder extends UiBinder<Widget, ModuleView> {
	}

	private ModuleProxy module;
	private boolean visible;

	@UiField
	Label titleView;
	@UiField
	TextBox titleEdit;
	@UiField
	Label descView;
	@UiField
	TextBox descEdit;

	@UiField
	Button editButton;
	@UiField
	HorizontalPanel editPanel;
	@UiField
	DeckLayoutPanel titleDeckPanel;
	@UiField
	DeckLayoutPanel descDeckPanel;
	@UiField
	CheckBox showHideBox;
	NotSavedPopup popup;
	@UiField
	FocusPanel focusPanel;

	private ClientFactory clientFactory;

	public ModuleView() {
		initWidget(uiBinder.createAndBindUi(this));
		titleView.setText("Nowy moduł");
		descView.setText("Opis");
		titleDeckPanel.showWidget(0);
		titleDeckPanel.setVisible(true);
		descDeckPanel.showWidget(0);

	}

	public ModuleProxy getModule() {
		return module;
	}

	public void setClientFactory(ClientFactory clientFactory) {
		this.clientFactory = clientFactory;
	}

	public void setModule(ModuleProxy module) {
		this.module = module;
		titleView.setText(module.getTitle());
		descView.setText(module.getText());
		visible = module.getIsVisible();

	}

	@UiHandler("focusPanel")
	void onFocusPanelFocus(FocusEvent event) {

		//if (clientFactory.getCurrentUser().equals(module.getAuthor())) {

			editButton.setVisible(false);
			editPanel.setVisible(true);

			titleEdit.setText(titleView.getText());
			descEdit.setText(descView.getText());
			titleDeckPanel.showWidget(1);
			descDeckPanel.showWidget(1);
			showHideBox.setValue(visible);
		//}
	}

	@UiHandler("focusPanel")
	void onFocusPanelBlur(BlurEvent event) {
		//if (clientFactory.getCurrentUser().equals(module.getAuthor())) {

			editButton.setVisible(true);
			editPanel.setVisible(false);

			titleDeckPanel.showWidget(0);
			descDeckPanel.showWidget(0);

			if (isChanged())
				saveData();
		//}
	}

	private boolean isChanged() {
		return (visible != showHideBox.getValue())
				|| (titleEdit.getText() != titleView.getText())
				|| (descEdit.getText() != descView.getText());
	}

	@UiHandler("cancelButton")
	void onCancelButtonClicked(ClickEvent event) {
		focusPanel.fireEvent(new BlurEvent() {
		});
	}

	@UiHandler("saveButton")
	void onSaveButtonClicked(ClickEvent event) {
		if (isChanged())
			saveData();
		focusPanel.fireEvent(new BlurEvent() {
		});
	}

	private void saveData() {
		titleView.setText(titleEdit.getText());
		descView.setText(descEdit.getText());
		module.setTitle(titleEdit.getText());
		module.setText(descEdit.getText());
		visible = showHideBox.getValue();
		module.setIsVisible(visible);
		
		ModuleRequest request = clientFactory.getRequestFactory()
				.moduleRequest();
		request.persist().using(module).fire();
	}

}
