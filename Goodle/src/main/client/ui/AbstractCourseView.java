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
	
	private Boolean owner;
	public boolean isCurrUserOwner() 
	{
		if (course == null) return false;
		
		return (owner == null ? course.getCoordinators().contains(cf.getCurrentUser().getId()) : owner);
	}
	
	private boolean member;
	public boolean isCurrUserMember() 
	{
		if (course == null) return false;
		
		return (owner == null ? course.getMembers().contains(cf.getCurrentUser().getId()) : owner);
	}
	
	public void onCourseSet() { }
}
