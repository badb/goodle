package main.client.ui;

import java.util.Date;
import java.util.logging.Logger;

import main.client.ClientFactory;
import main.shared.proxy.CourseRequest;
import main.shared.proxy.HomeworkProxy;
import main.shared.proxy.SolutionProxy;
import main.shared.proxy.UploadedFileProxy;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
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
	
	private Long courseId;
	private Long homeworkId;
	
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
		
		title.setText(homework.getTitle());
		text.setText(homework.getText());
		Date d = homework.getDeadline();
		
		if (d != null)
			deadline.setText(d.toString());
		else
			deadline.setText("brak terminu");
		
		if (homework.getIsVisible())
			visible.setText("Widoczny");
		else 
			visible.setText("Ukryty");
		
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
		
		if (isCurrUserOwner())
			upload.setVisible(false);
		
	}

	@Override
	public void addFile(String url, String name) 
	{
		CourseRequest request = cf.getRequestFactory().courseRequest();
		
		SolutionProxy file = request.create(SolutionProxy.class);

		file.setName(name);
		file.setUrl(url);
		file.setAuthor(cf.getCurrentUser().getId());
		file.setChecked(false);
		addSolution(file);
		request.uploadSolution(courseId, homeworkId, file).fire();

	}

	private void addSolution(SolutionProxy solution) 
	{
		// Member can upload only one solution.
		if (isCurrUserMember())
			solutions.removeAllRows();
		
		int rows = solutions.getRowCount();
		solutions.insertRow(rows);
		solutions.insertCell(rows, 0);
		SolutionView view = new SolutionView();
		view.setClientFactory(cf);
		view.setCourse(course);
		view.setSolution(solution);
		if (isCurrUserOwner())
			view.setAuthorNameAsTitle(true);
		solutions.setWidget(rows, 0, view);
	}

	@Override
	public void removeFile(UploadedFileProxy f) { }
	
}