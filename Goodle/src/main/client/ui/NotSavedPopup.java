package main.client.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.Widget;

public class NotSavedPopup extends PopupPanel {

	private static NotSavedPopupUiBinder uiBinder = GWT.create(NotSavedPopupUiBinder.class);

	private boolean save;
	
	//@UiBinder Button savePopupButton;
	
	interface NotSavedPopupUiBinder extends UiBinder<Widget, NotSavedPopup> {
	}

	public NotSavedPopup() {
		 super(true); 
	     add(uiBinder.createAndBindUi(this)); 
	}


}
