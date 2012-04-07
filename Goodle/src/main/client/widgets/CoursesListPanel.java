package main.client.widgets;

import java.util.List;

import main.client.services.ServicesManager;
import main.client.utils.CourseCell;
import main.client.utils.CourseShortDesc;

import com.google.gwt.cell.client.ValueUpdater;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

public class CoursesListPanel extends GoodleWidget
{	
	private VerticalPanel mainPanel = new VerticalPanel();
	private Label infoLabel = new Label();
	private CellList<CourseShortDesc> results = new CellList<CourseShortDesc>(new CourseCell());
	
	public CoursesListPanel(final ServicesManager manager, SimpleEventBus eventBus, List<CourseShortDesc> courses) 
	{ 
		super(manager, eventBus); 
		ValueUpdater<CourseShortDesc> updater = new ValueUpdater<CourseShortDesc>()
		{
			public void update(CourseShortDesc value) { manager.findCourseByKey(value.getKey()); }
		};
		results.setValueUpdater(updater);
		if (courses.isEmpty()) 
		{
			String none = "Nie udało się odnaleźć kursów pasujących " +
					"do podanych kryteriów wyszukiwania.";
			infoLabel.setText(none);
		}
		else results.setRowData(courses);
		mainPanel.add(infoLabel);
		mainPanel.add(results);
		initWidget(mainPanel);
	}
}
