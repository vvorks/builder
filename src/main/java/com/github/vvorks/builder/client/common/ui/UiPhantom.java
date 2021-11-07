package com.github.vvorks.builder.client.common.ui;

import com.github.vvorks.builder.common.logging.Logger;

/**
 * ファントムノード（実ノードには現れない）
 */
public class UiPhantom extends UiNode {

	public static final Class<?> THIS = UiPhantom.class;
	public static final Logger LOGGER = Logger.createLogger(THIS);

	private static class PhantomElement implements DomElement {

		public DomElement appendChild(DomElement newChild) {
			return newChild;
		}

		public DomElement removeChild(DomElement oldChild) {
			return oldChild;
		}

		public void setAttribute(String name, String value) {
			//NOP
		}

		public void setScrollPosition(int x, int y) {
			//NOP
		}

		public void removeAttribute(String name) {
			//NOP
		}

		public void setInnerText(String text) {
			//NOP
		}

		public void setInnerHtml(String html) {
			//NOP
		}

		public void setParent(DomElement newParent) {
			//NOP
		}

	}

	public UiPhantom() {
		setEnable(false);
		setVisible(false);
	}

	@Override
	public UiPhantom copy() {
		throw new UnsupportedOperationException();
	}

	@Override
	protected DomElement createDomElement() {
		return new PhantomElement();
	}

}