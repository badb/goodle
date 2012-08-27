package main.client.ui;

import main.shared.proxy.Converter;
import main.shared.proxy.CourseProxy;
import main.shared.proxy.CourseRequest;
import main.shared.proxy.LongUSOSCourseDescProxy;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.Widget;
import com.google.web.bindery.requestfactory.shared.Receiver;

public class SynchronizationConfirmationPopup extends AbstractCoursePopup {

	private static SynchronizationConfirmationPopupUiBinder uiBinder = GWT
			.create(SynchronizationConfirmationPopupUiBinder.class);
	
	private LongUSOSCourseDescProxy usosCourse;
	//@UiField Label infoLabel;
	@UiField Label courseName;
	//@UiField Label descLabel;
	@UiField TextArea courseDesc;
	//@UiField Label bibLabel;
	@UiField TextArea courseBib;
	@UiField Button okButton;
	@UiField Button cancelButton;

	interface SynchronizationConfirmationPopupUiBinder extends
			UiBinder<Widget, SynchronizationConfirmationPopup> {
	}

	public SynchronizationConfirmationPopup() {
		setWidget(uiBinder.createAndBindUi(this));
	}

	public void setUSOSCourseDesc(LongUSOSCourseDescProxy response) {
		usosCourse = response;
		if(usosCourse != null)
		{
			courseName.setText(usosCourse.getId() + " " + usosCourse.getName());
			courseDesc.setText(usosCourse.getDesc());
			courseBib.setText(usosCourse.getBibliography());			
		}		
	}
	@UiHandler("cancelButton")
	public void onCancelButtonClicked(ClickEvent click)
	{
		this.hide();
	}
	@UiHandler("okButton")
	public void onOKButtonClicked(ClickEvent click)
	{
		if(usosCourse != null && course != null)
		{
			CourseRequest request = cf.getRequestFactory().courseRequest();
			course = request.edit(course);
			course.setDescription(Converter.getList(usosCourse.getDesc()));
			course.setBibliography(Converter.getList(usosCourse.getBibliography()));
			request.update().using(course).fire
			(
				new Receiver<CourseProxy>()
				{
					@Override
					public void onSuccess(CourseProxy response) 
					{
						if (parent != null) {
							course = response;
							parent.setCourse(response);
						}
						
						cf.getCourseSynchronizationPopup().hide();
						hide();	
					}
				}
			);
		}
	}


}
