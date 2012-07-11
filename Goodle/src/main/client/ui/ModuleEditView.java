package main.client.ui;

import main.client.ClientFactory;
import main.client.ui.ModuleView.ModuleWidgetUiBinder;
import main.shared.proxy.ModuleProxy;
import main.shared.proxy.ModuleRequest;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Widget;

public class ModuleEditView extends Composite {

	private static ModuleWidgetUiBinder uiBinder = GWT
			.create(ModuleWidgetUiBinder.class);

	interface ModuleWidgetUiBinder extends UiBinder<Widget, ModuleEditView> {
	}

	private ModuleProxy module;
	private boolean visible;

	@UiField
	EditLabel titleEdit;
	@UiField
	EditLabel descEdit;

	@UiField
	CheckBox showHideBox;
	NotSavedPopup popup;

	private ClientFactory clientFactory;
	private ModuleRequest request;
	private boolean isEdited = false;

	public ModuleEditView() {
		initWidget(uiBinder.createAndBindUi(this));
		titleEdit.setText("Nowy moduł");
		descEdit.setText("Opis");

	}

	public ModuleProxy getModule() {
		return module;
	}

	public void setClientFactory(ClientFactory clientFactory) {
		this.clientFactory = clientFactory;
	}

	public void setModule(ModuleProxy module) {

		titleEdit.setText(module.getTitle());
		descEdit.setText(module.getText());
		visible = module.getIsVisible();

	}
	
	public boolean getIsEdited() {
		return isEdited;
	}
	
	
	public void showEdit() {
		//if (clientFactory.getCurrentUser().getId().equals(module.getAuthor().getId())) {

		if (!isEdited) {
		isEdited = true;
		showHideBox.setValue(!visible);
	
		}
		//}
	}

	
	public void hideEdit() {
		//if (clientFactory.getCurrentUser().getId().equals(module.getAuthor().getId())) {

		if (isEdited) {
			//if (isChanged())
			//	saveData();
		    
			isEdited = false;

		}
		//}
	}



	public boolean isChanged() {
		return (visible != showHideBox.getValue())
				|| (titleEdit.getText() != titleEdit.getText())
				|| (descEdit.getText() != descEdit.getText());
	}

	

	public void saveData() {
		//tODO usunąć:
		if (module == null) return;
		module.setTitle(titleEdit.getText());
		module.setText(descEdit.getText());
		visible = !showHideBox.getValue();
		module.setIsVisible(visible);
		

	}

}