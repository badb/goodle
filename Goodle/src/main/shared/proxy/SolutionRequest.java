package main.shared.proxy;


import main.server.domain.Solution;

import com.google.web.bindery.requestfactory.shared.Request;
import com.google.web.bindery.requestfactory.shared.RequestContext;
import com.google.web.bindery.requestfactory.shared.Service;

@Service(Solution.class)
public interface SolutionRequest extends RequestContext 
{
	Request<SolutionProxy> findSolution(Long id);
}
