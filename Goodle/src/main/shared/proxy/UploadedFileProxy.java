package main.shared.proxy;

import java.util.Date;

import main.server.domain.UploadedFile;

import com.google.web.bindery.requestfactory.shared.EntityProxy;
import com.google.web.bindery.requestfactory.shared.ProxyFor;
import com.google.web.bindery.requestfactory.shared.ValueProxy;

@ProxyFor(UploadedFile.class)
public interface UploadedFileProxy extends ValueProxy
{
	//Long getId();
	String getName();
	 
	//GoodleUserProxy getAuthor();
	/*Date getCreated();
	Date getModified();*/
	String getUrl();
	ModuleProxy getModule();
	
	void setName(String name);
	//void setAuthor(GoodleUserProxy author);
	void setModule(ModuleProxy module);
	void setUrl(String url);
}
