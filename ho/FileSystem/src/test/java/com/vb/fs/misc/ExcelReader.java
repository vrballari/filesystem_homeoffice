package com.vb.fs.misc;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelReader extends AbstractReader {

	private XSSFWorkbook myWorkBook;

	@Override
	public List<String> readFille(File inputFile) throws IOException {
		List<String> lines = new ArrayList<String>();
		FileInputStream fis = new FileInputStream(inputFile);
		myWorkBook = new XSSFWorkbook (fis);
		XSSFSheet mySheet = myWorkBook.getSheetAt(0);
		Iterator<Row> rowIterator = mySheet.iterator();
		while (rowIterator.hasNext()) { 
			Row row = rowIterator.next();
			Iterator<Cell> cellIterator = row.cellIterator();
			String box = "";
			while (cellIterator.hasNext()) {
				Cell cell = cellIterator.next();
				if (cell.getStringCellValue() != null) {
					box = box + cell.getStringCellValue() + ",";
				}				
			}
			box = box.substring(0, box.length() - 1);
			lines.add(box);
		}
		fis.close();
		return lines;
	}
}
