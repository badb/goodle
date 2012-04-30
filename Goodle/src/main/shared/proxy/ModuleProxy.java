package main.shared.proxy;

import main.server.domain.Module;

import com.google.web.bindery.requestfactory.shared.EntityProxy;
import com.google.web.bindery.requestfactory.shared.ProxyFor;

@ProxyFor(Module.class)
public interface ModuleProxy extends EntityProxy 
{
	Long getId();
	String getTitle();
	String getDesc();
	void setTitle(String title);
	void setDesc(String desc);	
}
