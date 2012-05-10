package main.shared.proxy;

import main.server.domain.Homework;

import com.google.web.bindery.requestfactory.shared.Request;
import com.google.web.bindery.requestfactory.shared.Service;

@Service(Homework.class)
public interface HomeworkRequest extends MaterialRequest
{
	Request<HomeworkProxy> findHomework(Long id);

}
