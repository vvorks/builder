package com.github.vvorks.builder.common.io;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

public class Readers {

	public static final String EOL = System.getProperty("line.separator");

	private Readers() {
	}

	public static List<String> readLines(Reader reader) throws IOException {
		List<String> lines = new ArrayList<>();
		try(
			BufferedReader in = new BufferedReader(reader)
		) {
			String line;
			while ((line = in.readLine()) != null) {
				lines.add(line);
			}
		}
		return lines;
	}

}
