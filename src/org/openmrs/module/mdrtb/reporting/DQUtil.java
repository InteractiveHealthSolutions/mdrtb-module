package org.openmrs.module.mdrtb.reporting;



public class DQUtil {

	
	public static double timeDiffInWeeks(long milli) {
		
		double diff = 0;
		
		diff = milli*1000*60*24*7;
		
		return diff;
	}
	
	
	
}
