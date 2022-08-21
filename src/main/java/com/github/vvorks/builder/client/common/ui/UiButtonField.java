package com.github.vvorks.builder.client.common.ui;

public class UiButtonField extends UiButton implements DataField {

	private DataRecord rec;

	private DataRecordAgent agent;

	public UiButtonField(String name) {
		super(name);
		this.agent = null;
	}

	public UiButtonField(String name, DataRecordAgent agent) {
		super(name);
		this.agent = agent;
	}

	protected UiButtonField(UiButtonField src) {
		super(src);
		rec = src.rec;
		agent = src.agent;
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
		String name = getDataName();
		String text;
		if (agent == null) {
			text = rec.getString(name);
		} else {
			text = agent.getTitle(rec, name);
		}
		super.setText(text);
		setChanged(CHANGED_CONTENT);
	}

}
