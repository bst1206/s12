package com.stackbaek.rnsarith.domain.exception;

public class OutOfRangeException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7393083178710706543L;

	public OutOfRangeException()
	{
		
	}
	
	public OutOfRangeException(String msg)
	{
		super(msg);
	}
}
