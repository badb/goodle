package main.shared.proxy;


import java.util.List;
import java.util.Set;

import main.server.domain.Course;
import main.shared.JoinMethod;

import com.google.web.bindery.requestfactory.shared.EntityProxy;
import com.google.web.bindery.requestfactory.shared.ProxyFor;

@ProxyFor(Course.class)
public interface CourseProxy extends EntityProxy 
{
	Long getId();
	String getName();
	String getTerm();
	String getDescription();
	String getBibliography();
	JoinMethod getJoinMethod();
	String getKey();
	String getCalendar();
	Set<Long> getTeachers();
	Set<Long> getMembers();
	List<Long> getModules();
	List<MessageProxy> getMessages();
	
	void setName(String name);
	void setTerm(String term);
	void setDescription(String description);
	void setBibliography(String bibliography);
	void setJoinMethod(JoinMethod joinMethod);
	void setKey(String key);
	void setCalendar(String link);
}
