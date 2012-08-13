package main.shared.proxy;


import main.shared.ShortCourseDesc;

import com.google.web.bindery.requestfactory.shared.ProxyFor;
import com.google.web.bindery.requestfactory.shared.ValueProxy;

@ProxyFor(ShortCourseDesc.class)
public interface ShortUSOSCourseDescProxy extends ValueProxy {
	String getId();
	String getName();
}
