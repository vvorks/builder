package com.github.vvorks.builder.server.component;

import java.io.File;
import java.io.IOException;
import java.io.Writer;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.github.vvorks.builder.common.lang.Factory;
import com.github.vvorks.builder.common.util.Logger;
import com.github.vvorks.builder.server.common.handlebars.ExtenderResolver;
import com.github.vvorks.builder.server.common.handlebars.GlobalResolver;
import com.github.vvorks.builder.server.common.handlebars.SeparatorHelper;
import com.github.vvorks.builder.server.common.handlebars.SourceHelper;
import com.github.vvorks.builder.server.common.io.Ios;
import com.github.vvorks.builder.server.common.io.LoggerWriter;
import com.github.vvorks.builder.server.domain.ProjectContent;
import com.github.vvorks.builder.server.extender.ClassExtender;
import com.github.vvorks.builder.server.extender.EnumExtender;
import com.github.vvorks.builder.server.extender.EnumValueExtender;
import com.github.vvorks.builder.server.extender.FieldExtender;
import com.github.vvorks.builder.server.extender.ProjectExtender;
import com.github.vvorks.builder.server.extender.QueryExtender;
import com.github.vvorks.builder.server.mapper.ProjectMapper;

@Component
public class SourceWriter {

	private static final Class<?> THIS = SourceWriter.class;
	private static final Logger LOGGER = Factory.newInstance(Logger.class, THIS);

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

	/** ソースを出力する（相対）トップディレクトリ */
	private static final String SRC_TOP = "src/main/java/";

	/** リソースを出力する（相対）トップディレクトリ */
	private static final String RES_TOP = "src/main/resources";

	/** hbsリソース読み込みパス */
	private static final String HBS_RES = "/handlebars/";

	private static final String HBS_EXT = ".hbs";

	public void process() throws IOException {
		String now = String.format("%tY%<tm%<td_%<tH%<tM%<tS", new Date());
		File projectDir = new File("out/" + now + "/"); ////TODO for Debug
		File javaRootDir = new File(projectDir, SRC_TOP);
		File resRootDir = new File(projectDir, RES_TOP);
		List<ProjectContent> projects = projectMapper.listContent(0, 0);
		try (
			Writer writer = new LoggerWriter(LOGGER)
		) {
			TemplateLoader loader = new ClassPathTemplateLoader(HBS_RES);
			//HBSリソースの取得
			List<String> hbsFiles = Ios.getResoureNames(this, HBS_RES, f -> f.endsWith(HBS_EXT));
			for (ProjectContent prj : projects) {
				File javaCodeDir = new File(javaRootDir, prj.getProjectName().replace('.', '/'));
				Handlebars hbs = new Handlebars(loader)
						.with(EscapingStrategy.NOOP)
						.prettyPrint(true)
						.registerHelper("separator", new SeparatorHelper())
						.registerHelper("java", new SourceHelper(javaCodeDir))
						.registerHelper("resources", new SourceHelper(resRootDir));
				Map<String, Object> globalMap = new HashMap<>();
				globalMap.put("project", prj);
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
										enumValueExtender))
						.build();
				//とりあえず全部適用
				for (String s : hbsFiles) {
					Template t = hbs.compile(s.substring(0, s.lastIndexOf('.')));
					t.apply(context, writer);
				}
			}
		} catch (Exception err) {
			Ios.deleteAll(projectDir);
			throw err;
		}
	}

}
