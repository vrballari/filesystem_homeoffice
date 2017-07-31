package com.vb.fs;

import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.vb.fs.util.NotADirectoryException;

public class FileServiceImplTest {
	
	String scanDir = null;
	String emptyDir = null;
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		scanDir = "/dvla/dummy/";
		emptyDir = "/empty";
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test(expected = NotADirectoryException.class)
	public void getFileListTestException() throws NotADirectoryException {
		FileServiceInjector injector = null;
		Consumer app = null;

		injector = new FileServiceInjectorImpl();
		app = injector.getConsumer();
		List<String> fileList = app.retrieveFileList(scanDir);
	}
	
	@Test
	public void getFileListTestZeroSizeDir() throws NotADirectoryException {
		FileServiceInjector injector = null;
		Consumer app = null;

		injector = new FileServiceInjectorImpl();
		app = injector.getConsumer();
		List<String> fileList = app.retrieveFileList(emptyDir);
		Assert.assertEquals(0, fileList.size());
	}

}
