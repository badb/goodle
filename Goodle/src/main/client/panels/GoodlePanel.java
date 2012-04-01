package main.client.panels;

import main.client.services.ServicesManager;

import com.google.gwt.user.client.ui.Composite;


public abstract class GoodlePanel extends Composite
{
        private final ServicesManager manager;

        public GoodlePanel(ServicesManager manager) { this.manager = manager; }

        public ServicesManager getServicesManager() { return manager; }
}
