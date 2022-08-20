package com.github.vvorks.builder.client.common.ui;

public class UiEditField extends UiEditText implements DataField {

	private transient DataRecord rec;

	public UiEditField(String name) {
		super(name);
	}

	protected UiEditField(UiEditField src) {
		super(src);
		this.rec = src.rec;
	}

	@Override
	public UiEditField copy() {
		return new UiEditField(this);
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
