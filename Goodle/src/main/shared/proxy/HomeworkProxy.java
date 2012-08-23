package main.shared.proxy;

import java.util.Date;
import java.util.List;

import main.server.domain.Homework;

import com.google.appengine.api.datastore.Key;
import com.google.web.bindery.requestfactory.shared.ProxyFor;

@ProxyFor(Homework.class)
public interface HomeworkProxy extends ModuleProxy
{
	Date getDeadline();
	void setDeadline(Date deadline);
}
