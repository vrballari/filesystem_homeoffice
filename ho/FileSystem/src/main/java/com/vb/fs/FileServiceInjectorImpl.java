package com.vb.fs;

/**
 * An injectors implementation
 * 
 */
public class FileServiceInjectorImpl implements FileServiceInjector {

	@Override
	public Consumer getConsumer() {
		return new FileSysApp(new FileServiceImpl());
	}

}
