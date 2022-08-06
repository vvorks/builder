package com.github.vvorks.builder.shared.common.util;

import java.util.Objects;

import com.github.vvorks.builder.shared.common.lang.Asserts;
import com.github.vvorks.builder.shared.common.lang.Copyable;

/**
 * 長整数範囲型
 */
public final class LongRange implements Copyable<LongRange> {

	/** 最小値 */
	private final long minValue;

	/** 最大値 */
	private final long maxValue;

	/**
	 * minValue 以上 maxValue 以下の長整数値の範囲を作成する
	 *
	 * @param minValue 最小値
	 * @param maxValue 最大値＋１
	 */
	public LongRange(long minValue, long maxValue) {
		Asserts.require(minValue <= maxValue);
		this.minValue = minValue;
		this.maxValue = maxValue;
	}

	/**
	 * コピーコンストラクタ
	 *
	 * @param other コピー元
	 */
	private LongRange(LongRange other) {
		this.minValue = other.minValue;
		this.maxValue = other.maxValue;
	}

	@Override
	public LongRange copy() {
		return new LongRange(this);
	}

	/**
	 * 最小値を取得する
	 *
	 * @return 最小値
	 */
	public long getMinValue() {
		return minValue;
	}

	/**
	 * 最大値を取得する
	 *
	 * @return 最大値
	 */
	public long getMaxValue() {
		return maxValue;
	}

	/**
	 * 範囲が指定値を含むか否かを判定する
	 *
	 * @param value 指定値
	 * @return 範囲が指定値を含む場合、真
	 */
	public boolean contains(long value) {
		return minValue <= value && value <= maxValue;
	}

	/**
	 * 範囲が指定の対象範囲を含むか否かを判定する
	 *
	 * @param other 対象範囲
	 * @return 範囲が対象範囲を含む場合、真
	 */
	public boolean contains(LongRange other) {
		return minValue <= other.minValue && other.maxValue <= maxValue;
	}

	/**
	 * 範囲が交差するか否かを判定する
	 *
	 * @param other 比較範囲
	 * @return 比較範囲の一部が重複する場合、真
	 */
	public boolean intersects(LongRange other) {
		return !(other.maxValue < minValue || maxValue < other.minValue);
	}

	/**
	 * 交差する２つの範囲を合成する
	 *
	 * @param other 別の（交差する）範囲
	 * @return 合成した範囲
	 * @throws IllegalArgumentException 範囲が交差していない場合
	 */
	public LongRange union(LongRange other) {
		Asserts.require(intersects(other));
		return new LongRange(Math.min(minValue, other.minValue), Math.max(maxValue, other.maxValue));
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
			return equals((LongRange) obj);
		}
	}

	private boolean equals(LongRange other) {
		return	minValue == other.minValue &&
				maxValue == other.maxValue;
	}

	@Override
	public String toString() {
		return "" + minValue + "-" + maxValue;
	}

}
