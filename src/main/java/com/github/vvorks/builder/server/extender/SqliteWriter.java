package com.github.vvorks.builder.server.extender;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.EnumMap;
import java.util.List;

import com.github.vvorks.builder.server.domain.DataType;
import com.github.vvorks.builder.server.domain.EnumContent;
import com.github.vvorks.builder.server.domain.EnumValueContent;
import com.github.vvorks.builder.server.domain.FieldContent;
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
import com.github.vvorks.builder.shared.common.lang.Asserts;
import com.github.vvorks.builder.shared.common.lang.Strings;

public class SqliteWriter extends SqlWriter {

	private static final String CONST_NULL = "NULL";

	private static final String CONST_TRUE = "TRUE";

	private static final String CONST_FALSE = "FALSE";

	private static final EnumMap<Operation.Code, String> SYMBOL_MAP = new EnumMap<>(Operation.Code.class);
	static {
		SYMBOL_MAP.put(Operation.Code.OR, "OR");
		SYMBOL_MAP.put(Operation.Code.AND, "AND");
		SYMBOL_MAP.put(Operation.Code.EQ, "=");
		SYMBOL_MAP.put(Operation.Code.NE, "<>");
		SYMBOL_MAP.put(Operation.Code.LT, "<");
		SYMBOL_MAP.put(Operation.Code.GT, ">");
		SYMBOL_MAP.put(Operation.Code.LE, "<=");
		SYMBOL_MAP.put(Operation.Code.GE, ">=");
		SYMBOL_MAP.put(Operation.Code.ADD, "+");
		SYMBOL_MAP.put(Operation.Code.SUB, "-");
		SYMBOL_MAP.put(Operation.Code.CONCAT, "||");
		SYMBOL_MAP.put(Operation.Code.MUL, "*");
		SYMBOL_MAP.put(Operation.Code.DIV, "/");
		SYMBOL_MAP.put(Operation.Code.MOD, "%");
		SYMBOL_MAP.put(Operation.Code.PLUS, "+");
		SYMBOL_MAP.put(Operation.Code.MINUS, "-");
		SYMBOL_MAP.put(Operation.Code.NOT, "~");
	}

	@Override
	public String visit(OrderByExpression exp, Extenders extenders) {
		StringBuilder sb = new StringBuilder();
		sb.append(exp.getExpr().accept(this, extenders));
		sb.append(" ");
		sb.append(exp.isAsc() ? "ASC" : "DESC");
		return sb.toString();
	}

	@Override
	public String visit(Operation exp, Extenders extenders) {
		Operation.Code code = exp.getCode();
		String result;
		switch (code) {
		case PAREN:
			result = visitParenOperation(exp, extenders);
			break;
		case GET:
			result = visitGetOperation(exp, extenders);
			break;
		case CONST:
			result = visitConstOperation(exp, extenders);
			break;
		case PLUS:
		case MINUS:
		case NOT:
			result = visitUnaryOperation(exp, extenders);
			break;
		case XOR:
			result = visitXorOperation(exp, extenders);
			break;
		case LIKE:
			result = visitLikeOperation(exp, extenders);
			break;
		case MATCH:
			result = visitMatchOperation(exp, extenders);
			break;
		case COMMA:
			result = visitCommaOperation(exp, extenders);
			break;
		default:
			result = visitBinaryOperation(exp, extenders);
			break;
		}
		return result;
	}

	private String visitParenOperation(Operation exp, Extenders extenders) {
		StringBuilder sb = new StringBuilder();
		List<Expression> operands = exp.getOperands();
		sb.append("(");
		sb.append(operands.get(0).accept(this, extenders));
		sb.append(")");
		return sb.toString();
	}

	private String visitGetOperation(Operation exp, Extenders extenders) {
		List<Expression> operands = exp.getOperands();
		int n = operands.size();
		Asserts.check(operands.get(n - 1) instanceof FieldRef);
		FieldContent fld = ((FieldRef) operands.get(n - 1)).getContent();
		StringBuilder sb = new StringBuilder();
		int joinNo = exp.getJoinNo();
		if (fld.getType() == DataType.BOOLEAN) {
			sb.append("(");
		}
		if (joinNo > 0) {
			sb.append(TABLE_ALIAS_PREFIX).append(joinNo).append(".");
		}
		sb.append(COLUMN_PREFIX);
		sb.append(Strings.toUpperSnake(fld.getFieldName()));
		if (fld.getType() == DataType.BOOLEAN) {
			sb.append(" == 1)");
		}
		return sb.toString();
	}

