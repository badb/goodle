package main.client.ui;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import main.client.ClientFactory;
import main.shared.proxy.Converter;
import main.shared.proxy.CourseRequest;
import main.shared.proxy.HomeworkProxy;
import main.shared.proxy.SolutionProxy;
import main.shared.proxy.UploadedFileProxy;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;


public class HomeworkView extends AbstractCourseView implements FileContainerInterface
{
	private static HomeworkViewUiBinder uiBinder = GWT.create(HomeworkViewUiBinder.class);

	interface HomeworkViewUiBinder extends UiBinder<Widget, HomeworkView> { }
	
	@UiField Label visible;
	@UiField Label title;
	@UiField Label text;
	@UiField Label deadline;
	@UiField FileUploadView upload;
	@UiField FlexTable attachedFiles;
	@UiField FlexTable solutions;
	@UiField Button save;
	
	// For debugging purposes.
	@UiField Label _out;
	
	private Long courseId;
	private Long homeworkId;
	
	private HomeworkProxy homework;
	
	public void setClientFactory(ClientFactory cf)
	{
		this.cf = cf;
		upload.setClientFactory(cf);
	}

	public HomeworkView()
	{
		initWidget(uiBinder.createAndBindUi(this));
	}	
	
	public void setHomework(HomeworkProxy homework) 
	{ 
		courseId = homework.getCourse();
		homeworkId = homework.getId();
		upload.setParent(this);
		
		this.homework = homework;
		
		title.setText(homework.getTitle());
		text.setText(Converter.getString(homework.getText()));
		Date d = homework.getDeadline();
		
		if (d != null)
			deadline.setText(DateTimeFormat.getFormat(ClientFactory.dateFormat).format(d));
		else
			deadline.setText("brak terminu");
		
		if (isCurrUserOwner()) {
			if (homework.getIsVisible())
				visible.setText("Widoczny");
			else {
				this.addStyleName("hidden");
				visible.setText("Ukryty");
			}
		} else {
			visible.setVisible(false);
		}
		
		for (UploadedFileProxy f : homework.getAttachedFiles()) 
		{ 
			 int rows = attachedFiles.getRowCount(); 
			 attachedFiles.insertRow(rows);
			 attachedFiles.insertCell(rows, 0);
			 FileView view = new FileView(); 
			 view.setUploadedFile(f);
			 attachedFiles.setWidget(rows, 0, view);
		}
		
		for (SolutionProxy f : homework.getSolutions()) 
			addSolution(f);
		
		if (isCurrUserOwner()) {
			upload.setVisible(false);
			if (!homework.getSolutions().isEmpty()) {
				save.setVisible(true);
				save.setEnabled(true);
			}
		}
	}

	@Override
	public void addFile(String url, String name) 
	{		
		CourseRequest request = cf.getRequestFactory().courseRequest();
		
		SolutionProxy file = request.create(SolutionProxy.class);
		

		file.setName(name);
		file.setUrl(url);
		//file.setAuthor(cf.getCurrentUser().getId());
		file.setComment("komentarz");
		file.setChecked(false);
		file.setUploaded(new Date());
		addSolution(file);
		request.uploadSolution(courseId, homeworkId, file).fire();
	}

	private void addSolution(SolutionProxy solution) 
	{
		Logger logger = Logger.getLogger("Goodle.Log");

		// Member can upload only one solution and only when his previous
		// one is not already checked.
		if (isCurrUserMember())
		{
			solutions.removeAllRows();
			 if (solution.isChecked()) {
				upload.setVisible(false);
			} else {
				upload.setVisible(true);
			}
		}
		int rows = solutions.getRowCount();
		solutions.insertRow(rows);
		solutions.insertCell(rows, 0);
		SolutionView view = new SolutionView();
		view.setClientFactory(cf);
		view.setCourse(course);
		view.setSolution(solution);
		if (isCurrUserOwner())
			view.setAuthorNameAsTitle(true);
		if (homework.getDeadline() != null && solution.getUploaded().after(homework.getDeadline())) 
			view.setLate();
		solutions.setWidget(rows, 0, view);
	}

	@Override
	public void removeFile(UploadedFileProxy f) { }
	
	@UiHandler("save")
	public void onButtonClick(ClickEvent event) {
		CourseRequest request = cf.getRequestFactory().courseRequest();
		HomeworkProxy homeworkCopy = request.edit(homework);
		
		for (int i = 0; i < solutions.getRowCount(); i++) 
		{
			SolutionView view = (SolutionView) solutions.getWidget(i, 0);
			view.updateSolution(request);
		}
		request.updateHomeworkMarks(course.getId(), homeworkCopy).with("solutions").fire();
	}
	
}