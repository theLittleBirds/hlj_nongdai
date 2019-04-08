package com.nongyeos.loan.base.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class ExcelShower {

	
	/**
	* filePath 文件路径
	* unCaseRow  要排除的行数（从上往下）
	* unCaseLine  要排除的列数（从左往右）
	*/
	public static List<String[]> readExcel(String filePath, int unCaseRow, int unCaseLine) throws Exception {
	   Sheet sheet = null;
	    FileInputStream inStream = null;
	        try {
	            inStream = new FileInputStream(new File(filePath));
	            Workbook workBook = WorkbookFactory.create(inStream);
	 
	            sheet = workBook.getSheetAt(0);
	        } catch (Exception e) {
	            e.printStackTrace();
	            throw new Exception();
	        } finally {
	            try {
	                if (inStream != null) {
	                    inStream.close();
	                }
	            } catch (IOException e) {
	                e.printStackTrace();
	            }
	        }
	 
	        List<String[]> list = init(sheet, unCaseRow, unCaseLine);// 需要排除行数
	 
	        return list;
	    }
	 
	// 初始化表格中的每一行，并得到每一个单元格的值
	private static List<String[]> init(Sheet sheet, int unCaseRow, int unCaseLine) {
	    int rowNum = sheet.getLastRowNum() + 1; // 从零开始
	    List<String[]> result = new ArrayList<String[]>();
	    String[] rowArr = null;
	 
	    Row row = null;
	    Cell cell = null;
	    int rowLength = 0;
	    int rowIndex = 0;
	    String rowStr = null;
	    for (int i = unCaseRow; i < rowNum; i++) {
	        row = sheet.getRow(i);
	        // 每有新的一行，创建一个新的LinkedList对象
	        rowLength = row.getLastCellNum();
	        rowIndex = 0;
	        if(rowLength > 0){
		        rowArr = new String[rowLength];
		        for (int j = unCaseLine; j < rowLength; j++) {
		            cell = row.getCell(j);
		            // 获取单元格的值
		            rowStr = getCellValue(cell);
		            // 将得到的值放入链表中
		            rowArr[rowIndex++] = rowStr;
		        }
		        result.add(rowArr);
	        }
	    }
	 
	    return result;
	}
	 
	// 获取单元格的值
	@SuppressWarnings("deprecation")
	private static String getCellValue(Cell cell) {
	    String cellValue = "";
	    DataFormatter formatter = new DataFormatter();
	    if (cell != null) {
	        // 判断单元格数据的类型，不同类型调用不同的方法
	        switch (cell.getCellType()) {
	        // 数值类型
	        case Cell.CELL_TYPE_NUMERIC:
	            // 进一步判断 ，单元格格式是日期格式
	            if (DateUtil.isCellDateFormatted(cell)) {
	                cellValue = formatter.formatCellValue(cell);
	            } else {
	                // 数值
	                double value = cell.getNumericCellValue();
	                int intValue = (int) value;
	                cellValue = value - intValue == 0 ? String.valueOf(intValue) : String.valueOf(value);
	            }
	            break;
	        case Cell.CELL_TYPE_STRING:
	            cellValue = cell.getStringCellValue();
	            break;
	        case Cell.CELL_TYPE_BOOLEAN:
	            cellValue = String.valueOf(cell.getBooleanCellValue());
	            break;
	        // 判断单元格是公式格式，需要做一种特殊处理来得到相应的值
	        case Cell.CELL_TYPE_FORMULA: {
	            try {
	                cellValue = String.valueOf(cell.getNumericCellValue());
	            } catch (IllegalStateException e) {
	                cellValue = String.valueOf(cell.getRichStringCellValue());
	            }
	 
	        }
	            break;
	        case Cell.CELL_TYPE_BLANK:
	            cellValue = "";
	            break;
	        case Cell.CELL_TYPE_ERROR:
	            cellValue = "";
	            break;
	        default:
	            cellValue = cell.toString().trim();
	            break;
	        }
	    }
	    return cellValue.trim();
	}
}
