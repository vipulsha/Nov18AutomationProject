package com.gmail.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelReader {
	
	public static void readExcel() throws FileNotFoundException, IOException {
		String filePath = System.getProperty("user.dir")+"\\src\\test\\resources\\excel\\Test.xlsx";
		
		// Step#1:
		XSSFWorkbook workbook = new XSSFWorkbook(new FileInputStream(new File(filePath)));
		
		// Step#2:
		XSSFSheet sheet = workbook.getSheetAt(0);
		
		// Step#3:
		Iterator<Row> rowIterator = sheet.rowIterator();
		
		while(rowIterator.hasNext()) {
			Row row = rowIterator.next();
			Iterator<Cell> cellIterator = row.cellIterator();
			while(cellIterator.hasNext()) {
				Cell cell = cellIterator.next();
				System.out.println(cell.getStringCellValue());
			}
		}
	}
	
	public static void writeExcel() throws IOException {
		String filePath = System.getProperty("user.dir")+"\\src\\test\\resources\\excel\\Test2.xlsx";
		
		// Step#1: 
		XSSFWorkbook workbook = new XSSFWorkbook();
		
		// Step#2:
		XSSFSheet sheet = workbook.createSheet("Vipul");
		
		// Step#3:
		for(int i=0; i<5; i++) {
			XSSFRow row = sheet.createRow(i);
			for(int j=0; j<5; j++) {
				XSSFCell cell = row.createCell(j);
				cell.setCellValue("Val_+"+i+"_"+j);
			}
		}
		
		// Step#4:
		workbook.write(new FileOutputStream(new File(filePath)));
	}

	public static void main(String[] args) throws FileNotFoundException, IOException {
//		readExcel();
		writeExcel();
	}
}
