package main.client.ui;

import java.util.logging.Level;
import java.util.logging.Logger;

import main.client.ClientFactory;
import main.client.place.CoursePlace;
import main.shared.JoinMethod;
import main.shared.proxy.CourseProxy;
import main.shared.proxy.CourseRequest;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.TabLayoutPanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.web.bindery.requestfactory.shared.Receiver;
import com.google.web.bindery.requestfactory.shared.ServerFailure;


public class CreateCourseView extends Composite 
{
	private static CreateCourseViewUiBinder uiBinder = GWT.create(CreateCourseViewUiBinder.class);

	interface CreateCourseViewUiBinder extends UiBinder<Widget, CreateCourseView> { }

	@UiField TabLayoutPanel tabPanel;
	@UiField TextBox courseNameBox;
	@UiField TextArea courseDescBox;
	@UiField ListBox courseYearBox;
	@UiField ListBox courseTermBox;
	@UiField RadioButton regMethod1;
	@UiField RadioButton regMethod2;
	@UiField RadioButton regMethod3;
	@UiField TextBox coursePasswordBox;
	@UiField Button saveButton;
	private ClientFactory clientFactory;
	
	public CreateCourseView() 
	{
		initWidget(uiBinder.createAndBindUi(this));
		coursePasswordBox.setEnabled(false);
		courseYearBox.addItem("2012");
		courseYearBox.addItem("2013");
		courseYearBox.setSelectedIndex(0);
		courseTermBox.setSelectedIndex(0);
	}

	public void setClientFactory(ClientFactory clientFactory) { this.clientFactory = clientFactory; }
	
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
			saveButton.setEnabled(false);
			CourseRequest request = clientFactory.getRequestFactory().courseRequest();
			final CourseProxy course = request.create(CourseProxy.class);
			course.setName(getCourseName());
			course.setTerm(getCourseTerm());
			course.setDescription(getCourseDesc());
			course.setJoinMethod(getCourseRegMethod());
			course.setKey(getCoursePassword());
			request.persist().using(course).fire(new Receiver<Long>()
			{
				@Override
				public void onSuccess(Long id)
				{
						clientFactory.getPlaceController().goTo(new CoursePlace(id.toString(), "1"));
				}
				@Override
				public void onFailure(ServerFailure error){
					Logger logger = Logger.getLogger("Goodle.Log");
				    logger.log(Level.SEVERE, error.getMessage());
				    logger.log(Level.SEVERE, error.getStackTraceString());
				    logger.log(Level.SEVERE, error.getExceptionType());
				}
			});
		}
	}
	
	@UiHandler("regMethod1")
	void onRegMethod1Selected(ClickEvent event) { coursePasswordBox.setEnabled(false); }

	@UiHandler("regMethod2")
	void onRegMethod2Selected(ClickEvent event) { coursePasswordBox.setEnabled(false); }
	
	@UiHandler("regMethod3")
	void onRegMethod3Selected(ClickEvent event) { coursePasswordBox.setEnabled(true); }
	
	private String getCourseName() { return courseNameBox.getText(); }

	private String getCourseTerm() 
	{ 
		String term = courseYearBox.getValue(courseYearBox.getSelectedIndex()); 
		term += courseTermBox.getValue(courseTermBox.getSelectedIndex());
		return term;
	}
	
	private String getCourseDesc() { return courseDescBox.getText(); }
	
	private JoinMethod getCourseRegMethod()
	{
		if (regMethod1.getValue()) return JoinMethod.OPEN;
		else if (regMethod1.getValue()) return JoinMethod.ASK;
		else return JoinMethod.KEY;
	}
	
	private String getCoursePassword() { return coursePasswordBox.getText(); }
}
