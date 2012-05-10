package com.stackbaek.rnsarith.util;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.JOptionPane;

import com.stackbaek.rnsarith.domain.MRSValue;
import com.stackbaek.rnsarith.domain.RNSValue;
import com.stackbaek.rnsarith.domain.exception.ResidueNumberSystemMismatchException;
import com.stackbaek.rnsarith.event.LogEvent;
import com.stackbaek.rnsarith.eventListener.LogEventListener;
import com.stackbaek.rnsarith.model.Model;
import com.stackbaek.rnsarith.util.exception.UnexpectedNumberException;


/**
 * @author Baek
 * @date 03/14/2012
 */

public class RNSUtil {
	
	public static final String RNS_OPERATION_ADD = "add";
	public static final String RNS_OPERATION_SUBTRACT = "subtract";
	public static final String RNS_OPERATION_MULTIPLY = "multiply";
	
		
	public static int[] parseModuliString(String stringModuli) throws Exception
	{
		int[] result = null;
		
		Vector<Integer> v = new Vector<Integer>();
		
		String[] mods_string = stringModuli.split(",");
		Integer value;
		for(int i = 0 ; i < mods_string.length ; ++i)
		{
			try{
				value = Integer.parseInt(mods_string[i].trim()); 
				if(v.contains(value))
				{
					JOptionPane.showMessageDialog(null, "found duplicates for \"" + mods_string[i] + "\" and only one entry will be considered.");
				}
				else if(value.intValue() < 2 )
				{
					JOptionPane.showMessageDialog(null, "moduli \"" + mods_string[i] + "\" needs to be at least 2");
				}
				else
				{
					v.add(value);
				}
			}
			catch (NumberFormatException nfe) {
				JOptionPane.showMessageDialog(null, "moduli \"" + mods_string[i] + "\" is not an integer and will be ignored.");
			}
			catch (Exception e) {
				JOptionPane.showMessageDialog(null, e.getMessage());
				throw e;
			}
		}
		
		//result allocation
		if(v.size() > 6)
		{
			JOptionPane.showMessageDialog(null, "there are more than 6 moduli. only first 6 will be considered.");
		}
		
		int len = Math.min(v.size(), 6);
		result = new int[len];
		for(int i = 0 ; i < len ; ++i)
		{
			result[i] = v.get(i);
		}
		
		return result;
	}
	
	public static boolean isValidModuli(int[] moduli, boolean debugEnabled)
	{
		if(debugEnabled)
		{
			fireLogEvent("------------------------------------------------");
			fireLogEvent("Validating " + Arrays.toString(moduli));
		}
		int len = moduli.length;
		int moduli_a;
		int moduli_b;

		if(len < 1)
		{
			fireLogEvent("There has to be at least 1 moduli");
			return false;
		}
		
		for(int i = 0 ; i < len ; i++ )
		{
			for(int j = i+1 ; j < len; j++)
			{
				moduli_a = moduli[i];
				moduli_b = moduli[j];
				try{
					if(moduli_a == moduli_b || !isCoPrime(moduli_a, moduli_b, debugEnabled))
					{
						if(debugEnabled)
						{
							fireLogEvent("They are NOT pairwise co-prime: " + moduli_a + ", " + moduli_b);
						}
						return false;
					}
				}
				catch (UnexpectedNumberException une)
				{
					if(debugEnabled)
					{
						fireLogEvent("They are NOT pairwise co-prime: " + moduli_a + ", " + moduli_b);
					}
					return false;
				}
			}
		}
		if(debugEnabled)
		{
			fireLogEvent("moduli " + Arrays.toString(moduli) + " is a valid moduli");
		}
		return true;
	}
	
	public static boolean isCoPrime(int a, int b, boolean debugEnabled) throws UnexpectedNumberException
	{
		if( a < 2 || b < 2)
		{
			throw new UnexpectedNumberException("Both number have to be greater than 1");
		}
		if(debugEnabled)
		{
//			fireLogEvent("------------------------------------------------");
//			fireLogEvent("Checking if " + a + " and " + b + " are co-prime");
		}
		if(a % 2 == 0 && b % 2 == 0)
		{
			if(debugEnabled)
			{
				fireLogEvent("Both " + a + " and " + b + " are even, so they are NOT co-prime");
				JOptionPane.showMessageDialog(null, "Both " + a + " and " + b + " are even, so they are NOT co-prime");
			}
			return false;
		}
		int max = Math.max(a, b);
		int min = Math.min(a, b);
		int rem = max % min;
		if(debugEnabled)
		{
//			fireLogEvent(max + " % " + min + " = " + rem);
		}
		while(rem != 0)
		{
			max = min;
			min = rem;
			rem = max % min;
			if(debugEnabled)
			{
//				fireLogEvent(max + " % " + min + " = " + rem);
			}
		}
		if(min == 1)
		{
			return true;
		}
		else
		{
			if(debugEnabled)
			{
				fireLogEvent("Their GCD is " + min);
				JOptionPane.showMessageDialog(null, a + " and " + b + " are not co-prime");
			}
			return false;
		}
	}
	
