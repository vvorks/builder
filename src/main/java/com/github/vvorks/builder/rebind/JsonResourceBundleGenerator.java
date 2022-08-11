package com.github.vvorks.builder.rebind;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.github.jknack.handlebars.Template;
import com.github.vvorks.builder.shared.common.json.Json;
import com.github.vvorks.builder.shared.common.lang.Iterables;
import com.github.vvorks.builder.shared.common.lang.Strings;
import com.google.gwt.core.ext.UnableToCompleteException;
import com.google.gwt.core.ext.typeinfo.JClassType;
import com.google.gwt.dev.resource.Resource;
import com.google.gwt.i18n.shared.GwtLocale;

public class JsonResourceBundleGenerator extends AbstractGenerator {

	private static final String JSON_SUFFIX = ".json";

	public static class Args {
		private String packageName;
		private String typeName;
		private String className;
		private String locale;
		private List<Class<?>> imports = new ArrayList<>();
		private Map<String, Map<String, List<String>>> contents;
		public String getPackageName() {
			return packageName;
		}
		public void setPackageName(String packageName) {
			this.packageName = packageName;
		}
		public String getTypeName() {
			return typeName;
		}
		public void setTypeName(String typeName) {
			this.typeName = typeName;
		}
		public String getClassName() {
			return className;
		}
		public void setClassName(String className) {
			this.className = className;
		}
		public String getLocale() {
			return locale;
		}
		public void setLocale(String locale) {
			this.locale = locale;
		}
		public void addImports(Class<?> cls) {
			imports.add(cls);
		}
		public Iterable<String> getImportNames() {
			return Iterables.from(imports, c -> c.getName());
		}
		public Map<String, Map<String, List<String>>> getContents() {
			return contents;
		}
		public void setContents(Map<String, Map<String, List<String>>> contents) {
			this.contents = contents;
		}
	}

	@Override
	protected String doGenerate(JClassType type) throws UnableToCompleteException {
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
		//Argsの準備
		Args args = new Args();
		args.setPackageName(packageName);
		args.setTypeName(typeName);
		args.setClassName(className);
		args.setLocale(locale);
		args.addImports(Json.class);
		List<Resource> resources = findResources(packageName);
		args.setContents(getContents(resources, locale));
		//handlebars templateを適用
		try {
			Template template = getTemplate("JsonResourceBundleImpl.hbs");
			pw.print(template.apply(args));
			pw.close();
			commit(pw);
		} catch (IOException err) {
			throw new UnableToCompleteException();
		}
		return fullName;
	}

	private List<Resource> findResources(String packageName) {
		String packagePath = packageName.replace('.', '/');
		return getResources(r -> {
			String path = r.getPath();
			return path.startsWith(packagePath) && path.endsWith(JSON_SUFFIX);
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
			String name = path.substring(prefix.length(), path.length() - JSON_SUFFIX.length());
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
