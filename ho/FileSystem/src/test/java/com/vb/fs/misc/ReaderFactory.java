package com.vb.fs.misc;


public class ReaderFactory {

	public static AbstractReader getReader(String mimeType, String extn) {
		String xcelMime = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
		String csvMime = "application/vnd.ms-excel";
		String[] xcelExt = {"xls", "xlsx"};
		String[] csvExtn = {"csv", "txt"};
		if ((mimeType != null && mimeType.equalsIgnoreCase(xcelMime)) 
				|| (extn != null && extn.equalsIgnoreCase(xcelExt[0])) 
				|| (extn != null && extn.equalsIgnoreCase(xcelExt[1]))) {
			return new ExcelReader();
		} else if ((mimeType != null && mimeType.equalsIgnoreCase(csvMime)) 
				|| (extn != null && extn.equalsIgnoreCase(csvExtn[0])) 
				|| (extn != null && extn.equalsIgnoreCase(csvExtn[1]))) {
			return new CsvReader();
		} 
		
		return null;
	}
}
