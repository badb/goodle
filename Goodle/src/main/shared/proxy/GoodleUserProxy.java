package main.shared.proxy;

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
	String getStudentId();
	String getEmail();
	Set<Long> getCoursesLed();
	Set<Long> getCoursesAttended();
	
	void setLogin(String login);
	void setFirstName(String firstName);
	void setLastName(String lastName);
	void setStudentId(String id);
	void setEmail(String email);
}
