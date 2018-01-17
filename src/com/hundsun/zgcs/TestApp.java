package com.hundsun.zgcs;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class TestApp {

	public TestApp() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {

		String str = "D:\\\\test\\\\testFile.xls";
		String str2 = "D:\\\\test\\\\testDic.xlsx";
		String str3 = "D:\\\\test\\\\testFile3.xls";
		String str4 = "D:\\\\test\\\\";
		String loc1 = str.replace("\\\\", "/");
		String loc2 = str2.replace("\\\\", "/");
		String loc3 = str3.replace("\\\\", "/");
		String loc4 = str4.replace("\\\\", "/");
		File file = new File(loc1);
		File dic = new File(loc2);
		File file1 = new File(loc3);
		File test = new File(".");
		try {
			System.out.println(test.getCanonicalPath());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		ArrayList<String> list = ExcelUtil.getColumByName(file, "测试执行人");
		ArrayList<String> QxList = ExcelUtil.getColumByName(file, "补丁单编号");
		//ArrayList<String> BdList = ExcelUtil.getColumByName(file1, "补丁编号");

		//ArrayList<String> tempList = TjUtil.getTemFile(QxList, BdList, list);

		ArrayList<ArrayList<String>> dicList = ExcelUtil.getColumnList(dic);

		ArrayList<TjBean> resList = TjUtil.getCount(list, dicList);
		for (TjBean tjBean : resList) {
			System.out.println(tjBean.getName() + ":" + tjBean.getNumber());
		}
		
		ArrayList<String> list2 = new ArrayList<>();
		list2.add("姓名");
		list2.add("数量");
		ExcelUtil.printExcel(resList, "", loc3, list2, "统计数量");

	}

}
