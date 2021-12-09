package com.github.vvorks.builder.client.common.ui;

import java.util.LinkedHashMap;
import java.util.Map;

import com.github.vvorks.builder.common.json.Json;
import com.github.vvorks.builder.common.json.Jsonizable;
import com.github.vvorks.builder.common.lang.Copyable;

public abstract class UiStyle implements Copyable<UiStyle>, Jsonizable {

	public static UiAtomicStyle.Builder newAtomicBuilder(Class<?> owner, String name, UiAtomicStyle baseStyle) {
		return new UiAtomicStyle.Builder(owner, name, baseStyle);
	}

	public static UiBundleStyle.Builder newBundleBuilder(Class<?> owner, String name, UiStyle defaultStyle) {
		return new UiBundleStyle.Builder(owner, name, defaultStyle);
	}

	public static void toCssStyles(Iterable<UiStyle> styles, Map<String, CssStyle> into) {
		Map<String, UiAtomicStyle> map = new LinkedHashMap<>();
		for (UiStyle s : styles) {
			s.getAtomicStyles(map);
		}
		for (UiAtomicStyle s : map.values()) {
			into.put("." + s.getCssClassName(), s.getCssStyle());
		}
	}

	private final Class<?> owner;

	private final String name;

	protected UiStyle(Class<?> owner, String name) {
		this.owner = owner;
		this.name = name;
	}

	protected UiStyle(UiStyle src) {
		this.owner = src.owner;
		this.name = src.name;
	}

	public String getName() {
		return name;
	}

	public String getCssClassName() {
		if (owner == null) {
			return name;
		}
		return owner.getName().replace('.', '-') + "-" + name;
	}

	public abstract UiAtomicStyle getAtomicStyleOf(UiNode node);

	public abstract void getAtomicStyles(Map<String, UiAtomicStyle> into);

	@Override
	public Json toJson() {
		Json json = Json.createObject();
		json.setString("owner", owner);
		json.setString("name", name);
		return json;
	}


}
