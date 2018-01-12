package com.hundsun.zgcs;

import java.io.File;
import java.util.ArrayList;

import org.apache.poi.ss.usermodel.Workbook;

public class TestApp {

	public TestApp() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		File file = new File("C:\\Users\\xiayu20540\\Desktop\\ModifyDetail-595799427.xls");
		Workbook workBook = ExcelUtil.getWorkBook(file);
		ArrayList<ArrayList<String>> list = ExcelUtil.getColumnList(file);
		ArrayList<String> cellList = ExcelUtil.getColumByName(file, "修改单编号");
		for (String string : cellList) {
			System.out.println(string);
		}
		

	}

}
