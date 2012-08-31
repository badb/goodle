package main.client.ui;

import java.util.logging.Logger;

import main.shared.proxy.CourseRequest;
import main.shared.proxy.SolutionProxy;
import main.shared.proxy.UploadedFileProxy;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public class SolutionView extends AbstractCourseView {

	private static SolutionViewUiBinder uiBinder = GWT
			.create(SolutionViewUiBinder.class);

	interface SolutionViewUiBinder extends UiBinder<Widget, SolutionView> {
	}

	public SolutionView() {
		initWidget(uiBinder.createAndBindUi(this));
	}
	
	
	@UiField FileView fileView;
	@UiField EditLabel comment;
	@UiField TextBox mark;
	@UiField HorizontalPanel markPanel;
	
	private SolutionProxy solution;
	public void setSolution(SolutionProxy solution) 
	{
		this.solution = solution;
		init();
	}
	
	public SolutionProxy getSolution(CourseRequest request) {
		if (solution == null) return null;
		solution = request.edit(solution);

		if (isCurrUserOwner()) {
			solution.setComment(comment.getText());
			if (!mark.getText().equals("")) {
				solution.setMark(Float.parseFloat(mark.getText()));
			}
		}
		return solution;
	}
	
	private void init() {
		if (solution == null) return;
		
		fileView.setClientFactory(cf);
		fileView.setCourse(course);
		fileView.setUploadedFile(solution);
		
		if (isCurrUserOwner()) {
			comment.setEditable(true);
			mark.setEnabled(true);
		}

		if ((isCurrUserMember() && solution.isChecked()) || isCurrUserOwner()) {
			if (solution.getComment() != null)
				mark.setText(solution.getComment());
			else 
				comment.setText("");
			if (solution.getMark() != null)
				mark.setText(""+solution.getMark());
			else 
				mark.setText("");
			markPanel.setVisible(true);
		}		
	}

	@UiHandler("mark")
	public void onKeyUpHandler(KeyUpEvent event) {
        String input = mark.getText();
        if (!input.matches("[0-9]*[.]?[0-9]*")) {
            mark.setText(input.substring(0, input.length()-1));
        }
	}
	

	public void setAuthorNameAsTitle(boolean authorNameAsTitle) 
	{
		fileView.setAuthorNameAsTitle(authorNameAsTitle);
	}
	
	public void setLate() {
		fileView.date.setStyleName("red");
	}
	
}
