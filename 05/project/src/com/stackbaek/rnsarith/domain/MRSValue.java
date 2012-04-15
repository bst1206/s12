package com.stackbaek.rnsarith.domain;

import java.util.Arrays;


public class MRSValue {
	private int[] _moduli;
	private int[] _weights;
	private int[] _digits;

	public int[] getWeights() {
		return _weights;
	}
	public void setWeights(int[] weights) {
		this._weights = weights;
	}
	public int[] getModuli() {
		return _moduli;
	}
	public void setModuli(int[] moduli) {
		this._moduli = moduli;
		setWeights(_getColWeight(moduli, false));
	}
	public int[] getDigits() {
		return _digits;
	}
	public void setDigits(int[] digits) {
		this._digits = digits;
	}
	
	//default constructor
	public MRSValue(int[] moduli)
	{
		_init(moduli, false);
	}
	
	public int getDecValue()
	{
		if(_digits != null && _weights != null)
		{
			if(_digits.length == _weights.length)
			{
				int value = 0;
				for(int i = 0 ; i < _digits.length ; ++i)
				{
					value += _digits[i]*_weights[i];
				}
				return value;
			}
			else
			{
				Exception e = new Exception("DIGITS and WEIGHTS HAVE DIFFERENT LENGTH");
				e.printStackTrace();
			}
		}
		return -1;
	}
	
	// ------- private methods -------
	private void _init(int[] moduli, boolean debugEnabled)
	{
		//first validating moduli and residue
		int len = moduli.length;
		
		if(len < 1)
		{
			if(debugEnabled)
			{
				System.out.println("There has to be at least one moduli");
				return;
			}
		}
		setModuli(moduli);
		return;
	}
	
	//PRECONDITION: moduli has at least 1 item.
	private int[] _getColWeight(int[] moduli, boolean debugEnabled)
	{
		int len = moduli.length;
		int[] result = new int[moduli.length];
		result[len-1] = 1;
		for(int i = len-2 ; i > -1 ; --i)
		{
			result[i] = result[i+1]*moduli[i+1];
		}
		
		if(debugEnabled)
		{
			System.out.println("column weights for RNS" + Arrays.toString(moduli) + ": MRS" + Arrays.toString(result));
		}
		return result;
	}
	
}
