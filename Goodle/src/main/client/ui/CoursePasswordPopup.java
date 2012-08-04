package main.client.ui;

import main.shared.proxy.CourseRequest;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.web.bindery.requestfactory.shared.Receiver;

public class CoursePasswordPopup extends AbstractCoursePopup
{
	private static CoursePasswordPopupUiBinder uiBinder = GWT.create(CoursePasswordPopupUiBinder.class);

	interface CoursePasswordPopupUiBinder extends UiBinder<Widget, CoursePasswordPopup> { }
	
	@UiField PasswordTextBox keyBox;
	@UiField Label message;
	
	@UiField Button save;
	@UiField Button cancel;
	
	private static String wrongKeyMessage = "Operacja nie powiodła się. Sprawdź czy podany przez Ciebie klucz jest prawidłowy.";
	private static String emptyKeyMessage = "Klucz nie może być pusty!";
	
	public CoursePasswordPopup() 
	{
		setWidget(uiBinder.createAndBindUi(this));
		message.setVisible(false);
	}
	
	@UiHandler("save")
	public void onSaveButtonClicked(ClickEvent click)
	{
		message.setText("");
		message.setVisible(true);
		
		if (keyBox.getText().isEmpty())
		{
			message.setText(emptyKeyMessage);
	
			return;
		}
		
		final CoursePasswordPopup me = this;
		String key = keyBox.getText();
		
		CourseRequest request = cf.getRequestFactory().courseRequest();
		course = request.edit(course);
		request.registerCurrentUser(key).using(course).fire
		(
				new Receiver<Boolean>() 
				{
					@Override
					public void onSuccess(Boolean response)
					{
						if (response) 
						{
							parent.onUserRegistered();
							me.hide();
						}
						else message.setText(wrongKeyMessage);
					}
				}
		);
	}

	@UiHandler("cancel")
	public void onCancelButtonClicked(ClickEvent click)
	{
		message.setText("");
		this.hide();
	}
}
