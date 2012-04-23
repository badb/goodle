package main.shared.proxy;


import main.server.domain.Module;

import com.google.web.bindery.requestfactory.shared.InstanceRequest;
import com.google.web.bindery.requestfactory.shared.RequestContext;
import com.google.web.bindery.requestfactory.shared.Service;


	@Service(Module.class)
	public interface ModuleRequest extends RequestContext 
	{
		InstanceRequest<ModuleProxy, Void> persist();

	}