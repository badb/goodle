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
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.Widget;
import main.client.ui.ResizableTextArea;

public class EditLabel extends Composite implements HasValue<String>, HasValueChangeHandlers <String> {

	private static EditLabelUiBinder uiBinder = GWT.create(EditLabelUiBinder.class);

	interface EditLabelUiBinder extends UiBinder<Widget, EditLabel> {
	}

	@UiField ResizableTextArea textArea;
	@UiField Button saveButton;

	private boolean enabled = true;
	@UiField Button cancelButton;
	
	private String text;
	private final String MAX_LENGTH = "300";
	
	public EditLabel() {
		initWidget(uiBinder.createAndBindUi(this));
		//TODO ustawić własciwą długość
		textArea.getElement().setAttribute("maxlength", MAX_LENGTH);
		text = getValue();
		textArea.resize();
	}
	
	@UiHandler("saveButton")
	public void onSaveButtonClick(ClickEvent event) {
		setValue(textArea.getText(), true);
		textArea.resize();
	}

	@UiHandler("cancelButton")
	public void onCancelButtonClick(ClickEvent event) {
		setValue(text, false);
	}
	
	public void onLoad() {
		textArea.resize();
	}
	
	@Override
	public HandlerRegistration addValueChangeHandler(ValueChangeHandler<String> handler) {
		return addHandler(handler, ValueChangeEvent.getType());
	}

	@Override
	public String getValue() {
		return text;
	}

	@Override
	public void setValue(String text) {
		textArea.setText(text);
		this.text = text;
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
