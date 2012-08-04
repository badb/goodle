package main.client.ui;

import main.shared.proxy.CourseProxy;
import main.shared.proxy.CourseRequest;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JsDate;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.web.bindery.requestfactory.shared.Receiver;

public class CourseNameTermPopup extends AbstractCoursePopup
{
	private static CourseNameTermPopupUiBinder uiBinder = GWT.create(CourseNameTermPopupUiBinder.class);

	interface CourseNameTermPopupUiBinder extends UiBinder<Widget, CourseNameTermPopup> { }
	
	@UiField TextBox name;
	@UiField ListBox year;
	@UiField ListBox term;
	
	@UiField Label message;
	
	@UiField Button save;
	@UiField Button cancel;
	
	private static String failure = "Operacja nie powiodła się. Spróbuj ponownie.";
	private static String emptyNameMsg = "Nazwa kursu nie może być pusta.";
	
	public CourseNameTermPopup() 
	{
		setWidget(uiBinder.createAndBindUi(this));
		setTermOptions();
	}
	
	private void setTermOptions()
	{
		int currYear = JsDate.create().getFullYear() - 2000;
		year.addItem("" + (currYear - 1) + "/" + currYear); /* e.g. "11/12" */
		year.addItem("" + currYear + "/" + (currYear + 1)); /* "" for implicit string cast */
	}
	
	@Override
	protected void onCourseSet()
	{
		name.setText(course.getName());
	}
	
	@UiHandler("save")
	public void onSaveButtonClicked(ClickEvent click)
	{
		message.setText("");
		
		if (name.getText().isEmpty()) {
			message.setText(emptyNameMsg);
			return;
		}
		
		final CourseNameTermPopup me = this;
		
		CourseRequest request = cf.getRequestFactory().courseRequest();
		course = request.edit(course);
		
		course.setName(name.getText());
		course.setTerm(year.getValue(year.getSelectedIndex()) + term.getItemText(term.getSelectedIndex()));
		
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
		message.setText("");
		this.hide();
	}
}
