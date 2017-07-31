package com.vb.fs.misc;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class CsvReader extends AbstractReader {

	@Override
	public List<String> readFille(File inputFile) throws IOException {
		List<String> lines = Files.readAllLines(Paths.get(inputFile.getAbsolutePath()));
		return lines;
	}
}
