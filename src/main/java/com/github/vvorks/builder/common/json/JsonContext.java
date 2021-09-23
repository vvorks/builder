package com.github.vvorks.builder.common.json;

/**
 * JsonContext
 *
 * JSONデータの読み込み、編集、書き出しを行う。
 */
public interface JsonContext {

	/**
	 * 操作対象のJSONデータ型を取得する
	 *
	 * @return
	 * 		操作対象のJSONデータ型
	 * @see JsonType
	 */
	public JsonType getCurrentType();

	/**
	 * 指定位置の要素を新たな操作対象とする
	 *
	 * @param key
	 * 		キー
	 * 		指定のキーの要素が存在しない場合、要素を追加する。
	 * @throws IllegalStateException
	 * 		現在操作中の要素種別が{@link JsonType#OBJECT}以外の場合。
	 * 		および、新たな操作対象の要素種別が{@link JsonType#OBJECT}	以外の場合。
	 */
	public void enterObject(String key);

	/**
	 * 指定位置の要素を新たな操作対象とする
	 *
	 * @param index
	 * 		index == getLengh() の場合、要素を追加する。
	 * @throws IllegalStateException
	 * 		現在操作中の要素種別が{@link JsonType#ARRAY}以外の場合。
	 * 		および、新たな操作対象の要素種別が{@link JsonType#OBJECT}	以外の場合
	 */
	public void enterObject(int index);

	/**
	 * 指定位置の要素を新たな操作対象とする
	 *
	 * @param key
	 * 		キー
	 * 		指定のキーの要素が存在しない場合、要素を追加する。
	 * @throws IllegalStateException
	 * 		現在操作中の要素種別が{@link JsonType#OBJECT}以外の場合。
	 * 		および、新たな操作対象の要素種別が{@link JsonType#ARRAY}以外の場合
	 */
	public void enterArray(String key);

	/**
	 * 指定位置の要素を新たな操作対象とする
	 *
	 * @param index
	 * 		index == getLengh() の場合、要素を追加する
	 * 		現在操作中の要素種別は {@link JsonType#ARRAY} となる
	 * @throws IllegalStateException
	 * 		現在操作中の要素種別が{@link JsonType#ARRAY}以外の場合。
	 * 		および、新たな操作対象の要素種別が{@link JsonType#ARRAY}以外の場合
	 */
	public void enterArray(int index);

	/**
	 * 操作対象を1つ前の状態に戻す
	 *
	 * @throws IllegalStateException
	 * 		enter()回数よりleave回数が多い場合
	 */
	public void leave();

	/**
	 * 配列長を取得する
	 *
	 * @return
	 * 		配列長
	 * @throws IllegalStateException
	 * 		現在操作対象の要素種別が {@link JsonType#ARRAY} 以外の場合
	 */
	public int length();

	/**
	 * 指定位置のboolean要素を得る
	 *
	 * @param key
	 * 		指定位置キー
	 * @return
	 * 		指定位置のboolean要素
	 * 		キーが存在しない、又は値の型が不一致だった場合は、false
	 * @throws IllegalStateException
	 * 		現在操作対象の要素種別が
	 * 		{@link JsonType#OBJECT}
	 * 		以外の場合
	 */
	public boolean getBoolean(String key);

	/**
	 * 指定位置のboolean要素を得る
	 *
	 * @param key
	 * 		指定位置キー
	 * @oaram defaultValue
	 * 		キーが存在しない、又は値の型が不一致だった場合の既定値
	 * @return
	 * 		指定位置のboolean要素
	 * 		キーが存在しない、又は値の型が不一致だった場合は、defaultValue
	 * @throws IllegalStateException
	 * 		現在操作対象の要素種別が
	 * 		{@link JsonType#OBJECT}
	 * 		以外の場合
	 */
	public boolean getBoolean(String key, boolean defaultValue);

