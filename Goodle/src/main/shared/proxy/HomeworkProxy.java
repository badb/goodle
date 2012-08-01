package main.shared.proxy;

import java.util.Date;
import java.util.List;

import main.server.domain.Homework;

import com.google.web.bindery.requestfactory.shared.ProxyFor;

@ProxyFor(Homework.class)
public interface HomeworkProxy extends MaterialProxy
{
	String getText();
	Date getDeadline();
//	List<Long> getSolutions();
	boolean getIsVisible();
	
	void setText(String text);
	void setDeadline(Date deadline);
	void setIsVisible(boolean isVisible);
}
