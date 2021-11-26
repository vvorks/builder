package com.github.vvorks.builder.client.common.ui;

import com.github.vvorks.builder.common.json.Json;
import com.github.vvorks.builder.common.lang.Asserts;
import com.github.vvorks.builder.common.logging.Logger;

public class UiList extends UiGroup {

	public static final Class<?> THIS = UiList.class;
	public static final Logger LOGGER = Logger.createLogger(THIS);

	private static final int VIEW_MARGIN = 3;

	protected static class UiLine extends UiNode implements DataRecord {

		private int index;

		private Json json;

		public UiLine(String name) {
			super(name);
			this.index = -1;
		}

		protected UiLine(UiLine src) {
			super(src);
			this.index = src.index;
		}

		@Override
		public UiLine copy() {
			return new UiLine(this);
		}

		public int getIndex() {
			return index;
		}

		public void setIndex(int index) {
			this.index = index;
		}

		@Override
		public String getName() {
			return index < 0 ? super.getName() : "" + index;
		}

		@Override
		public DataRecord getDataRecord() {
			return this;
		}

		@Override
		public boolean exists() {
			return json != null;
		}

		@Override
		public Class<?> getType(String column) {
			Asserts.assume(exists());
			return json.getType().asClass();
		}

		@Override
		public boolean getBoolean(String column, boolean defaultValue) {
			Asserts.assume(exists());
			return json.getBoolean(column, defaultValue);
		}

		@Override
		public double getNumber(String column, double defaultValue) {
			Asserts.assume(exists());
			return json.getNumber(column, defaultValue);
		}

		@Override
		public String getString(String column, String defaultValue) {
			Asserts.assume(exists());
			return json.getString(column, defaultValue);
		}

		@Override
		public void setNull(String column) {
			Asserts.assume(exists());
			json.setNull(column);
		}

		@Override
		public void setBoolean(String column, boolean value) {
			Asserts.assume(exists());
			json.setBoolean(column, value);
		}

		@Override
		public void setNumber(String column, double value) {
			Asserts.assume(exists());
			json.setNumber(column, value);
		}

		@Override
		public void setString(String column, String value) {
			Asserts.assume(exists());
			json.setString(column, value);
		}

	}

	/** データソース */
	private DataSource dataSource;

	/** テンプレートノード */
	private UiLine template;

	/** ページ高さ（ピクセル単位） */
	private int pageHeight;

	/** 行高さ（ピクセル単位） */
	private int lineHeight;

	/** １画面に表示可能な行数 */
	private int linesPerView;

	public UiList(String name) {
		super(name);
	}

	public UiList(UiList src) {
		super(src);
	}

	@Override
	public UiList copy() {
		return new UiList(this);
	}

	public void setDataSource(DataSource ds) {
		UiApplication app = getApplication();
		if (this.dataSource != null) {
			app.detachDataSource(this, this.dataSource);
		}
		this.dataSource = ds;
		if (this.dataSource != null) {
			app.attachDataSource(this, this.dataSource);
		}
	}

	@Override
	public boolean isFocusable() {
		return template == null || getFirstChild() == null;
	}

	@Override
	public void onMount() {
		if (template == null) {
			template = new UiLine("template");
			template.setBounds(Length.ZERO, Length.ZERO, Length.ZERO, null, null, getMaxHeight());
			template.setChildren(clearChildren());
			template.setParent(this);
		}
		updateMetrics();
		super.onMount();
	}

	private void updateMetrics() {
		pageHeight = getHeightPx() - getBorderTopPx() - getBorderBottomPx();
		lineHeight = template.getHeightPx(pageHeight);
		linesPerView = (int) Math.ceil((double)pageHeight / (double)lineHeight);
	}

	private Length getMaxHeight() {
		Length maxHeight = null;
		int maxHeightPx = 0;
		for (UiNode c : getChildren()) {
			Length height = c.getHeight();
			int heightPx = c.getHeightPx();
			if (maxHeight == null || maxHeightPx < heightPx) {
				maxHeight = height;
				maxHeightPx = heightPx;
			}
		}
		return maxHeight;
	}

