package main.client.ui;

import main.client.ClientFactory;
import main.shared.proxy.CourseProxy;

import com.google.gwt.user.client.ui.Composite;

public abstract class AbstractCourseView extends Composite
{	
	protected CourseView parent;
	public void setParent(CourseView parent) { this.parent = parent; }
	
	protected ClientFactory cf;
	public void setClientFactory(ClientFactory cf) { this.cf = cf; }
	
	protected CourseProxy course;
	public void setCourse(CourseProxy course)
	{
		owner = null;
		if (course != null && !course.equals(this.course)) 
		{
			this.course = course;
			if (course != null) onCourseSet();
		}
	}
	
	protected Boolean owner = null;
	public boolean isCurrUserOwner() 
	{
		if (course == null) return false;
		
		if (owner == null)
			owner = course.getCoordinators().contains(cf.getCurrentUser().getId());
		
		return owner;
	}
	
	protected Boolean member = null;
	public boolean isCurrUserMember() 
	{
		if (course == null) return false;
		
		if (member == null)
			member = course.getMembers().contains(cf.getCurrentUser().getId());
		
		return member;
	}
	
	public void onCourseSet() { }
}
