package com.stackbaek.rnsarith.event;
import java.util.EventObject;


public class LogEvent extends EventObject {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4833245964815843860L;
	private String _logMessage;
	
	public LogEvent(Object source, String _logMessage) {
		super(source);
		setLogMessage(_logMessage);
	}

	public String getLogMessage() {
		return _logMessage;
	}
	public void setLogMessage(String logMessage) {
		this._logMessage = logMessage;
	}
	

}
