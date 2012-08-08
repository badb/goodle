package main.client.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.cell.client.Cell.Context;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Button;

public class CourseSynchronizationPopup extends AbstractCoursePopup {

	private static CourseSynchronizationPopupUiBinder uiBinder = GWT.
			create(CourseSynchronizationPopupUiBinder.class);
	
	interface CourseSynchronizationPopupUiBinder extends
		UiBinder<Widget, CourseSynchronizationPopup> { }
	
	@UiField(provided=true) CellList<Object> cellList = new CellList<Object>(new AbstractCell<Object>(){
		@Override
		public void render(Context context, Object value, SafeHtmlBuilder sb) {
			// TODO
		}
	});
	@UiField TextBox searchBox;
	@UiField Button searchButton;
	@UiField Button okButton;
	@UiField Button cancelButton;

	

	public CourseSynchronizationPopup() {
		setWidget(uiBinder.createAndBindUi(this));
	}
	
	@UiHandler("cancelButton")
	public void onCancelButtonClicked(ClickEvent click)
	{
		searchBox.setText("");
		this.hide();
	}

}
