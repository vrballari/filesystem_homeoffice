package com.vb.fs;

import java.io.File;
import java.io.IOException;
import java.util.List;

import com.vb.fs.util.NotADirectoryException;

/**
* A Consumer interface for file system consumer
*
*/ 
public interface Consumer {

	public String retrieveFileName(File file);
	
	public String retrieveMimeType(File file) throws IOException;
	
	public double retrieveFileSize(File file) ;

	public String retrieveFileExtn(File file);
	
	public List<String> retrieveFileList(String directory) throws NotADirectoryException;
	
	public File[] retrieveFilteredFiles(File file, String apply);
}
