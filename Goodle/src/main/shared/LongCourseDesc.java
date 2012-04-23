package main.shared;

import java.io.Serializable;

@SuppressWarnings("serial")
public class LongCourseDesc extends ShortCourseDesc implements Serializable 
{
	private String homepageUrl = null;
	private String profileUrl = null;
    private Boolean isConducted = null;
    private String description = null;
    private String bibliography = null;
	
    public String getHomepageUrl() { return homepageUrl; }
	public void setHomepageUrl(String homepageUrl) { this.homepageUrl = homepageUrl; }
	
	public String getProfileUrl() { return profileUrl; }
	public void setProfileUrl(String profileUrl) { this.profileUrl = profileUrl; }
	
	public Boolean getisConducted() { return isConducted; }
	public void setisConducted(Boolean isConducted) { this.isConducted = isConducted; }
	
	public String getdesc() { return description; }
	public void setdesc(String desc) { this.description = desc; }
	
	public String getBibliography() { return bibliography; }
	public void setBibliography(String bibliography) { this.bibliography = bibliography; }
}
