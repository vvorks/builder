package com.github.vvorks.builder.server.component;

import java.io.File;
import java.io.IOException;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.github.jknack.handlebars.Context;
import com.github.jknack.handlebars.EscapingStrategy;
import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.context.JavaBeanValueResolver;
import com.github.jknack.handlebars.context.MapValueResolver;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;
import com.github.vvorks.builder.server.common.handlebars.ExtenderResolver;
import com.github.vvorks.builder.server.common.handlebars.GlobalResolver;
import com.github.vvorks.builder.server.common.handlebars.ReverseHelper;
import com.github.vvorks.builder.server.common.handlebars.SeparatorHelper;
import com.github.vvorks.builder.server.common.handlebars.SourceHelper;
import com.github.vvorks.builder.server.common.io.Ios;
import com.github.vvorks.builder.server.common.io.LoggerWriter;
import com.github.vvorks.builder.server.common.io.Resources;
import com.github.vvorks.builder.server.common.sql.SqlHelper;
import com.github.vvorks.builder.server.domain.ProjectContent;
import com.github.vvorks.builder.server.extender.ClassExtender;
import com.github.vvorks.builder.server.extender.EnumExtender;
import com.github.vvorks.builder.server.extender.EnumValueExtender;
import com.github.vvorks.builder.server.extender.FieldExtender;
import com.github.vvorks.builder.server.extender.ProjectExtender;
import com.github.vvorks.builder.server.extender.QueryExtender;
import com.github.vvorks.builder.server.extender.StyleExtender;
import com.github.vvorks.builder.server.mapper.ProjectMapper;
import com.github.vvorks.builder.shared.common.logging.Logger;

@Component
public class SourceWriter {

	private static final Class<?> THIS = SourceWriter.class;

	private static final Logger LOGGER = Logger.createLogger(SourceWriter.class);

	/** hbsリソース読み込みパス */
	private static final String HBS_RES = "/handlebars/";

	private static final String HBS_EXT = ".hbs";

	@Autowired
	private ProjectMapper projectMapper;

	@Autowired
	private ProjectExtender projectExtender;

	@Autowired
	private ClassExtender classExtender;

	@Autowired
	private FieldExtender fieldExtender;

	@Autowired
	private QueryExtender queryExtender;

	@Autowired
	private EnumExtender enumExtender;

	@Autowired
	private EnumValueExtender enumValueExtender;

	@Autowired
	private StyleExtender styleExtender;

	private SqlHelper sqlHelper = SqlHelper.get();

	public void process() throws IOException {
		String now = String.format("%tY%<tm%<td_%<tH%<tM%<tS", new Date());
		File outDir = new File("out/" + now + "/"); ////TODO for Debug
		File srcDir = new File(outDir, "src");
		List<ProjectContent> projects = projectMapper.listContent(0, 0);
		try (
			Writer writer = new LoggerWriter(s -> LOGGER.debug(s))
		) {
			TemplateLoader loader = new ClassPathTemplateLoader(HBS_RES);
			//HBSリソースの取得
			Set<String> resNames = Resources.getResourceNames(THIS,
					name -> name.startsWith(HBS_RES) && name.endsWith(HBS_EXT));
			List<String> hbsFiles = new ArrayList<>();
			for (String res : resNames) {
				hbsFiles.add(res.substring(HBS_RES.length()));
			}
			for (ProjectContent prj : projects) {
				String gradleName = prj.getGradleName();
				File javaRootDir = new File(srcDir, gradleName + "/java/");
				File javaCodeDir = new File(javaRootDir, prj.getProjectName().replace('.', '/'));
				File resRootDir = new File(srcDir, gradleName + "/resources/");
				Handlebars hbs = new Handlebars(loader)
						.with(EscapingStrategy.NOOP)
						.setCharset(StandardCharsets.UTF_8)
						.prettyPrint(true)
						.registerHelper("separator", new SeparatorHelper())
						.registerHelper("reverse", new ReverseHelper())
						.registerHelper("java", new SourceHelper(javaCodeDir))
						.registerHelper("resources", new SourceHelper(resRootDir));
				Map<String, Object> globalMap = new HashMap<>();
				globalMap.put("project", prj);
				globalMap.put("disableForeignKey", sqlHelper.disableForeignKey());
				globalMap.put("enableForeignKey", sqlHelper.enableForeignKey());
				Context context = Context.newBuilder(prj)
						.resolver(
								new GlobalResolver("global", globalMap),
								MapValueResolver.INSTANCE,
								JavaBeanValueResolver.INSTANCE,
								new ExtenderResolver(
										projectExtender,
										classExtender,
										fieldExtender,
										queryExtender,
										enumExtender,
										enumValueExtender,
										styleExtender))
						.build();
				//テンプレートの適用
				for (String s : hbsFiles) {
					Template t = hbs.compile(s.substring(0, s.lastIndexOf('.')));
					t.apply(context, writer);
				}
			}
		} catch (Exception err) {
			Ios.deleteAll(outDir);
			throw err;
		}
	}

}
