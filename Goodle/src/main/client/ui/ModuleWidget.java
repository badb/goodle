package main.client.ui;

import main.client.ui.ModulesTabView.ModulesTabUiBinder;
import main.shared.ModuleProxy;


import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public class ModuleWidget extends Composite {

	private ModuleProxy module;
	@UiField Label desc;
	
	private static ModuleWidgetUiBinder uiBinder = GWT.create(ModuleWidgetUiBinder.class);

	interface ModuleWidgetUiBinder extends UiBinder<Widget, ModuleWidget> { }

	
	
	public ModuleWidget() {
		initWidget(uiBinder.createAndBindUi(this));
	}



	public ModuleProxy getModule() {
		return module;
	}



	public void setModule(ModuleProxy module) {
		this.module = module;
	}

}
