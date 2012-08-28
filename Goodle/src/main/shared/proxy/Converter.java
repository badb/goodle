package main.shared.proxy;

import java.util.List;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Converter {
	private static int MAX_SIZE = 450;
	private static Logger logger = Logger.getLogger("main.shared.proxy.Converter");
	public static String getString(List<String> val){
		logger.log(Level.INFO, "getString input size =" + Integer.toString(val.size()));
		String toRet = "";
		for( String s: val){
			toRet += s;
		}
		return toRet;
		
	}
	
	public static List<String> getList(String val){
		List<String> toRet = new Vector<String>();
		if(val == null){
			logger.log(Level.INFO, "getList NULL string as input");
			toRet.add("");
			return toRet;
		}
		logger.log(Level.INFO, "getList vale.length() = " + Integer.toString(val.length()));		
		String copy = new String(val);
		while( copy.length() > MAX_SIZE){
			toRet.add(copy.substring(0, MAX_SIZE));
			copy = copy.substring(MAX_SIZE);
		}
		toRet.add(copy);
		
		logger.log(Level.INFO, "getList return list size =" + Integer.toString(toRet.size()));
		return toRet;
	}

}
