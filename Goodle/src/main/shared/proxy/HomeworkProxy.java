package main.shared.proxy;

import java.util.Date;
import java.util.List;

import main.server.domain.Homework;

import com.google.web.bindery.requestfactory.shared.EntityProxy;
import com.google.web.bindery.requestfactory.shared.ProxyFor;

@ProxyFor(Homework.class)
public interface HomeworkProxy extends ModuleProxy
{
	
	
	Date getDeadline();
	Long getCourse();
	
	void setDeadline(Date deadline);
	void setCourse(Long course);
}
