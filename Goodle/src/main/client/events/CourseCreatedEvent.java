package main.client.events;

import main.shared.models.Course;

import com.google.gwt.event.shared.GwtEvent;

public class CourseCreatedEvent extends GwtEvent<CourseCreatedEventHandler> {

	public static Type<CourseCreatedEventHandler> TYPE = new Type<CourseCreatedEventHandler>();
	private Course course;
	
	public CourseCreatedEvent(Course course) { this.course = course; }

	@Override
	public Type<CourseCreatedEventHandler> getAssociatedType() { return TYPE; }

	@Override
	protected void dispatch(CourseCreatedEventHandler handler) { handler.onEvent(this); }

	public Course getCourse() { return course; }
	
	

}
