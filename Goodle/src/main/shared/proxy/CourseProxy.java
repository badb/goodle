package main.shared.proxy;


import java.util.List;

import main.server.domain.Course;
import main.server.domain.Module;
import main.shared.RegMethod;

import com.google.web.bindery.requestfactory.shared.EntityProxy;
import com.google.web.bindery.requestfactory.shared.ProxyFor;

@ProxyFor(Course.class)
public interface CourseProxy extends EntityProxy 
{
	Long getId();
	String getName();
	String getTerm();
	String getDesc();
	RegMethod getRegMethod();
	String getPassword();
	List<String> getModuleIds();
	List<ModuleProxy> getModules();
	void setName(String name);
	void setTerm(String term);
	void setDesc(String desc);
	void setRegMethod(RegMethod regMethod);
	void setPassword(String password);

}
