package com.stackbaek.rnsarith.util;

import java.util.Arrays;

import com.stackbaek.rnsarith.domain.RNSValue;
import com.stackbaek.rnsarith.util.exception.UnexpectedNumberException;


/**
 * @author Baek
 * @date 03/14/2012
 */

public class RNSUtil {
	
	public static final String RNS_OPERATION_ADD = "rnsOperationAdd";
	public static final String RNS_OPERATION_SUBTRACT = "rnsOperationSubtract";
	public static final String RNS_OPERATION_MULTIPLY = "rnsOperationMultiply";
	
	public static boolean validateModuli(int[] moduli, boolean debugEnabled)
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
			System.out.println("There has to be more than 1 moduli");
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
	
	public static int[] multi_inv_arry(int[] mod , int index) throws IndexOutOfBoundsException
	{
		if(index > mod.length - 1)
		{
			throw new IndexOutOfBoundsException("index " + index + " is greater than the lenght of moduli array " + mod.length);
			
		}
		int[] result = new int[index];
		for(int i = 0 ; i < index ; ++i)
		{
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
	
	public static String RNStoDec(RNSValue value)
	{
		String conversionString = "";
		
		
		
		return conversionString;
	}
	
	// ------- Core Artirhmetic Functions ------
	public static RNSValue rns_operation(RNSValue value1, RNSValue value2, String operation, boolean debugEnabled) throws MismatchResidueNumberSystemException
	{
		RNSValue result = new RNSValue();
		int[] residue1 = value1.getResidues();
		int[] residue2 = value2.getResidues();
		int[] result_res = new int[residue1.length];
		int[] mod = value1.getModuli();
		
		// are they in the same RNS system?
		if(!Arrays.equals(value1.getModuli(), value2.getModuli()))
		{
			throw new MismatchResidueNumberSystemException("Mismatching moduli: " + Arrays.toString(value1.getModuli()) + ", " + Arrays.toString(value2.getModuli()));
		}
		// is there any inconsistencies?
		else if(residue1.length != residue2.length)
		{
			throw new MismatchResidueNumberSystemException("Mismatching residue length: value1 has length of " + residue1.length + " and value2 has " + residue2.length);
		}
		else if(residue1.length != mod.length)
		{
			throw new MismatchResidueNumberSystemException("Wrong residue/moduli: there are " + mod.length + "moduli and " + residue1.length + " residues");
		}
		
		for(int i = 0 ; i < mod.length ; ++i)
		{
			if(operation.equals(RNS_OPERATION_ADD))
			{
				result_res[i] = (residue1[i] + residue2[i]) % mod[i];
			}
			else if(operation.equals(RNS_OPERATION_MULTIPLY))
			{
				result_res[i] = (residue1[i] * residue2[i]) % mod[i];
			}
			else if(operation.equals(RNS_OPERATION_SUBTRACT))
			{
				result_res[i] = (residue1[i] - residue2[i]) % mod[i];
			}
		}
		
		
		return result;
	}
	
}
