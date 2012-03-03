package edu.goodle.prototype.shared;

import java.io.Serializable;
import java.util.Vector;

public class UsosGetCoursesApiResponse extends UsosApiResponse implements Serializable{
	private Vector<ShortCourseDescription> courses;
	// ma zawierać nazwy i identyfikatory kursów
	protected UsosGetCoursesApiResponse(){}

	public UsosGetCoursesApiResponse(UsosApiResponseStatus status) {
		super(status);
	}

	public Vector<ShortCourseDescription> getCourses() {
		return courses;
	}

	public void setCourses(Vector<ShortCourseDescription> courses) {
		this.courses = courses;
	}

}
