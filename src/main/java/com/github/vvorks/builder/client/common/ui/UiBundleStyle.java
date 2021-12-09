package com.github.vvorks.builder.client.common.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Predicate;

import com.github.vvorks.builder.common.json.Json;
import com.github.vvorks.builder.common.lang.Asserts;
import com.github.vvorks.builder.common.logging.Logger;

public class UiBundleStyle extends UiStyle {

	public static final Logger LOGGER = Logger.createLogger(UiBundleStyle.class);

	private static class Item {

		private final Predicate<UiNode> cond;

		private final UiStyle style;

		private Item(Predicate<UiNode> cond, UiStyle style) {
			this.cond = cond;
			this.style = style;
		}

		@Override
		public int hashCode() {
			return Objects.hash(cond, style);
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj) {
				return true;
			} else if (obj == null) {
				return false;
			} else if (getClass() != obj.getClass()) {
				return false;
			} else {
				return equals((Item) obj);
			}
		}

		private boolean equals(Item other) {
			return	Objects.equals(cond, other.cond) &&
					Objects.equals(style, other.style);
		}

	}

	private final List<Item> items;

	private final UiStyle defaultStyle;

	public UiBundleStyle(Class<?> owner, String name, UiStyle defaultStyle) {
		super(owner, name);
		this.items = new ArrayList<>();
		this.defaultStyle = defaultStyle;
	}

	protected UiBundleStyle(UiBundleStyle src) {
		super(src);
		this.items = new ArrayList<>();
		this.items.addAll(src.items);
		this.defaultStyle = src.defaultStyle;
	}

	public UiBundleStyle copy() {
		return new UiBundleStyle(this);
	}

	public void add(Predicate<UiNode> cond, UiStyle s) {
		items.add(new Item(cond, s));
	}

	@Override
	public UiAtomicStyle getAtomicStyleOf(UiNode node) {
		UiStyle result = defaultStyle;
		for (Item item : items) {
			if (item.cond.test(node)) {
				result = item.style;
				break;
			}
		}
		if (result instanceof UiAtomicStyle) {
			return (UiAtomicStyle) result;
		}
		return result.getAtomicStyleOf(node);
	}

	@Override
	public void getAtomicStyles(Map<String, UiAtomicStyle> map) {
		defaultStyle.getAtomicStyles(map);
		for (Item item : items) {
			item.style.getAtomicStyles(map);
		}
	}

	@Override
	public int hashCode() {
		return Objects.hash(defaultStyle, items);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		} else if (obj == null) {
			return false;
		} else if (getClass() != obj.getClass()) {
			return false;
		} else {
			return equals((UiBundleStyle) obj);
		}
	}

	private boolean equals(UiBundleStyle other) {
		return	Objects.equals(defaultStyle, other.defaultStyle) &&
				Objects.equals(items, other.items);
	}

	@Override
	public Json toJson() {
		Json json = super.toJson();
		Json it = json.setNewArray("items");
		for (int i = 0; i < items.size(); i++) {
			it.set(i, Json.valueOf(items.get(i).style));
		}
		json.set("defaultStyle", Json.valueOf(defaultStyle));
		return json;
	}

	public static class Builder {

		private static final Predicate<UiNode> DISABLE	= node -> !node.isEnableAll();
		private static final Predicate<UiNode> CLICKING	= node -> node.isClicking();
		private static final Predicate<UiNode> FOCUS	= node -> node.isFocus() || node.hasFocus();

		private UiBundleStyle style;

		public Builder(Class<?> owner, String name, UiStyle defaultStyle) {
			Asserts.require(defaultStyle != null);
			style = new UiBundleStyle(owner, name, defaultStyle);
		}

		public Builder disable(UiStyle s) {
			style.add(DISABLE, s);
			return this;
		}

		public Builder clicking(UiStyle s) {
			style.add(CLICKING, s);
			return this;
		}

		public Builder focus(UiStyle s) {
			style.add(FOCUS, s);
			return this;
		}

		public Builder when(Predicate<UiNode> cond, UiStyle s) {
			style.add(cond, s);
			return this;
		}

		public UiBundleStyle build() {
			return style;
		}

	}

}
