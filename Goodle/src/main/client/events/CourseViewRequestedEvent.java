package main.client.events;

import main.shared.models.Course;

import com.google.gwt.event.shared.GwtEvent;

public class CourseViewRequestedEvent extends GwtEvent<CourseViewRequestedEventHandler>
{
	public static Type<CourseViewRequestedEventHandler> TYPE = new Type<CourseViewRequestedEventHandler>();
	private Course course;
	
	public CourseViewRequestedEvent(Course course) { this.course = course; }
	
	@Override
	public Type<CourseViewRequestedEventHandler> getAssociatedType() { return TYPE ; }

	@Override
	protected void dispatch(CourseViewRequestedEventHandler handler) { handler.onEvent(this); }
	
	public Course getCourse() { return course; }

}
