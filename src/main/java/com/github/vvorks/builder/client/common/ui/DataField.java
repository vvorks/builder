package com.github.vvorks.builder.client.common.ui;

public interface DataField {

	/**
	 * 編集対象データレコードを取得する
	 *
	 * @return データレコード
	 */
	public DataRecord getRecord();

	/**
	 * 編集対象データレコードを設定する
	 *
	 * @param rec データレコード
	 */
	public void setRecord(DataRecord rec);

}
