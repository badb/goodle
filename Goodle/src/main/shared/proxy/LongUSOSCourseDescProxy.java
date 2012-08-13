package main.shared.proxy;

import main.shared.LongCourseDesc;

import com.google.web.bindery.requestfactory.shared.ProxyFor;
import com.google.web.bindery.requestfactory.shared.ValueProxy;

@ProxyFor(LongCourseDesc.class)
public interface LongUSOSCourseDescProxy extends ValueProxy {
	public String getId();
	public String getName();
	public String getHomepageUrl();
	public String getProfileUrl();	
	public Boolean getIsConducted();	
	public String getDesc();	
	public String getBibliography();


}
