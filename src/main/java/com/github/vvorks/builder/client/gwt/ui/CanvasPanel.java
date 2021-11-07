package com.github.vvorks.builder.client.gwt.ui;

import com.github.vvorks.builder.common.logging.Logger;
import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.dom.client.CanvasElement;
import com.google.gwt.dom.client.Document;

public class CanvasPanel extends DomPanel {

	public static final Class<?> THIS = CanvasPanel.class;
	public static final Logger LOGGER = Logger.createLogger(THIS);

	public CanvasPanel() {
		//このパネル用のElementを作成し、設定
		CanvasElement canvasElement = Document.get().createCanvasElement();
		setElement(canvasElement);
		//test
		Context2d con = canvasElement.getContext2d();
		con.clearRect(0, 0, 640, 480);
		con.setFillStyle("#FFA500");
		con.fillRect(32, 0, 16, 16);
	}

}
