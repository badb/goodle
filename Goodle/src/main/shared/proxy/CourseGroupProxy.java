package main.shared.proxy;

import java.util.List;
import java.util.Set;

import main.server.domain.CourseGroup;
import main.shared.CourseGroupType;

import com.google.web.bindery.requestfactory.shared.EntityProxy;
import com.google.web.bindery.requestfactory.shared.ProxyFor;

@ProxyFor(CourseGroup.class)
public interface CourseGroupProxy extends EntityProxy 
{
	Long getId();
	String getSubname();
	String getDescription();
	CourseGroupType getGroupType();
	String getCalendar();
	Set<Long> getTeachers();
	Set<Long> getMembers();
	List<Long> getModules();
	List<MessageProxy> getMessages();
	
	void setSubname(String name);
	void setDescription(String description);
	void setGroupType(CourseGroupType groupType);
	void setCalendar(String link);
}
