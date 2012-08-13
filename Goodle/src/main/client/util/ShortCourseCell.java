package main.client.util;

import main.shared.proxy.ShortUSOSCourseDescProxy;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.cell.client.ValueUpdater;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;

public class ShortCourseCell extends AbstractCell<ShortUSOSCourseDescProxy> {
	public ShortCourseCell(){
		super("click");
	}

	@Override
	public void render(com.google.gwt.cell.client.Cell.Context context,
			ShortUSOSCourseDescProxy value, SafeHtmlBuilder sb) {
		if (value == null) return;
        String id = value.getId();
        String name = value.getName();
        sb.appendEscaped(id).appendHtmlConstant(" ").appendEscaped(name);		
	}
	
	@Override
	public void onBrowserEvent
    (
        Context context, 
        Element parent, 
        ShortUSOSCourseDescProxy value, 
        NativeEvent event, 
        ValueUpdater<ShortUSOSCourseDescProxy> valueUpdater
    ) 
    {
        if ("click".equals(event.getType())) { valueUpdater.update(value); }
    }

}
