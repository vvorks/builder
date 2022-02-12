package com.github.vvorks.builder.rebind;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.github.jknack.handlebars.Template;
import com.github.vvorks.builder.common.util.Test;
import com.github.vvorks.builder.common.util.TestCase;
import com.google.gwt.core.ext.UnableToCompleteException;
import com.google.gwt.core.ext.typeinfo.JClassType;
import com.google.gwt.core.ext.typeinfo.JMethod;

public class GwtTestRunnerGenerator extends AbstractGenerator {

	@Override
	public String doGenerate(JClassType type) throws UnableToCompleteException {
		//処理準備
		String typeName = type.getName();
		//指定されたタイプのソースが既に出力済みか否かを確認する
		String packageName = type.getPackage().getName();
		String className = typeName.replace('.', '_') + "Impl";
		String fullName = packageName + "." + className;
		PrintWriter pw = getWriter(packageName, className);
		if (pw == null) {
			return fullName;
		}
		//TestCodeアノテーションを持つタイプを検索
		JClassType testCase = getType(TestCase.class);
		List<JClassType> testTypes = getTypes(t -> t.isAssignableTo(testCase) && !t.equals(testCase));
		Map<String, List<String>> contents = new LinkedHashMap<>();
		for (JClassType t : testTypes) {
			List<JMethod> methods = getMethods(t, m -> m.getAnnotation(Test.class) != null);
			List<String> list = contents.computeIfAbsent(t.getQualifiedSourceName(), k -> new ArrayList<>());
			for (JMethod m : methods) {
				list.add(m.getName());
			}
		}
		//template 適用
		try {
			Template template = getTemplate("GwtTestRunnerImpl.hbs");
			pw.print(template.apply(contents));
			pw.close();
			commit(pw);
		} catch (IOException err) {
			throw new UnableToCompleteException();
		}

		return fullName;
	}

}
