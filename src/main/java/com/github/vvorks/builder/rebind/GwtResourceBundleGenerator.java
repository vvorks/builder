package com.github.vvorks.builder.rebind;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.github.jknack.handlebars.EscapingStrategy;
import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.URLTemplateSource;
import com.github.vvorks.builder.common.io.Readers;
import com.github.vvorks.builder.common.lang.Strings;
import com.github.vvorks.builder.common.util.JsonResourcePackage;
import com.google.gwt.core.ext.Generator;
import com.google.gwt.core.ext.GeneratorContext;
import com.google.gwt.core.ext.TreeLogger;
import com.google.gwt.core.ext.UnableToCompleteException;
import com.google.gwt.core.ext.typeinfo.JClassType;
import com.google.gwt.core.ext.typeinfo.JPackage;
import com.google.gwt.core.ext.typeinfo.TypeOracle;
import com.google.gwt.dev.resource.Resource;
import com.google.gwt.dev.resource.ResourceOracle;

public class GwtResourceBundleGenerator extends Generator {

	private static final String SUFFIX = ".json";

	@Override
	public String generate(TreeLogger logger, GeneratorContext context, String fullTypeName)
			throws UnableToCompleteException {
		//処理準備
		TypeOracle types = context.getTypeOracle();
		//処理対象となる型データを取得する
		JClassType type = types.findType(fullTypeName);
		if (type == null) {
			logger.log(TreeLogger.Type.ERROR, "" + fullTypeName + " not found.");
			throw new UnableToCompleteException();
		}
		if (type == null || type.isInterface() == null) {
			logger.log(TreeLogger.Type.ERROR, "" + fullTypeName + " is not interface.");
			throw new UnableToCompleteException();
		}
		String typeName = type.getName();
		//指定されたタイプのソースが既に出力済みか否かを確認する
		String packageName = type.getPackage().getName();
		String className = typeName.replace('.', '_') + "Impl";
		String fullName = packageName + "." + className;
		PrintWriter pw = context.tryCreate(logger, packageName, className);
		if (pw == null) {
			return fullName;
		}
		//resource package取得
		List<Resource> jsonResources = findResources(logger, context);
		//contents取得
		Map<String, Map<String, List<String>>> contents = getContents(logger, context, jsonResources);
		//handlebars templateを適用
		try {
			Template template = getTemplate("GwtResourceBundleImpl.hbs");
			pw.print(template.apply(contents));
			pw.close();
			context.commit(logger, pw);
		} catch (IOException err) {
			throw new UnableToCompleteException();
		}
		return fullName;
	}

	private List<Resource> findResources(TreeLogger logger, GeneratorContext context) {
		List<String> resPkgNames = new ArrayList<>();
		for (JPackage pkg : context.getTypeOracle().getPackages()) {
			JsonResourcePackage a = pkg.getAnnotation(JsonResourcePackage.class);
			if (a != null) {
				resPkgNames.add(pkg.getName().replace('.', '/'));
			}
		}
		ResourceOracle res = context.getResourcesOracle();
		List<Resource> jsonResources = new ArrayList<>();
		for (Resource r : res.getResources()) {
			String path = r.getPath();
			if (path.endsWith(SUFFIX)) {
				for (String p : resPkgNames) {
					if (path.startsWith(p)) {
						jsonResources.add(r);
					}
				}
			}
		}
		return jsonResources;
	}

	private Map<String, Map<String, List<String>>> getContents(TreeLogger logger,
			GeneratorContext context, List<Resource> jsonResources) throws UnableToCompleteException {
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
		ResourceOracle res = context.getResourcesOracle();
		for (Resource r : jsonResources) {
			String path = r.getPath();
			String name = path.substring(prefix.length(), path.length() - SUFFIX.length());
			name = name.replace('/', '.');
			int sep = name.indexOf('_');
			String key = (sep == -1) ? name : name.substring(0, sep);
			String locale = (sep == -1) ? "" : name.substring(sep + 1);
			try {
				Reader reader = new InputStreamReader(
						res.getResourceAsStream(path), StandardCharsets.UTF_8);
				List<String> lines = Readers.readLines(reader);
				for (int i = 0; i < lines.size(); i++) {
					lines.set(i, Strings.toStringConstant(lines.get(i)));
				}
				resourceMap.computeIfAbsent(key, k -> new HashMap<>()).put(locale, lines);
			} catch (IOException err) {
				logger.log(TreeLogger.Type.ERROR, "IOException");
				throw new UnableToCompleteException();
			}
		}
		return resourceMap;
	}

	private Template getTemplate(String resName) throws IOException {
		Handlebars handlebars = new Handlebars()
				.with(EscapingStrategy.NOOP)
				.prettyPrint(true)
				;
		URL url = this.getClass().getResource(resName);
		Template template = handlebars.compile(new URLTemplateSource(resName, url));
		return template;
	}

}
