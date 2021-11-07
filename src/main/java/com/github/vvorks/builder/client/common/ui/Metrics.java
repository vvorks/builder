package com.github.vvorks.builder.client.common.ui;

import com.github.vvorks.builder.common.json.Json;
import com.github.vvorks.builder.common.json.Jsonizable;
import com.github.vvorks.builder.common.logging.Logger;

public class Metrics implements Jsonizable {

	public static final Class<?> THIS = Metrics.class;
	public static final Logger LOGGER = Logger.createLogger(THIS);

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
		json.setNumber("screenWidth", screenWidth);
		json.setNumber("screenHeight", screenHeight);
		json.setNumber("emSize", emSize);
		json.setNumber("exSize", exSize);
		json.setNumber("inSize", inSize);
		return json;
	}



}