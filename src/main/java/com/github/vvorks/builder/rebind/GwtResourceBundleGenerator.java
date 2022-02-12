package com.github.vvorks.builder.rebind;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.github.jknack.handlebars.Template;
import com.github.vvorks.builder.common.lang.Strings;
import com.github.vvorks.builder.common.util.JsonResourcePackage;
import com.google.gwt.core.ext.UnableToCompleteException;
import com.google.gwt.core.ext.typeinfo.JClassType;
import com.google.gwt.core.ext.typeinfo.JPackage;
import com.google.gwt.dev.resource.Resource;
import com.google.gwt.i18n.shared.GwtLocale;

public class GwtResourceBundleGenerator extends AbstractGenerator {

	private static final String SUFFIX = ".json";

	public static class Param {

		private final String locale;

		private final Map<String, Map<String, List<String>>> contents;

		public Param(String locale, Map<String, Map<String, List<String>>> contents) {
			this.locale = locale;
			this.contents = contents;
		}

		public String getLocale() {
			return locale;
		}

		public Map<String, Map<String, List<String>>> getContents() {
			return contents;
		}

	}

	@Override
	public String doGenerate(JClassType type) throws UnableToCompleteException {
		//ロケール取得
		GwtLocale gwtLocale = getCompileLocale();
		String locale = gwtLocale.toString();
		//指定されたタイプのソースが既に出力済みか否かを確認する
		String typeName = type.getName();
		String packageName = type.getPackage().getName();
		String className = typeName.replace('.', '_') + "_" + locale;
		String fullName = packageName + "." + className;
		PrintWriter pw = getWriter(packageName, className);
		if (pw == null) {
			return fullName;
		}
		info("gennerate " + fullName);
		//resource package取得
		List<Resource> jsonResources = findResources();
		//contents取得
		Param param = new Param(locale, getContents(jsonResources, locale));
		//handlebars templateを適用
		try {
			Template template = getTemplate("GwtResourceBundleImpl.hbs");
			pw.print(template.apply(param));
			pw.close();
			commit(pw);
		} catch (IOException err) {
			throw new UnableToCompleteException();
		}
		return fullName;
	}

	private List<Resource> findResources() {
		List<JPackage> pkgs = getPackages(p -> p.getAnnotation(JsonResourcePackage.class) != null);
		return getResources(r -> {
			String path = r.getPath();
			if (path.endsWith(SUFFIX)) {
				for (JPackage p : pkgs) {
					String name = p.getName().replace('.', '/');
					if (path.startsWith(name)) {
						return true;
					}
				}
			}
			return false;
		});
	}

	private Map<String, Map<String, List<String>>> getContents(
			List<Resource> jsonResources, String locale) throws UnableToCompleteException {
		int n = jsonResources.size();
		if (n == 0) {
			return Collections.emptyMap();
		}
		Map<String, Map<String, List<String>>> resourceMap = new HashMap<>();
		String prefix = jsonResources.get(0).getPath();
		for (int i = 1; i < n; i++) {
			prefix = Strings.commonPrefix(prefix, jsonResources.get(i).getPath());
		}
		prefix = prefix.substring(0, prefix.lastIndexOf('/') + 1);
		for (Resource r : jsonResources) {
			String path = r.getPath();
			String name = path.substring(prefix.length(), path.length() - SUFFIX.length());
			name = name.replace('/', '.');
			int sep = name.indexOf('_');
			String key = (sep == -1) ? name : name.substring(0, sep);
			String resLocale = (sep == -1) ? "" : name.substring(sep + 1);
			if (resLocale.isEmpty() || locale.startsWith(resLocale)) {
				try {
					List<String> lines = getTextResource(path, s -> Strings.toStringConstant(s));
					resourceMap.computeIfAbsent(key, k -> new HashMap<>()).put(resLocale, lines);
				} catch (IOException err) {
					error("IOException");
					throw new UnableToCompleteException();
				}
			}
		}
		return resourceMap;
	}

}
