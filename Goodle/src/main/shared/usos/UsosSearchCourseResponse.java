package main.shared.usos;

import java.util.Vector;

import main.shared.LongCourseDesc;

@SuppressWarnings("serial")
public class UsosSearchCourseResponse extends UsosResponse 
{
	private Vector<LongCourseDesc> courses;
	
	public UsosSearchCourseResponse(UsosResponseStatus status) { super(status); }

	public Vector<LongCourseDesc> getCourses() { return courses; }

	public void setCourses(Vector<LongCourseDesc> courses) { this.courses = courses; }
}
