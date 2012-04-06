package main.client.utils;

import com.google.appengine.api.datastore.Key;
import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.core.client.GWT;
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
	    				  "<div class = \"courseCellName\">{1}</div>" +
	    				  "<div class = \"courseCellDesc\">{2}</div>" +
	    		  "</div>"
	      )
	      SafeHtml cell(Key key, String title, String desc);
	}
	
    private static Templates templates = GWT.create(Templates.class);
    
	@Override
	public void render(Context context, CourseShortDesc value, SafeHtmlBuilder sb) 
	{
		if (value == null) return;
		Key key = value.getKey();
		String name = value.getName();
		String desc = value.getDesc();
		SafeHtml rendered = templates.cell(key, name, desc);
		sb.append(rendered);
	}
}