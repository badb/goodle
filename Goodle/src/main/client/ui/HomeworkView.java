package main.client.ui;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import main.client.ClientFactory;
import main.shared.proxy.CourseProxy;
import main.shared.proxy.CourseRequest;
import main.shared.proxy.HomeworkProxy;
import main.shared.proxy.ModuleProxy;
import main.shared.proxy.UploadedFileProxy;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.attributeparsers.CssNameConverter.Failure;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.google.web.bindery.requestfactory.shared.Receiver;
import com.google.web.bindery.requestfactory.shared.ServerFailure;


public class HomeworkView extends AbstractCourseView implements FileContainerInterface
{
	private static HomeworkViewUiBinder uiBinder = GWT.create(HomeworkViewUiBinder.class);

	interface HomeworkViewUiBinder extends UiBinder<Widget, HomeworkView> { }
	
	@UiField Label visible;
	@UiField Label title;
	@UiField Label text;
	@UiField Label deadline;
	@UiField FlexTable filesTable;
	@UiField FileUploadView upload;
	
	private List <UploadedFileProxy> files;
	//private CourseHomeworksView parent;
//	public void setParent(CourseHomeworksView parent) {this.parent = parent;}

	//private CourseRequest request;
	//public void setRequest(CourseRequest request) { this.request = request; }
	
	private HomeworkProxy homework;
	
	//private ClientFactory clientFactory;
	public void setClientFactory(ClientFactory clientFactory) { cf = clientFactory; 
		upload.setClientFactory(clientFactory);}

	private Logger logger = Logger.getLogger("Goodle.Log");
	
	public HomeworkView()
	{
		initWidget(uiBinder.createAndBindUi(this));
	}	
	
	private void updateCourse(CourseRequest request)
	{
		
		List<HomeworkProxy> updated = new ArrayList<HomeworkProxy>();
		updated.add(homework);

		course = request.edit(course);
		request.updateHomeworks(updated).using(course).with("materials").fire
		(
			new Receiver<CourseProxy>()
			{
				@Override
				public void onSuccess(CourseProxy response) 
				{
					if (parent != null) parent.setCourse(response);
					course = response;
				}				
			}
		);
	
	}
	
	public void setHomework(HomeworkProxy homework) 
	{ 
		this.homework = homework;
		upload.setParent(this);
		title.setText(homework.getTitle());
		text.setText(homework.getText());
		files = homework.getMaterials();
		Date d = homework.getDeadline();
		if (d != null)
			deadline.setText(d.toString());
		else
			deadline.setText("brak terminu");
		if (homework.getIsVisible())
		{
			visible.setText("Widoczny");
		}
		else visible.setText("Ukryty");
	}

	@Override
	public void addFile(String url, String title) {
		/*if (request == null) {
			logger.log(Level.SEVERE, "HomeworkView: CourseRequest is NULL");
			return;
		}*/

		CourseRequest request = cf.getRequestFactory().courseRequest();
		homework = request.edit(homework);
		
		logger.log(Level.SEVERE, "Going to create proxy you sheepfaka.");
		UploadedFileProxy file = request.create(UploadedFileProxy.class);
		logger.log(Level.SEVERE, "Created proxy you madafaka.");
		if (file == null) {
			logger.log(Level.SEVERE, "HomeworkView: file is NULL");
			return;
		}
		file.setName(title);
		file.setUrl(url);
		file.setModule(homework);
		
		if (files == null) {
			logger.log(Level.SEVERE, "HomeworkView: files is NULL");
			return;
		}
		files.add(file);

		homework.setMaterials(files);
		
		updateCourse(request);
		
		addFileView(file);
	}

	private void addFileView(UploadedFileProxy file) {

		if (filesTable == null) {
			logger.log(Level.SEVERE, "filesTable is NULL");
			return;
		} else {
			logger.log(Level.INFO, "inside addFileVIew");
		}
		int rows = filesTable.getRowCount();
		filesTable.insertRow(rows);
		filesTable.insertCell(rows, 0);
		FileEditView view = new FileEditView();
		view.setClientFactory(cf);
		view.setUploadedFile(file);
		view.setParent(this);
		filesTable.setWidget(rows, 0, view);
	}

	private void refreshFiles() {
		filesTable.removeAllRows();
		for (UploadedFileProxy file : files) {
			addFileView(file);
		}
	}
	
	
	@Override
	public void removeFile(UploadedFileProxy file) {
		int index = files.lastIndexOf(file);
		if (index == -1) {
			return;
		}

		filesTable.removeRow(index);
		files.remove(index);
		
		CourseRequest request = cf.getRequestFactory().courseRequest();
		course = request.edit(course);
		
		homework = request.edit(homework);
		homework.setMaterials(files);
		updateCourse(request);
	}
}
