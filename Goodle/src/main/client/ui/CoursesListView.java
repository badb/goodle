package main.client.ui;

import java.util.List;

import main.client.ClientFactory;
import main.client.place.CoursePlace;
import main.client.util.CourseCell;
import main.shared.CourseProxy;

import com.google.gwt.cell.client.ValueUpdater;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.google.web.bindery.requestfactory.shared.Receiver;


public class CoursesListView extends Composite
{
	private static CoursesListViewUiBinder uiBinder = GWT.create(CoursesListViewUiBinder.class);

	interface CoursesListViewUiBinder extends UiBinder<Widget, CoursesListView> { }

	@UiField Label infoLabel;
	@UiField(provided=true) 
	CellList<CourseProxy> coursesList;
	private ClientFactory clientFactory;

	public CoursesListView()
	{
		initList();
		initWidget(uiBinder.createAndBindUi(this));
	}

	public void setClientFactory(ClientFactory clientFactory) { this.clientFactory = clientFactory; }

	private void initList()
	{
		coursesList = new CellList<CourseProxy>(new CourseCell());
		ValueUpdater<CourseProxy> updater = new ValueUpdater<CourseProxy>()
				{
			public void update(CourseProxy value) 
			{
				clientFactory.getPlaceController().goTo(new CoursePlace(value.getId().toString()));
			}
				};
				coursesList.setValueUpdater(updater);
	}

	public void clear()
	{
		infoLabel.setText("");
	}

	public void findCoursesByName(String name) 
	{
		if (clientFactory != null)
		{
			infoLabel.setText("Trwa wyszukiwanie...");
			clientFactory.getRequestFactory().courseRequest().findCoursesByName(name).fire
			(
					new Receiver<List<CourseProxy>>()
					{
						@Override
						public void onSuccess(List<CourseProxy> response)
						{
							if (response.isEmpty())
							{
								infoLabel.setText("Nie odnaleziono kursów pasujących " +
										"do podanych kryteriów wyszukiwania");
							}
							else infoLabel.setText("");
							coursesList.setRowData(response);
						}
					}
			);
		}
	}


}
