package main.shared;


import main.server.domain.Course;

import com.google.web.bindery.requestfactory.shared.EntityProxy;
import com.google.web.bindery.requestfactory.shared.ProxyFor;

@ProxyFor(Course.class)
public interface CourseProxy extends EntityProxy 
{
	Long getId();
	String getName();
	String getTerm();
	String getDesc();
	//List<String> getModuleIds();
	void setName(String name);
	void setTerm(String term);
	void setDesc(String desc);
}