	/**
	 * 指定位置のint要素を得る
	 *
	 * @param key
	 * 		指定位置キー
	 * @return
	 * 		指定位置のint要素
	 * 		キーが存在しない、又は値の型が不一致だった場合は、0
	 * @throws IllegalStateException
	 * 		現在操作対象の要素種別が
	 * 		{@link JsonType#OBJECT}
	 * 		以外の場合
	 */
	public int getInt(String key);

	/**
	 * 指定位置のint要素を得る
	 *
	 * @param key
	 * 		指定位置キー
	 * @oaram defaultValue
	 * 		キーが存在しない、又は値の型が不一致だった場合の既定値
	 * @return
	 * 		指定位置のint要素
	 * 		キーが存在しない、又は値の型が不一致だった場合は、defaultValue
	 * @throws IllegalStateException
	 * 		現在操作対象の要素種別が
	 * 		{@link JsonType#OBJECT}
	 * 		以外の場合
	 */
	public int getInt(String key, int defaultValue);

	/**
	 * 指定位置のdouble要素を得る
	 *
	 * @param key
	 * 		指定位置キー
	 * @return
	 * 		指定位置のdouble要素
	 * 		キーが存在しない、又は値の型が不一致だった場合は、0.0d
	 * @throws IllegalStateException
	 * 		現在操作対象の要素種別が
	 * 		{@link JsonType#OBJECT}
	 * 		以外の場合
	 */
	public double getDouble(String key);

	/**
	 * 指定位置のdouble要素を得る
	 *
	 * @param key
	 * 		指定位置キー
	 * @oaram defaultValue
	 * 		キーが存在しない、又は値の型が不一致だった場合の既定値
	 * @return
	 * 		指定位置のdouble要素
	 * 		キーが存在しない、又は値の型が不一致だった場合は、defaultValue
	 * @throws IllegalStateException
	 * 		現在操作対象の要素種別が
	 * 		{@link JsonType#OBJECT}
	 * 		以外の場合
	 */
	public double getDouble(String key, double defaultValue);

	/**
	 * 指定位置のString要素を得る
	 *
	 * @param key
	 * 		指定位置キー
	 * @return
	 * 		指定位置のString要素
	 * 		キーが存在しない、又は値の型が不一致だった場合は、null
	 * @throws IllegalStateException
	 * 		現在操作対象の要素種別が
	 * 		{@link JsonType#OBJECT}
	 * 		以外の場合
	 */
	public String getString(String key);

	/**
	 * 指定位置のString要素を得る
	 *
	 * @param key
	 * 		指定位置キー
	 * @oaram defaultValue
	 * 		キーが存在しない、又は値の型が不一致だった場合の既定値
	 * @return
	 * 		指定位置のString要素
	 * 		キーが存在しない、又は値の型が不一致だった場合は、defaultValue
	 * @throws IllegalStateException
	 * 		現在操作対象の要素種別が
	 * 		{@link JsonType#OBJECT}
	 * 		以外の場合
	 */
	public String getString(String key, String defaultValue);

	/**
	 * 指定位置のJSON要素を得る
	 *
	 * @param key
	 * 		指定位置キー
	 * @return
	 * 		指定位置のJSON要素
	 * 		キーが存在しない、又は値の型が不一致だった場合は、null
	 * @throws IllegalStateException
	 * 		現在操作対象の要素種別が
	 * 		{@link JsonType#OBJECT}
	 * 		以外の場合
	 */
	public JsonValue getValue(String key);

	/**
	 * 指定位置のJSON要素を得る
	 *
	 * @param key
	 * 		指定位置キー
	 * @oaram defaultValue
	 * 		キーが存在しない、又は値の型が不一致だった場合の既定値
	 * @return
	 * 		指定位置のJSON要素
	 * 		キーが存在しない、又は値の型が不一致だった場合は、defaultValue
	 * @throws IllegalStateException
	 * 		現在操作対象の要素種別が
	 * 		{@link JsonType#OBJECT}
	 * 		以外の場合
	 */
	public JsonValue getValue(String key, JsonValue defaultValue);

