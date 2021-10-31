package com.github.vvorks.builder.server.component;

import java.util.EnumMap;
import java.util.List;

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
import com.github.vvorks.builder.server.expression.StringLiteral;

public class JavaTextWriter implements Expression.Visitor<Object, String> {

	private static final EnumMap<Operation.Code, String> SYMBOL_MAP = new EnumMap<>(Operation.Code.class);
	static {
		SYMBOL_MAP.put(Operation.Code.OR, "||");
		SYMBOL_MAP.put(Operation.Code.AND, "&&");
		SYMBOL_MAP.put(Operation.Code.XOR, "^");
		SYMBOL_MAP.put(Operation.Code.EQ, "==");
		SYMBOL_MAP.put(Operation.Code.NE, "!=");
		SYMBOL_MAP.put(Operation.Code.LT, "<");
		SYMBOL_MAP.put(Operation.Code.GT, ">");
		SYMBOL_MAP.put(Operation.Code.LE, "<=");
		SYMBOL_MAP.put(Operation.Code.GE, ">=");
		SYMBOL_MAP.put(Operation.Code.ADD, "+");
		SYMBOL_MAP.put(Operation.Code.SUB, "-");
		SYMBOL_MAP.put(Operation.Code.CONCAT, "+");
		SYMBOL_MAP.put(Operation.Code.MUL, "*");
		SYMBOL_MAP.put(Operation.Code.DIV, "/");
		SYMBOL_MAP.put(Operation.Code.MOD, "%");
		SYMBOL_MAP.put(Operation.Code.PLUS, "+");
		SYMBOL_MAP.put(Operation.Code.MINUS, "-");
		SYMBOL_MAP.put(Operation.Code.NOT, "!");
		//SYMBOL_MAP.put(Operation.Code.NOT, "~");
		SYMBOL_MAP.put(Operation.Code.GET, ".");
		SYMBOL_MAP.put(Operation.Code.CALL, ".");
	}

	@Override
	public String visit(Operation exp, Object option) {
		StringBuilder sb = new StringBuilder();
		Operation.Code code = exp.getCode();
		List<Expression> operands = exp.getOperands();
		if (code == Operation.Code.PAREN) {
			sb.append("(");
			sb.append(operands.get(0).accept(this, option));
			for (int i = 1; i < operands.size(); i++) {
				sb.append(",");
				sb.append(operands.get(i).accept(this, option));
			}
			sb.append(")");
		} else if (operands.size() == 1) {
			String symbol = SYMBOL_MAP.get(code);
			sb.append(symbol);
			sb.append(operands.get(0).accept(this, option));
		} else {
			String symbol = SYMBOL_MAP.get(code);
			sb.append(operands.get(0).accept(this, option));
			for (int i = 1; i < operands.size(); i++) {
				sb.append(symbol);
				sb.append(operands.get(i).accept(this, option));
			}
		}
		return sb.toString();
	}

	@Override
	public String visit(ClassRef exp, Object option) {
		return exp.getContent().getClassName();
	}

	@Override
	public String visit(FieldRef exp, Object option) {
		return exp.getContent().getFieldName();
	}

	@Override
	public String visit(EnumRef exp, Object option) {
		return exp.getContent().getEnumName();
	}

	@Override
	public String visit(EnumValueRef exp, Object option) {
		return exp.getContent().getValueId();
	}

	@Override
	public String visit(BooleanLiteral exp, Object option) {
		return String.valueOf(exp.getValue());
	}

	@Override
	public String visit(IntLiteral exp, Object option) {
		StringBuilder sb = new StringBuilder();
		int value = exp.getValue();
		int radix = exp.getRadix();
		if (radix == 8) {
			sb.append("0").append(Integer.toOctalString(value));
		} else if (radix == 16) {
			sb.append("0x").append(Integer.toHexString(value));
		} else {
			sb.append(Integer.toString(value));
		}
		return sb.toString();
	}

	@Override
	public String visit(LongLiteral exp, Object option) {
		StringBuilder sb = new StringBuilder();
		long value = exp.getValue();
		int radix = exp.getRadix();
		if (radix == 8) {
			sb.append("0").append(Long.toOctalString(value));
		} else if (radix == 16) {
			sb.append("0x").append(Long.toHexString(value));
		} else {
			sb.append(Long.toString(value));
		}
		sb.append("L");
		return sb.toString();
	}

	@Override
	public String visit(FloatLiteral exp, Object option) {
		StringBuilder sb = new StringBuilder();
		float value = exp.getValue();
		if (Float.isNaN(value)) {
			//NaNの場合
			sb.append("Float");
			sb.append(".NaN");
		} else if (Float.isInfinite(value)) {
			//正又は負の無限大の場合
			sb.append("Float");
			sb.append(value >= 0 ? ".POSITIVE_INFINITY" : ".NEGATIVE_INFINITY");
		} else {
			//実数表記又は指数表記
			sb.append(Float.toString(value));
			sb.append("f");
		}
		return sb.toString();
	}

	@Override
	public String visit(DoubleLiteral exp, Object option) {
		StringBuilder sb = new StringBuilder();
		double value = exp.getValue();
		if (Double.isNaN(value)) {
			//NaNの場合
			sb.append("Double");
			sb.append(".NaN");
		} else if (Double.isInfinite(value)) {
			//正又は負の無限大の場合
			sb.append("Double");
			sb.append(value >= 0 ? ".POSITIVE_INFINITY" : ".NEGATIVE_INFINITY");
		} else {
			//実数表記又は指数表記
			sb.append(Double.toString(value));
			sb.append("d");
		}
		return sb.toString();
	}

	@Override
	public String visit(NumericLiteral exp, Object option) {
		return exp.getValue().toString();
	}

	@Override
	public String visit(DateLiteral exp, Object option) {
		long t = exp.getValue().getTime();
		return String.valueOf(t) + "L";
	}

	@Override
	public String visit(StringLiteral exp, Object option) {
		return "\"" + exp.getValue() + "\"";
	}

	@Override
	public String visit(NullLiteral exp, Object option) {
		return "null";
	}

}
