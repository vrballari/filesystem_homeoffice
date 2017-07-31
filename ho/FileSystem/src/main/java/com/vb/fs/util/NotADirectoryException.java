package com.vb.fs.util;

public class NotADirectoryException extends Exception {
	private static final long serialVersionUID = 1L;

	public NotADirectoryException() {
		super("Not a directory");
	}

	public NotADirectoryException(String message) {
		super(message);
	}

	public NotADirectoryException(Throwable cause) {
		super(cause);
	}

	public NotADirectoryException(String message, Throwable cause) {
		super(message, cause);
	}

}
