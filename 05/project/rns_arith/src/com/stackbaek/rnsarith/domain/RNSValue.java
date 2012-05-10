package com.stackbaek.rnsarith.domain;

import java.util.Arrays;
import java.util.Iterator;

import com.stackbaek.rnsarith.domain.exception.OutOfRangeException;
import com.stackbaek.rnsarith.event.LogEvent;
import com.stackbaek.rnsarith.eventListener.LogEventListener;
import com.stackbaek.rnsarith.model.Model;

/**
 * @author Baek
 * @date 03/14/2012
 */

public class RNSValue {
	private int _value;
	private int[] _moduli;
	private int[] _residues;
	private String _binaryString;
	private String _conversionStep;
	
	// ------- Getters and Setters -------
	public int getValue() {
		return _value;
	}
	public void setValue(int value) {
		this._value = value;
	}
	public int[] getModuli() {
		return _moduli;
	}
	public void setModuli(int[] moduli) {
		this._moduli = moduli;
	}
	public int[] getResidues() {
		return _residues;
	}
	public void setResidues(int[] residues) {
		this._residues = residues;
	}
	public String getBinaryString() {
		return _binaryString;
	}
	public void setBinaryString(String binaryString) {
		this._binaryString = binaryString;
	}
	public String getConversionStep() {
		return _conversionStep;
	}
	public void setConversionStep(String conversionStep) {
		this._conversionStep = conversionStep;
	}
	
	// ------- Public functions -------
	public void init(int value, int[] moduli, boolean debugEnabled) throws OutOfRangeException,Exception
	{	
		if(debugEnabled)
		{
			fireLogEvent("------------------------------------------------");
//			fireLogEvent("Converting " + value + " to RNS" + Arrays.toString(moduli));
		}
		
		long range = 1;
//		long max;
//		long min;
		
		for(int i = 0 ; i < moduli.length; ++i)
		{
			range *= moduli[i];
		}
		
//		max = range/2 - 1 + (range % 2);
//		min = -range/2;
		
		//x
//		if(min > max)
//		{
//			fireLogEvent("??!?!?!?");
//		}
//		
		if(debugEnabled)
		{
			fireLogEvent("The maximum range for this RNS"+Arrays.toString(moduli)
					+ " is from 0 to " + (range-1));
		}
		
		
		try{
			if(_isInRange(range-1, 0, value, debugEnabled))
			{
				_initValues(value, moduli, debugEnabled);
			}
			else
			{
				throw new Exception("ERROR: Buggy Case: {value:" + value + ", moduli:"+Arrays.toString(moduli) + "}");
			}
		}
		catch (OutOfRangeException oore) {
			throw oore;
		}
	}
	
	// ------- Private functions -------
	private boolean _isInRange(long max, long min, long value, boolean debugEnabled) throws OutOfRangeException
	{
		if(value > max)
		{
			if(debugEnabled)
			{
				fireLogEvent("Overflow has occurred: " + value + ">" + max);
			}
			throw new OutOfRangeException("Overflow has occurred: " + value + ">" + max);
		}
		else if(value < min)
		{
			if(debugEnabled)
			{
				fireLogEvent("Underflow has occurred: " + value + "<" + min);
			}
			throw new OutOfRangeException("Underflow has occurred: " + value + "<" + min);
		}
		
		return true;
	}
	
	
	/*
	 * PRECONDITION: value is within the range, set by moduli.
	 */
	private void _initValues(int value, int[] moduli, boolean debugEnabled)
	{
		int len = moduli.length;
		int[] residue = new int[len];
		
		String conversionStep = "Converting " + value + " to RNS" + Arrays.toString(moduli);
		
		for(int i= 0 ; i < len ; ++i)
		{
			residue[i] = value % moduli[i];
			conversionStep += "\nResidue" + i + ": " + value + "%" + moduli[i] + " = " + residue[i]; 
		}
		conversionStep += "\n\nFinally, " + value + " in RNS" + Arrays.toString(moduli) + " = " + Arrays.toString(residue); 
		
		setModuli(moduli);
		setResidues(residue);
		setValue(value);
		setConversionStep(conversionStep);
		
		if(debugEnabled)
		{
			fireLogEvent(conversionStep);
			fireLogEvent("RNSValue Init: {value:" + value + ", moduli:" + Arrays.toString(getModuli())
					+ ", residue:" + Arrays.toString(getResidues()) + "}");
		}
	}
	
	private Model model = Model.getInstance();
	private void fireLogEvent(String msg)
	{
		Iterator<LogEventListener> listeners = model.getLogEventListenerList().iterator();
		while(listeners.hasNext())
		{
				listeners.next().handleLogEvent(new LogEvent(new Object(), msg));
		}
	}
}
