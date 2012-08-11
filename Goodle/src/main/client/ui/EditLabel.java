package main.client.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.FocusEvent;
import com.google.gwt.event.dom.client.FocusHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.event.logical.shared.HasValueChangeHandlers;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public class EditLabel extends Composite implements HasValue<String>, HasValueChangeHandlers <String> {

	private static EditLabelUiBinder uiBinder = GWT.create(EditLabelUiBinder.class);

	interface EditLabelUiBinder extends UiBinder<Widget, EditLabel> { }

	@UiField HTML label;
	@UiField ResizableTextArea textArea;
	@UiField TextBox textBox;
	
	@UiField Button save;
	@UiField Button cancel;
	
	private String text;
	public String getText() { return text; }
	public void setText(String text) { setValue(text); }
	
	private int maxLength = 1024;
	public int getMaxLength() { return maxLength; }
	public void setMaxLength(int maxLength) 
	{ 
		this.maxLength = maxLength; 
		textBox.setMaxLength(maxLength);
		textArea.setMaxLength(maxLength);
	}
	
	private boolean wordWrapped = false;
	public boolean isWordWrapped() { return wordWrapped; }
	public void setWordWrapped(boolean wordWrapped) 
	{ 
		if (this.wordWrapped != wordWrapped && isEdited)
		{
			if (wordWrapped) 
			{
				textArea.setText(textBox.getText());
				textBox.setVisible(false);
				textArea.setVisible(true);
			}
			else 
			{
				textBox.setText(textArea.getText());
				textBox.setVisible(true);
				textArea.setVisible(false);
			}
		} 
		this.wordWrapped = wordWrapped;
	}
	
	private boolean isEdited = false;
	
	private boolean editable = true;
	public boolean isEditable() { return editable; }
	public void setEditable(boolean editable) { this.editable = editable; }
	
	public EditLabel() 
	{
		initWidget(uiBinder.createAndBindUi(this));
		
		// Handler registration
		
		label.addClickHandler(new ClickHandler()
		{
			@Override public void onClick(ClickEvent event) { edit(); }
		});
		
		FocusHandler focus = new FocusHandler()
		{
			@Override public void onFocus(FocusEvent event) { edit(); }
		};
		
		textBox.addFocusHandler(focus);
		textArea.addFocusHandler(focus);
		
		textBox.addKeyPressHandler(new KeyPressHandler() 
		{	 
			@Override public void onKeyPress(KeyPressEvent event) 
			{
				int keyCode = event.getUnicodeCharCode();
			    if (keyCode == 0) // To make it work on Firefox
			    {
			    	keyCode = event.getNativeEvent().getKeyCode();
			    }
			    
				if (keyCode == KeyCodes.KEY_ENTER) 
					acceptEdition();
				else if (keyCode == KeyCodes.KEY_ESCAPE) // Still doesn't work on Chrome
					cancelEdition();
			}
		});
	}
	
	private void acceptEdition()
	{
		if (!isEdited) return;
		
		isEdited = false;
		String value;
		if (wordWrapped)
		{
			value = textArea.getText();
			textArea.setVisible(false);
		}
		else
		{
			value = textBox.getText();
			textBox.setVisible(false);
		}	
		cancel.setVisible(false);
		save.setVisible(false);
		setValue(value, true);
		label.setVisible(true);
	}
	
	private void cancelEdition()
	{
		if (!isEdited) return; 
		
		isEdited = false;
		if (wordWrapped) 
			textArea.setVisible(false);
		else 
			textBox.setVisible(false);
		cancel.setVisible(false);
		save.setVisible(false);
		setValue(text);
		label.setVisible(true);
	}
	
	private void edit()
	{
		if (!editable || isEdited) return; 
		
		isEdited = true;
		label.setVisible(false);
		if (wordWrapped) 
		{
			textArea.setVisible(true);
			textArea.setFocus(true);
		}
		else 
		{
			textBox.setVisible(true);
			textBox.setFocus(true);
		}
		cancel.setVisible(true);
		save.setVisible(true);
	}
	
	@UiHandler("save")
	public void onSaveButtonClick(ClickEvent event) { acceptEdition(); }

	@UiHandler("cancel")
	public void onCancelButtonClick(ClickEvent event) { cancelEdition(); }
	
	@Override
	public HandlerRegistration addValueChangeHandler(ValueChangeHandler<String> handler) 
	{
		return addHandler(handler, ValueChangeEvent.getType());
	}

	@Override
	public String getValue() { return label.getText(); }

	@Override
	public void setValue(String text) 
	{ 
		SafeHtml escaped = new SafeHtmlBuilder().appendEscapedLines(text).toSafeHtml();
		
		this.text = text;
		label.setHTML(escaped);
		textArea.setText(text);
		textBox.setText(text);
		
		if (!isEdited && text.equals(""))
		{
			label.setVisible(false);
			if (wordWrapped) textArea.setVisible(true);
			else textBox.setVisible(true);
		}
	}

	@Override
	public void setValue(String text, boolean fireEvents) 
	{
		if	(fireEvents) ValueChangeEvent.fireIfNotEqual(this, getValue(), text);
		this.setValue(text);
	}

}
