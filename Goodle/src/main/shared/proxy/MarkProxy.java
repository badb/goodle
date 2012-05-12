package main.shared.proxy;

import main.server.domain.Mark;

import com.google.web.bindery.requestfactory.shared.ProxyFor;
import com.google.web.bindery.requestfactory.shared.ValueProxy;

@ProxyFor(Mark.class)
public interface MarkProxy extends ValueProxy 
{
	int getVal();
	GoodleUserProxy getTeacher();
	String getComment();
	
	void setVal(int val);
	void setTeacher(GoodleUserProxy teacher);
	void setComment(String comment);
}
