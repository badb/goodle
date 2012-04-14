package main.shared;

import main.server.domain.GoodleUser;

import com.google.web.bindery.requestfactory.shared.EntityProxy;
import com.google.web.bindery.requestfactory.shared.ProxyFor;

@ProxyFor(GoodleUser.class)
public interface GoodleUserProxy extends EntityProxy
{
	Long getId();
	String getFirstName();
	String getLastName();
	void setFirstName(String firstName);
	void setLastName(String lastName);
}
