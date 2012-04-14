package edu.goodle.prototype.shared;

import java.io.Serializable;

public class LongCourseDescription extends ShortCourseDescription implements
		Serializable {
    public String getHomepage_url() {
		return homepage_url;
	}
	public void setHomepage_url(String homepage_url) {
		this.homepage_url = homepage_url;
	}
	public String getProfile_url() {
		return profile_url;
	}
	public void setProfile_url(String profile_url) {
		this.profile_url = profile_url;
	}
	public Boolean getIs_currently_conducted() {
		return is_currently_conducted;
	}
	public void setIs_currently_conducted(Boolean is_currently_conducted) {
		this.is_currently_conducted = is_currently_conducted;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getBibliography() {
		return bibliography;
	}
	public void setBibliography(String bibliography) {
		this.bibliography = bibliography;
	}
	private String homepage_url = null;
	private String profile_url = null;
    private Boolean is_currently_conducted = null;
    private String description = null;
    private String bibliography = null;
}
