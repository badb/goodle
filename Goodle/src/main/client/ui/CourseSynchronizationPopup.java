package main.client.ui;

import java.util.List;

import main.client.util.ShortCourseCell;
import main.shared.proxy.LongUSOSCourseDescProxy;
import main.shared.proxy.ShortUSOSCourseDescProxy;

import com.google.gwt.cell.client.ValueUpdater;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.FocusEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.web.bindery.requestfactory.shared.Receiver;

public class CourseSynchronizationPopup extends AbstractCoursePopup {

	private static CourseSynchronizationPopupUiBinder uiBinder = GWT.
			create(CourseSynchronizationPopupUiBinder.class);

	interface CourseSynchronizationPopupUiBinder extends
	UiBinder<Widget, CourseSynchronizationPopup> { }

	@UiField(provided=true) 
	CellList<ShortUSOSCourseDescProxy> cellList;


	@UiField TextBox searchBox;
	@UiField Button searchButton;
	//	@UiField Button okButton;
	@UiField Button cancelButton;



	public CourseSynchronizationPopup() {

		cellList = new CellList<ShortUSOSCourseDescProxy>(new ShortCourseCell());

		ValueUpdater<ShortUSOSCourseDescProxy> updater = new ValueUpdater<ShortUSOSCourseDescProxy>()
		{
			public void update(ShortUSOSCourseDescProxy value) 
			{
				cf.getRequestFactory().usosApiRequest().getCourseById(value.getId()).fire
				(
					new Receiver<LongUSOSCourseDescProxy>()
					{
						@Override
						public void onSuccess(LongUSOSCourseDescProxy response)
						{
							SynchronizationConfirmationPopup popup = cf.getSynchronizationConfirmationPopup();
							popup.setCourse(course);
							popup.setUSOSCourseDesc(response);
							popup.center();

						}
					});
			}
		};
		cellList.setValueUpdater(updater);
		setWidget(uiBinder.createAndBindUi(this));
	}

	@UiHandler("cancelButton")
	public void onCancelButtonClicked(ClickEvent click)
	{
		searchBox.setText("");
		this.hide();
	}

	@UiHandler("searchButton")
	public void onSearchButtonClicked(ClickEvent click)
	{
		if(searchBox.getText().isEmpty())
			return;
		cf.getRequestFactory().usosApiRequest().searchCourses(searchBox.getText()).fire(
				new Receiver<List<ShortUSOSCourseDescProxy>>()
				{
					@Override
					public void onSuccess(List<ShortUSOSCourseDescProxy> response)
					{
						cellList.setRowData(response);
					}
				});
	}
	@UiHandler("searchBox")
	public void onSearchBoxFocus(FocusEvent focus){
		if (searchBox.getText().equals("Szukaj kursu"))
			searchBox.setText("");
	}

}
