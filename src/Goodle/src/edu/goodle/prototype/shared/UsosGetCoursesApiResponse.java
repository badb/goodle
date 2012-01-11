package edu.goodle.prototype.shared;

import java.util.List;

public class UsosGetCoursesApiResponse extends UsosApiResponse {
	private List<String> courses;

	public UsosGetCoursesApiResponse(UsosApiResponseStatus status) {
		super(status);
	}

	public List<String> getCourses() {
		return courses;
	}

	public void setCourses(List<String> courses) {
		this.courses = courses;
	}

}
