package main.client.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.logical.shared.HasValueChangeHandlers;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DeckLayoutPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RichTextArea;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.Widget;

public class EditLabel extends Composite implements HasValue<String>, HasValueChangeHandlers <String> {

	private static EditLabelUiBinder uiBinder = GWT.create(EditLabelUiBinder.class);

	interface EditLabelUiBinder extends UiBinder<Widget, EditLabel> {
	}
	

	@UiField HTML label;
	@UiField DeckLayoutPanel deckPanel;
	@UiField TextArea textArea;
	@UiField Button saveButton;

	private boolean enabled = true;
	
	public EditLabel() {
		initWidget(uiBinder.createAndBindUi(this));
		deckPanel.showWidget(0);
		label.getElement().getStyle().setProperty("whiteSpace", "pre");
	}
	

	@UiHandler("label")
	void onLabelClick(ClickEvent e) {
		if (enabled) {
			textArea.setText(getValue());
			deckPanel.showWidget(1);
		}
	}
	
	
	@UiHandler("saveButton")
	public void onSaveButtonClick(ClickEvent event) {
		if(deckPanel.getVisibleWidgetIndex() == 0) return;
		setValue(textArea.getText(), true);
		deckPanel.showWidget(0);
	}

	
	@Override
	public HandlerRegistration addValueChangeHandler(ValueChangeHandler<String> handler) {
		return addHandler(handler, ValueChangeEvent.getType());
		//return textArea.addValueChangeHandler(handler);
	}

	@Override
	public String getValue() {
		return label.getText();
	}

	@Override
	public void setValue(String text) {
		label.setHTML(text);
		textArea.setText(text);
		
	}

	@Override
	public void setValue(String text, boolean fireEvents) {
		if(fireEvents & enabled) ValueChangeEvent.fireIfNotEqual(this, getValue(), text);
		this.setValue(text);
	}

	public void setText(String text) {
		setValue(text, true);
	}
	
	public String getText() {
		return getValue();
	}
	
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	

}
