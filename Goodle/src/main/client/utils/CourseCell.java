package main.client.utils;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.cell.client.ValueUpdater;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.safehtml.client.SafeHtmlTemplates;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
	
public class CourseCell extends AbstractCell<CourseShortDesc>
{
	interface Templates extends SafeHtmlTemplates
	{
	      @SafeHtmlTemplates.Template
	      (
	    		  "<div class = \"courseCell\" key = \"{0}\">" +
	    				  "<div class = \"courseCellName\">{1} ({2})</div>" +
	    				  "<div class = \"courseCellDesc\">{3}</div>" +
	    		  "</div>"
	      )
	      SafeHtml cell(String key, String name, String term, String desc);
	}
	
    private static Templates templates = GWT.create(Templates.class);
    
    public CourseCell() { super("click"); } 
    
    @Override
    public void onBrowserEvent
    (
    	Context context, 
    	Element parent, 
    	CourseShortDesc value, 
    	NativeEvent event, 
    	ValueUpdater<CourseShortDesc> valueUpdater
    ) 
    {
    	if ("click".equals(event.getType()))
    	{
    		
    	}
    }
    
	@Override
	public void render(Context context, CourseShortDesc value, SafeHtmlBuilder sb) 
	{
		if (value == null) return;
		String key = value.getKey().toString();
		String name = value.getName();
		String term = value.getTerm();
		String desc = value.getDesc();
		SafeHtml rendered = templates.cell(key, name, term, desc);
		sb.append(rendered);
	}
}