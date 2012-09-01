package main.client.ui;

import main.client.ClientFactory;
import main.client.Goodle;
import main.shared.proxy.GoodleUserProxy;
import main.shared.proxy.GoodleUserRequest;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.web.bindery.requestfactory.shared.Receiver;

public class UserDataPopup extends DialogBox 
{
	private static UserDataPopupUiBinder uiBinder = GWT.create(UserDataPopupUiBinder.class);

	interface UserDataPopupUiBinder extends UiBinder<Widget, UserDataPopup> { }
	
	@UiField TextBox firstName;
	@UiField TextBox lastName;
	@UiField TextBox studentId;
	
	@UiField Label message;
	
	@UiField Button save;
	
	private static final String NAME_FIELDS_REQUIRED = "Wymagane jest podanie imienia i nazwiska.";
	private static final String INVALID_STUDENT_ID = "Podany numer indeksu nie jest poprawny."; 
	
	private Goodle goodleApp;
	public void setGoodleApp(Goodle app) { goodleApp = app; }
	
	private ClientFactory cf;
	public void setClientFactory(ClientFactory cf) { this.cf = cf; }
	
	private GoodleUserProxy user;
	public void setUser(GoodleUserProxy user) { this.user = user; }
	
	public UserDataPopup() { setWidget(uiBinder.createAndBindUi(this)); }
	
	@UiHandler("save")
	public void onSaveButtonClicked(ClickEvent event)
	{
		String fName = firstName.getText();
		String lName = lastName.getText();
		String id = studentId.getText();
		
		if (fName.isEmpty() || lName.isEmpty())
		{
			message.setText(NAME_FIELDS_REQUIRED);
			return;
		}
		
		if (!id.isEmpty() && !id.matches("\\d{6}"))
		{
			message.setText(INVALID_STUDENT_ID);
			return;
		}
		
		final UserDataPopup me = this;
		
		GoodleUserRequest request = cf.getRequestFactory().goodleUserRequest();
		user = request.edit(user);
		
		user.setFirstName(fName);
		user.setLastName(lName);
		if (!id.isEmpty())
			user.setStudentId(id);
		
		request.update().using(user).fire
		(
			new Receiver<GoodleUserProxy>()
			{
				@Override public void onSuccess(GoodleUserProxy response)
				{
					cf.setCurrentUser(response);
					goodleApp.onUserLogged();
					me.hide();
				}
			}
		);
		
	}
	
	
}
