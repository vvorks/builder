package com.github.vvorks.builder.client.common.ui;

import com.github.vvorks.builder.common.logging.Logger;

public class UiList extends UiGroup {

	public static final Class<?> THIS = UiList.class;
	public static final Logger LOGGER = Logger.createLogger(THIS);

	private UiNode template;

	private int logicalCount;

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

	@Override
	public void onMount() {
		if (template == null) {
			template = clearChildren();
			template.clearNextSibling();
			//TODO テンプレートをWRAPする
			logicalCount = 0;
		}
		super.onMount();
		updateLogicalLine();
	}

	@Override
	public void onUnmount() {
		clearChildren();
		super.onUnmount();
	}

	@Override
	protected void syncChildren() {
		relocateChildren();
		super.syncChildren();
	}

	@Override
	public void onResize(int screenWidth, int screenHeight) {
		updateLogicalLine();
	}

	private void updateLogicalLine() {
		int listHeight = getHeightPx() - getBorderTopPx() - getBorderBottomPx();
		int itemHeight = template.getHeightPx(listHeight);
		int visibleCount = (int) Math.ceil((double)listHeight / (double)itemHeight);
		int logicalCount = visibleCount * 3;
		int delta = logicalCount - this.logicalCount;
		if (delta > 0) {
			//子ノードの追加
			for (int i = 0; i < delta; i++) {
				UiNode child = template.copy();
				if (child instanceof UiButton) {
					((UiButton)child).setText("child " + i);
				}
				insertChild(child);
			}
		} else if (delta < 0) {
			//子ノードの削除
			delta = -delta;
			for (int i = 0; i < delta; i++) {
				removeFirstChild();
			}
		}
		this.logicalCount = logicalCount;
	}

	private void relocateChildren() {
		int total = 0;
		for (UiNode child : getChildrenIf(c -> !c.isDeleted() && c.isVisible())) {
			Length top = new Length(total);
			Length height = child.getHeight();
			child.setBounds(Length.ZERO, top, Length.ZERO, null, null, height);
			total += child.getHeightPx();
		}
		setScrollHeight(total);
	}

}
