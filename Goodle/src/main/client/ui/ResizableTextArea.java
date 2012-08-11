/*
 * Based on code by kraftan and z00bs found on http://ambitz.com/2010/12/11/autogrow-textarea-with-gwt/
 */

package main.client.ui;

import com.google.gwt.dom.client.Style.Overflow;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.event.dom.client.MouseMoveEvent;
import com.google.gwt.event.dom.client.MouseMoveHandler;
import com.google.gwt.user.client.ui.TextArea;

public class ResizableTextArea extends TextArea 
{
	private class SizeHandler implements KeyUpHandler, KeyDownHandler, MouseMoveHandler
	{
		 
        @Override public void onKeyUp(KeyUpEvent event) { size(); }
        
        @Override public void onKeyDown(KeyDownEvent event) { size(); }
        
        @Override public void onMouseMove(MouseMoveEvent event) { size(); }
 
        public void size() 
        {
            shrink();
            grow();
            
            // For older browsers, which doesn't handle attribute maxLength properly.
            if (getText().length() > maxLength)
            {
            	setText(getText().substring(0, maxLength - 1));
            }
        }
 
        private void grow() 
        {
        	while (getElement().getScrollHeight() > getElement().getClientHeight()) 
                setVisibleLines(getVisibleLines() + growLines);
        }
 
        private void shrink() 
        {
            int rows = getVisibleLines();
            while (rows > initLines) 
            	setVisibleLines(--rows);
        }
    }
 
    private int initLines = 2;
    private int growLines = 1;
    private SizeHandler sizeHandler;
    
    private int maxLength;
    public int getMaxLength() { return maxLength; }
    public void setMaxLength(int length) // HTML5 feature; may not work on older browsers.
    { 
    	maxLength = length;
    	this.getElement().setAttribute("maxLength", "" + length); 
    }
 
    public ResizableTextArea() 
    {
        super();
        setMaxLength(20000);
        registerHandlers();
        adjustStyle();
        setVisibleLines(initLines);
    }
 
    private void adjustStyle() 
    {
        getElement().getStyle().setOverflow(Overflow.HIDDEN);
        getElement().getStyle().setProperty("resize", "none");
    }
 
    private void registerHandlers() 
    {
        sizeHandler = new SizeHandler();
        addKeyUpHandler(sizeHandler);
        addKeyDownHandler(sizeHandler);
        addMouseMoveHandler(sizeHandler);
    }

}
