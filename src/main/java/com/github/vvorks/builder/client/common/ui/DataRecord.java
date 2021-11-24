package com.github.vvorks.builder.client.common.ui;

/**
 * データレコードI/F
 */
public interface DataRecord {

	/**
	 * デフォルトboolean値
	 */
	public static final boolean DEFAULT_BOOLEAN = false;

	/**
	 * デフォルトdouble値
	 */
	public static final double DEFAULT_NUMBER = 0.0d;

	/**
	 * デフォルトString値
	 */
	public static final String DEFAULT_STRING = "";

	/**
	 * データが存在するか否かを取得する
	 *
	 * @return データが存在する場合、真
	 */
	public abstract boolean exists();

	/**
	 * 指定したカラムのデータ型を取得する
	 *
	 * @param column カラム名
	 * @return データ型
	 */
	public abstract Class<?> getType(String column);

	/**
	 * 指定したカラムのboolean値を取得する
	 *
	 * @param column カラム名
	 * @return 値
	 */
	public default boolean getBoolean(String column) {
		return getBoolean(column, DEFAULT_BOOLEAN);
	}

	/**
	 * 指定したカラムのdouble値を取得する
	 *
	 * @param column カラム名
	 * @return 値
	 */
	public default double getNumber(String column) {
		return getNumber(column, DEFAULT_NUMBER);
	}

	/**
	 * 指定したカラムのString値を取得する
	 *
	 * @param column カラム名
	 * @return 値
	 */
	public default String getString(String column) {
		return getString(column, DEFAULT_STRING);
	}

	/**
	 * 指定したカラムのboolean値を取得する
	 *
	 * @param column カラム名
	 * @param defaultValue デフォルト値
	 * @return 値
	 */
	public abstract boolean getBoolean(String column, boolean defaultValue);

	/**
	 * 指定したカラムのdouble値を取得する
	 *
	 * @param column カラム名
	 * @param defaultValue デフォルト値
	 * @return 値
	 */
	public abstract double getNumber(String column, double defaultValue);

	/**
	 * 指定したカラムのString値を取得する
	 *
	 * @param column カラム名
	 * @param defaultValue デフォルト値
	 * @return 値
	 */
	public abstract String getString(String column, String defaultValue);

	/**
	 * 指定したカラムにnull値を取得する
	 *
	 * @param column カラム名
	 */
	public abstract void setNull(String column);

	/**
	 * 指定したカラムにboolean値を取得する
	 *
	 * @param column カラム名
	 * @param value 値
	 */
	public abstract void setBoolean(String column, boolean value);

	/**
	 * 指定したカラムにdouble値を取得する
	 *
	 * @param column カラム名
	 * @param value 値
	 */
	public abstract void setNumber(String column, double value);

	/**
	 * 指定したカラムにString値を取得する
	 *
	 * @param column カラム名
	 * @param value 値
	 */
	public abstract void setString(String column, String value);

}
