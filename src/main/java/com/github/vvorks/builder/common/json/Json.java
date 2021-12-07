package com.github.vvorks.builder.common.json;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import java.util.Map;
import java.util.Set;

import com.github.vvorks.builder.common.lang.Factory;

/**
 * JSON操作クラス
 *
 * 動作環境毎のJSONライブラリを抽象化するラッパークラス
 *
 * TODO 型変換追加処理（Integer, Long, Date, BigDecimal）
 */
public abstract class Json {

	/**
	 * JSONデータ種別を示す列挙型
	 */
	public enum Type {

		/** 未定義 */
		UNDEFINED(Void.class),

		/** null */
		NULL(null),

		/** 真偽値型 */
		BOOLEAN(Boolean.class),

		/** 数値型 */
		NUMBER(Double.class),

		/** 文字列型 */
		STRING(String.class),

		/** オブジェクト型 */
		OBJECT(Object.class),

		/** 配列型 */
		ARRAY(Object[].class);

		/** 対応するクラスオブジェクト */
		private final Class<?> cls;

		/** タイプを初期化する */
		private Type(Class<?> cls) {
			this.cls = cls;
		}

		/** 対応するJavaのクラスオブジェクトを取得する */
		public Class<?> asClass() {
			return cls;
		}

	}

	/** null値 */
	public static final Json NULL = createJson(null);

	/** true値 */
	public static final Json TRUE = createJson(true);

	/** false値 */
	public static final Json FALSE = createJson(false);

	public static final boolean DEFAULT_BOOLEAN = false;

	public static final int DEFAULT_INT = 0;

	public static final long DEFAULT_LONG = 0L;

	public static final float DEFAULT_FLOAT = 0.0f;

	public static final double DEFAULT_DOUBLE = 0.0d;

	public static final BigDecimal DEFAULT_DECIMAL = BigDecimal.ZERO;

	public static final Date DEFAULT_DATE = new Date(0);

	public static final String DEFAULT_STRING = "";

	/** Date型を文字列化する時のパターン文字列(SimpleDateFormat仕様) */
	public static final String DATE_PATTERN = "yyyy-MM-dd HH:mm:ss.SSSZ";

	public static Json createJson(Object arg) {
		return Factory.newInstance(Json.class, arg);
	}

	public static Json createObject() {
		return createJson("{}");
	}

	public static Json createArray() {
		return createJson("[]");
	}

	public static Json valueOf(Jsonizable value) {
		return value != null ? value.toJson() : NULL;
	}

	/** POJOへの変換（サポートされない場合あり） */
	public abstract Object toObject(Class<?> cls);

	/** このノードのデータ種別を返す */
	public abstract Type getType();

	/** このノードのBoolean値を返す */
	public boolean getBooleanValue() {
		return getBooleanValue(DEFAULT_BOOLEAN);
	}

	/** このノードのint値を返す */
	public int getIntValue() {
		return getIntValue(DEFAULT_INT);
	}

	/** このノードのLong値を返す */
	public long getLongValue() {
		return getLongValue(DEFAULT_LONG);
	}

	/** このノードのFloat値を返す */
	public float getFloatValue() {
		return getFloatValue(DEFAULT_FLOAT);
	}

	/** このノードのDouble値を返す */
	public double getDoubleValue() {
		return getDoubleValue(DEFAULT_DOUBLE);
	}

	/** このノードのDecimal値を返す */
	public BigDecimal getDecimalValue() {
		return getDecimalValue(DEFAULT_DECIMAL);
	}

	/** このノードのDate値を返す */
	public Date getDateValue() {
		return getDateValue(DEFAULT_DATE);
	}

	/** このノードのString値を返す */
	public String getStringValue() {
		return getStringValue(DEFAULT_STRING);
	}

	/** このノードのBoolean値を返す */
	public abstract boolean getBooleanValue(boolean defaultValue);

	/** このノードのint値を返す */
	public abstract int getIntValue(int defaultValue);

	/** このノードのLong値を返す */
	public abstract long getLongValue(long defaultValue);

	/** このノードのFloat値を返す */
	public abstract float getFloatValue(float defaultValue);

