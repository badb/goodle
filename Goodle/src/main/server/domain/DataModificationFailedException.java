package main.server.domain;

public class DataModificationFailedException extends Exception 
{
	static final long serialVersionUID = 14531794; 
	
	public DataModificationFailedException() { super(); }
	public DataModificationFailedException(String msg) { super(msg); }
}
