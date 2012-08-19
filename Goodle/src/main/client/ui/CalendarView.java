package main.client.ui;

import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import main.client.ClientFactory;
import main.shared.proxy.CourseProxy;
import main.shared.proxy.GoodleUserProxy;
import main.shared.proxy.HomeworkProxy;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import com.google.web.bindery.requestfactory.shared.Receiver;

public class CalendarView extends Composite 
{

	private static CalendarViewUiBinder uiBinder = GWT.create(CalendarViewUiBinder.class);

	@UiField(provided=true) 
	CellTable<HomeworkProxy> termsList;
	
	private TextColumn<HomeworkProxy> nameColumn;
	private TextColumn<HomeworkProxy> dateColumn;
	
	interface CalendarViewUiBinder extends UiBinder<Widget, CalendarView> { }
	 
	private ClientFactory clientFactory;
	
	public CalendarView()
	{
		initList();
		initWidget(uiBinder.createAndBindUi(this));
	}
	
	public void setClientFactory(ClientFactory clientFactory) { 
		this.clientFactory = clientFactory; 
		refreshList();
	}

	
	private void initList() {
		termsList = new CellTable<HomeworkProxy> ();
		nameColumn = new TextColumn<HomeworkProxy>() {
			public String getValue(HomeworkProxy object) 
			{
				return object.getName();
			}
		};
		
		dateColumn = new TextColumn<HomeworkProxy>() {
			public String getValue(HomeworkProxy object) 
			{
				
				Date d = object.getDeadline();
				if (d != null)
					return object.getDeadline().toString();
				else 
					return ""; 
			}
		};
		dateColumn.setSortable(true);

		termsList.addColumn(nameColumn);
		termsList.addColumn(dateColumn);
	}
	
	private void refreshList() {
		clientFactory.getRequestFactory().goodleUserRequest().getLedCourseIds()
		.using(clientFactory.getCurrentUser()).fire
		(
			new Receiver<List<Long>>()
			{
				@Override
				public void onSuccess(List<Long> response)
				{
					refresh(response);
				}
			}
		);
	}
	
	private void refresh(List<Long> l) {
		clientFactory.getRequestFactory().courseRequest().findUserHomeworks(l).fire(
				new Receiver<List<HomeworkProxy>>() {
			
					@Override
					public void onSuccess(List<HomeworkProxy> h) {
						termsList.setRowData(h);
	
					}
				}
		);
	}
}