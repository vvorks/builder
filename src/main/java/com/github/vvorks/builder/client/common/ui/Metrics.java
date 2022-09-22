package com.github.vvorks.builder.client.common.ui;

import java.util.ArrayList;
import java.util.List;

import com.github.vvorks.builder.shared.common.json.Json;
import com.github.vvorks.builder.shared.common.json.Jsonizable;
import com.github.vvorks.builder.shared.common.lang.Factory;

public class Metrics implements Jsonizable {

	/**
	 * メトリックスのインスタンスを取得する
	 *
	 * @return メトリックスインスタンス
	 */
	public static Metrics get() {
		return Factory.getInstance(Metrics.class);
	}

	/**
	 * locale
	 */
	private String locale;

	/**
	 * locales
	 */
	private String[] locales;

	/**
	 * languages
	 */
	private String[] languages;

	/**
	 * スクリーン幅
	 */
	private int screenWidth;

	/**
	 * スクリーン高
	 */
	private int screenHeight;

	/**
	 * emサイズ（ピクセル単位）
	 */
	private double emSize;

	/**
	 * exサイズ（ピクセル単位）
	 */
	private double exSize;

	/**
	 * inサイズ（ピクセル単位）
	 */
	private double inSize;

	/**
	 * 現在ロケールを取得する
	 *
	 * @return 現在ロケール文字列
	 */
	public String getLocale() {
		return locale;
	}

	/**
	 * 現在ロケールを設定する
	 *
	 * @param locale 設定するロケール
	 */
	public void setLocale(String locale) {
		this.locale = locale;
	}

	/**
	 * システムで利用可能なロケール一覧を取得する
	 *
	 * @return システムで利用可能なロケール一覧
	 */
	public String[] getLocales() {
		return locales;
	}

	/**
	 * システムで利用可能なロケール一覧を設定する
	 *
	 * @param locales システムで利用可能なロケール一覧
	 */
	public void setLocales(String[] locales) {
		this.locales = locales;
		List<String> list = new ArrayList<>();
		for (String loc : locales) {
			if (loc.length() == 2) {
				list.add(loc);
			}
		}
		this.languages = list.toArray(new String[list.size()]);
	}

	/**
	 * システムで利用可能な言語一覧を設定する
	 *
	 * @return システムで利用可能な言語一覧
	 */
	public String[] getLanguages() {
		return languages;
	}

	/**
	 * システムのスクリーン幅を取得する
	 *
	 * @return システムのスクリーン幅
	 */
	public int getScreenWidth() {
		return screenWidth;
	}

	/**
	 * システムのスクリーン高を取得する
	 *
	 * @return システムのスクリーン高
	 */
	public int getScreenHeight() {
		return screenHeight;
	}

	/**
	 * 1EMのサイズをピクセル単位で取得する
	 *
	 * @return 1EMのサイズ
	 */
	public double getEmSize() {
		return emSize;
	}

	/**
	 * 1EXのサイズをピクセル単位で取得する
	 *
	 * @return 1EXのサイズ
	 */
	public double getExSize() {
		return exSize;
	}

	/**
	 * 1インチのサイズをピクセル単位で取得する
	 *
	 * @return 1インチのサイズ
	 */
	public double getInSize() {
		return inSize;
	}

	/**
	 * 1cmのサイズをピクセル単位で取得する
	 *
	 * @return 1cmのサイズ
	 */
	public double getCmSize() {
		return inSize / 2.54;
	}

	/**
	 * 1mmのサイズをピクセル単位で取得する
	 *
	 * @return 1mmのサイズ
	 */
	public double getMmSize() {
		return inSize / 25.4;
	}

	/**
	 * 1ポイントのサイズをピクセル単位で取得する
	 *
	 * @return 1ポイントのサイズ
	 */
	public double getPtSize() {
		return inSize / 72.0;
	}

	/**
	 * 1パイカのサイズをピクセル単位で取得する
	 *
	 * @return 1パイカのサイズ
	 */
	public double getPcSize() {
		return inSize /  6.0;
	}

	/**
	 * スクリーンサイズを設定する
	 *
	 * @param width スクリーン幅
	 * @param height スクリーン高
	 */
	public void setScreenSize(int width, int height) {
		this.screenWidth = width;
		this.screenHeight = height;
	}

	/**
	 * 1EMのサイズを設定する
	 *
	 * @param emSize 1EMのサイズ
	 */
	public void setEmSize(double emSize) {
		this.emSize = emSize;
	}

	/**
	 * 1EXのサイズを設定する
	 *
	 * @param exSize 1EXのサイズ
	 */
	public void setExSize(double exSize) {
		this.exSize = exSize;
	}

	/**
	 * 1インチのサイズを設定する
	 *
	 * @param inSize 1インチのサイズ
	 */
	public void setInSize(double inSize) {
		this.inSize = inSize;
	}

	@Override
	public Json toJson() {
		Json json = Json.createObject();
		json.setInt("screenWidth", screenWidth);
		json.setInt("screenHeight", screenHeight);
		json.setDouble("emSize", emSize);
		json.setDouble("exSize", exSize);
		json.setDouble("inSize", inSize);
		return json;
	}

}
