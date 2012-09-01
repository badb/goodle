package main.client.util;

import java.util.Date;

import main.client.ClientFactory;
import main.shared.proxy.HomeworkProxy;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.cell.client.ValueUpdater;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.safehtml.client.SafeHtmlTemplates;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;;

public class HomeworkCell extends AbstractCell<HomeworkProxy> {

	interface Templates extends SafeHtmlTemplates {
		@SafeHtmlTemplates.Template
        (
        		"<div class = \"homeworkCell\" key = \"{0}\">" +
        				"<div class = \"homeworkCellName\">{1} ({2})</div>" +
        		"</div>"
        )
        SafeHtml cell(String key, String name, String term);
      }
      
	private static Templates templates = GWT.create(Templates.class);
		
	public HomeworkCell () {
		super("click");
	}
	
	@Override
	public void onBrowserEvent(Context context, Element parent, HomeworkProxy value, NativeEvent event, ValueUpdater<HomeworkProxy> valueUpdater) {
		 if ("click".equals(event.getType())) { valueUpdater.update(value); }
	}
	
	@Override
	public void render(com.google.gwt.cell.client.Cell.Context context,
			HomeworkProxy value, SafeHtmlBuilder sb) {
        if (value == null) return;
        String id = value.getId().toString();
        String name = value.getTitle();
        Date d = value.getDeadline();
        String term = "brak terminu";
        if (d != null)
        	term = DateTimeFormat.getFormat(ClientFactory.dateFormat).format(d);
        SafeHtml rendered = templates.cell(id, name, term);
        sb.append(rendered);
	}

}
