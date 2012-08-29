package main.shared.proxy;

import main.server.domain.Homework;

import com.google.web.bindery.requestfactory.shared.Request;
import com.google.web.bindery.requestfactory.shared.Service;

@Service(Homework.class)
public interface HomeworkRequest extends ModuleRequest
{
	Request<ModuleProxy> findHomework(Long id);
}
