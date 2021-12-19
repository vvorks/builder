package com.github.vvorks.builder.server.component;

import com.github.vvorks.builder.common.lang.Strings;
import com.github.vvorks.builder.server.expression.Argument;
import com.github.vvorks.builder.server.expression.BooleanLiteral;
import com.github.vvorks.builder.server.expression.ClassRef;
import com.github.vvorks.builder.server.expression.DateLiteral;
import com.github.vvorks.builder.server.expression.DoubleLiteral;
import com.github.vvorks.builder.server.expression.EnumRef;
import com.github.vvorks.builder.server.expression.EnumValueRef;
import com.github.vvorks.builder.server.expression.Expression;
import com.github.vvorks.builder.server.expression.FieldRef;
import com.github.vvorks.builder.server.expression.FloatLiteral;
import com.github.vvorks.builder.server.expression.IntLiteral;
import com.github.vvorks.builder.server.expression.LongLiteral;
import com.github.vvorks.builder.server.expression.NullLiteral;
import com.github.vvorks.builder.server.expression.NumericLiteral;
import com.github.vvorks.builder.server.expression.Operation;
import com.github.vvorks.builder.server.expression.OrderByExpression;
import com.github.vvorks.builder.server.expression.StringLiteral;

public class DebugWriter implements Expression.Visitor<Integer, String> {

	private String toString(Expression exp, Object trailer, int depth) {
		StringBuilder sb = new StringBuilder();
		sb.append(Strings.repeat("    ", depth));
		sb.append(exp.getType());
		sb.append("(");
		sb.append(exp.getWidth());
		sb.append(",");
		sb.append(exp.getScale());
		sb.append(")");
		sb.append(" ");
		sb.append(trailer.toString());
		sb.append("\n");
		return sb.toString();
	}

	@Override
	public String visit(OrderByExpression exp, Integer depth) {
		StringBuilder sb = new StringBuilder();
		sb.append(exp.getExpr().accept(this, depth));
		sb.append(" ");
		sb.append(exp.isAsc() ? "ASC" : "DESC");
		return sb.toString();
	}

	@Override
	public String visit(Operation exp, Integer depth) {
		StringBuilder sb = new StringBuilder(toString(exp, exp.getCode(), depth));
		for (Expression sub : exp.getOperands()) {
			sb.append(sub.accept(this, depth + 1));
		}
		return sb.toString();
	}

	@Override
	public String visit(ClassRef exp, Integer depth) {
		return toString(exp, exp.getContent().getClassName(), depth);
	}

	@Override
	public String visit(FieldRef exp, Integer depth) {
		return toString(exp, exp.getContent().getFieldName(), depth);
	}

	@Override
	public String visit(EnumRef exp, Integer depth) {
		return toString(exp, exp.getContent().getEnumName(), depth);
	}

	@Override
	public String visit(EnumValueRef exp, Integer depth) {
		return toString(exp, exp.getContent().getValueId(), depth);
	}

	@Override
	public String visit(BooleanLiteral exp, Integer depth) {
		return toString(exp, exp.getValue(), depth);
	}

	@Override
	public String visit(IntLiteral exp, Integer depth) {
		return toString(exp, exp.getValue(), depth);
	}

	@Override
	public String visit(LongLiteral exp, Integer depth) {
		return toString(exp, exp.getValue(), depth);
	}

	@Override
	public String visit(FloatLiteral exp, Integer depth) {
		return toString(exp, exp.getValue(), depth);
	}

	@Override
	public String visit(DoubleLiteral exp, Integer depth) {
		return toString(exp, exp.getValue(), depth);
	}

	@Override
	public String visit(NumericLiteral exp, Integer depth) {
		return toString(exp, exp.getValue(), depth);
	}

	@Override
	public String visit(DateLiteral exp, Integer depth) {
		return toString(exp, exp.getValue(), depth);
	}

	@Override
	public String visit(StringLiteral exp, Integer depth) {
		return toString(exp, exp.getValue(), depth);
	}

	@Override
	public String visit(NullLiteral exp, Integer depth) {
		return toString(exp, "null", depth);
	}

	@Override
	public String visit(Argument exp, Integer depth) {
		return toString(exp, "$" + exp.getName(), depth);
	}

}
