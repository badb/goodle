package main.client.events;

import java.util.ArrayList;

import main.client.utils.CourseShortDesc;

import com.google.gwt.event.shared.GwtEvent;

public class CoursesFoundEvent extends GwtEvent<CoursesFoundEventHandler> 
{
	public static Type<CoursesFoundEventHandler> TYPE = new Type<CoursesFoundEventHandler>();
	private ArrayList<CourseShortDesc> courses;
	
	public CoursesFoundEvent(ArrayList<CourseShortDesc> courses) { this.courses = courses; }

	@Override
	public Type<CoursesFoundEventHandler> getAssociatedType() { return TYPE; }

	@Override
	protected void dispatch(CoursesFoundEventHandler handler) { handler.onEvent(this); }

	public ArrayList<CourseShortDesc> getCourses() { return courses; }
}
