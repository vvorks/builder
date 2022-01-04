package com.github.vvorks.builder.client.common.ui;

public class UiDataField extends UiTextField implements DataField {

	private transient DataRecord rec;

	public UiDataField(String name) {
		super(name);
	}

	protected UiDataField(UiDataField src) {
		super(src);
		this.rec = src.rec;
	}

	@Override
	public UiDataField copy() {
		return new UiDataField(this);
	}

	@Override
	public DataRecord getRecord() {
		return rec;
	}

	@Override
	public void setRecord(DataRecord rec) {
		this.rec = rec;
		super.setText(rec.getString(getName()));
		setChanged(CHANGED_CONTENT);
	}

	public void setText(String text) {
		super.setText(text);
		rec.setString(getName(), text); //とりあえず文字列として設定
	}

}
