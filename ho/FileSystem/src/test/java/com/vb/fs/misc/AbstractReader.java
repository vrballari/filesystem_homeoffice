package com.vb.fs.misc;

import java.io.File;
import java.io.IOException;
import java.util.List;

public abstract class AbstractReader {
	public abstract List<String> readFille(File inputFile) throws IOException;
}
