package com.github.vvorks.builder.client.common.ui;

import com.github.vvorks.builder.common.logging.Logger;

public class UiDataField extends UiNode implements DataField {

	public static final Class<?> THIS = UiDataField.class;
	public static final Logger LOGGER = Logger.createLogger(THIS);

	private transient DataRecord rec;

	private transient String text;

	public UiDataField(String name) {
		super(name);
		setFocusable(true);
	}

	protected UiDataField(UiDataField src) {
		super(src);
	}

	@Override
	public UiDataField copy() {
		return new UiDataField(this);
	}

	@Override
	public void setRecord(DataRecord rec) {
		this.rec = rec;
		this.text = rec.getString(getName());
		setChanged(CHANGED_CONTENT);
	}

	@Override
	protected void syncContent() {
		getDomElement().setInnerText(text);
	}

}
