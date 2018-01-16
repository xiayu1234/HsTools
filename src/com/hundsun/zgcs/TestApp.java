package com.hundsun.zgcs;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

public class TestApp {

	public TestApp() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {

		String str = "D:\\\\test\\\\testFile.xls";
		String str2 = "D:\\\\test\\\\testDic.xlsx";
		String str3 = "D:\\\\test\\\\testFile2.xls";
		String loc1 = str.replace("\\\\", "/");
		String loc2 = str2.replace("\\\\", "/");
		String loc3 = str3.replace("\\\\", "/");
		File file = new File(loc1);
		File dic = new File(loc2);
		File file1 = new File(loc3);

		// ArrayList<String> list = ExcelUtil.getColumByName(file, "测试执行人");
		ArrayList<String> QxList = ExcelUtil.getColumByName(file, "补丁单编号");
		ArrayList<String> BdList = ExcelUtil.getColumByName(file1, "补丁编号");

		ArrayList<String> list = TjUtil.getTemFile(ExcelUtil.getColumByName(file, "补丁单编号"),
				ExcelUtil.getColumByName(file1, "补丁编号"), ExcelUtil.getColumByName(file1, "测试执行人"));

		ArrayList<ArrayList<String>> dicList = ExcelUtil.getColumnList(dic);

		HashMap<String, Integer> map = TjUtil.getCount(list, dicList);

		for (int i = 0; i < dicList.size(); i++) {
			for (int j = 0; j < dicList.get(i).size(); j++) {
				System.out.println(dicList.get(i).get(j) + ":" + map.get(dicList.get(i).get(j)));
			}
		}

	}

}
