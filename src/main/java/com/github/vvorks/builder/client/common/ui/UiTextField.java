package com.github.vvorks.builder.client.common.ui;

public class UiTextField extends UiText implements DataField {

	private transient DataRecord rec;

	public UiTextField(String name) {
		super(name);
	}

	protected UiTextField(UiTextField src) {
		super(src);
		this.rec = src.rec;
	}

	@Override
	public UiTextField copy() {
		return new UiTextField(this);
	}

	@Override
	public DataRecord getRecord() {
		return rec;
	}

	@Override
	public void setRecord(DataRecord rec) {
		this.rec = rec;
		String name = getDataName();
		String text = rec.getString(name);
		super.setText(text);
		setChanged(CHANGED_CONTENT);
	}

	@Override
	public void setText(String text) {
		super.setText(text);
		String name = getDataName();
		rec.setString(name, text);
	}

}
