package com.github.vvorks.builder.client.common.ui;

import java.util.ArrayList;
import java.util.List;

import com.github.vvorks.builder.common.json.Json;
import com.github.vvorks.builder.common.json.Jsonizable;
import com.github.vvorks.builder.common.lang.Factory;

public class Metrics implements Jsonizable {

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

	public Metrics() {
	}

	public String getLocale() {
		return locale;
	}

	public void setLocale(String locale) {
		this.locale = locale;
	}

	public String[] getLocales() {
		return locales;
	}

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

	public String[] getLanguages() {
		return languages;
	}

	public int getScreenWidth() {
		return screenWidth;
	}

	public int getScreenHeight() {
		return screenHeight;
	}

	public double getEmSize() {
		return emSize;
	}

	public double getExSize() {
		return exSize;
	}

	public double getInSize() {
		return inSize;
	}

	public double getCmSize() {
		return inSize / 2.54;
	}

	public double getMmSize() {
		return inSize / 25.4;
	}

	public double getPtSize() {
		return inSize / 72.0;
	}

	public double getPcSize() {
		return inSize /  6.0;
	}

	public void setScreenSize(int width, int height) {
		this.screenWidth = width;
		this.screenHeight = height;
	}

	public void setEmSize(double emSize) {
		this.emSize = emSize;
	}

	public void setExSize(double exSize) {
		this.exSize = exSize;
	}

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
