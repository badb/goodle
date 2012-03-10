package edu.goodle.prototype.db;

public class DataModificationFailedException extends Exception 
{
	static final long serialVersionUID = 14531794; 
	
	public DataModificationFailedException(String msg) { super(msg); }
}
