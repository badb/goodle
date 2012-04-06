package main.client.panels;

import main.client.services.ServicesManager;

import com.google.gwt.dom.client.Element;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

public class CreateCoursePanel extends GoodlePanel
{
	private VerticalPanel mainPanel = new VerticalPanel();
	private TextBox courseNameBox = new TextBox();
	private TextArea courseDescBox = new TextArea();
	private Button applyButton = new Button();
	
	public CreateCoursePanel(final ServicesManager manager)
	{
		super(manager);
		
		courseNameBox.getElement().setAttribute("placeholder", "Nazwa kursu");
		courseDescBox.getElement().setAttribute("placeholder", "Krótki opis kursu");
		
		applyButton.addClickHandler(new ClickHandler()
		{
			public void onClick(ClickEvent event) 
			{
				String name = courseNameBox.getText();
				String desc = courseDescBox.getText();
				manager.createCourse(name, desc); 
			};
		});
		
		mainPanel.add(courseNameBox);
		mainPanel.add(courseDescBox);
		mainPanel.add(applyButton);
		initWidget(mainPanel);
	}

}