	private String visitConstOperation(Operation exp, Extenders extenders) {
		List<Expression> operands = exp.getOperands();
		Asserts.check(operands.size() == 2);
		Asserts.check(operands.get(0) instanceof EnumRef);
		Asserts.check(operands.get(1) instanceof EnumValueRef);
		EnumContent ec = ((EnumRef) operands.get(0)).getContent();
		EnumValueContent ev = ((EnumValueRef) operands.get(1)).getContent();
		if (ec.isEncodeString()) {
			return "'" + ev.getValueId() +"'";
		} else {
			return String.valueOf(ev.getCode());
		}
	}

	private String visitUnaryOperation(Operation exp, Extenders extenders) {
		StringBuilder sb = new StringBuilder();
		Operation.Code code = exp.getCode();
		List<Expression> operands = exp.getOperands();
		String symbol = SYMBOL_MAP.get(code);
		sb.append(symbol);
		sb.append(operands.get(0).accept(this, extenders));
		return sb.toString();
	}

	private String visitXorOperation(Operation exp, Extenders extenders) {
		List<Expression> operands = exp.getOperands();
		//一旦結果を逆順でスタックに積む
		Deque<String> stack = new ArrayDeque<>();
		for (int i = operands.size() - 1; i >= 0; i--) {
			stack.push(operands.get(i).accept(this, extenders));
		}
		//スタックトップから順に組み立て
		StringBuilder wb = new StringBuilder();
		while (stack.size() > 1) {
			wb.setLength(0);
			String v1 = stack.pop();
			String v2 = stack.pop();
			wb.append("(");
			wb.append("(NOT (").append(v1).append(" AND ").append(v2).append("))");
			wb.append(" AND ");
			wb.append("(").append(v1).append(" OR ").append(v2).append(")");
			wb.append(")");
			stack.push(wb.toString());
		}
		return stack.pop();
	}

	private String visitLikeOperation(Operation exp, Extenders extenders) {
		StringBuilder sb = new StringBuilder();
		List<Expression> operands = exp.getOperands();
		String lValue = operands.get(0).accept(this, extenders);
		String rValue = operands.get(1).accept(this, extenders);
		sb.append(lValue).append(" LIKE ").append(rValue);
		return sb.toString();
	}

	private String visitMatchOperation(Operation exp, Extenders extenders) {
		StringBuilder sb = new StringBuilder();
		List<Expression> operands = exp.getOperands();
		String lValue = operands.get(0).accept(this, extenders);
		String rValue = operands.get(1).accept(this, extenders);
		sb.append(lValue).append(" REGEXP ").append(rValue);
		return sb.toString();
	}

	private String visitCommaOperation(Operation exp, Extenders extenders) {
		StringBuilder sb = new StringBuilder();
		List<Expression> operands = exp.getOperands();
		sb.append(operands.get(0).accept(this, extenders));
		for (int i = 1; i < operands.size(); i++) {
			sb.append(", ");
			sb.append(operands.get(i).accept(this, extenders));
		}
		return sb.toString();
	}

	private String visitBinaryOperation(Operation exp, Extenders extenders) {
		StringBuilder sb = new StringBuilder();
		Operation.Code code = exp.getCode();
		List<Expression> operands = exp.getOperands();
		Expression lv = operands.get(0);
		if (lv.getType() == DataType.REF) {
			sb.append(visitRefOperation(exp, extenders));
		} else {
			String symbol = SYMBOL_MAP.get(code);
			sb.append(lv.accept(this, extenders));
			for (int i = 1; i < operands.size(); i++) {
				sb.append(" ").append(symbol).append(" ");
				sb.append(operands.get(i).accept(this, extenders));
			}
		}
		return sb.toString();
	}

