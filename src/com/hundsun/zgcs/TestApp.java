package com.hundsun.zgcs;

import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TestApp {

	public static Logger log = LogManager.getLogger(TestApp.class);

	public static void main(String[] args) {
		
		ArrayList<String> list = new ArrayList<>();
		
		list.add("'201802070118,201802060598");
		list.add("201802060598");
		list.add("201802070118");
		
		ArrayList<String> lis = ExcelUtil.dataSeparate(list, list, list).get(0);
		ArrayList<String> li = ExcelUtil.removal(lis, lis, lis, "需求").get(0);
		System.out.println(li.size());
		
		for (int i = 0; i < li.size(); i++) {
			System.out.println(li.get(i));
		}
		
	
	}
}
