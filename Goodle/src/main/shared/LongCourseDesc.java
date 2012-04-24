package main.shared;

import java.io.Serializable;

@SuppressWarnings("serial")
public class LongCourseDesc extends ShortCourseDesc implements Serializable 
{
	private String homepageUrl;
	private String profileUrl;
    private Boolean isConducted;
    private String description;
    private String bibliography;
	
    public String getHomepageUrl() { return homepageUrl; }
	public void setHomepageUrl(String homepageUrl) { this.homepageUrl = homepageUrl; }
	
	public String getProfileUrl() { return profileUrl; }
	public void setProfileUrl(String profileUrl) { this.profileUrl = profileUrl; }
	
	public Boolean getIsConducted() { return isConducted; }
	public void setIsConducted(Boolean isConducted) { this.isConducted = isConducted; }
	
	public String getDesc() { return description; }
	public void setDesc(String desc) { this.description = desc; }
	
	public String getBibliography() { return bibliography; }
	public void setBibliography(String bibliography) { this.bibliography = bibliography; }
}
