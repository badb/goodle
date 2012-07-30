package main.shared.proxy;

import java.util.List;

import main.server.domain.Module;

import com.google.web.bindery.requestfactory.shared.EntityProxy;
import com.google.web.bindery.requestfactory.shared.ProxyFor;

@ProxyFor(Module.class)
public interface ModuleProxy extends EntityProxy 
{
	Long getId();
	String getTitle();
	Long getAuthor();
	String getText();
	boolean getIsVisible();
	List<MessageProxy> getComments();
	List<UploadedFileProxy> getMaterials();
	
	void setTitle(String title);
	void setAuthor(Long author);
	void setText(String text);
	void setIsVisible(boolean isVisible);
	void setMaterials(List<UploadedFileProxy> materials);
}
