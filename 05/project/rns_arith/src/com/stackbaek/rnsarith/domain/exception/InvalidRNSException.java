package com.stackbaek.rnsarith.domain.exception;

public class InvalidRNSException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5056009413766375974L;

	public InvalidRNSException()
	{
		
	}
	
	public InvalidRNSException(String msg)
	{
		super(msg);
	}
}
