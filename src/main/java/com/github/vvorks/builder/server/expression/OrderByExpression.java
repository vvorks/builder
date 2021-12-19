package com.github.vvorks.builder.server.expression;

import java.util.Collections;

import com.github.vvorks.builder.server.domain.DataType;

public class OrderByExpression extends Expression {

	private Expression expr;

	private boolean asc;

	public OrderByExpression(Expression expr, boolean asc) {
		this.expr = expr;
		this.asc = asc;
	}

	@Override
	public DataType getType() {
		return expr.getType();
	}

	public Expression getExpr() {
		return expr;
	}

	public boolean isAsc() {
		return asc;
	}

	public Iterable<? extends Expression> getChildren() {
		return Collections.singleton(expr);
	}

	@Override
	public <A,R> R accept(Visitor<A,R> visitor, A option) {
		return visitor.visit(this, option);
	}

}
