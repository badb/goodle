package main.client.ui;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import main.client.ClientFactory;
import main.shared.proxy.Converter;
import main.shared.proxy.CourseRequest;
import main.shared.proxy.HomeworkProxy;
import main.shared.proxy.SolutionProxy;
import main.shared.proxy.UploadedFileProxy;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.datepicker.client.DatePicker;

public class HomeworkEditView extends AbstractCourseView implements FileContainerInterface
{

	private static HomeworkWidgetUiBinder uiBinder = GWT.create(HomeworkWidgetUiBinder.class);

	interface HomeworkWidgetUiBinder extends UiBinder<Widget, HomeworkEditView> { }

	@UiField EditLabel title;
	@UiField EditLabel text;
	@UiField CheckBox isVisible;
	@UiField DatePicker deadline;
	@UiField FlexTable filesTable;
	@UiField FileUploadView upload;
	
	private String previousTitle;
	private String previousText;
	private boolean previousIsVisible;
	private Date previousDeadline;
	
	private List <UploadedFileProxy> files;
	private List <UploadedFileProxy> newFiles = new ArrayList<UploadedFileProxy>();
	private boolean filesChanged = false;
	
	@Override
	public void setClientFactory(ClientFactory cf)
	{
		this.cf = cf;
		upload.setClientFactory(cf);
	}

	private CourseRequest request;
	public void setRequest(CourseRequest request) { this.request = request; }
	
	private HomeworkProxy homework;
	public HomeworkProxy getHomework()
	{
		homework.setTitle(title.getText());
		homework.setText(Converter.getList(text.getText()));
		homework.setDeadline(deadline.getValue());
		homework.setIsVisible(isVisible.getValue());
		homework.setAttachedFiles(newFiles);
		return homework;
	}
	
	public void setHomework(HomeworkProxy homework) 
	{
		this.homework = request.edit(homework);
		prepareView();
	}
	
	public void newHomework(Integer n)
	{
		if (n == null) return;
		
		homework = request.create(HomeworkProxy.class);
		homework.setTitle(course.getName() + ": Zadanie " + n.toString());
		homework.setText(Converter.getList("Edytuj treść"));
		homework.setIsVisible(false);
		homework.setAttachedFiles(new ArrayList<UploadedFileProxy>());
		homework.setSolutions(new ArrayList<SolutionProxy>());
		prepareView();
	}
	
	public void prepareView()
	{
		previousTitle = homework.getTitle();
		previousText = Converter.getString(homework.getText());
		previousIsVisible = homework.getIsVisible();
		previousDeadline = homework.getDeadline();
		files = homework.getAttachedFiles();
		title.setText(homework.getTitle());
		text.setText(Converter.getString(homework.getText()));
		if (previousDeadline != null)
			deadline.setValue(homework.getDeadline());
		isVisible.setValue(homework.getIsVisible());
		if (homework.getIsVisible())
		{
			isVisible.setText("Widoczny");
		}
		else isVisible.setText("Ukryty");
		
		upload.setParent(this);
		refreshFiles();
	}
	
	public boolean isChanged() 
	{ 
		return (!title.getText().equals(previousTitle) ||
				!text.getText().equals(previousText) ||
				isVisible.getValue() != previousIsVisible ||
				!deadline.getValue().equals(previousDeadline) ||
				filesChanged);
	}
	
	public HomeworkEditView() 
	{
		initWidget(uiBinder.createAndBindUi(this));
	}
	
	@UiHandler("isVisible")
	public void onVisibilityChanged(ClickEvent click)
	{
		if (isVisible.getText().equals("Widoczny"))
		{
			isVisible.setText("Ukryty");
		}
		else isVisible.setText("Widoczny");
	}
	
	public void addFile(String url, String title) 
	{
		UploadedFileProxy f = request.create(UploadedFileProxy.class);
		f.setName(title);
		f.setUrl(url);
		files.add(f);
		newFiles.add(f);
		filesChanged = true;
		addFileView(f);
	}
	
	private void addFileView(UploadedFileProxy file) 
	{
		int rows = filesTable.getRowCount();
		filesTable.insertRow(rows);
		filesTable.insertCell(rows, 0);
		FileEditView view = new FileEditView();
		view.setClientFactory(cf);
		view.setUploadedFile(file);
		view.setParent(this);
		filesTable.setWidget(rows, 0, view);
	}
	
	private void refreshFiles() 
	{
		filesTable.removeAllRows();
		for (UploadedFileProxy f : files) { addFileView(f); }
	}
	
	public void removeFile(UploadedFileProxy file) 
	{
		int index = files.lastIndexOf(file);
		
		if (index == -1)
			return;
		
		filesTable.removeRow(index);
		files.remove(file);
		newFiles.remove(file);
	}
}