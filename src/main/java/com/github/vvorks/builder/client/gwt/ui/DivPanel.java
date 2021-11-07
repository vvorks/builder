package com.github.vvorks.builder.client.gwt.ui;

import com.github.vvorks.builder.common.logging.Logger;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;

public class DivPanel extends DomPanel {

	public static final Class<?> THIS = DivPanel.class;
	public static final Logger LOGGER = Logger.createLogger(THIS);

	public DivPanel() {
		//このパネル用のElementを作成し、設定
		Element panelElement = Document.get().createDivElement();
		setElement(panelElement);
		//ルートノードの作成とこのパネルのElementとの関連付け
		Element e = ((GwtDomElement) app.getRootElement()).getNativeElement();
		panelElement.appendChild(e);
	}

}