	/**
	 * 指定位置にnullを設定する
	 *
	 * @param key
	 * 		指定位置キー
	 * @throws IllegalStateException
	 * 		現在操作対象の要素種別が
	 * 		{@link JsonType#OBJECT}
	 * 		以外の場合
	 */
	public void setNull(String key);

	/**
	 * 指定位置のboolean要素を設定する
	 *
	 * @param key
	 * 		指定位置キー
	 * @param value
	 * 		指定位置のboolean要素
	 * @throws IllegalStateException
	 * 		現在操作対象の要素種別が
	 * 		{@link JsonType#OBJECT}
	 * 		以外の場合
	 */
	public void setBoolean(String key, boolean value);

	/**
	 * 指定位置のint要素を設定する
	 *
	 * @param key
	 * 		指定位置キー
	 * @param value
	 * 		指定位置のint要素
	 * @throws IllegalStateException
	 * 		現在操作対象の要素種別が
	 * 		{@link JsonType#OBJECT}
	 * 		以外の場合
	 */
	public void setInt(String key, int value);

	/**
	 * 指定位置のdouble要素を設定する
	 *
	 * @param key
	 * 		指定位置キー
	 * @param value
	 * 		指定位置のdouble要素
	 * @throws IllegalStateException
	 * 		現在操作対象の要素種別が
	 * 		{@link JsonType#OBJECT}
	 * 		以外の場合
	 */
	public void setDouble(String key, double value);

	/**
	 * 指定位置のString要素を設定する
	 *
	 * @param key
	 * 		指定位置キー
	 * @param value
	 * 		指定位置のString要素
	 * @throws IllegalStateException
	 * 		現在操作対象の要素種別が
	 * 		{@link JsonType#OBJECT}
	 * 		以外の場合
	 */
	public void setString(String key, String value);

	/**
	 * 指定位置のJSON要素を設定する
	 *
	 * @param key
	 * 		指定位置キー
	 * @param obj
	 * 		指定位置のJavaオブジェクト。nullの場合JSONのnullを、それ以外の場合文字列化して設定する。
	 * @throws IllegalStateException
	 * 		現在操作対象の要素種別が
	 * 		{@link JsonType#OBJECT}
	 * 		以外の場合
	 */
	public void setString(String key, Object obj);

	/**
	 * 指定位置のJSON要素を設定する
	 *
	 * @param key
	 * 		指定位置キー
	 * @param value
	 * 		指定位置のJSON要素
	 * @throws IllegalStateException
	 * 		現在操作対象の要素種別が
	 * 		{@link JsonType#OBJECT}
	 * 		以外の場合
	 */
	public void setValue(String key, JsonValue value);

	/**
	 * 指定位置のJSON要素を設定する
	 *
	 * @param key
	 * 		指定位置キー
	 * @param jsonString
	 * 		指定位置のJSON要素となるJSON文字列
	 * @throws IllegalStateException
	 * 		現在操作対象の要素種別が
	 * 		{@link JsonType#OBJECT}
	 * 		以外の場合
	 */
	public void setValue(String key, String jsonString);

	/**
	 * 指定位置のboolean要素を得る
	 *
	 * @param index
	 * 		位置
	 * @return
	 * 		指定位置のboolean要素
	 * 		キーが存在しない、又は値の型が不一致だった場合は、false
	 * @throws IllegalStateException
	 * 		現在操作対象の要素種別が
	 * 		{@link JsonType#ARRAY}
	 * 		以外の場合
	 */
	public boolean getBoolean(int index);

