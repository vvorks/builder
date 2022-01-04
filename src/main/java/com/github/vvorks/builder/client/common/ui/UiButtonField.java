package com.github.vvorks.builder.client.common.ui;

public class UiButtonField extends UiButton implements DataField {

	private DataRecord rec;

	public UiButtonField(String name) {
		super(name);
	}

	protected UiButtonField(UiButtonField src) {
		super(src);
		rec = src.rec;
	}

	@Override
	public UiButtonField copy() {
		return new UiButtonField(this);
	}

	@Override
	public DataRecord getRecord() {
		return rec;
	}

	@Override
	public void setRecord(DataRecord rec) {
		this.rec = rec;
		setText(rec.getString(getName()));
	}

}
