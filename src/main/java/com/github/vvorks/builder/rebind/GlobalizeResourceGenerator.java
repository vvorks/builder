package com.github.vvorks.builder.rebind;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import com.github.vvorks.builder.client.gwt.util.GlobalizeResource;
import com.github.vvorks.builder.client.gwt.util.GlobalizeResource.Require;
import com.github.vvorks.builder.common.lang.Iterables;
import com.google.gwt.core.ext.Generator;
import com.google.gwt.core.ext.GeneratorContext;
import com.google.gwt.core.ext.PropertyOracle;
import com.google.gwt.core.ext.TreeLogger;
import com.google.gwt.core.ext.UnableToCompleteException;
import com.google.gwt.core.ext.typeinfo.JMethod;
import com.google.gwt.i18n.rebind.LocaleUtils;
import com.google.gwt.i18n.shared.GwtLocale;
import com.google.gwt.resources.ext.AbstractResourceGenerator;
import com.google.gwt.resources.ext.ResourceContext;
import com.google.gwt.resources.ext.SupportsGeneratorResultCaching;
import com.google.gwt.user.rebind.SourceWriter;
import com.google.gwt.user.rebind.StringSourceWriter;

public class GlobalizeResourceGenerator extends AbstractResourceGenerator implements SupportsGeneratorResultCaching {

	private static final int REQUIRE_CORE			= 0x0001;
	private static final int REQUIRE_MESSAGE		= 0x0002;
	private static final int REQUIRE_NUMBER			= 0x0004;
	private static final int REQUIRE_PLURAL			= 0x0008;
	private static final int REQUIRE_DATE			= 0x0010;
	private static final int REQUIRE_CURRENCY		= 0x0020;
	private static final int REQUIRE_RELATIVE_TIME	= 0x0040;
	private static final int REQUIRE_UNIT			= 0x0080;

	private static final int LOC_CLDR				= 0x0100;
	private static final int LOC_CODE				= 0x0200;
	private static final int LOC_DATA				= 0x0400;

	private static final String RES_CLDR = "META-INF/resources/webjars/cldrjs/dist/";

	private static final String RES_CODE = "META-INF/resources/webjars/globalize/dist/";

	private static final Map<String, Integer> REQUIRE_MAP;
	static {
		Map<String, Integer> m =  new HashMap<>();
		m.put("core",         REQUIRE_CORE);
		m.put("message",      REQUIRE_CORE|REQUIRE_MESSAGE);
		m.put("number",       REQUIRE_CORE|REQUIRE_NUMBER );
		m.put("plural",       REQUIRE_CORE|REQUIRE_PLURAL );
		m.put("currency",     REQUIRE_CORE|REQUIRE_NUMBER |REQUIRE_PLURAL|REQUIRE_CURRENCY);
		m.put("date",         REQUIRE_CORE|REQUIRE_NUMBER |REQUIRE_DATE);
		m.put("relativeTime", REQUIRE_CORE|REQUIRE_NUMBER |REQUIRE_PLURAL|REQUIRE_RELATIVE_TIME);
		m.put("unit",         REQUIRE_CORE|REQUIRE_NUMBER |REQUIRE_PLURAL|REQUIRE_UNIT);
		REQUIRE_MAP = m;
	}

	private static final int REQUIRE_DEFAULT =
		REQUIRE_CORE|REQUIRE_MESSAGE|REQUIRE_NUMBER|REQUIRE_PLURAL|REQUIRE_DATE|REQUIRE_CURRENCY;

	private static class ResourceFile {
		private final int flag;
		private final String path;
		private ResourceFile(int flag, String path) {
			this.flag = flag;
			this.path = path;
		}
		public boolean isSet(int f) {
			return (flag & f) != 0;
		}
	}

	private static ResourceFile res(int flags, String path) {
		return new ResourceFile(flags, path);
	}

	private static final ResourceFile[] RESOURCE_FILES = {

		res(REQUIRE_CORE|LOC_CLDR, "cldr.js"),
		res(REQUIRE_CORE|LOC_CLDR, "cldr/event.js"),
		res(REQUIRE_CORE|LOC_CLDR, "cldr/supplemental.js"),

		res(REQUIRE_CORE|LOC_CODE, "globalize.js"),
		res(REQUIRE_CORE|LOC_DATA, "supplemental/likelySubtags.json"),

		res(REQUIRE_MESSAGE|LOC_CODE, "globalize/message.js"),

		res(REQUIRE_NUMBER|LOC_CODE, "globalize/number.js"),
		res(REQUIRE_NUMBER|LOC_DATA, "main/$/numbers.json"),
		res(REQUIRE_NUMBER|LOC_DATA, "supplemental/numberingSystems.json"),

		res(REQUIRE_PLURAL|LOC_CODE, "globalize/plural.js"),
		res(REQUIRE_PLURAL|LOC_DATA, "supplemental/plurals.json"),
		res(REQUIRE_PLURAL|LOC_DATA, "supplemental/ordinals.json"),

		res(REQUIRE_DATE|LOC_CODE, "globalize/date.js"),
		res(REQUIRE_DATE|LOC_DATA, "main/$/ca-gregorian.json"),
		res(REQUIRE_DATE|LOC_DATA, "main/$/timeZoneNames.json"),
		res(REQUIRE_DATE|LOC_DATA, "supplemental/timeData.json"),
		res(REQUIRE_DATE|LOC_DATA, "supplemental/weekData.json"),
		res(REQUIRE_DATE|LOC_DATA, "supplemental/calendarData.json"),
		res(REQUIRE_DATE|LOC_DATA, "supplemental/calendarPreferenceData.json"),
		res(REQUIRE_DATE|LOC_DATA, "main/$/ca-japanese.json"),  //TODO 仮

		res(REQUIRE_CURRENCY|LOC_CODE, "globalize/currency.js"),
		res(REQUIRE_CURRENCY|LOC_DATA, "main/$/currencies.json"),
		res(REQUIRE_CURRENCY|LOC_DATA, "supplemental/currencyData.json"),

		res(REQUIRE_RELATIVE_TIME|LOC_CODE, "globalize/relative-time.js"),
		res(REQUIRE_RELATIVE_TIME|LOC_DATA, "main/$/dateFields.json"),

		res(REQUIRE_UNIT|LOC_CODE, "globalize/unit.js"),
		res(REQUIRE_UNIT|LOC_DATA, "main/$/unit.json"),

	};