	/**
	 * 指定位置のboolean要素を得る
	 *
	 * @param index
	 * 		位置
	 * @oaram defaultValue
	 * 		キーが存在しない、又は値の型が不一致だった場合の既定値
	 * @return
	 * 		指定位置のboolean要素
	 * 		キーが存在しない、又は値の型が不一致だった場合は、defaultValue
	 * @return
	 * 		指定位置のboolean要素
	 * @throws IllegalStateException
	 * 		現在操作対象の要素種別が
	 * 		{@link JsonType#ARRAY}
	 * 		以外の場合
	 */
	public boolean getBoolean(int index, boolean defaultValue);

	/**
	 * 指定位置のint要素を得る
	 *
	 * @param index
	 * 		位置
	 * @return
	 * 		指定位置のint要素
	 * 		キーが存在しない、又は値の型が不一致だった場合は0
	 * @throws IllegalStateException
	 * 		現在操作対象の要素種別が
	 * 		{@link JsonType#ARRAY}
	 * 		以外の場合
	 */
	public int getInt(int index);

	/**
	 * 指定位置のint要素を得る
	 *
	 * @param index
	 * 		位置
	 * @oaram defaultValue
	 * 		キーが存在しない、又は値の型が不一致だった場合の既定値
	 * @return
	 * 		指定位置のint要素
	 * 		キーが存在しない、又は値の型が不一致だった場合は、defaultValue
	 * @throws IllegalStateException
	 * 		現在操作対象の要素種別が
	 * 		{@link JsonType#ARRAY}
	 * 		以外の場合
	 */
	public int getInt(int index, int defaultValue);

	/**
	 * 指定位置のdouble要素を得る
	 *
	 * @param index
	 * 		位置
	 * @return
	 * 		指定位置のdouble要素
	 * 		キーが存在しない、又は値の型が不一致だった場合は、0.0d
	 * @throws IllegalStateException
	 * 		現在操作対象の要素種別が
	 * 		{@link JsonType#ARRAY}
	 * 		以外の場合
	 */
	public double getDouble(int index);

	/**
	 * 指定位置のdouble要素を得る
	 *
	 * @param index
	 * 		位置
	 * @oaram defaultValue
	 * 		キーが存在しない、又は値の型が不一致だった場合の既定値
	 * @return
	 * 		指定位置のdouble要素
	 * 		キーが存在しない、又は値の型が不一致だった場合は、defaultValue
	 * @throws IllegalStateException
	 * 		現在操作対象の要素種別が
	 * 		{@link JsonType#ARRAY}
	 * 		以外の場合
	 */
	public double getDouble(int index, double defaultValue);

	/**
	 * 指定位置のString要素を得る
	 *
	 * @param index
	 * 		位置
	 * @return
	 * 		指定位置のString要素
	 * 		キーが存在しない、又は値の型が不一致だった場合、null
	 * @throws IllegalStateException
	 * 		現在操作対象の要素種別が
	 * 		{@link JsonType#ARRAY}
	 * 		以外の場合
	 */
	public String getString(int index);

	/**
	 * 指定位置のString要素を得る
	 *
	 * @param index
	 * 		位置
	 * @oaram defaultValue
	 * 		キーが存在しない、又は値の型が不一致だった場合の既定値
	 * @return
	 * 		指定位置のString要素
	 * 		キーが存在しない、又は値の型が不一致だった場合は、defaultValue
	 * @throws IllegalStateException
	 * 		現在操作対象の要素種別が
	 * 		{@link JsonType#ARRAY}
	 * 		以外の場合
	 */
	public String getString(int index, String defaultValue);

	/**
	 * 指定位置のJSON要素を得る
	 *
	 * @param index
	 * 		位置
	 * @return
	 * 		指定位置のJSON要素
	 * 		キーが存在しない、又は値の型が不一致だった場合、null
	 * @throws IllegalStateException
	 * 		現在操作対象の要素種別が
	 * 		{@link JsonType#ARRAY}
	 * 		以外の場合
	 */
	public JsonValue getValue(int index);

