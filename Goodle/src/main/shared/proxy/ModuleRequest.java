package main.shared.proxy;


import java.util.List;

import main.server.domain.Module;

import com.google.web.bindery.requestfactory.shared.InstanceRequest;
import com.google.web.bindery.requestfactory.shared.Request;
import com.google.web.bindery.requestfactory.shared.RequestContext;
import com.google.web.bindery.requestfactory.shared.Service;


@Service(Module.class)
public interface ModuleRequest extends RequestContext 
{
	InstanceRequest<ModuleProxy, Void> addMaterial(MaterialProxy material);
	InstanceRequest<ModuleProxy, Void> removeMaterial(MaterialProxy material);
	InstanceRequest<ModuleProxy, Void> addComment(MessageProxy comment);
	InstanceRequest<ModuleProxy, Void> removeComment(MessageProxy comment);
	
	InstanceRequest<ModuleProxy, Void> persist();
	InstanceRequest<ModuleProxy, Void> remove();
	Request<ModuleProxy> findModule(Long id);
	Request<List<ModuleProxy>> findModules(List<Long> ids);
}