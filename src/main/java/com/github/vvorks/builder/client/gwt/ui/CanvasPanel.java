package com.github.vvorks.builder.client.gwt.ui;

import com.github.vvorks.builder.common.logging.Logger;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;

public class CanvasPanel extends DomPanel {

	public static final Class<?> THIS = CanvasPanel.class;
	public static final Logger LOGGER = Logger.createLogger(THIS);

	public CanvasPanel() {
		//このパネル用のElementを作成し、設定
		Element canvasElement = Document.get().createCanvasElement();
		setElement(canvasElement);
	}

}
