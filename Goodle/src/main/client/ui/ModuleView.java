package main.client.ui;

import main.client.ClientFactory;
import main.shared.proxy.Converter;
import main.shared.proxy.ModuleProxy;
import main.shared.proxy.UploadedFileProxy;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public class ModuleView extends AbstractCourseView {

	private static ModuleWidgetUiBinder uiBinder = GWT
			.create(ModuleWidgetUiBinder.class);

	interface ModuleWidgetUiBinder extends UiBinder<Widget, ModuleView> {
	}

	@UiField Label visible;
	@UiField EditLabel title;
	@UiField EditLabel text;
	@UiField FlexTable filesTable;
	

	public void setModule(ModuleProxy module) 
	{
		
		title.setText(module.getTitle());
		text.setText(Converter.getString(module.getText()));
		if (isCurrUserOwner()) {
			if (module.getIsVisible())
				visible.setText("Widoczny");
			else {
				this.addStyleName("hidden");
				visible.setText("Ukryty");
			}
		} else {
			visible.setVisible(false);
		}
		if (module.getAttachedFiles() != null) 
		{
			for (UploadedFileProxy f : module.getAttachedFiles()) 
			{ 
				 int rows = filesTable.getRowCount(); 
				 filesTable.insertRow(rows);
				 filesTable.insertCell(rows, 0);
				 FileView view = new FileView(); 
				 view.setUploadedFile(f);
				 filesTable.setWidget(rows, 0, view);
			}
		}
	}

	public ModuleView() { initWidget(uiBinder.createAndBindUi(this)); }
	
	
}
