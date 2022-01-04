package com.github.vvorks.builder.client.common.ui;

import java.math.BigDecimal;
import java.util.Date;

/**
 * データレコードI/F
 */
public interface DataRecord {

	/**
	 * デフォルトboolean値
	 */
	public static final boolean DEFAULT_BOOLEAN = false;

	/**
	 * デフォルトint値
	 */
	public static final int DEFAULT_INT = 0;

	/**
	 * デフォルトlong値
	 */
	public static final long DEFAULT_LONG = 0L;

	/**
	 * デフォルトfloat値
	 */
	public static final float DEFAULT_FLOAT = 0f;

	/**
	 * デフォルトdouble値
	 */
	public static final double DEFAULT_DOUBLE = 0.0d;

	/**
	 * デフォルトdecimal値
	 */
	public static final BigDecimal DEFAULT_DECIMAL = BigDecimal.ZERO;

	/**
	 * デフォルトdate値
	 */
	public static final Date DEFAULT_DATE = new Date(0L);

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
	 * 指定したカラムのint値を取得する
	 *
	 * @param column カラム名
	 * @return 値
	 */
	public default int getInt(String column) {
		return getInt(column, DEFAULT_INT);
	}

	/**
	 * 指定したカラムのlong値を取得する
	 *
	 * @param column カラム名
	 * @return 値
	 */
	public default long getLong(String column) {
		return getLong(column, DEFAULT_LONG);
	}

	/**
	 * 指定したカラムのfloat値を取得する
	 *
	 * @param column カラム名
	 * @return 値
	 */
	public default float getFloat(String column) {
		return getFloat(column, DEFAULT_FLOAT);
	}

	/**
	 * 指定したカラムのdouble値を取得する
	 *
	 * @param column カラム名
	 * @return 値
	 */
	public default double getDouble(String column) {
		return getDouble(column, DEFAULT_DOUBLE);
	}

	/**
	 * 指定したカラムのdeciamal値を取得する
	 *
	 * @param column カラム名
	 * @return 値
	 */
	public default BigDecimal getDecimal(String column) {
		return getDecimal(column, DEFAULT_DECIMAL);
	}

	/**
	 * 指定したカラムのdeciamal値を取得する
	 *
	 * @param column カラム名
	 * @return 値
	 */
	public default Date getDate(String column) {
		return getDate(column, DEFAULT_DATE);
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
	 * 指定したカラムのint値を取得する
	 *
	 * @param column カラム名
	 * @param defaultValue デフォルト値
	 * @return 値
	 */
	public abstract int getInt(String column, int defaultValue);

	/**
	 * 指定したカラムのlong値を取得する
	 *
	 * @param column カラム名
	 * @param defaultValue デフォルト値
	 * @return 値
	 */
	public abstract long getLong(String column, long defaultValue);

	/**
	 * 指定したカラムのfloat値を取得する
	 *
	 * @param column カラム名
	 * @param defaultValue デフォルト値
	 * @return 値
	 */
	public abstract float getFloat(String column, float defaultValue);

	/**
	 * 指定したカラムのdouble値を取得する
	 *
	 * @param column カラム名
	 * @param defaultValue デフォルト値
	 * @return 値
	 */
	public abstract double getDouble(String column, double defaultValue);

	/**
	 * 指定したカラムのdecimal値を取得する
	 *
	 * @param column カラム名
	 * @param defaultValue デフォルト値
	 * @return 値
	 */
	public abstract BigDecimal getDecimal(String column, BigDecimal defaultValue);

	/**
	 * 指定したカラムのdate値を取得する
	 *
	 * @param column カラム名
	 * @param defaultValue デフォルト値
	 * @return 値
	 */
	public abstract Date getDate(String column, Date defaultValue);

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
	 * 指定したカラムにint値を取得する
	 *
	 * @param column カラム名
	 * @param value 値
	 */
	public abstract void setInt(String column, int value);

	/**
	 * 指定したカラムにlong値を取得する
	 *
	 * @param column カラム名
	 * @param value 値
	 */
	public abstract void setLong(String column, long value);

	/**
	 * 指定したカラムにfloat値を取得する
	 *
	 * @param column カラム名
	 * @param value 値
	 */
	public abstract void setFloat(String column, float value);

	/**
	 * 指定したカラムにdouble値を取得する
	 *
	 * @param column カラム名
	 * @param value 値
	 */
	public abstract void setDouble(String column, double value);

	/**
	 * 指定したカラムにdecimal値を取得する
	 *
	 * @param column カラム名
	 * @param value 値
	 */
	public abstract void setDecimal(String column, BigDecimal value);

	/**
	 * 指定したカラムにdate値を取得する
	 *
	 * @param column カラム名
	 * @param value 値
	 */
	public abstract void setDate(String column, Date value);

	/**
	 * 指定したカラムにString値を取得する
	 *
	 * @param column カラム名
	 * @param value 値
	 */
	public abstract void setString(String column, String value);

}
