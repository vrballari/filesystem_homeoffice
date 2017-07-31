package com.vb.fs;

import java.io.File;
import java.io.IOException;
import java.util.List;

import com.vb.fs.util.NotADirectoryException;

/**
* A FileService interface for a single point to use different file system API.
*
*/
public interface FileService {
	
	public String getFileName(File file);
	
	public String getMimeType(File file) throws IOException;
	
	public double getFileSize(File file) ;

	public String getFileExtn(File file);
	
	public List<String> getFileList(String directory) throws NotADirectoryException;
	
	public File[] getFilteredFiles(File file, String apply);
}