	@Override
	public void onUnmount() {
		super.onUnmount();
		clearChildren();
	}

	@Override
	public void onResize(int screenWidth, int screenHeight) {
		updateMetrics();
		DataSource ds = dataSource;
		int count = ds.isLoaded() ? ds.getCount() : 0;
		prepareLines(count);
	}

	@Override
	public int onDataSourceUpdated(DataSource ds) {
		int result = super.onDataSourceUpdated(ds);
		int count = ds.isLoaded() ? ds.getCount() : 0;
		prepareLines(count);
		result |= EVENT_AFFECTED;
		return result;
	}

	private void prepareLines(int count) {
		UiApplication app = getApplication();
		UiNode focus = app.getFocus();
		boolean isFocus = (focus == this);
		boolean hasFocus = (this.isAncestor(focus));
		if (count <= 0 || linesPerView <= 0) {
			clearChildren();
			if (hasFocus) {
				app.setFocus(this);
			}
			return;
		}
		boolean hasMargin = (count >= linesPerView);
		int oldLines = size();
		int newLines = hasMargin ? linesPerView + VIEW_MARGIN * 2 : count;
		if (oldLines != newLines) {
			int scrollTop = -1;
			if (newLines > oldLines) {
				for (int i = 0; i < newLines - oldLines; i++) {
					insertChild(template.copy());
				}
				if (oldLines < linesPerView && newLines >= linesPerView) {
					scrollTop = lineHeight * VIEW_MARGIN;
				}
			} else {
				for (int i = 0; i < oldLines - newLines; i++) {
					removeFirstChild();
				}
				if (oldLines >= linesPerView && newLines < linesPerView) {
					scrollTop = 0;
				}
			}
			int index = hasMargin ? newLines - VIEW_MARGIN : 0;
			for (UiNode c = getFirstChild(); c != null; c = c.getNextSibling()) {
				((UiLine)c).setIndex(index);
				if (c.getFirstChild() instanceof UiButton) {
					((UiButton)c.getFirstChild()).setText("" + index);
				}
				index = (index + 1) % newLines;
			}
			relocateChildren();
			if (scrollTop >= 0) {
				setScrollTop(scrollTop);
			}
			if (isFocus || hasFocus) {
				setFocus(app.getFirstFocus(this));
			}
		}
	}

	private void relocateChildren() {
		Length spacingWidth = getSpacingWidth();
		int spacingHeightPx = getSpacingHeightPx();
		int total = spacingHeightPx;
		for (UiNode child : this.getChildrenIf(c -> !c.isDeleted() && c.isVisible())) {
			Length top = new Length(total);
			Length height = child.getHeight();
			Length left = child.getLeft();
			Length right = child.getRight();
			Length width = child.getWidth();
			if (left == null && right == null && width == null) {
				left = right = spacingWidth;
			}
			child.setBounds(left, top, right, null, width, height);
			total += child.getHeightPx() + spacingHeightPx;
		}
		setScrollHeight(total);
	}

	@Override
	public void scrollFor(UiNode child) {
		super.scrollFor(child);
		if (size() >= linesPerView) {
			int scrollTop = getScrollTopPx();
			int scrollHeight = getScrollHeightPx();
			int margin = lineHeight * VIEW_MARGIN;
			if (scrollTop < margin) {
				rollUp();
				relocateChildren();
			} else if (scrollHeight - (scrollTop + pageHeight) < margin) {
				rollDown();
				relocateChildren();
			}
		}
	}

	private void rollUp() {
		insertChild(removeLastChild());
		setScrollTop(getScrollTopPx() + lineHeight);
	}

	private void rollDown() {
		appendChild(removeFirstChild());
		setScrollTop(getScrollTopPx() - lineHeight);
	}

}
