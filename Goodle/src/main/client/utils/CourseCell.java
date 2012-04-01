package main.client.utils;

import main.shared.models.Course;

import com.google.appengine.api.datastore.Text;
import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.safehtml.client.SafeHtmlTemplates;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
	
public class CourseCell extends AbstractCell<Course>
{
	interface Templates extends SafeHtmlTemplates
	{
	      @SafeHtmlTemplates.Template
	      (
	    		  "<div>" +
	    		  "<h1>{0}</h1>" +
	    		  "<p>{1}</p>" +
	    		  "</div>"
	      )
	      SafeHtml cell(String title, Text desc);
	}
	
    private static Templates templates = GWT.create(Templates.class);
    
	@Override
	public void render(Context context, Course value, SafeHtmlBuilder sb) 
	{
		if (value == null) return;
		String name = value.getName();
		Text desc = value.getDesc();
		SafeHtml rendered = templates.cell(name, desc);
		sb.append(rendered);
	}
}