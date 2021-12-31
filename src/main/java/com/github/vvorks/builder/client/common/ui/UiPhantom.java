package com.github.vvorks.builder.client.common.ui;

/**
 * ファントムノード（実ノードには現れない）
 */
public class UiPhantom extends UiNode {

	private static class PhantomElement implements DomElement {

		public void setParent(DomElement newParent) {
			//NOP
		}

		@Override
		public void setDefinedStyle(UiAtomicStyle style) {
			//NOP
		}

		@Override
		public void setLocalStyle(CssStyle style) {
			//NOP
		}

		@Override
		public void setBounds(int left, int top, int width, int height) {
			//NOP
		}

		@Override
		public void setScrollBounds(int x, int y, int width, int height) {
			//NOP
		}

		@Override
		public void setInnerText(String text) {
			//NOP
		}

		@Override
		public void setImageUrl(String url) {
			//NOP
		}

		@Override
		public void sync() {
			//NOP
		}

	}

	public UiPhantom() {
		setEnable(false);
		setVisible(false);
		setLeft(Length.ZERO);
		setTop(Length.ZERO);
		setWidth(Length.ZERO);
		setHeight(Length.ZERO);
	}

	@Override
	public UiPhantom copy() {
		throw new UnsupportedOperationException();
	}

	@Override
	protected DomElement createDomElement(String namespaceURI, String qualifiedName, UiNode owner) {
		return new PhantomElement();
	}

}