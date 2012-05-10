package main.shared.proxy;

import main.server.domain.Material;

import com.google.web.bindery.requestfactory.shared.InstanceRequest;
import com.google.web.bindery.requestfactory.shared.Request;
import com.google.web.bindery.requestfactory.shared.RequestContext;
import com.google.web.bindery.requestfactory.shared.Service;

@Service(Material.class)
public interface MaterialRequest extends RequestContext
{
	InstanceRequest<MaterialProxy, Long> persist();
	InstanceRequest<MaterialProxy, Void> remove();
	Request<MaterialProxy> findMaterial(Long id);
}
