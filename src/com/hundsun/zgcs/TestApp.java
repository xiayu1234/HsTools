package com.hundsun.zgcs;

import java.io.File;
import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TestApp {

	public TestApp() {
		// TODO Auto-generated constructor stub
	}

	public static Logger log = LogManager.getLogger(TestApp.class);

	public static void main(String[] args) {

		ArrayList<String> keyList = new ArrayList<>();
		ArrayList<String> valueList = new ArrayList<>();
		keyList.add(0, "S0000006705");
		keyList.add(1, "S0000006705-1");
		valueList.add(0, "夏雨");
		valueList.add(1, "xia雨");
		ArrayList<String> list = ExcelUtil.removal(keyList, valueList,"补丁");
		System.out.println(list.size());
		for (int i = 0; i < list.size(); i++) {
			System.out.println(list.get(i));
		}
		/**
		String str = "D:\\\\test\\\\testFile.xls";
		log.debug("获取str的路径");
		String str2 = "D:\\\\test\\\\testDic.xlsx";
		String str3 = "D:\\\\test\\\\testFile2.xls";
		String loc1 = str.replace("\\\\", "/");
		String loc2 = str2.replace("\\\\", "/");
		String loc3 = str3.replace("\\\\", "/");
		File file = new File(loc1);
		File dic = new File(loc2);
		File file1 = new File(loc3);

		ArrayList<String> list = ExcelUtil.getColumByName(file, "测试执行人");
		ArrayList<String> qxList = ExcelUtil.getColumByName(file, "补丁单编号");
		ArrayList<String> bdList = ExcelUtil.getColumByName(file1, "补丁编号");
		System.out.println(list.size());
		System.out.println(qxList.size());
		System.out.println(bdList.size());

		ArrayList<String> tempList = TjUtil.getTemFile(qxList, bdList, list);
		System.out.println(tempList.size());
		for (int i = 0; i < tempList.size(); i++) {
			System.out.println(tempList.get(i));
		}

		ArrayList<ArrayList<String>> dicList = ExcelUtil.getColumnList(dic);
		System.out.println("统计开始");
		ArrayList<TjBean> resList = TjUtil.getCount(tempList, dicList);

		ArrayList<String> list2 = new ArrayList<>();
		list2.add("姓名");
		list2.add("数量");
		System.out.println("打印开始");
		ExcelUtil.printExcel(resList, System.currentTimeMillis() + " ", "", list2, "统计数量");
		System.out.println("打印结束");
		System.out.println(file.getParent());
		*/
	}

}
