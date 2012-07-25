package main.client.resources;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.resources.client.CssResource.NotStrict;

public interface GoodleResources extends ClientBundle {
	public static final GoodleResources INSTANCE = GWT.create(GoodleResources.class);
	@NotStrict
	@Source("Goodle.css")
	public CssResource css();
}
