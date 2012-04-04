package com.stackbaek.rnsarith.util.test;

import java.util.Arrays;

import javax.naming.TimeLimitExceededException;

import com.stackbaek.rnsarith.domain.MRSValue;
import com.stackbaek.rnsarith.domain.RNSValue;
import com.stackbaek.rnsarith.domain.exception.OutOfRangeException;
import com.stackbaek.rnsarith.util.RNSUtil;

import junit.framework.TestCase;

public class MRSValueTest extends TestCase {
	
	public MRSValueTest(String name)
	{
		super(name);
	}
	
	public void testRNStoMRS()
	{
		int moduli[];
		int iter = 30000;
		int value;
		System.out.println("Number of test cases: " + iter);
		for(int i = 0 ; i < iter ; ++i)
		{
			try {
				moduli = RNSUtilTest.generateValidModuli();
			} catch (TimeLimitExceededException e) {
//				System.out.println("Takes too long to generate one: go to next");
				continue;
			}
			
			value = (int)(Math.random()*4000000);
//			System.out.println("value: " + value + " in RNS" + Arrays.toString(moduli));
			try {
				RNSValue rns = new RNSValue();
				rns.init(value, moduli, false);
				MRSValue mrs = RNSUtil.RNStoMRS(rns);
				
				//output
				if(value != mrs.getDecValue())
				{
					System.out.println("Test Failed for value: " + value + " in RNS" + Arrays.toString(moduli));
					MRSValue failed = RNSUtil.RNStoMRS(rns);
					System.out.println(failed.getDecValue());
				}
			} catch (OutOfRangeException e) {
//				System.out.println("Out of Range, don't consider");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
