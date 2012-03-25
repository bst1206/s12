package com.stackbaek.rnsarith.domain;

import java.util.Arrays;

import com.stackbaek.rnsarith.domain.exception.OutOfRangeException;

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
		try {
			init(_value, this._moduli, false);
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (OutOfRangeException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
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
	public void init(int value, int[] moduli, boolean debugEnabled) throws NumberFormatException, OutOfRangeException,Exception
	{	
		if(debugEnabled)
		{
			System.out.println("------------------------------------------------");
			System.out.println("Converting " + value + " to RNS" + Arrays.toString(moduli));
		}
		
		long range = 1;
		long max;
		long min;
		
		for(int i = 0 ; i < moduli.length; ++i)
		{
			range *= moduli[i];
		}
		
		max = range/2 - 1 + (range % 2);
		min = -range/2;
		
		//x
		if(min > max)
		{
			System.out.println("??!?!?!?");
		}
		
		if(debugEnabled)
		{
			System.out.println("The maximum range for this RNS"+Arrays.toString(moduli)
					+ " is from " + min + " to " + max);
		}
		
		
		try{
			if(_isInRange(max, min, value, debugEnabled))
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
		catch (NumberFormatException nfe) {
			throw nfe;
		}
	}
	
	// ------- Private functions -------
	private boolean _isInRange(long max, long min, long value, boolean debugEnabled) throws OutOfRangeException
	{
		if(value > max)
		{
			if(debugEnabled)
			{
				System.out.println("Overflow has occurred: " + value + ">" + max);
			}
			throw new OutOfRangeException("Overflow has occurred: " + value + ">" + max);
		}
		else if(value < min)
		{
			if(debugEnabled)
			{
				System.out.println("Underflow has occurred: " + value + "<" + min);
			}
			throw new OutOfRangeException("Overflow has occurred: " + value + "<" + min);
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
		
		String conversionStep = "Converting " + value + "(bin) to RNS" + Arrays.toString(moduli);
		
		for(int i= 0 ; i < len ; ++i)
		{
			residue[i] = value % moduli[i];
			conversionStep += "\nResidue" + i + ": " + value + "(bin)%" + moduli[i] + " = " + residue[i]; 
		}
		conversionStep += "\n\nFinally, " + value + "(bin) in RNS" + Arrays.toString(moduli) + " = " + Arrays.toString(residue); 
		
		setModuli(moduli);
		setResidues(residue);
		setValue(value);
		setConversionStep(conversionStep);
		
		if(debugEnabled)
		{
			System.out.println("RNSValue Init: {value:" + value + ", moduli:" + Arrays.toString(getModuli())
					+ ", residue:" + Arrays.toString(getResidues()) + "}");
		}
	}
}
