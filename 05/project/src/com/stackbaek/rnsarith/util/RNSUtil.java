package com.stackbaek.rnsarith.util;

import java.util.Arrays;

import com.stackbaek.rnsarith.domain.MRSValue;
import com.stackbaek.rnsarith.domain.RNSValue;
import com.stackbaek.rnsarith.domain.exception.ResidueNumberSystemMismatchException;
import com.stackbaek.rnsarith.util.exception.UnexpectedNumberException;


/**
 * @author Baek
 * @date 03/14/2012
 */

public class RNSUtil {
	
	public static final String RNS_OPERATION_ADD = "rnsOperationAdd";
	public static final String RNS_OPERATION_SUBTRACT = "rnsOperationSubtract";
	public static final String RNS_OPERATION_MULTIPLY = "rnsOperationMultiply";
	
	public static boolean isValidModuli(int[] moduli, boolean debugEnabled)
	{
		if(debugEnabled)
		{
			System.out.println("------------------------------------------------");
			System.out.println("Validating " + Arrays.toString(moduli));
		}
		int len = moduli.length;
		int moduli_a;
		int moduli_b;

		if(len < 1)
		{
			System.out.println("There has to be at least 1 moduli");
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
							System.out.println("They are NOT pairwise co-prime: " + moduli_a + ", " + moduli_b);
						}
						return false;
					}
				}
				catch (UnexpectedNumberException une)
				{
					if(debugEnabled)
					{
						System.out.println("They are NOT pairwise co-prime: " + moduli_a + ", " + moduli_b);
					}
					return false;
				}
			}
		}
		if(debugEnabled)
		{
			System.out.println("They are pairwise co-prime");
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
			System.out.println("------------------------------------------------");
			System.out.println("Checking if " + a + " and " + b + " are co-prime");
		}
		if(a % 2 == 0 && b % 2 == 0)
		{
			if(debugEnabled)
			{
				System.out.println("Both are even, so they are NOT co-prime");
			}
			return false;
		}
		int max = Math.max(a, b);
		int min = Math.min(a, b);
		int rem = max % min;
		if(debugEnabled)
		{
			System.out.println(max + " % " + min + " = " + rem);
		}
		while(rem != 0)
		{
			max = min;
			min = rem;
			rem = max % min;
			if(debugEnabled)
			{
				System.out.println(max + " % " + min + " = " + rem);
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
				System.out.println("Their GCD is " + min);
			}
			return false;
		}
	}
	
	// ------- Conversion Functions -------
	public static MRSValue RNStoMRS(RNSValue value)
	{
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
		
		
		for(int i = len-1; i > 0 ; --i)
		{
			
			try {
				//subtract digits[i+1]
				residues = residue_opertaion(residues, get_subtr_inv(residues, i), moduli, RNS_OPERATION_SUBTRACT);
				//multiply by multiplicative inverse of moduli[i+1];
				residues = residue_opertaion(residues, multi_inv_arry(moduli, i), moduli, RNS_OPERATION_MULTIPLY);
				digits[i-1] = residues[i-1];
			} catch (ResidueNumberSystemMismatchException e) {
				e.printStackTrace();
			}
			
		}
		
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
	private static int[] residue_opertaion(int[] residue1, int[] residue2, int[] moduli, String operation) throws ResidueNumberSystemMismatchException
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
		
		for(int i = 0 ; i < len ; ++i)
		{
			if(operation.equals(RNS_OPERATION_ADD))
			{
				result[i] = (residue1[i] + residue2[i]) % moduli[i];
			}
			else if(operation.equals(RNS_OPERATION_MULTIPLY))
			{
				result[i] = (residue1[i] * residue2[i]) % moduli[i];
			}
			else if(operation.equals(RNS_OPERATION_SUBTRACT))
			{
				result[i] = (residue1[i] - residue2[i]) % moduli[i];
				if(result[i] < 0)
				{
					result[i] += moduli[i];
				}
			}
		}
		
		return result;
	}
	
}
