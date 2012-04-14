package edu.goodle.prototype.shared;

import java.util.Vector;

public class UsosSearchCourseResponse extends UsosApiResponse {
	private Vector<LongCourseDescription> courses;
	
	public UsosSearchCourseResponse(UsosApiResponseStatus status) {
		super(status);
	}

	public Vector<LongCourseDescription> getCourses() {
		return courses;
	}

	public void setCourses(Vector<LongCourseDescription> courses) {
		this.courses = courses;
	}
	

}