	// ------- Conversion Functions -------
	public static MRSValue RNStoMRS(RNSValue value)
	{
		fireLogEvent("------------------------------------------------");
		fireLogEvent("Converting " + Arrays.toString(value.getResidues()) 
				+ " in RNS" + Arrays.toString(value.getModuli()) + " to decimal value");
		int[] moduli = value.getModuli();
		//validate
		if(!isValidModuli(value.getModuli(), false))
		{
			return null;
		}

		MRSValue result = new MRSValue(moduli);
		
		int len = moduli.length;
		int[] residues = value.getResidues();
		int[] digits = new int[len];
		digits[len-1] = residues[len-1];
		fireLogEvent("z[" + (len-1) + "] = " + digits[len-1]);
		
		
		for(int i = len-1; i > 0 ; --i)
		{
			
			try {
				//subtract digits[i+1]
				fireLogEvent("subtracting z[" + i + "] = " + digits[i]);
				residues = residue_opertaion(residues, get_subtr_inv(residues, i), moduli, RNS_OPERATION_SUBTRACT, false);
				//multiply by multiplicative inverse of moduli[i+1];
				fireLogEvent("and dividing by " + moduli[i]);
				residues = residue_opertaion(residues, multi_inv_arry(moduli, i), moduli, RNS_OPERATION_MULTIPLY, false);
				digits[i-1] = residues[i-1];
				fireLogEvent("z[" + (i-1) + "] = " + digits[i-1]);
			} catch (ResidueNumberSystemMismatchException e) {
				e.printStackTrace();
			}
			
		}
		
		// detect overflow
		
		result.setDigits(digits);
		return result;
	}
	
	private static int[] get_subtr_inv(int[] res, int index) //misnomer.... but...
	{
		int[] result = new int[res.length];
		
		for(int i = 0 ; i < index+1 ; ++i)
		{
			result[i] = res[index];
		}
		return result;
	}
	
	public static int[] multi_inv_arry(int[] mod , int index) throws IndexOutOfBoundsException
	{
		if(index > mod.length - 1)
		{
			throw new IndexOutOfBoundsException("index " + index + " is greater than the lenght of moduli array " + mod.length);
			
		}
		int len = mod.length;
		int[] result = new int[len];
		for(int i = 0 ; i < index ; ++i)
		{
			if(i < index)
			result[i] = _multi_inv(mod[index], mod[i]);
		}
		return result;
	}
	
	private static int _multi_inv(int number, int mod)
	{
		if(number == mod)
		{
			return -1;
		}
		int result = 1;
		int rem = 0;
		while(rem != 1)
		{
			rem = number * result++;
			rem %= mod;
			
		}
		return --result;
	}
	
	// ------- Core Artirhmetic Functions ------
	public static RNSValue RNS_operation(RNSValue value1, RNSValue value2, int[] mod, String operation)
	{
		RNSValue result = new RNSValue();
		
		fireLogEvent("------------------------------------------------");
		try {
			result.setResidues(residue_opertaion(value1.getResidues(), value2.getResidues(), value1.getModuli(), operation, true));
			result.setModuli(value1.getModuli());
		} catch (ResidueNumberSystemMismatchException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
		return result;
	}
	
	public static int[] residue_opertaion(int[] residue1, int[] residue2, int[] moduli, String operation, boolean detailedMesage) throws ResidueNumberSystemMismatchException
	{
		if(residue1.length != residue2.length)
		{
			throw new ResidueNumberSystemMismatchException("Mismatching residue length: value1 has length of " + residue1.length + " and value2 has " + residue2.length);
		}
		if(residue1.length != moduli.length)
		{
			throw new ResidueNumberSystemMismatchException("Wrong residue/moduli: there are " + moduli.length + "moduli and " + residue1.length + " residues");
		}
		
		int len = residue1.length;
		if(len != residue2.length)
		{
			return null;
		}
		
		int[] result = new int[len];
		
		if(operation.equals(RNS_OPERATION_ADD))
		{
			for(int i = 0 ; i < len ; ++i)
			{
				result[i] = (residue1[i] + residue2[i]) % moduli[i];
				if(detailedMesage)
				{
					fireLogEvent("(" + residue1[i] + " + " + residue2[i] + ") % " + moduli[i] + " = " + result[i]);
				}
			}
			fireLogEvent("Operation: " + Arrays.toString(residue1) + " + " + Arrays.toString(residue2) + " = " + Arrays.toString(result));
		}
		else if(operation.equals(RNS_OPERATION_MULTIPLY))
		{
			for(int i = 0 ; i < len ; ++i)
			{
				result[i] = (residue1[i] * residue2[i]) % moduli[i];
				if(detailedMesage)
				{
					fireLogEvent("(" + residue1[i] + " * " + residue2[i] + ") % " + moduli[i] + " = " + result[i]);
				}
			}
			fireLogEvent("Operation: " + Arrays.toString(residue1) + " * " + Arrays.toString(residue2) + " = " + Arrays.toString(result));
		}
		else if(operation.equals(RNS_OPERATION_SUBTRACT))
		{
			for(int i = 0 ; i < len ; ++i)
			{
				result[i] = (residue1[i] - residue2[i]) % moduli[i];
				if(result[i] < 0)
				{
					result[i] += moduli[i];
				}
				if(detailedMesage)
				{
					fireLogEvent("(" + residue1[i] + " - " + residue2[i] + ") % " + moduli[i] + " = " + result[i]);
				}
			}
			fireLogEvent("Operation: " + Arrays.toString(residue1) + " - " + Arrays.toString(residue2) + " = " + Arrays.toString(result));
		}
		
		return result;
	}
	
	private static Model model = Model.getInstance();
	public static void fireLogEvent(String msg)
	{
		Iterator<LogEventListener> listeners = model.getLogEventListenerList().iterator();
		while(listeners.hasNext())
		{
			listeners.next().handleLogEvent(new LogEvent(new Object(), msg));
		}
	}
}
