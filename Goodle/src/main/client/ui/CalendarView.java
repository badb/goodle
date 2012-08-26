package main.client.ui;

import java.util.Collections;
import java.util.Comparator;
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
import com.google.gwt.user.cellview.client.ColumnSortEvent.ListHandler;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.ListDataProvider;
import com.google.web.bindery.requestfactory.shared.Receiver;

public class CalendarView extends Composite 
{

	private static CalendarViewUiBinder uiBinder = GWT.create(CalendarViewUiBinder.class);

	@UiField Label termsLabel;
	@UiField(provided=true) 
	CellTable<HomeworkProxy> termsList;
	@UiField VerticalPanel calendarPanel;
	
	private TextColumn<HomeworkProxy> nameColumn;
	private TextColumn<HomeworkProxy> dateColumn;
	ListDataProvider<HomeworkProxy> dataProvider;
	
	interface CalendarViewUiBinder extends UiBinder<Widget, CalendarView> { }
	 
	private ClientFactory clientFactory;
	
	public CalendarView()
	{
		initList();
		initWidget(uiBinder.createAndBindUi(this));
		calendarPanel.setCellHeight(termsLabel, "20px");
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
				return object.getTitle();
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
		
		dataProvider = new ListDataProvider<HomeworkProxy>();
		dataProvider.addDataDisplay(termsList);
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
						class HomeworkComparator implements Comparator<HomeworkProxy> {
					          public int compare(HomeworkProxy h1, HomeworkProxy h2) {
					        	  Date d1 = h1.getDeadline();
					        	  Date d2 = h2.getDeadline();
					            if (d1 == d2) {
					              return 0;
					            }

					            if (d1 != null) {
					              return (d2 != null) ? d1.compareTo(d2) : 1;
					            }
					            return -1;
					        }
					}
						Collections.sort(h, new HomeworkComparator());
						List<HomeworkProxy> list = dataProvider.getList();
						list.addAll(h);
						ListHandler<HomeworkProxy> columnSortHandler = new ListHandler<HomeworkProxy>(dataProvider.getList());
					    columnSortHandler.setComparator(dateColumn,
					        new Comparator<HomeworkProxy>() {
					          public int compare(HomeworkProxy h1, HomeworkProxy h2) {
					        	  Date d1 = h1.getDeadline();
					        	  Date d2 = h2.getDeadline();
					            if (d1 == d2) {
					              return 0;
					            }

					            if (d1 != null) {
					              return (d2 != null) ? d1.compareTo(d2) : 1;
					            }
					            return -1;
					          }
					        });
					    termsList.addColumnSortHandler(columnSortHandler);
					    
					    termsList.getColumnSortList().push(dateColumn);
					    list = dataProvider.getList();
					}
				}
		);
	}
}