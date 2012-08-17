package main.shared.proxy;

import java.util.Date;
import java.util.List;

import main.server.domain.Homework;

import com.google.appengine.api.datastore.Key;
import com.google.web.bindery.requestfactory.shared.ProxyFor;

@ProxyFor(Homework.class)
public interface HomeworkProxy extends MaterialProxy
{
	String getText();
	Date getDeadline();
	List<UploadedFileProxy> getSolutions();
	boolean getIsVisible();
	
	void setText(String text);
	void setDeadline(Date deadline);
	void setMaterials(List<UploadedFileProxy> materials);
	void setIsVisible(boolean isVisible);
}
