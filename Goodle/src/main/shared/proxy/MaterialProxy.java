package main.shared.proxy;

import java.util.Date;

import main.server.domain.Material;

import com.google.web.bindery.requestfactory.shared.EntityProxy;
import com.google.web.bindery.requestfactory.shared.ProxyFor;

@ProxyFor(Material.class)
public interface MaterialProxy extends EntityProxy
{
	Long getId();
	String getName();
	Long getAuthor();
	Date getCreated();
	Date getModified();
	
	void setName(String name);
	void setAuthor(Long author);
}
