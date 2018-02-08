package com.hundsun.zgcs;

import java.text.DecimalFormat;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TestApp {

	public static Logger log = LogManager.getLogger(TestApp.class);

	public static void main(String[] args) {
		
	       
			double result=0.051111122111111;
	        DecimalFormat df = new DecimalFormat("0.00%");
	        String r = df.format(result);
	        System.out.println(r);
	
	}
}
