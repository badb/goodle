package main.shared.proxy;

import java.util.List;
import java.util.Set;

import main.server.domain.GoodleUser;

import com.google.web.bindery.requestfactory.shared.EntityProxy;
import com.google.web.bindery.requestfactory.shared.ProxyFor;

@ProxyFor(GoodleUser.class)
public interface GoodleUserProxy extends EntityProxy
{
	Long getId();
	String getLogin();
	String getFirstName();
	String getLastName();
	String getEmail();
	Set<Long> getCoursesLed();
	Set<Long> getCoursesAttended();
	List<MessageProxy> getMessages();
	Set<String> getFlags();
	
	void setLogin(String login);
	void setFirstName(String firstName);
	void setLastName(String lastName);
	void setEmail(String email);
}
