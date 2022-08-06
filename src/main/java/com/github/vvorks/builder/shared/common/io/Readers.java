package com.github.vvorks.builder.shared.common.io;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.function.UnaryOperator;

public class Readers {

	public static final String EOL = System.getProperty("line.separator");

	private Readers() {
	}

	public static List<String> readLines(Reader reader, UnaryOperator<String> escape) throws IOException {
		List<String> lines = new ArrayList<>();
		try(
			BufferedReader in = new BufferedReader(reader)
		) {
			String line;
			while ((line = in.readLine()) != null) {
				lines.add(escape.apply(line));
			}
		}
		return lines;
	}

}