	/** このノードのDouble値を返す */
	public abstract double getDoubleValue(double defaultValue);

	/** このノードのDecimal値を返す */
	public abstract BigDecimal getDecimalValue(BigDecimal defaultValue);

	/** このノードのDate値を返す */
	public abstract Date getDateValue(Date defaultValue);

	/** このノードのString値を返す */
	public abstract String getStringValue(String defaultValue);

	/** 指定したキー値で得られる子ノードを返す */
	public abstract Json get(String key);

	/** 指定したキー値で得られる子ノードのデータ種別を返す */
	public abstract Type getType(String key);

	/** 指定したキー値で得られる子ノードのBoolean値を返す */
	public boolean getBoolean(String key) {
		return getBoolean(key, DEFAULT_BOOLEAN);
	}

	/** 指定したキー値で得られる子ノードのint値を返す */
	public int getInt(String key) {
		return getInt(key, DEFAULT_INT);
	}

	/** 指定したキー値で得られる子ノードのLong値を返す */
	public long getLong(String key) {
		return getLong(key, DEFAULT_LONG);
	}

	/** 指定したキー値で得られる子ノードのFloat値を返す */
	public float getFloat(String key) {
		return getFloat(key, DEFAULT_FLOAT);
	}

	/** 指定したキー値で得られる子ノードのDouble値を返す */
	public double getDouble(String key) {
		return getDouble(key, DEFAULT_DOUBLE);
	}

	/** 指定したキー値で得られる子ノードのDecimal値を返す */
	public BigDecimal getDecimal(String key) {
		return getDecimal(key, DEFAULT_DECIMAL);
	}

	/** 指定したキー値で得られる子ノードのDate値を返す */
	public Date getDate(String key) {
		return getDate(key, DEFAULT_DATE);
	}

	/** 指定したキー値で得られる子ノードのString値を返す */
	public String getString(String key) {
		return getString(key, DEFAULT_STRING);
	}

	/** 指定したキー値で得られる子ノードのBoolean値を返す */
	public abstract boolean getBoolean(String key, boolean defaultValue);

	/** 指定したキー値で得られる子ノードのint値を返す */
	public abstract int getInt(String key, int defaultValue);

	/** 指定したキー値で得られる子ノードのLong値を返す */
	public abstract long getLong(String key, long defaultValue);

	/** 指定したキー値で得られる子ノードのFloat値を返す */
	public abstract float getFloat(String key, float defaultValue);

	/** 指定したキー値で得られる子ノードのDouble値を返す */
	public abstract double getDouble(String key, double defaultValue);

	/** 指定したキー値で得られる子ノードのDecimal値を返す */
	public abstract BigDecimal getDecimal(String key, BigDecimal defaultValue);

	/** 指定したキー値で得られる子ノードのDate値を返す */
	public abstract Date getDate(String key, Date defaultValue);

	/** 指定したキー値で得られる子ノードのString値を返す */
	public abstract String getString(String key, String defaultValue);

	/** 配列ノードの子ノード数を返す */
	public abstract int size();

	/** 指定したindex値で得られる子ノードを返す */
	public abstract Json get(int index);

	/** 指定したindex値で得られる子ノードのノード種別を返す */
	public abstract Type getType(int index);

	/** 指定したindex値で得られる子ノードのBoolean値を返す */
	public boolean getBoolean(int index) {
		return getBoolean(index, DEFAULT_BOOLEAN);
	}

	/** 指定したindex値で得られる子ノードのint値を返す */
	public int getInt(int index) {
		return getInt(index, DEFAULT_INT);
	}

	/** 指定したindex値で得られる子ノードのLong値を返す */
	public long getLong(int index) {
		return getLong(index, DEFAULT_LONG);
	}

	/** 指定したindex値で得られる子ノードのFloat値を返す */
	public float getFloat(int index) {
		return getFloat(index, DEFAULT_FLOAT);
	}

	/** 指定したindex値で得られる子ノードのDouble値を返す */
	public double getDouble(int index) {
		return getDouble(index, DEFAULT_DOUBLE);
	}

