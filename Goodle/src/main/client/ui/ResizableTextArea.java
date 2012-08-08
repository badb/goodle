package main.client.ui;

import com.google.gwt.user.client.ui.TextArea;

public class ResizableTextArea extends TextArea {

	private final int MIN_LINES = 3;
	
	public void resize() {
		setVisibleLines(MIN_LINES);

        if (getElement().getScrollHeight() >= getElement().getOffsetHeight()) {
        	float newLines = (getElement().getScrollHeight() - getElement().getOffsetHeight() + 2)/16;
        	setVisibleLines(getVisibleLines() + Math.round(newLines) );
        }
	}
	
}
