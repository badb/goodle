package main.shared.usos;

import java.io.Serializable;
import java.util.Vector;

import main.shared.ShortCourseDesc;


@SuppressWarnings("serial")
public class UsosGetCoursesResponse extends UsosResponse implements Serializable
{
	private Vector<ShortCourseDesc> courses;

	protected UsosGetCoursesResponse(){}

	public UsosGetCoursesResponse(UsosResponseStatus status) { super(status); }

	public Vector<ShortCourseDesc> getCourses() { return courses; }

	public void setCourses(Vector<ShortCourseDesc> courses) { this.courses = courses; }
}
