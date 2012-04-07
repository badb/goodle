package main.client.widgets;

import main.client.events.CreateCourseViewRequestedEvent;
import main.client.services.ServicesManager;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.user.client.ui.Button;

public class CreateCourseCaller extends GoodleWidget 
{
	private Button createCourseButton = new Button("Utwórz kurs");

	public CreateCourseCaller(ServicesManager manager, final SimpleEventBus eventBus) 
	{
		super(manager, eventBus);
		createCourseButton.addClickHandler(new ClickHandler ()
		{
			public void onClick(ClickEvent event) 
			{ 
				eventBus.fireEvent(new CreateCourseViewRequestedEvent()); 
			}
		});
		initWidget(createCourseButton);
	}

}
