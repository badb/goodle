package main.client.ui;

import java.util.List;

import main.client.place.CoursePlace;
import main.shared.proxy.CourseRequest;
import main.shared.proxy.ModuleProxy;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.google.web.bindery.requestfactory.shared.Receiver;

public class CourseModulesView  extends AbstractCourseView
{
	private static CourseModulesViewUiBinder uiBinder = GWT.create(CourseModulesViewUiBinder.class);

	interface CourseModulesViewUiBinder extends UiBinder<Widget, CourseModulesView> { }

	@UiField Label info;
	@UiField Button edit;
	@UiField FlexTable modules;
	
	public static String notRegistered = "Musisz być zarejestrowany na kurs, aby obejrzeć zawartość.";
	
	public CourseModulesView()
	{
		initWidget(uiBinder.createAndBindUi(this));
	}
	
	@Override
	public void onCourseSet()
	{
		info.setText("");
		edit.setEnabled(isCurrUserOwner());
		edit.setVisible(isCurrUserOwner());
		getModules();
	}
	
	private void getModules()
	{
		modules.clear();
		modules.removeAllRows();
		
		if (!isCurrUserMember())
		{
			info.setText(notRegistered);
			return;
		}
		
		CourseRequest request = cf.getRequestFactory().courseRequest();
		course = request.edit(course);
		
		request.getModulesSafe().using(course).fire
		(
			new Receiver<List<ModuleProxy>>()
			{
				@Override
				public void onSuccess(List<ModuleProxy> result)
				{
					for (ModuleProxy m : result) 
					{
						ModuleView view = new ModuleView();
						view.setClientFactory(cf);
						view.setModule(m);

						int rows = modules.getRowCount();
						modules.insertRow(rows);
						modules.insertCell(rows, 0);
						modules.setWidget(rows, 0, view);
					}
				}
			}
		);
	}
	
	@UiHandler("edit")
	public void onEditClick(ClickEvent event) 
	{
		if (isCurrUserOwner())
		{
			String courseId = course.getId().toString();
			cf.getPlaceController().goTo(new CoursePlace(courseId, "modulesEdit"));
		}
	}
}
