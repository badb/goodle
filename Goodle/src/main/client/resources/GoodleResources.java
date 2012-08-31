package main.client.resources;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.resources.client.CssResource.NotStrict;
import com.google.gwt.resources.client.ImageResource;

public interface GoodleResources extends ClientBundle 
{
	public static final GoodleResources INSTANCE = GWT.create(GoodleResources.class);
	@NotStrict
	@Source("Goodle.css")
	public CssResource css();
	
	// Various images
	@Source("btn_download.png") ImageResource downloadIcon();
	
	@Source("btn_preview.png") ImageResource previewIcon();
	
	@Source("btn_remove.png") ImageResource removeIcon();
	
}