	private String visitRefOperation(Operation exp, Extenders extenders) {
		FieldExtender fe = extenders.getFieldExtender();
		Operation lv = (Operation) exp.getOperands().get(0);
		Operation.Code code = exp.getCode();
		Expression rv = exp.getOperands().get(1);
		Class<?> rType = rv.getClass();
		Asserts.check(rType == NullLiteral.class || rType == Argument.class);
		List<Expression> operands = lv.getOperands();
		int n = operands.size();
		FieldContent lastField = ((FieldRef) operands.get(n - 1)).getContent();
		StringBuilder sb = new StringBuilder();
		int joinNo = lv.getJoinNo();
		String sep = "";
		for (FieldContent fld : fe.getRefKeyFields(lastField)) {
			sb.append(sep);
			if (joinNo > 0) {
				sb.append(TABLE_ALIAS_PREFIX).append(joinNo).append(".");
			}
			sb.append(COLUMN_PREFIX);
			sb.append(Strings.toUpperSnake(fld.getFieldName()));
			if (rType == NullLiteral.class) {
				sb.append(" IS ");
				sb.append(code == Operation.Code.EQ ? "NULL" : "NOT NULL");
			} else if (rType == Argument.class) {
				//TODO 未確認
				sb.append("#{ ");
				sb.append(((Argument)rv).getName());
				sb.append(".");
				sb.append(fld.getFieldName());
				sb.append(" }");
			}
			sep = " AND ";
		}
		return sb.toString();
	}

	@Override
	public String visit(ClassRef exp, Extenders extenders) {
		Asserts.check(false);
		return null;
	}

	@Override
	public String visit(FieldRef exp, Extenders extenders) {
		Asserts.check(false);
		return null;
	}

	@Override
	public String visit(EnumRef exp, Extenders extenders) {
		Asserts.check(false);
		return null;
	}

	@Override
	public String visit(EnumValueRef exp, Extenders extenders) {
		Asserts.check(false);
		return null;
	}

	@Override
	public String visit(BooleanLiteral exp, Extenders extenders) {
		Boolean value = exp.getValue();
		if (value == null) {
			return CONST_NULL;
		} else if (value.equals(Boolean.TRUE)) {
			return CONST_TRUE;
		} else {
			return CONST_FALSE;
		}
	}

	@Override
	public String visit(IntLiteral exp, Extenders extenders) {
		if (exp.getValue() == null) {
			return CONST_NULL;
		}
		StringBuilder sb = new StringBuilder();
		int value = exp.getValue();
		int radix = exp.getRadix();
		if (radix == 16) {
			sb.append("0x").append(Integer.toHexString(value));
		} else {
			sb.append(Integer.toString(value));
		}
		return sb.toString();
	}

	@Override
	public String visit(LongLiteral exp, Extenders extenders) {
		if (exp.getValue() == null) {
			return CONST_NULL;
		}
		StringBuilder sb = new StringBuilder();
		long value = exp.getValue();
		int radix = exp.getRadix();
		if (radix == 16) {
			sb.append("0x").append(Long.toHexString(value));
		} else {
			sb.append(Long.toString(value));
		}
		return sb.toString();
	}

	@Override
	public String visit(FloatLiteral exp, Extenders extenders) {
		if (exp.getValue() == null) {
			return CONST_NULL;
		}
		StringBuilder sb = new StringBuilder();
		float value = exp.getValue();
		if (Float.isNaN(value)) {
			sb.append("'NaN'");
		} else if (Float.isInfinite(value)) {
			sb.append(value >= 0 ? "'+Infinity'" : "'-Infinity'");
		} else {
			sb.append(Float.toString(value));
		}
		return sb.toString();
	}

	@Override
	public String visit(DoubleLiteral exp, Extenders extenders) {
		if (exp.getValue() == null) {
			return CONST_NULL;
		}
		StringBuilder sb = new StringBuilder();
		double value = exp.getValue();
		if (Double.isNaN(value)) {
			sb.append("'NaN'");
		} else if (Double.isInfinite(value)) {
			sb.append(value >= 0 ? "'+Infinity'" : "'-Infinity'");
		} else {
			sb.append(Double.toString(value));
		}
		return sb.toString();
	}

	@Override
	public String visit(NumericLiteral exp, Extenders extenders) {
		if (exp.getValue() == null) {
			return CONST_NULL;
		}
		return exp.getValue().toString();
	}

	@Override
	public String visit(DateLiteral exp, Extenders extenders) {
		if (exp.getValue() == null) {
			return CONST_NULL;
		}
		long t = exp.getValue().getTime();
		return String.valueOf(t);
	}

	@Override
	public String visit(StringLiteral exp, Extenders extenders) {
		if (exp.getValue() == null) {
			return CONST_NULL;
		}
		return "'" + exp.getValue() + "'";
	}

	@Override
	public String visit(NullLiteral exp, Extenders extenders) {
		return CONST_NULL;
	}

	@Override
	public String visit(Argument exp, Extenders extenders) {
		return "#{" + exp.getName() + "}";
	}

}
