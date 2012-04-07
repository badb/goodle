package main.client;

import main.client.services.ServicesManager;
import main.client.widgets.GoodleWidget;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;


public class LoginPanel extends GoodleWidget{

        public LoginPanel(ServicesManager manager, SimpleEventBus eventBus) {
                super(manager, eventBus);
        }

        private VerticalPanel loginPanel = new VerticalPanel();

        private Button loginButton = new Button("OK");
        private TextBox nameField = new TextBox();
        private PasswordTextBox passwordField = new PasswordTextBox();
        private Label nameLabel = new Label("Login");
        private Label passwordLabel = new Label("Hasło");
        final Label loginFailLabel = new Label("Login failed");
        

        public VerticalPanel getPanel() {
        		loginPanel.clear();
                loginPanel.add(nameLabel);
                loginPanel.add(nameField);
                loginPanel.add(passwordLabel);
                loginPanel.add(passwordField);
                loginPanel.add(loginButton);
                loginButton.getElement().setId("loginButton");

                nameField.setFocus(true);
                nameField.selectAll();

                loginButton.addClickHandler(new ClickHandler() {
                        public void onClick(ClickEvent event) {
                        		loginPanel.remove(loginFailLabel);
                        		//getGoodleServiceController().loginUser(nameField.getText(), passwordField.getText());
                           }
                });
                
                return loginPanel;
        }
        
        public void loginFail() {
    		loginPanel.add(loginFailLabel);
        }
        
        public void afterLogin() {
            RootPanel.get("goodleLogin").remove(loginPanel);
        }
    
}