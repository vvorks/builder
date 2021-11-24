package com.github.vvorks.builder.client.common.ui;

import com.github.vvorks.builder.common.json.Json;
import com.github.vvorks.builder.common.lang.Asserts;
import com.github.vvorks.builder.common.lang.Iterables;
import com.github.vvorks.builder.common.logging.Logger;

public class UiList extends UiGroup {

	public static final Class<?> THIS = UiList.class;
	public static final Logger LOGGER = Logger.createLogger(THIS);

	private static final int VIEW_MARGIN = 3;

	protected static class UiLine extends UiNode implements DataRecord {

		private final int index;

		private Json json;

		public UiLine(String name) {
			super(name);
			this.index = -1;
		}

		protected UiLine(UiLine src) {
			super(src);
			this.index = src.index;
		}

		protected UiLine(UiLine src, int index) {
			super(src);
			this.index = index;
		}

		@Override
		public UiLine copy() {
			return new UiLine(this);
		}

		public UiLine copy(int index) {
			return new UiLine(this, index);
		}

		public int getIndex() {
			return index;
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

	/** ビューが保持する行数 */
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
	public void onMount() {
		if (template == null) {
			template = new UiLine("template");
			template.setBounds(Length.ZERO, Length.ZERO, Length.ZERO, null, null, getMaxHeight());
			template.setChildren(clearChildren());
			template.setParent(this);
			linesPerView = 0;
		}
		prepareLines();
		for (int i = 0; i < VIEW_MARGIN; i++) {
			rollUp();
		}
		relocateChildren();
		super.onMount();
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
		prepareLines();
	}

	@Override
	public int onDataSourceUpdated(DataSource ds) {
		int result = super.onDataSourceUpdated(ds);
		//TODO 情報の反映
		return result;
	}

	private void prepareLines() {
		pageHeight = getHeightPx() - getBorderTopPx() - getBorderBottomPx();
		lineHeight = template.getHeightPx(pageHeight);
		int newLinesPerView = (int) Math.ceil((double)pageHeight / (double)lineHeight) + VIEW_MARGIN * 2;
		if (newLinesPerView > linesPerView) {
			//挿入ポイントの探索
			UiLine ref = (UiLine) getFirstChild();
			if (ref == null || ref.getIndex() == 0) {
				ref = null;
			} else {
				ref = (UiLine) Iterables.getFirst(
						getChildrenIf(c -> ((UiLine)c).getIndex() == 0), null);
			}
			//子ノードの追加
			for (int i = linesPerView; i < newLinesPerView; i++) {
				UiLine child = template.copy(i);
				//TODO 一旦非表示で追加する
				UiNode gc = child.getFirstChild();
				if (gc instanceof UiButton) {
					((UiButton) gc).setText("child " + i);
				}
				//TODO ↑はテスト用なので削除する
				insertBefore(child, ref);
			}
			linesPerView = newLinesPerView;
		}

	}

	private void relocateChildren() {
		int spc = getSpacingHeightPx();
		int total = spc;
		for (UiNode child : getChildrenIf(c -> !c.isDeleted() && c.isVisible())) {
			Length top = new Length(total);
			Length height = child.getHeight();
			Length left = child.getLeft();
			Length right = child.getRight();
			Length width = child.getWidth();
			if (left == null && right == null && width == null) {
				left = right = getSpacingWidth();
			}
			child.setBounds(left, top, right, null, width, height);
			total += child.getHeightPx() + spc;
		}
		setScrollHeight(total);
	}

	@Override
	public void scrollFor(UiNode child) {
		super.scrollFor(child);
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

	private void rollUp() {
		insertChild(removeLastChild());
		setScrollTop(getScrollTopPx() + lineHeight);
	}

	private void rollDown() {
		appendChild(removeFirstChild());
		setScrollTop(getScrollTopPx() - lineHeight);
	}

}
