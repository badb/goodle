package main.client.widgets;

import main.client.services.ServicesManager;

import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.user.client.ui.Composite;


public abstract class GoodleWidget extends Composite
{
        protected final ServicesManager manager;
        protected final SimpleEventBus eventBus;

        public GoodleWidget(ServicesManager manager, SimpleEventBus eventBus) 
        { 
        	this.manager = manager;
        	this.eventBus = eventBus;
        }

        public ServicesManager getServicesManager() { return manager; }
        
        public SimpleEventBus getEventBus() { return eventBus; }
}
