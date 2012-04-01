package main.client.panels;

import java.util.List;

import main.client.services.ServicesManager;
import main.client.utils.CourseCell;
import main.shared.models.Course;

import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;


public class ResultListPanel extends GoodlePanel
{

	
	private VerticalPanel mainPanel = new VerticalPanel();
	private Label infoLabel = new Label();
	private CellList<Course> results = new CellList<Course>(new CourseCell());
	
	public ResultListPanel(ServicesManager manager, List<Course> courses) 
	{ 
		super(manager); 
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
