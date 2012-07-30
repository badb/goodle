package main.shared.proxy;

import java.util.Date;

import main.server.domain.UploadedFile;

import com.google.web.bindery.requestfactory.shared.EntityProxy;
import com.google.web.bindery.requestfactory.shared.ExtraTypes;
import com.google.web.bindery.requestfactory.shared.ProxyFor;

@ProxyFor(UploadedFile.class)
public interface UploadedFileProxy extends EntityProxy
{
	String getId();
	String getName();
	GoodleUserProxy getAuthor();
	Date getCreated();
	Date getModified();
	String getUrl();
	
	void setName(String name);
	void setAuthor(GoodleUserProxy author);
}
