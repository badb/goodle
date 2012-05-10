package main.client.util;

import main.shared.proxy.CourseProxy;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.cell.client.ValueUpdater;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.safehtml.client.SafeHtmlTemplates;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
        
public class CourseCell extends AbstractCell<CourseProxy>
{
        interface Templates extends SafeHtmlTemplates
        {
              @SafeHtmlTemplates.Template
              (
                          "<div class = \"courseCell\" key = \"{0}\">" +
                                          "<div class = \"courseCellName\">{1} ({2})</div>" +
                          "</div>"
              )
              SafeHtml cell(String key, String name, String term);
        }
        
    private static Templates templates = GWT.create(Templates.class);
    
    public CourseCell() { super("click"); } 
    
    @Override
    public void onBrowserEvent
    (
        Context context, 
        Element parent, 
        CourseProxy value, 
        NativeEvent event, 
        ValueUpdater<CourseProxy> valueUpdater
    ) 
    {
        if ("click".equals(event.getType())) { valueUpdater.update(value); }
    }
    
        @Override
        public void render(Context context, CourseProxy value, SafeHtmlBuilder sb) 
        {
                if (value == null) return;
                String id = value.getId().toString();
                String name = value.getName();
                String term = value.getTerm();
                SafeHtml rendered = templates.cell(id, name, term);
                sb.append(rendered);
        }
}