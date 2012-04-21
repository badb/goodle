package main.shared;


import java.util.List;

import main.server.domain.Course;
import main.server.domain.Module;

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
	List<String> getModuleIds();
	List<ModuleProxy> getModules();
}