	/**
	 * 指定位置のJSON要素を得る
	 *
	 * @param index
	 * 		位置
	 * @oaram defaultValue
	 * 		キーが存在しない、又は値の型が不一致だった場合の既定値
	 * @return
	 * 		指定位置のJSON要素
	 * 		キーが存在しない、又は値の型が不一致だった場合は、defaultValue
	 * @throws IllegalStateException
	 * 		現在操作対象の要素種別が
	 * 		{@link JsonType#ARRAY}
	 * 		以外の場合
	 */
	public JsonValue getValue(int index, JsonValue defaultValue);

	/**
	 * 指定位置のnullを設定する
	 *
	 * @param index
	 * 		位置
	 * @throws IllegalStateException
	 * 		現在操作対象の要素種別が
	 * 		{@link JsonType#ARRAY}
	 * 		以外の場合
	 */
	public void setNull(int index);

	/**
	 * 指定位置のboolean要素を設定する
	 *
	 * @param index
	 * 		位置
	 * @param value
	 * 		指定位置のboolean要素
	 * @throws IllegalStateException
	 * 		現在操作対象の要素種別が
	 * 		{@link JsonType#ARRAY}
	 * 		以外の場合
	 */
	public void setBoolean(int index, boolean value);

	/**
	 * 指定位置のint要素を設定する
	 *
	 * @param index
	 * 		位置
	 * @param value
	 * 		指定位置のint要素
	 * @throws IllegalStateException
	 * 		現在操作対象の要素種別が
	 * 		{@link JsonType#ARRAY}
	 * 		以外の場合
	 */
	public void setInt(int index, int value);

	/**
	 * 指定位置のdouble要素を設定する
	 *
	 * @param index
	 * 		位置
	 * @param value
	 * 		指定位置のdouble要素
	 * @throws IllegalStateException
	 * 		現在操作対象の要素種別が
	 * 		{@link JsonType#ARRAY}
	 * 		以外の場合
	 */
	public void setDouble(int index, double value);

	/**
	 * 指定位置のString要素を設定する
	 *
	 * @param index
	 * 		位置
	 * @param value
	 * 		指定位置のString要素
	 * @throws IllegalStateException
	 * 		現在操作対象の要素種別が
	 * 		{@link JsonType#ARRAY}
	 * 		以外の場合
	 */
	public void setString(int index, String value);

	/**
	 * 指定位置のJSON要素を設定する
	 *
	 * @param index
	 * 		位置
	 * @param jsonString
	 * 		指定位置のJavaオブジェクト。nullの場合JSONのnullを、それ以外の場合文字列化して設定する。
	 * @throws IllegalStateException
	 * 		現在操作対象の要素種別が
	 * 		{@link JsonType#ARRAY}
	 * 		以外の場合
	 */
	public void setString(int index, Object obj);

	/**
	 * 指定位置のJSON要素を設定する
	 *
	 * @param index
	 * 		位置
	 * @param value
	 * 		指定位置のJSON要素
	 * @throws IllegalStateException
	 * 		現在操作対象の要素種別が
	 * 		{@link JsonType#ARRAY}
	 * 		以外の場合
	 */
	public void setValue(int index, JsonValue value);

	/**
	 * 指定位置のJSON要素を設定する
	 *
	 * @param index
	 * 		位置
	 * @param jsonString
	 * 		指定位置のJSON要素となるJSON文字列
	 * @throws IllegalStateException
	 * 		現在操作対象の要素種別が
	 * 		{@link JsonType#ARRAY}
	 * 		以外の場合
	 */
	public void setValue(int index, String jsonString);

	/**
	 * 現在のデータ一式をJSON要素として返す
	 *
	 * @return
	 * 		JSON要素
	 */
	public JsonValue toJsonValue();

	/**
	 * 現在のデータ一式をJSON文字列として返す
	 *
	 * @return
	 * 		JSON文字列
	 */
	public String toJsonString();

}
