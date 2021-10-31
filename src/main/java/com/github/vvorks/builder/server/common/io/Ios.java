package com.github.vvorks.builder.server.common.io;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class Ios {

	private Ios() {
	}

	public static String getBaseName(File f) {
		String name = f.getName();
		int dotpos = name.lastIndexOf('.');
		if (dotpos <= 0) {
			return name;
		}
		return name.substring(0, dotpos);

	}

	public static String getExtent(File f) {
		String name = f.getName();
		int dotpos = name.lastIndexOf('.');
		if (dotpos <= 0) {
			return "";
		}
		return name.substring(dotpos);
	}

	public static void mkdirs(File dir) throws IOException {
		if (dir != null && !dir.exists() && !dir.mkdirs()) {
			throw new IOException();
		}
	}

	public static List<String> getResoureNames(Object obj, String path, Predicate<String> filter) throws IOException {
		List<String> list = new ArrayList<>();
		try (
			BufferedReader dir = newReader(obj.getClass().getResourceAsStream(path))
		) {
			for (String res = null; (res = dir.readLine()) != null;) {
				if (filter.test(res)) {
					list.add(res);
				}
			}
		}
		return list;
	}

	public static BufferedReader newReader(InputStream in) {
		return new BufferedReader(new InputStreamReader(in));
	}

	public static PrintWriter newWriter(OutputStream out) {
		return new PrintWriter(new BufferedWriter(new OutputStreamWriter(out)));
	}

	public static void deleteAll(File fileOrDir) throws IOException {
		if (fileOrDir.isDirectory()) {
			for (File child : fileOrDir.listFiles()) {
				deleteAll(child);
			}
			if (!fileOrDir.delete()) {
				throw new IOException("DELETE FAILED " + fileOrDir);
			}
		} else {
			if (fileOrDir.exists() && !fileOrDir.delete()) {
				throw new IOException("DELETE FAILED " + fileOrDir);
			}
		}
	}

}
