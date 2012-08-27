package main.shared.proxy;

import java.util.Date;
import java.util.List;

import main.server.domain.Homework;

import com.google.web.bindery.requestfactory.shared.EntityProxy;
import com.google.web.bindery.requestfactory.shared.ProxyFor;

@ProxyFor(Homework.class)
public interface HomeworkProxy extends ModuleProxy
{
	
	Long getId();
	String getTitle();
	Long getAuthor();
	String getText();
	boolean getIsVisible();
	//List<UploadedFileProxy> getFiles();
	
	void setTitle(String title);
	void setAuthor(Long author);
	void setText(String text);
	void setIsVisible(boolean isVisible);
	//void setFiles(List<UploadedFileProxy> files);
	
	Date getDeadline();
	Long getCourse();
	
	void setDeadline(Date deadline);
	void setCourse(Long course);
}
