package main.client.util;

import main.shared.GoodleUserProxy;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.cell.client.ValueUpdater;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.safehtml.client.SafeHtmlTemplates;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;

public class UserCell extends AbstractCell<GoodleUserProxy>
{
        interface Templates extends SafeHtmlTemplates
        {
              @SafeHtmlTemplates.Template
              (
                          "<div class = \"UserCell\" key = \"{0}\">" +
                                          "<div class = \"GoodleUserCellName\">{1} {2}</div>" +         
                          "</div>"
              )
              SafeHtml cell(String key, String firstName, String lastName);
        }
        
    private static Templates templates = GWT.create(Templates.class);
    
    public UserCell() { super("click"); } 
    
    @Override
    public void onBrowserEvent
    (
        Context context, 
        Element parent, 
        GoodleUserProxy value, 
        NativeEvent event, 
        ValueUpdater<GoodleUserProxy> valueUpdater
    ) 
    {
        if ("click".equals(event.getType())) { valueUpdater.update(value); }
    }
    
        @Override
        public void render(Context context, GoodleUserProxy value, SafeHtmlBuilder sb) 
        {
                if (value == null) return;
                String id = value.getId().toString();
                String firstName = value.getFirstName();
                String lastName = value.getLastName();
                SafeHtml rendered = templates.cell(id, firstName, lastName);
                sb.append(rendered);
        }
}