package com.github.vvorks.builder.server.common.handlebars;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringReader;

import com.github.jknack.handlebars.Helper;
import com.github.jknack.handlebars.Options;
import com.github.vvorks.builder.common.lang.Factory;
import com.github.vvorks.builder.common.lang.Strings;
import com.github.vvorks.builder.common.logging.Logger;
import com.github.vvorks.builder.server.common.io.Ios;

public class SourceHelper implements Helper<Object> {

	private static final Class<?> THIS = SourceHelper.class;
	private static final Logger LOGGER = Factory.newInstance(Logger.class, THIS);

	private final File outDir;

	//private final JavaFormatter javaFormatter;

	public SourceHelper(File outDir) {
		this.outDir = outDir;
		//this.javaFormatter = new JavaFormatter();
	}

	@Override
	public Object apply(Object context, Options options) throws IOException {
		//ファイル名を取得
		String name = Strings.concat("", options.params);
		//パッケージ名を取得してパス名にする
		File file = new File(outDir, name);
		Ios.mkdirs(file.getParentFile());
		//テンプレート適用
		LOGGER.info("gen ./%s", name);
		String source = options.fn().toString();
		//ファイル種別毎の後処理
		if (name.endsWith(".java")) {
			//source = javaFormatter.format(source);
		}
		//改行正規化とファイル出力
		try (
			BufferedReader in = new BufferedReader(new StringReader(source));
			PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(file)))
		) {
			String prev = "";
			String line = "";
			while ((line = in.readLine()) != null) {
				if (!prev.trim().isEmpty() || !line.trim().isEmpty()) {
					out.println(line);
				}
				prev = line;
			}
		}
		//上位レイヤーの出力先には何も返さない
		return "";
	}

}