	@Override
	public String createAssignment(TreeLogger logger, ResourceContext context, JMethod method) throws UnableToCompleteException {
		int require = getRequireFlags(method);
		ClassLoader loader = getClass().getClassLoader();
		//write code
		SourceWriter sw = new StringSourceWriter();
		GwtLocale locale = getCompileLocale(logger, context.getGeneratorContext());
		sw.println("new %s() {", GlobalizeResource.class.getName());
			sw.indent();
			sw.println("private String[] jsContents = {");
			sw.indent();
			try {
				for (ResourceFile e : RESOURCE_FILES) {
					if (e.isSet(require) && e.isSet(LOC_CLDR)) {
						writeJsContent(sw, loader, e, RES_CLDR);
					} else if (e.isSet(require) && e.isSet(LOC_CODE)) {
						writeJsContent(sw, loader, e, RES_CODE);
					}
				}
			} catch (IOException err) {
				logger.log(TreeLogger.ERROR, "I/O Exeption", err);
				throw new UnableToCompleteException();
			}
			sw.outdent();
			sw.println("};");
			sw.println("private String[] jsonContents = {");
			sw.indent();
			try {
				List<String> names = new ArrayList<>();
				for (ResourceFile e : RESOURCE_FILES) {
					if (e.isSet(require) && e.isSet(LOC_DATA)) {
						int pos = e.path.indexOf('$');
						String name;
						if (pos != -1) {
							name = e.path.substring(0, pos) + locale.getAsString() + e.path.substring(pos + 1);
						} else {
							name = e.path;
						}
						names.add(name);
					}
				}
				writeCldrContents(sw, loader, names);
			} catch (IOException err) {
				logger.log(TreeLogger.ERROR, "I/O Exeption", err);
				throw new UnableToCompleteException();
			}
			sw.outdent();
			sw.println("};");
			sw.println("public String getLocale() {");
				sw.indent();
				sw.println("return \"%s\";", locale.getAsString());
				sw.outdent();
			sw.println("}");
			sw.println("public String[] getJsContents() {");
				sw.indent();
				sw.println("return jsContents;");
				sw.outdent();
			sw.println("}");
			sw.println("public String[] getJsonContents() {");
				sw.indent();
				sw.println("return jsonContents;");
				sw.outdent();
			sw.println("}");
			sw.println("public String getName() {");
				sw.indent();
				sw.println("return \"%s\";", method.getName());
				sw.outdent();
			sw.println("}");
			sw.outdent();
		sw.println("}");
		return sw.toString();
	}

	private int getRequireFlags(JMethod method) {
		int require;
		Require a = method.getAnnotation(GlobalizeResource.Require.class);
		if (a != null) {
			require = 0;
			for (String v : a.value()) {
				Integer flag = REQUIRE_MAP.get(v.toLowerCase());
				if (flag != null) {
					require |= flag;
				}
			}
		} else {
			require = REQUIRE_DEFAULT;
		}
		return require;
	}

	private void writeJsContent(SourceWriter sw, ClassLoader loader, ResourceFile e, String path) throws IOException {
		try (
			InputStream is = loader.getResourceAsStream(path + e.path);
		) {
			sw.print("// ");
			sw.println(e.path);
			Reader reader = new InputStreamReader(is, StandardCharsets.UTF_8);
			char[] cbuf = new char[4096];
			int len = reader.read(cbuf);
			while (len > 0) {
				sw.print("\"");
				sw.print(Generator.escape(new String(cbuf, 0, len)));
				sw.print("\"");
				len = reader.read(cbuf);
				sw.println(len > 0 ? "+" : ",");
			}
		}
	}

	private void writeCldrContents(SourceWriter sw, ClassLoader loader, List<String> names) throws IOException {
		try (
			ZipInputStream is = new ZipInputStream(loader.getResourceAsStream("cldr-json.zip"));
		) {
			ZipEntry entry = is.getNextEntry();
			while (entry != null) {
				String name = entry.getName();
				if (Iterables.exists(names, e -> name.endsWith(e))) {
					sw.print("// ");
					sw.println(entry.getName());
					Reader reader = new InputStreamReader(is, StandardCharsets.UTF_8);
					char[] cbuf = new char[4096];
					int len = reader.read(cbuf);
					while (len > 0) {
						sw.print("\"");
						sw.print(Generator.escape(new String(cbuf, 0, len)));
						sw.print("\"");
						len = reader.read(cbuf);
						sw.println(len > 0 ? "+" : ",");
					}
				}
				entry = is.getNextEntry();
			}
		}
	}

	protected GwtLocale getCompileLocale(TreeLogger logger, GeneratorContext context) {
		PropertyOracle propertyOracle = context.getPropertyOracle();
		LocaleUtils localeUtils = LocaleUtils.getInstance(logger, propertyOracle, context);
		GwtLocale gwtLocale = localeUtils.getCompileLocale();
		return gwtLocale;
	}
}
