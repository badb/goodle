package main.shared.proxy;


import main.server.domain.Module;

import com.google.web.bindery.requestfactory.shared.InstanceRequest;
import com.google.web.bindery.requestfactory.shared.Request;
import com.google.web.bindery.requestfactory.shared.RequestContext;
import com.google.web.bindery.requestfactory.shared.Service;


@Service(Module.class)
public interface ModuleRequest extends RequestContext 
{
	InstanceRequest<ModuleProxy, Void> addMaterial(UploadedFileProxy material);
	InstanceRequest<ModuleProxy, Void> removeMaterial(UploadedFileProxy material);
	InstanceRequest<ModuleProxy, Void> addComment(MessageProxy comment);
	InstanceRequest<ModuleProxy, Void> removeComment(MessageProxy comment);
	
	InstanceRequest<ModuleProxy, Void> persist();
	InstanceRequest<ModuleProxy, Void> remove();
	Request<ModuleProxy> findModule(Long id);
}