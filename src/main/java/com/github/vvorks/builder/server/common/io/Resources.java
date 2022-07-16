package com.github.vvorks.builder.server.common.io;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.security.CodeSource;
import java.util.Enumeration;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.function.Predicate;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import com.github.vvorks.builder.common.logging.Logger;

public class Resources {

	private static final Logger LOGGER = Logger.createLogger(Resources.class);

	private static final Predicate<String> ANY = url -> true;

	private Resources() {
	}

	private static void collectURI(String path, Predicate<String> filter, Set<String> into) {
		if (filter.test(path)) {
			into.add(path);
		}
	}

	private static void iterateFileSystem(File dir, String prefix,
			Predicate<String> filter, Set<String> into) throws IOException {
		File[] files = dir.listFiles();
		for (File file : files) {
			if (file.isDirectory()) {
				iterateFileSystem(file, prefix, filter, into);
			} else if (file.isFile()) {
				String resPath = file.getPath().substring(prefix.length()).replace('\\', '/');
				collectURI(resPath, filter, into);
			}
		}
	}

	private static void iterateJarFile(File file, String prefix,
			Predicate<String> filter, Set<String> into) throws IOException {
		try (
			JarFile jarFile = new JarFile(file)
		) {
			Enumeration<JarEntry> je = jarFile.entries();
			while (je.hasMoreElements()) {
				JarEntry j = je.nextElement();
				String name = j.getName();
				if (!j.isDirectory() && name.startsWith(prefix)) {
					String resPath = name.substring(prefix.length()).replace('\\', '/');
					collectURI(resPath, filter, into);
				}
			}
		}
	}

	private static void iterateEntry(URI uri,
			Predicate<String> filter, Set<String> into) throws IOException {
		if (uri.getScheme().equals("jar")) {
			String[] bodies = uri.getSchemeSpecificPart().split("!/");
			try {
				File jarFile = new File(new URL(bodies[0]).toURI());
				StringBuilder sb = new StringBuilder();
				for (int i = 1; i < bodies.length; i++) {
					String part = bodies[i];
					sb.append(part);
				}
				String prefix = sb.toString();
				iterateJarFile(jarFile, prefix, filter, into);
			} catch (MalformedURLException|URISyntaxException e) {
				LOGGER.error(e);
			}
		} else {
			File file = new File(uri);
			if (file.isDirectory()) {
				iterateFileSystem(file, file.getPath(), filter, into);
			}
		}
	}

	public static Set<String> getResourceNames(Class<?> rootClass) throws IOException {
		return getResourceNames(rootClass, ANY);
	}

	public static Set<String> getResourceNames(Class<?> rootClass, Predicate<String> filter) throws IOException {
		Set<String> names = new LinkedHashSet<>();
		CodeSource src = rootClass.getProtectionDomain().getCodeSource();
		try {
			iterateEntry(src.getLocation().toURI(), filter, names);
		} catch (IOException | URISyntaxException e) {
			LOGGER.error(e);
		}
		return names;
	}

}