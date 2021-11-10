package com.github.vvorks.builder.client.common.ui;

import java.util.Objects;

import com.github.vvorks.builder.common.lang.Strings;
import com.github.vvorks.builder.common.logging.Logger;

public class UiButton extends UiNode {

	public static final Class<?> THIS = UiButton.class;
	public static final Logger LOGGER = Logger.createLogger(THIS);

	/** テキスト */
	private String text;

	/** テキスト表示用内部Element */
	private DomElement innerElement;

	public UiButton(String name) {
		super(name);
		setFocusable(true);
	}

	protected UiButton(UiButton src) {
		super(src);
		this.text = src.text;
	}

	@Override
	public UiButton copy() {
		return new UiButton(this);
	}

	public String getText() {
		return text;
	}

	public void setText(String newText) {
		if (!Objects.equals(this.text, newText)) {
			this.text = newText;
			setChanged(CHANGED_CONTENT);
		}
	}

	@Override
	protected void setDomElement(DomElement element) {
		super.setDomElement(element);
		innerElement = createDomElement(NS_HTML, HTML_DIV, this);
		innerElement.setParent(element);
	}

	@Override
	protected void syncStyle() {
		super.syncStyle();
		syncInnerStyle();
	}

	private void syncInnerStyle() {
		UiAtomicStyle s = getStyle().getAtomicStyleOf(this);
		String align = s.getTextAlign();
		String valign = s.getVerticalAlign();
		CssStyle.Builder sb = new CssStyle.Builder()
				.property("position", "absolute")
				.property("overflow", "hidden")
				.left(Length.ZERO)
				.width(Length.FULL)
				.property("text-align", align);
		if (Objects.equals(valign, UiAtomicStyle.VERTICAL_ALIGN_MIDDLE)) {
			//上下中央ぞろえ
			sb.top(Length.HALF).transform("translateY(-50%)");
		} else if (Objects.equals(valign, UiAtomicStyle.VERTICAL_ALIGN_BOTTOM)) {
			//下ぞろえ
			sb.bottom(Length.ZERO);
		} else {
			//上ぞろえ
			sb.top(Length.ZERO);
		}
		innerElement.setAttribute("style", sb.build().toString());
	}

	@Override
	protected void syncContent() {
		innerElement.setInnerHtml(Strings.escapeHtml(text));
	}

}
