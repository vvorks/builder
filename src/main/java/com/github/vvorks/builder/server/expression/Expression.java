package com.github.vvorks.builder.server.expression;

import java.util.Collections;
import java.util.function.Consumer;

import com.github.vvorks.builder.server.domain.DataType;

public abstract class Expression {

	public static interface Visitor<A,R> {

		public R visit(Operation exp, A option);
		public R visit(OrderByExpression exp, A option);
		public R visit(ClassRef exp, A option);
		public R visit(FieldRef exp, A option);
		public R visit(EnumRef exp, A option);
		public R visit(EnumValueRef exp, A option);
		public R visit(BooleanLiteral exp, A option);
		public R visit(IntLiteral exp, A option);
		public R visit(LongLiteral exp, A option);
		public R visit(FloatLiteral exp, A option);
		public R visit(DoubleLiteral exp, A option);
		public R visit(NumericLiteral exp, A option);
		public R visit(DateLiteral exp, A option);
		public R visit(StringLiteral exp, A option);
		public R visit(NullLiteral exp, A option);
		public R visit(Argument exp, A option);

	}

	public abstract DataType getType();

	public int getWidth() {
		return 0;
	}

	public int getScale() {
		return 0;
	}

	public Iterable<Expression> getChildren() {
		return Collections.emptyList();
	}

	/**
	 * 簡易巡回
	 *
	 * @param func 式消費者
	 */
	public void accept(Consumer<Expression> func) {
		func.accept(this);
		for (Expression c : getChildren()) {
			c.accept(func);
		}
	}

	/**
	 * Visitorインターフェースによるノード巡回
	 *
	 * @param <A> Visitor引数型
	 * @param <R> Visitor戻り値型
	 * @param visitor Visitorインスタンス
	 * @param option Visitor引数
	 * @return Visitor戻り値
	 */
	public <A,R> R accept(Visitor<A,R> visitor, A option) {
		return null;
	}

}
