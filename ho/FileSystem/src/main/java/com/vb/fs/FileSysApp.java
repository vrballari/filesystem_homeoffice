package com.vb.fs;

import java.io.File;
import java.io.IOException;
import java.util.List;
import com.vb.fs.util.NotADirectoryException;

public class FileSysApp implements Consumer {
	
	private FileService fileSvc;
	
	public FileSysApp(FileService svc) {
		this.fileSvc = svc;
	}

	@Override
	public String retrieveFileName(File file) {
		String name = this.fileSvc.getFileName(file);
		return name;
	}

	@Override
	public String retrieveMimeType(File file) throws IOException {
		String mime = this.fileSvc.getMimeType(file);
		return mime;
	}

	@Override
	public double retrieveFileSize(File file) {
		double size = this.fileSvc.getFileSize(file);
		return size;
	}

	@Override
	public String retrieveFileExtn(File file) {
		String extn = this.fileSvc.getFileExtn(file);
		return extn;
	}

	@Override
	public List<String> retrieveFileList(String directory) throws NotADirectoryException {
		
		File dirCheck = new File(directory);
		if (!dirCheck.exists() || !dirCheck.isDirectory()) {
			throw new NotADirectoryException("Not a directory: " + directory);
		}
		List<String> files = this.fileSvc.getFileList(directory);
		return files;
	}

	@Override
	public File[] retrieveFilteredFiles(File file, String apply) {
		File[] filteredFiles = this.fileSvc.getFilteredFiles(file, apply);
		return filteredFiles;
	}

}
