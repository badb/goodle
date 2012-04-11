package main.client.ui;

import main.client.ClientFactory;
import main.shared.CourseProxy;
import main.shared.CourseRequest;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.web.bindery.requestfactory.shared.Receiver;


public class CreateCourseView extends Composite 
{
	private static CreateCourseViewUiBinder uiBinder = GWT.create(CreateCourseViewUiBinder.class);

	interface CreateCourseViewUiBinder extends UiBinder<Widget, CreateCourseView> { }

	@UiField TextBox courseNameBox;
	@UiField TextArea courseDescBox;
	@UiField ListBox courseYearBox;
	@UiField ListBox courseTermBox;
	//@UiField (provided=true) CellList<GoodleUserProxy> teacherList;
	//@UiField (provided=true) CellList<GoodleUserProxy> memberList;
	@UiField Button saveButton;
	private ClientFactory clientFactory;
	
	public CreateCourseView() 
	{
		initCourseYearBox();
		initUserLists();
		initWidget(uiBinder.createAndBindUi(this));
	}

	public void setClientFactory(ClientFactory clientFactory) { this.clientFactory = clientFactory; }
	
	
	private void initCourseYearBox()
	{
		
	}
	
	private void initUserLists()
	{
		
	}
	
	public void clear()
	{
		courseNameBox.setText("");
		courseDescBox.setText("");
	}
	
	@UiHandler("saveButton")
	void onSaveButtonClicked(ClickEvent event)
	{
		if (clientFactory != null)
		{
			CourseRequest request = clientFactory.getRequestFactory().courseRequest();
			final CourseProxy course = request.create(CourseProxy.class);
			course.setName(courseNameBox.getText());
			course.setTerm("2012L");
			course.setDesc(courseDescBox.getText());
			request.persist().using(course).fire(new Receiver<Void>()
			{
				@Override
				public void onSuccess(Void v)
				{
					//clientFactory.getPlaceController().goTo(new CoursePlace(course.getId().toString()));
				}
			});
		}
	}
	
}
