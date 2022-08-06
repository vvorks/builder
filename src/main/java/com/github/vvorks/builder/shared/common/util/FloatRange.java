package com.github.vvorks.builder.shared.common.util;

import java.util.Objects;

import com.github.vvorks.builder.shared.common.lang.Asserts;
import com.github.vvorks.builder.shared.common.lang.Copyable;

/**
 * 単精度実数範囲型
 */
public final class FloatRange implements Copyable<FloatRange> {

	/** 最小値 */
	private final float minValue;

	/** 最大値 */
	private final float maxValue;

	/**
	 * minValue 以上 maxValue 以下の単精度実数値の範囲を作成する
	 *
	 * @param minValue 最小値
	 * @param maxValue 最大値＋１
	 */
	public FloatRange(float minValue, float maxValue) {
		Asserts.require(minValue <= maxValue);
		this.minValue = minValue;
		this.maxValue = maxValue;
	}

	/**
	 * コピーコンストラクタ
	 *
	 * @param other コピー元
	 */
	private FloatRange(FloatRange other) {
		this.minValue = other.minValue;
		this.maxValue = other.maxValue;
	}

	@Override
	public FloatRange copy() {
		return new FloatRange(this);
	}

	/**
	 * 最小値を取得する
	 *
	 * @return 最小値
	 */
	public float getMinValue() {
		return minValue;
	}

	/**
	 * 最大値を取得する
	 *
	 * @return 最大値
	 */
	public float getMaxValue() {
		return maxValue;
	}

	/**
	 * 範囲が指定値を含むか否かを判定する
	 *
	 * @param value 指定値
	 * @return 範囲が指定値を含む場合、真
	 */
	public boolean contains(float value) {
		return minValue <= value && value <= maxValue;
	}

	/**
	 * 範囲が指定の対象範囲を含むか否かを判定する
	 *
	 * @param other 対象範囲
	 * @return 範囲が対象範囲を含む場合、真
	 */
	public boolean contains(FloatRange other) {
		return minValue <= other.minValue && other.maxValue <= maxValue;
	}

	/**
	 * 範囲が交差するか否かを判定する
	 *
	 * @param other 比較範囲
	 * @return 比較範囲の一部が重複する場合、真
	 */
	public boolean intersects(FloatRange other) {
		return !(other.maxValue < minValue || maxValue < other.minValue);
	}

	/**
	 * 交差する２つの範囲を合成する
	 *
	 * @param other 別の（交差する）範囲
	 * @return 合成した範囲
	 * @throws IllegalArgumentException 範囲が交差していない場合
	 */
	public FloatRange union(FloatRange other) {
		Asserts.require(intersects(other));
		return new FloatRange(Math.min(minValue, other.minValue), Math.max(maxValue, other.maxValue));
	}

	@Override
	public int hashCode() {
		return Objects.hash(maxValue, minValue);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		} else if (obj == null) {
			return false;
		} else if (getClass() != obj.getClass()) {
			return false;
		} else {
			return equals((FloatRange) obj);
		}
	}

	private boolean equals(FloatRange other) {
		return	Float.floatToIntBits(minValue) == Float.floatToIntBits(other.minValue) &&
				Float.floatToIntBits(maxValue) == Float.floatToIntBits(other.maxValue);
	}

	@Override
	public String toString() {
		return "" + minValue + "-" + maxValue;
	}

}
