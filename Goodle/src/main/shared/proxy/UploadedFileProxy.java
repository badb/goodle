package main.shared.proxy;

import java.util.Date;

import main.server.domain.UploadedFile;

import com.google.web.bindery.requestfactory.shared.ProxyFor;
import com.google.web.bindery.requestfactory.shared.ValueProxy;

@ProxyFor(UploadedFile.class)
public interface UploadedFileProxy extends ValueProxy
{
	Long getId();
	String getName(); 
	Long getAuthor();
	String getAuthorName();
	Date getUploaded();
	String getUrl();
	
	void setName(String name);
	void setAuthor(Long id);
	void setUrl(String url);
}