	/** 指定したindex値で得られる子ノードのDecimal値を返す */
	public BigDecimal getDecimal(int index) {
		return getDecimal(index, DEFAULT_DECIMAL);
	}

	/** 指定したindex値で得られる子ノードのDate値を返す */
	public Date getDate(int index) {
		return getDate(index, DEFAULT_DATE);
	}

	/** 指定したindex値で得られる子ノードのDouble値を返す */
	public String getString(int index) {
		return getString(index, DEFAULT_STRING);
	}

	/** 指定したindex値で得られる子ノードのBoolean値を返す */
	public abstract boolean getBoolean(int index, boolean defaultValue);

	/** 指定したindex値で得られる子ノードのint値を返す */
	public abstract int getInt(int index, int defaultValue);

	/** 指定したindex値で得られる子ノードのLong値を返す */
	public abstract long getLong(int index, long defaultValue);

	/** 指定したindex値で得られる子ノードのFloat値を返す */
	public abstract float getFloat(int index, float defaultValue);

	/** 指定したindex値で得られる子ノードのDouble値を返す */
	public abstract double getDouble(int index, double defaultValue);

	/** 指定したindex値で得られる子ノードのDecimal値を返す */
	public abstract BigDecimal getDecimal(int index, BigDecimal defaultValue);

	/** 指定したindex値で得られる子ノードのDate値を返す */
	public abstract Date getDate(int index, Date defaultValue);

	/** 指定したindex値で得られる子ノードのDouble値を返す */
	public abstract String getString(int index, String defaultValue);

	/** Json文字列を返す */
	public abstract String toJsonString();

	/** 子ノードの値一式を返す */
	public abstract Collection<Json> values();

	/** 子ノードのキー一式を返す */
	public abstract Set<String> keySet();

	/** 子ノードのエントリー一式を返す */
	public abstract Set<Map.Entry<String,Json>> entrySet();

	public abstract void remove(String key);

	public abstract void setNull(String key);

	public abstract void setBoolean(String key, boolean value);

	public abstract void setInt(String key, int value);

	public abstract void setLong(String key, long value);

	public abstract void setFloat(String key, float value);

	public abstract void setDouble(String key, double value);

	public abstract void setDecimal(String key, BigDecimal value);

	public abstract void setDate(String key, Date value);

	public abstract void setString(String key, String value);

	public void setString(String key, Object value) {
		if (value == null) {
			setNull(key);
		} else {
			setString(key, value.toString());
		}
	}

	public abstract void set(String key, Json value);

	public abstract Json setNewObject(String key);

	public abstract Json setNewArray(String key);

	public abstract void setNull(int index);

	public abstract void setBoolean(int index, boolean value);

	public abstract void setInt(int index, int value);

	public abstract void setLong(int index, long value);

	public abstract void setFloat(int index, float value);

	public abstract void setDouble(int index, double value);

	public abstract void setDecimal(int index, BigDecimal value);

	public abstract void setDate(int index, Date value);

	public abstract void setString(int index, String value);

	public void setString(int index, Object value) {
		if (value == null) {
			setNull(index);
		} else {
			setString(index, value.toString());
		}
	}

	public abstract void set(int index, Json value);

	public abstract Json setNewObject(int index);

	public abstract Json setNewArray(int index);

	public void addNull() {
		setNull(size());
	}

	public void addBoolean(boolean value) {
		setBoolean(size(), value);
	}

	public void addInt(int value) {
		setInt(size(), value);
	}

	public void addLong(long value) {
		setLong(size(), value);
	}

	public void addFloat(float value) {
		setFloat(size(), value);
	}

	public void addDouble(double value) {
		setDouble(size(), value);
	}

	public void addDecimal(BigDecimal value) {
		setDecimal(size(), value);
	}

	public void addDate(Date value) {
		setDate(size(), value);
	}

	public void addString(String value) {
		setString(size(), value);
	}

	public void add(Json value) {
		set(size(), value);
	}

	public Json addNewObject() {
		return setNewObject(size());
	}

	public Json addNewArray() {
		return setNewArray(size());
	}

	public String toString() {
		return toJsonString();
	}

}
