package com.hundsun.zgcs;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtil {
	


	public ExcelUtil() {
		// TODO Auto-generated constructor stub
	}
	
	/**
	 *获取合适的workbook对象 ,兼容excel2003及2007以上版本
	 * @param file
	 * 选择的文件
	 * @return
	 * 合适的workbook对象 
	 */
	public static Workbook getWorkBook(File file) {
		Workbook workBook = null;
		InputStream stream;
		try {
			stream = new FileInputStream(file);
			String fileType = file.getName().substring(file.getName().lastIndexOf(".") + 1);
			if(fileType.equals("xls")) {
				workBook = new HSSFWorkbook(stream);  
			}else if (fileType.equals("xlsx")) {
				workBook = new XSSFWorkbook(stream);  
			}else {
				System.out.println("文件格式不正确");	
			}
		} catch (FileNotFoundException e) {
			System.out.println("找不到对应的文件");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("文件读取异常");
			e.printStackTrace();
		}
		
	 return workBook;
	}
	
	/**
	 * 获取单元格内容以String形式返回
	 * @param cell
	 * 单元格
	 * @return
	 * 单元格内容
	 */
	  public static String getStringValueFromCell(Cell cell) {
	        SimpleDateFormat sFormat = new SimpleDateFormat("MM/dd/yyyy");
	        DecimalFormat decimalFormat = new DecimalFormat("#.#");
	        String cellValue = " ";
	        if(cell == null) {
	            return cellValue;
	        }
	        else if(cell.getCellType() == Cell.CELL_TYPE_STRING) {
	            cellValue = cell.getStringCellValue();
	        }

	        else if(cell.getCellType() == XSSFCell.CELL_TYPE_NUMERIC) {
	            if(HSSFDateUtil.isCellDateFormatted(cell)) {
	                double d = cell.getNumericCellValue();
	                Date date = HSSFDateUtil.getJavaDate(d);
	                cellValue = sFormat.format(date);
	            }
	            else {                
	                cellValue = decimalFormat.format((cell.getNumericCellValue()));
	            }
	        }
	        else if(cell.getCellType() == Cell.CELL_TYPE_BLANK) {
	            cellValue = "";
	        }
	        else if(cell.getCellType() == Cell.CELL_TYPE_BOOLEAN) {
	            cellValue = String.valueOf(cell.getBooleanCellValue());
	        }
	        else if(cell.getCellType() == Cell.CELL_TYPE_ERROR) {
	            cellValue = "";
	        }
	        else if(cell.getCellType() == Cell.CELL_TYPE_FORMULA) {
	            cellValue = cell.getCellFormula().toString();
	        }
	        return cellValue;
	    }
	
	/**
	 * 根据文件获取所有列的集合
	 * @author xiayu20540
	 * @param file
	 * 需要操作的文件
	 * @return
	 * 所有列的链表
	 */
	public static ArrayList<ArrayList<String>> getColumnList(File file) {
		
		
		ArrayList<ArrayList<String>>  colList = new ArrayList<>();
		Workbook workBook = getWorkBook(file);
		Sheet sheet = workBook.getSheetAt(0);
		//获取行数
		int rowNum = sheet.getLastRowNum()+1;
		
		Row row  = sheet.getRow(0);
		//获取列数
		int colNum = row.getPhysicalNumberOfCells();
		int num = 0;
		for (int j = 0; j < colNum; j++) {
			ArrayList<String> cellList = new ArrayList<>();
				cellList.clear();
				for (int i = 0; i < rowNum; i++) {
					//获取单元格
					Cell cell = sheet.getRow(i).getCell(j);
					//将获取的单元格内容存到链表
					cellList.add(getStringValueFromCell(cell));
				}
				colList.add(cellList);
			
		}
		return colList;
	}

	/**
	 * 根据列名获得一列
	 * @param name
	 * 列名
	 * @param file
	 * 需要操作的文件
	 * @return
	 * 列
	 */
	public static ArrayList<String> getColumByName(File file ,String name){
		
		ArrayList<ArrayList<String>> columList = getColumnList(file);
		ArrayList<String> list = null; 
		for (ArrayList<String> arrayList : columList) {
			if (arrayList.get(0).equals(name)) {
				list = arrayList;
				break;
			}
		}
		return list;
		
		
	}
}
