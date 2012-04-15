package com.stackbaek.rnsarith.model;

import java.util.ArrayList;
import java.util.List;

import com.stackbaek.rnsarith.eventListener.LogEventListener;
import com.stackbaek.rnsarith.eventListener.RNSArithEventListener;

public class Model {
	private static Model model = new Model();
	private List<LogEventListener> _logEventListenerList = new ArrayList<LogEventListener>();
	private List<RNSArithEventListener> _RNSArithEvnetListenerList = new ArrayList<RNSArithEventListener>();
	

	private Model()
	{
//		System.err.println("called via new Method");
	}
	
	public static Model getInstance()
	{
		return model;
	}
	
	public void addLogEventListener(LogEventListener l)
	{
		_logEventListenerList.add(l);
	}
	public List<LogEventListener> getLogEventListenerList()
	{
		return _logEventListenerList;
	}
	public List<RNSArithEventListener> getRNSArithEvnetListenerList() {
		return _RNSArithEvnetListenerList;
	}
	public void addRNSArithEvnetListenerList(RNSArithEventListener rael) {
		_RNSArithEvnetListenerList.add(rael);
	}

	
}
