package com.hundsun.zgcs;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtil {

	public static Logger log = LogManager.getLogger(ExcelUtil.class);

	public ExcelUtil() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * 获取合适的workbook对象 ,兼容excel2003及2007以上版本
	 * 
	 * @param file
	 *            选择的文件
	 * @return 合适的workbook对象
	 */
	public static Workbook getWorkBook(File file) {
		Workbook workBook = null;
		InputStream stream;
		try {
			stream = new FileInputStream(file);
			String fileType = file.getName().substring(file.getName().lastIndexOf(".") + 1);
			if (fileType.equals("xls")) {
				log.debug("所选文件格式为xls，创建一个HSSFWorkbook对象");
				workBook = new HSSFWorkbook(stream);
			} else if (fileType.equals("xlsx")) {
				log.debug("所选文件格式为xls，创建一个XSSFWorkbook对象");
				workBook = new XSSFWorkbook(stream);
			} else {
				log.info("所选择的文件格式不正确");
			}
		} catch (FileNotFoundException e) {
			log.error("所选的文件不存在");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("文件读取异常");
			e.printStackTrace();
		}

		return workBook;
	}

	/**
	 * 获取单元格内容以String形式返回
	 * 
	 * @param cell
	 *            单元格
	 * @return 单元格内容
	 */
	public static String getStringValueFromCell(Cell cell) {
		SimpleDateFormat sFormat = new SimpleDateFormat("MM/dd/yyyy");
		DecimalFormat decimalFormat = new DecimalFormat("#.#");
		String cellValue = " ";
		if (cell == null) {
			return cellValue;
		} else if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
			cellValue = cell.getStringCellValue();

		}

		else if (cell.getCellType() == XSSFCell.CELL_TYPE_NUMERIC) {
			if (HSSFDateUtil.isCellDateFormatted(cell)) {
				double d = cell.getNumericCellValue();
				Date date = HSSFDateUtil.getJavaDate(d);
				cellValue = sFormat.format(date);
			} else {
				cellValue = decimalFormat.format((cell.getNumericCellValue()));
			}
		} else if (cell.getCellType() == Cell.CELL_TYPE_BLANK) {
			cellValue = "";
		} else if (cell.getCellType() == Cell.CELL_TYPE_BOOLEAN) {
			cellValue = String.valueOf(cell.getBooleanCellValue());
		} else if (cell.getCellType() == Cell.CELL_TYPE_ERROR) {
			cellValue = "";
		} else if (cell.getCellType() == Cell.CELL_TYPE_FORMULA) {
			cellValue = cell.getCellFormula().toString();
		}
		return cellValue;
	}

	/**
	 * 根据文件获取所有列的集合
	 * 
	 * @author xiayu20540
	 * @param file
	 *            需要操作的文件
	 * @return 所有列的链表
	 */
	public static ArrayList<ArrayList<String>> getColumnList(File file) {

		ArrayList<ArrayList<String>> colList = new ArrayList<>();
		Workbook workBook = getWorkBook(file);
		Sheet sheet = workBook.getSheetAt(0);
		// 获取行数
		int rowNum = sheet.getLastRowNum() + 1;
		log.info(file.getName()+"行数为：" + rowNum);

		Row row = sheet.getRow(0);
		// 获取列数
		int colNum = row.getPhysicalNumberOfCells();
		log.info(file.getName() + "列数为：" + colNum);
		for (int j = 0; j < colNum; j++) {
			ArrayList<String> cellList = new ArrayList<>();
			cellList.clear();
			for (int i = 0; i < rowNum; i++) {
				// 获取单元格
				Cell cell = sheet.getRow(i).getCell(j);
				// 将获取的单元格内容存到链表
				cellList.add(getStringValueFromCell(cell));
			}
			colList.add(cellList);

		}
		return colList;
	}

	/**
	 * 根据列名获得一列
	 * 
	 * @param name
	 *            列名
	 * @param file
	 *            需要操作的文件
	 * @return 列
	 */
	public static ArrayList<String> getColumByName(File file, String name) {
		log.info("获取列名为" + name + "的列");
		ArrayList<ArrayList<String>> columList = getColumnList(file);
		ArrayList<String> list = null;
		for (ArrayList<String> arrayList : columList) {
			if (arrayList.get(0).equals(name)) {
				list = arrayList;
				break;
			}
		}
		log.info(name + "列的行数：" + list.size());
		return list;

	}

	/**
	 * 导出Excel文件
	 * 
	 * @param list
	 *            需要导出的数据对象
	 * @param fileName
	 *            导出excel文件的文件名
	 * @param filePath
	 *            导出文件的路径
	 * @param columnNameList
	 *            excel文件的列名列表
	 * @param title
	 *            标签名
	 * 
	 */
	public static void printExcel(ArrayList<TjBean> list, String fileName, String filePath,
			ArrayList<String> columnNameList, String title) {
		
		TjBean bean = new TjBean();
		// 声明一个工作薄
		HSSFWorkbook workbook = new HSSFWorkbook();
		// 生成一个表格
		HSSFSheet sheet = workbook.createSheet(title);
		// 设置表格默认列宽度为15个字节
		sheet.setDefaultColumnWidth((short) 15);
		// 生成一个样式
		HSSFCellStyle style = workbook.createCellStyle();
		// 设置这些样式
		style.setFillForegroundColor(HSSFColor.SKY_BLUE.index);
		style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style.setBorderTop(HSSFCellStyle.BORDER_THIN);
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		// 生成一个字体
		HSSFFont font = workbook.createFont();
		font.setColor(HSSFColor.VIOLET.index);
		font.setFontHeightInPoints((short) 12);
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		// 把字体应用到当前的样式
		style.setFont(font);
		// 生成并设置另一个样式
		HSSFCellStyle style2 = workbook.createCellStyle();
		style2.setFillForegroundColor(HSSFColor.LIGHT_YELLOW.index);
		style2.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		style2.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style2.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style2.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style2.setBorderTop(HSSFCellStyle.BORDER_THIN);
		style2.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		style2.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		// 生成另一个字体
		HSSFFont font2 = workbook.createFont();
		font2.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
		// 把字体应用到当前的样式
		style2.setFont(font2);

		// 创建标题行（列行）
		HSSFRow nameRow = sheet.createRow(0);
		// 设置标题行内容
		for (int i = 0; i < columnNameList.size(); i++) {
			HSSFCell cell = nameRow.createCell(i);
			cell.setCellStyle(style);
			cell.setCellValue(columnNameList.get(i));
		}

		for (int j = 1; j < list.size() + 1; j++) {
			HSSFRow row = sheet.createRow(j);
			bean = list.get(j - 1);
			Field[] fields = bean.getClass().getDeclaredFields();
			for (int i = 0; i < fields.length; i++) {
				HSSFCell cell = row.createCell(i);
				cell.setCellStyle(style2);
				Field field = fields[i];
				String fieldName = field.getName();
				// 获取get方法
				String methodName = "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
				//System.out.println(methodName);
				Class clsBean = bean.getClass();
				try {
					Method getMethod = clsBean.getMethod(methodName, new Class[] {});
					Object obj = getMethod.invoke(bean, new Object[] {});
					if (obj != null) {
						cell.setCellValue(obj.toString());
					}
				} catch (NoSuchMethodException e) {
					log.error("反射机制找不到对应的方法名");
					e.printStackTrace();
				} catch (SecurityException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					log.error("访问TjBean类、字段、方法异常");
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		}

		try {
			OutputStream out = new FileOutputStream(filePath + fileName + ".xls");
			workbook.write(out);
			out.flush();
		} catch (FileNotFoundException e) {
			log.error("找不到输出的文件对象");
			e.printStackTrace();
		} catch (IOException e) {
			log.error("输出excel的IO流异常");
			e.printStackTrace();
		}

	}
}
