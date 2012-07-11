package main.client.ui;

import main.client.ClientFactory;
import main.shared.JoinMethod;
import main.shared.proxy.CourseProxy;
import main.shared.proxy.CourseRequest;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.Widget;
import com.google.web.bindery.requestfactory.shared.Receiver;

public class CourseJoinMethodPopup extends DialogBox
{
	private static CourseJoinMethodPopupUiBinder uiBinder = GWT.create(CourseJoinMethodPopupUiBinder.class);

	interface CourseJoinMethodPopupUiBinder extends UiBinder<Widget, CourseJoinMethodPopup> { }
	
	@UiField RadioButton openRadio;
	@UiField RadioButton keyRadio;
	@UiField PasswordTextBox keyBox;
	
	@UiField Label message;
	
	@UiField Button save;
	@UiField Button cancel;
	
	private CourseView parent;
	public void setParent(CourseView parent) { this.parent = parent; }
	
	private CourseProxy course;
	public void setCourseProxy(CourseProxy course) 
	{ 
		this.course = course;
		if (course.getJoinMethod().equals(JoinMethod.OPEN))
		{
			openRadio.setValue(true);
		}
		else if (course.getJoinMethod().equals(JoinMethod.KEY))
		{
			keyRadio.setValue(true);
		}
	}
	
	private ClientFactory clientFactory;
	public void setClientFactory(ClientFactory clientFactory) { this.clientFactory = clientFactory; }
	
	public static String failure = "Operacja nie powiodła się. Spróbuj ponownie.";
	
	public CourseJoinMethodPopup() 
	{
		setWidget(uiBinder.createAndBindUi(this));
	}
	
	@UiHandler("openRadio")
	public void onOpenMethodSelected(ClickEvent event)
	{
		keyBox.setEnabled(false);
	}
	
	@UiHandler("keyRadio")
	public void onKeyMethodSelected(ClickEvent event)
	{
		keyBox.setEnabled(true);
	}
	
	@UiHandler("save")
	public void onSaveButtonClicked(ClickEvent click)
	{
		final CourseJoinMethodPopup me = this;
		
		CourseRequest request = clientFactory.getRequestFactory().courseRequest();
		course = request.edit(course);
		
		if (openRadio.getValue()) 
		{
			course.setJoinMethod(JoinMethod.OPEN);
		}
		else if (keyRadio.getValue()) 
		{
			course.setJoinMethod(JoinMethod.KEY);
			course.setKey(keyBox.getText());
		}
		
		request.update().using(course).fire
		(
			new Receiver<CourseProxy>()
			{
				@Override
				public void onSuccess(CourseProxy response)
				{
					if (response != null)
					{
						parent.setCourse(response);
						me.hide();
					}
					else message.setText(failure);
				}
			}
		); 
	}

	@UiHandler("cancel")
	public void onCancelButtonClicked(ClickEvent click)
	{
		this.hide();
	}
}
