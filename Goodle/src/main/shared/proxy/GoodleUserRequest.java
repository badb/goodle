package main.shared.proxy;

import java.util.List;

import main.server.domain.GoodleUser;

import com.google.web.bindery.requestfactory.shared.InstanceRequest;
import com.google.web.bindery.requestfactory.shared.Request;
import com.google.web.bindery.requestfactory.shared.RequestContext;
import com.google.web.bindery.requestfactory.shared.Service;

@Service(GoodleUser.class)
public interface GoodleUserRequest extends RequestContext
{
	InstanceRequest<GoodleUserProxy, Void> persist();
	Request<GoodleUserProxy> findGoodleUser(Long id);
	Request<List<GoodleUserProxy>> findGoodleUsersByName(String name);

}
