package com.github.vvorks.builder.shared.common.net;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

public class URLFragment {

	private static final String TAG_SEPARATOR = ":";

	private static final String PARAM_SEPARATOR = "&";

	private static final String NAME_SEPARATOR = "=";

	private String tag;

	private Map<String, String> parameters;

	public URLFragment(String fragment) {
		parse(fragment);
	}

	private void parse(String fragment) {
		if (fragment == null || fragment.isEmpty()) {
			//デフォルト
			tag = "";
			parameters = Collections.emptyMap();
		} else {
			int tagSep = fragment.indexOf(TAG_SEPARATOR);
			if (tagSep == -1) {
				//引数なし
				tag = fragment;
				parameters = Collections.emptyMap();
			} else {
				//引数あり
				tag = fragment.substring(0, tagSep);
				Map<String, String> params = new LinkedHashMap<>();
				for (String param : fragment.substring(tagSep + 1).split(PARAM_SEPARATOR)) {
					int equalPos = param.indexOf(NAME_SEPARATOR);
					if (equalPos == -1) {
						params.put(param, "");
					} else {
						String key = param.substring(0, equalPos);
						String value = param.substring(equalPos + 1);
						params.put(key, value);
					}
				}
				parameters = Collections.unmodifiableMap(params);
			}
		}
	}

	public String getTag() {
		return tag;
	}

	public Map<String, String> getParameters() {
		return parameters;
	}

	public String getParameter(String key) {
		return parameters.get(key);
	}

}
