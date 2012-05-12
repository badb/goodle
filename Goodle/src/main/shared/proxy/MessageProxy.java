package main.shared.proxy;

import java.util.Date;

import main.server.domain.Message;

import com.google.web.bindery.requestfactory.shared.ProxyFor;
import com.google.web.bindery.requestfactory.shared.ValueProxy;

@ProxyFor(Message.class)
public interface MessageProxy extends ValueProxy
{
	GoodleUserProxy getAuthor();
	String getText();
	Date getCreated();
	Date getModified();
	
	void setAuthor(GoodleUserProxy author);
	void setText(String text);
}
