package edu.goodle.prototype.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.LogRecord;

public class LoginPanel extends GoodlePanel{

        private static Logger logger = Logger.getLogger("");

        public LoginPanel(GoodleServiceAsync goodleService, Goodle goodle) {
                super(goodleService, goodle);
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
                                getGoodleService().loginUser(nameField.getText(), 
                                                passwordField.getText(), new AsyncCallback<String>() {
                                        public void onFailure(Throwable caught) {
                                        		loginPanel.add(loginFailLabel);
                                                logger.severe("Login failed." + caught);
                                        }
                                        public void onSuccess(String result) {
                                                RootPanel.get("goodleLogin").remove(loginPanel);
                                                loginPanel.remove(loginFailLabel);
                                                getGoodle().afterLogin(result);
                                        }
                                });
                        }
                });
                return loginPanel;
        }
}