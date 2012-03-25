package com.stackbaek.rnsarith.util.test;

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
			RNSUtil.validateModuli(moduli, true);
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
				moduli = _generateValidModuli();
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
	
	public void test()
	{
//		int[] mod = {16, 15, 7};
//	
//		int[] r1 = {8, 3, 0};
//		int[] r2 = {7, 13, -1};
//		int[] result1 = RNSUtil.RNS_multiply(r1, r2, mod);
//		System.out.println(Arrays.toString(result1));
//		int[] subtract = {result1[1], result1[1], -1};
//		int[] result2 = RNSUtil.RNS_subtract(result1, subtract, mod);
//		System.out.println(Arrays.toString(result2));
//		
//		int a = (15*15) % 16;
//		System.out.println(a);
	}
	
	
	// ------- Test util ------
	private int[] _generateValidModuli() throws TimeLimitExceededException
	{
		int len = (int)(Math.random()*6) +1;
		int[] moduli = new int[len];
		for(int t = 0 ; t < 100 ; ++t)
		{
			for(int i = 0 ; i < len ; ++i)
			{
				moduli[i] = (int)(Math.random()*100+1);
			}
			if(RNSUtil.validateModuli(moduli, false))
			{
				return moduli;
			}
		}
		throw new TimeLimitExceededException();
	}
	
}
