package com.vb.fs;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import com.vb.fs.util.NotADirectoryException;
/**
 * An implementation class for FileService
 *
 */
public class FileServiceImpl implements FileService {
	private static final String CLASS_NAME = FileServiceImpl.class.getName();
	
	private static final Logger LOGGER = Logger.getLogger(CLASS_NAME);
	
	public String getFileName(File file) {
		final String METHOD_NAME = "getFileName";
		LOGGER.entering(CLASS_NAME, METHOD_NAME);
		if (file.exists() && file.isFile()) {
			LOGGER.exiting(CLASS_NAME, METHOD_NAME);
			return file.getName();
		} else {
			LOGGER.exiting(CLASS_NAME, METHOD_NAME);
			return null;
		}
	}
	
	public String getMimeType(File file) throws IOException {
		final String METHOD_NAME = "getMimeType";
		LOGGER.entering(CLASS_NAME, METHOD_NAME);
		String name = file.getName();
		Path source = Paths.get(name);
		LOGGER.exiting(CLASS_NAME, METHOD_NAME);
		return Files.probeContentType(source);		
	}
	
	public double getFileSize(File file) {
		final String METHOD_NAME = "getFileSize";
		LOGGER.entering(CLASS_NAME, METHOD_NAME);
		long byteSize = file.length();
		double kbSize = byteSize / 1024;
		LOGGER.exiting(CLASS_NAME, METHOD_NAME);
		return kbSize;
	}
	
	public String getFileExtn(File file) {
		final String METHOD_NAME = "getFileExtn";
		LOGGER.entering(CLASS_NAME, METHOD_NAME);
		String extn = "";

		int lastDot = file.getName().lastIndexOf('.');
		if (lastDot >= 0) {
			extn = file.getName().substring(lastDot + 1);
		}
		LOGGER.exiting(CLASS_NAME, METHOD_NAME);
		return extn;
	}
	
	public List<String> getFileList(String directory) throws NotADirectoryException {
		final String METHOD_NAME = "getFileList";
		LOGGER.entering(CLASS_NAME, METHOD_NAME);
		if (directory == null || directory.trim().length() == 0) {
			throw new NotADirectoryException();
		}
		File dirCheck = new File(directory);
		if (!dirCheck.exists() || !dirCheck.isDirectory()) {
			throw new NotADirectoryException("Not a directory: " + directory);
		}
        List<String> fileNames = new ArrayList<>();
        try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(Paths.get(directory))) {
            for (Path path : directoryStream) {
                fileNames.add(path.toString());
            }
        } catch (IOException ex) {
        	LOGGER.severe(CLASS_NAME + ":" + METHOD_NAME + "->" + ex);
        }
        LOGGER.exiting(CLASS_NAME, METHOD_NAME);
        return fileNames;
    }
	
	public File[] getFilteredFiles(File file, String apply) {
		final String METHOD_NAME = "getFilteredFiles";
		LOGGER.entering(CLASS_NAME, METHOD_NAME);
		final String conditions = "";
		final String[] filters = apply.split(",");
		File[] files = file.listFiles(new FilenameFilter() {
		    public boolean accept(File dir, String name) {
		    	LOGGER.exiting(CLASS_NAME, METHOD_NAME);
		        return (name.toLowerCase().endsWith("." + filters[0]) || name.toLowerCase().endsWith("." + filters[1]));
		    }
		});
		LOGGER.exiting(CLASS_NAME, METHOD_NAME);
		return files;
	}
}
