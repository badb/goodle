package main.client.util;
import main.shared.ModuleProxy;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.cell.client.ValueUpdater;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.safehtml.client.SafeHtmlTemplates;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;

public class ModuleCell extends AbstractCell<ModuleProxy>
{
        interface Templates extends SafeHtmlTemplates
        {
              @SafeHtmlTemplates.Template
              (
                          "<div class = \"moduleCell\" key = \"{0}\">" +
                                          "<div class = \"moduleNumber\">{0}</div>" +
                                          "<div class = \"moduleDesc\">{1}</div>" +
                          "</div>"
              )
              SafeHtml cell(String key, String desc);
        }
        
    private static Templates templates = GWT.create(Templates.class);
    
    public ModuleCell() { super("click"); } 
    

    public void onBrowserEvent
    (
        Context context, 
        Element parent, 
        ModuleProxy value, 
        NativeEvent event, 
        ValueUpdater<ModuleProxy> valueUpdater
    ) 
    {
        if ("click".equals(event.getType())) { valueUpdater.update(value); }
    }
    
        @Override
        public void render(Context context, ModuleProxy value, SafeHtmlBuilder sb) 
        {
                if (value == null) return;
                String id = value.getId().toString();
                String desc = value.getDescription();
                SafeHtml rendered = templates.cell(id, desc);
                sb.append(rendered);
        }
}