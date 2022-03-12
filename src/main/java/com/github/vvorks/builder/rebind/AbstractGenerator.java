package com.github.vvorks.builder.rebind;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;

import com.github.jknack.handlebars.EscapingStrategy;
import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.URLTemplateSource;
import com.github.vvorks.builder.common.io.Readers;
import com.google.gwt.core.ext.Generator;
import com.google.gwt.core.ext.GeneratorContext;
import com.google.gwt.core.ext.PropertyOracle;
import com.google.gwt.core.ext.TreeLogger;
import com.google.gwt.core.ext.UnableToCompleteException;
import com.google.gwt.core.ext.typeinfo.JClassType;
import com.google.gwt.core.ext.typeinfo.JMethod;
import com.google.gwt.core.ext.typeinfo.JPackage;
import com.google.gwt.core.ext.typeinfo.TypeOracle;
import com.google.gwt.dev.resource.Resource;
import com.google.gwt.i18n.rebind.LocaleUtils;
import com.google.gwt.i18n.shared.GwtLocale;

public abstract class AbstractGenerator extends Generator {

	private TreeLogger logger;

	private GeneratorContext context;

	@Override
	public String generate(TreeLogger logger, GeneratorContext context, String fullTypeName)
			throws UnableToCompleteException {
		//処理準備
		this.logger = logger;
		this.context = context;
		TypeOracle types = context.getTypeOracle();
		//処理対象となる型データを取得する
		JClassType type = types.findType(fullTypeName);
		if (type == null) {
			error("" + fullTypeName + " not found.");
			throw new UnableToCompleteException();
		}
		if (type.isInterface() == null) {
			error("" + fullTypeName + " is not interface.");
			throw new UnableToCompleteException();
		}
		return doGenerate(type);
	}

	protected abstract String doGenerate(JClassType type) throws UnableToCompleteException;

	protected GwtLocale getCompileLocale() {
		PropertyOracle propertyOracle = context.getPropertyOracle();
		LocaleUtils localeUtils = LocaleUtils.getInstance(logger, propertyOracle, context);
		GwtLocale gwtLocale = localeUtils.getCompileLocale();
		return gwtLocale;
	}

	protected JPackage getPackage(String name) {
		return context.getTypeOracle().findPackage(name);
	}

	protected List<JPackage> getPackages() {
		return Arrays.asList(context.getTypeOracle().getPackages());
	}

	protected List<JPackage> getPackages(Predicate<JPackage> func) {
		List<JPackage> result = new ArrayList<>();
		for (JPackage pkg : context.getTypeOracle().getPackages()) {
			if (func.test(pkg)) {
				result.add(pkg);
			}
		}
		return result;
	}

	protected List<Resource> getResources() {
		return new ArrayList<>(context.getResourcesOracle().getResources());
	}

	protected List<Resource> getResources(Predicate<Resource> func) {
		List<Resource> result = new ArrayList<>();
		for (Resource r : context.getResourcesOracle().getResources()) {
			if (func.test(r)) {
				result.add(r);
			}
		}
		return result;
	}

	protected List<String> getTextResource(String path, UnaryOperator<String> escape) throws IOException {
		try (
			InputStream is = context.getResourcesOracle().getResourceAsStream(path)
		) {
			return Readers.readLines(new InputStreamReader(is, StandardCharsets.UTF_8), escape);
		}
	}

	protected JClassType getType(Class<?> cls) {
		return getType(cls.getName());
	}

	protected JClassType getType(String name) {
		return context.getTypeOracle().findType(name);
	}

	protected List<JClassType> getTypes() {
		return Arrays.asList(context.getTypeOracle().getTypes());
	}

	protected List<JClassType> getTypes(Predicate<JClassType> func) {
		List<JClassType> result = new ArrayList<>();
		for (JClassType t : context.getTypeOracle().getTypes()) {
			if (func.test(t)) {
				result.add(t);
			}
		}
		return result;
	}

	protected List<JMethod> getMethods(JClassType t) {
		return Arrays.asList(t.getMethods());
	}

	protected List<JMethod> getMethods(JClassType t, Predicate<JMethod> func) {
		List<JMethod> result = new ArrayList<>();
		for (JMethod m : t.getMethods()) {
			if (func.test(m)) {
				result.add(m);
			}
		}
		return result;
	}

	protected PrintWriter getWriter(String packageName, String className) {
		return context.tryCreate(logger, packageName, className);
	}

	protected void commit(PrintWriter pw) {
		context.commit(logger, pw);
	}

	protected Template getTemplate(String resName) throws IOException {
		Handlebars handlebars = new Handlebars()
				.with(EscapingStrategy.NOOP)
				.prettyPrint(true)
				;
		URL url = this.getClass().getResource(resName);
		Template template = handlebars.compile(new URLTemplateSource(resName, url));
		return template;
	}

	protected void error(String msg) {
		logger.log(TreeLogger.Type.ERROR, msg);
	}

	protected void warn(String msg) {
		logger.log(TreeLogger.Type.WARN, msg);
	}

	protected void info(String msg) {
		logger.log(TreeLogger.Type.INFO, msg);
	}

	protected void debug(String msg) {
		logger.log(TreeLogger.Type.DEBUG, msg);
	}

}
