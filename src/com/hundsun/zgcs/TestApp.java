package com.hundsun.zgcs;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

public class TestApp {

	public TestApp() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		
		String str="D:\\\\test\\\\testFile.xls";  
		String str2 = "D:\\\\test\\\\testDic.xlsx";
		String loc1=str.replace("\\\\", "/"); 
		String loc2 = str2.replace("\\\\", "/");
		File file = new File(loc1);
		File dic = new File(loc2);

		ArrayList<String> list = ExcelUtil.getColumByName(file, "测试执行人");
		/**
		for (int i = 0; i < list.size(); i++) {
			if(list.get(i).indexOf("夏雨") != -1) {
				System.out.println(list.get(i));
			}
			
		}
		System.out.println(list.size());
		*/
		ArrayList<ArrayList<String>> dicList = ExcelUtil.getColumnList(dic);
		//System.out.println(dicList.size());
		HashMap<String, Integer> map = TjUtil.getCount(list, dicList);
	//	System.out.println(map.size());
		
		
		for (int i = 0; i < dicList.size(); i++) {
			for (int j = 0; j < dicList.get(i).size(); j++) {
				System.out.println(dicList.get(i).get(j) + ":" + map.get(dicList.get(i).get(j)));
			}
		}
		
	}

}
