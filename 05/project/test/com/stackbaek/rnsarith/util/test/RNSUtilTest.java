package com.stackbaek.rnsarith.util.test;

import java.util.Arrays;

import javax.naming.TimeLimitExceededException;

import junit.framework.TestCase;

import com.stackbaek.rnsarith.domain.RNSValue;
import com.stackbaek.rnsarith.domain.exception.OutOfRangeException;
import com.stackbaek.rnsarith.util.RNSUtil;
import com.stackbaek.rnsarith.util.exception.UnexpectedNumberException;

public class RNSUtilTest extends TestCase {

	public RNSUtilTest(String name)
	{
		super(name);
	}
	
	public void test_workflow()
	{
		try {
			int[] mod = generateValidModuli();
			String s_mod = Arrays.toString(mod);
			int range = 1;
			for(int i = 0 ; i < mod.length ; ++i)
			{
				range *= mod[i];
			}
			int x = (int)(Math.random()*range*1.5);
			int y = (int)(Math.random()*range*1.5);

			System.out.println(s_mod.substring(1, s_mod.length()-1));
			System.out.println("X= " + x);
			System.out.println("Y= " + y);
			System.out.println("X+Y= " + (x+y));
			System.out.println("X*Y= " + (x*y));
			System.out.println("X-Y= " + (x-y));
		} catch (TimeLimitExceededException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void testValidateModuli() {
		int moduli[];
		int len;
		int iter = (int)(Math.random()*100);
		System.out.println("Number of test cases: " + iter);
		for(int i = 0 ; i < iter; i++)
		{
			len = (int)(Math.random()*6)+1;
			moduli = new int[len];
			for(int j = 0 ; j < len ; ++j)
			{
				moduli[j] = (int)(Math.random()*100+1);
			}
			RNSUtil.isValidModuli(moduli, true);
		}
		
	}

	public void testIsCoPrime() {
		int a;
		int b;
		int iter = (int)(Math.random()*100);
		System.out.println("Number of test cases: " + iter);
		for(int i = 0 ; i < iter; i++)
		{
			a = (int)(Math.random()*100);
			b = (int)(Math.random()*100);
			try{
				if(RNSUtil.isCoPrime(a, b, true))
				{
					System.out.println(a + " and " + b + " are co-prime");
				}
				else
				{
					System.out.println(a + " and " + b + " are NOT co-prime");
				}
			}
			catch (UnexpectedNumberException une)
			{
				System.out.println(a+","+b+":"+une.getMessage());
			}
		}
	}
	
	public void testConverToRNS() {
		int iter = (int)(Math.random()*100);
		System.out.println("Number of test cases: " + iter);
		int [] moduli;
		RNSValue rns = new RNSValue();
		for(int i = 0 ; i < iter ; ++i)
		{
			try {
				moduli = generateValidModuli();
				int value = (int)(Math.random()*100);
				rns.init(value, moduli, true);
				System.out.println(rns.getConversionStep());
			} 
			catch (TimeLimitExceededException tlee) {
				System.out.println("Takes too long");
			}
			catch (NumberFormatException nfe) {
				nfe.printStackTrace();
			} 
			catch (OutOfRangeException oore) {
				oore.printStackTrace();
			} 
			catch (Exception e) {
				e.printStackTrace();
			}
				
		}
	}
	
	
	// ------- Test util ------
	public static int[] generateValidModuli() throws TimeLimitExceededException
	{
		int len = (int)(Math.random()*6) +1;
		int[] moduli = new int[len];
		for(int t = 0 ; t < 200 ; ++t)
		{
			for(int i = 0 ; i < len ; ++i)
			{
				moduli[i] = (int)(Math.random()*100+1);
			}
			if(RNSUtil.isValidModuli(moduli, false))
			{
				return moduli;
			}
		}
		throw new TimeLimitExceededException();
	}
	
}
