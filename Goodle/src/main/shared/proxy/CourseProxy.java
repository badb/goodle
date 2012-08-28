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
	JoinMethod getJoinMethod();
	String getKey();
	String getCalendar();
	Set<Long> getCoordinators();
	Set<Long> getMembers();
	List<MessageProxy> getMessages();
	List<String> getDescription();
	List<String> getBibliography();
	
	void setName(String name);
	void setTerm(String term);
	void setJoinMethod(JoinMethod joinMethod);
	void setKey(String key);
	void setCalendar(String link);
	void setDescription(List<String> description);
	void setBibliography(List<String> bibliography);
}
