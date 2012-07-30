package main.client.ui;

import main.client.ClientFactory;
import main.shared.proxy.ModuleProxy;
import main.shared.proxy.UploadedFileProxy;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public class ModuleView extends Composite {

	private static ModuleWidgetUiBinder uiBinder = GWT
			.create(ModuleWidgetUiBinder.class);

	interface ModuleWidgetUiBinder extends UiBinder<Widget, ModuleView> {
	}

	@UiField
	Label visible;
	@UiField
	Label title;
	@UiField
	Label text;
	@UiField
	FlexTable filesTable;

	private ClientFactory clientFactory;

	public void setClientFactory(ClientFactory clientFactory) {
		this.clientFactory = clientFactory;
	}

	public void setModule(ModuleProxy module) {
		title.setText(module.getTitle());
		text.setText(module.getText());
		if (module.getIsVisible()) {
			visible.setText("Widoczny");
		} else
			visible.setText("Ukryty");

		/*
		 * ModuleRequest request =
		 * clientFactory.getRequestFactory().moduleRequest(); module =
		 * request.edit(module);
		 * 
		 * request.getFiles().using(module).fire ( new
		 * Receiver<List<UploadedFileProxy>>() {
		 * 
		 * @Override public void onSuccess(List<UploadedFileProxy> result) { for
		 * (UploadedFileProxy m : result) { int rows = filesTable.getRowCount();
		 * filesTable.insertRow(rows); filesTable.insertCell(rows, 0); FileView
		 * view = new FileView(); view.setClientFactory(clientFactory);
		 * view.setUploadedFile(m); filesTable.setWidget(rows, 0, view); } } }
		 * );
		 */

		
		 /*for (UploadedFileProxy m : module.getMaterials()) { 
			 int rows = filesTable.getRowCount(); filesTable.insertRow(rows);
			 filesTable.insertCell(rows, 0);
			 FileView view = new FileView(); 
			 view.setClientFactory(clientFactory);
			 view.setUploadedFile(m);
			 filesTable.setWidget(rows, 0, view);
		 }*/
	}

	public ModuleView() {
		initWidget(uiBinder.createAndBindUi(this));
	}

}
