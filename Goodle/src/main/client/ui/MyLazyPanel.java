package main.client.ui;

import com.google.gwt.user.client.ui.LazyPanel;
import com.google.gwt.user.client.ui.ProvidesResize;
import com.google.gwt.user.client.ui.RequiresResize;
import com.google.gwt.user.client.ui.SimpleLayoutPanel;
import com.google.gwt.user.client.ui.Widget;

public class MyLazyPanel extends LazyPanel implements ProvidesResize, RequiresResize {

	public void onResize() { 
		   Widget widget = getWidget(); 
		   if (widget instanceof RequiresResize) { 
		      ((RequiresResize) widget).onResize(); 
		   } 
		}

	@Override
	protected Widget createWidget() {
		return new SimpleLayoutPanel(); 
	}
}
