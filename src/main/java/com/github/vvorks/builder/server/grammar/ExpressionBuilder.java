package com.github.vvorks.builder.server.grammar;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.github.vvorks.builder.common.lang.Factory;
import com.github.vvorks.builder.common.lang.Strings;
import com.github.vvorks.builder.common.util.Logger;
import com.github.vvorks.builder.server.domain.ClassDto;
import com.github.vvorks.builder.server.domain.DataType;
import com.github.vvorks.builder.server.domain.EnumDto;
import com.github.vvorks.builder.server.domain.EnumValueDto;
import com.github.vvorks.builder.server.domain.FieldDto;
import com.github.vvorks.builder.server.domain.ProjectDto;
import com.github.vvorks.builder.server.expression.BooleanLiteral;
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
import com.github.vvorks.builder.server.mapper.ClassMapper;
import com.github.vvorks.builder.server.mapper.EnumValueMapper;
import com.github.vvorks.builder.server.mapper.FieldMapper;
import com.github.vvorks.builder.server.mapper.ProjectMapper;

@Component
public class ExpressionBuilder implements ExprParserVisitor {

	private static final Class<?> THIS = ExpressionBuilder.class;
	private static final Logger LOGGER = Factory.newInstance(Logger.class, THIS);

	private static final String UNDER_CONSTRUCTION = "under construction";
	private static final String TYPE_UNMATCH = "type unmatch";
	private static final String UNDEFINED_PROPERTY = "undefined property";

	@Autowired
	private ExprParser parser;

	@Autowired
	private ProjectMapper projectMapper;

	@Autowired
	private ClassMapper classMapper;

	@Autowired
	private FieldMapper fieldMapper;

	@Autowired
	private EnumValueMapper enumValueMapper;

	private ProjectDto project;

	private ClassDto context;

	public Expression build(ExprNode main, ProjectDto project, ClassDto context) throws ParseException {
		this.project = project;
		this.context = context;
		return main.jjtAccept(this, null);
	}

	private FieldDto getField(String name, ClassDto cls) {
		List<FieldDto> fields = classMapper.findFields(cls, name, null, null, null, null, null, null, 0, 0);
		return (fields.size() == 1) ? fields.get(0) : null;
	}

	private ClassDto getReturnClass(FieldDto fld) {
		FieldDto resultField = fieldMapper.get(fld.getFrefFieldId());
		ClassDto returnClass = classMapper.get(resultField.getOwnerClassId());
		return returnClass;
	}

	private EnumDto getEnum(String name, ProjectDto prj) {
		List<EnumDto> enums = projectMapper.findEnums(prj, name, null, 0, 0);
		return (enums.size() == 1) ? enums.get(0) : null;
	}

	private EnumValueDto getEnumValue(String rName, EnumDto enm) {
		return enumValueMapper.get(rName, enm.getEnumId());
	}

	public Expression visitChildren(ExprNode node, Expression unused) throws ParseException {
		Expression result = null;
		for (ExprNode child : node.getChildren()) {
			LOGGER.debug("accept %s", child.getClass().getSimpleName());
			result = child.jjtAccept(this, unused);
		}
		return result;
	}

	@Override
	public Expression visit(SimpleNode node, Expression unused) throws ParseException {
		return visitChildren((ExprNode) node, unused);
	}

	@Override
	public Expression visit(ASTMain node, Expression unused) throws ParseException {
		return visitChildren(node, unused);
	}

	@Override
	public Expression visit(ASTBinaryExpression node, Expression unused) throws ParseException {
		int n = node.size();
		int i = 0;
		Expression lValue = node.getChild(i++).jjtAccept(this, unused);
		ExprNode operator = node.getChild(i++);
		Expression rValue = node.getChild(i++).jjtAccept(this, unused);
		lValue = newBinaryOperation(lValue, operator, rValue);
		while (i < n) {
			operator = node.getChild(i++);
			rValue = node.getChild(i++).jjtAccept(this, unused);
			lValue = newBinaryOperation(lValue, operator, rValue);
		}
		return lValue;
	}

	@Override
	public Expression visit(ASTBinaryOperator node, Expression unused) throws ParseException {
		throw new InternalError();
	}

	private Operation newBinaryOperation(
			Expression lValue, ExprNode node, Expression rValue) throws ParseException {
		Operation.Code operator = getBinaryOperator(node);
		switch (operator) {
		case ADD:
			if (isEitherString(lValue, rValue)) {
				return newStringOperation(lValue, Operation.Code.CONCAT, rValue);
			} else if (isBothNumber(lValue, rValue)) {
				return newArithmeticOperation(lValue, operator, rValue);
			}
			throw new SemanticException(TYPE_UNMATCH, node);
		case SUB:
		case MUL:
		case DIV:
		case MOD:
			if (isBothNumber(lValue, rValue)) {
				return newArithmeticOperation(lValue, operator, rValue);
			}
			throw new SemanticException(TYPE_UNMATCH, node);
		case GE: case GT:
		case LE: case LT:
			if (isBothNumber(lValue, rValue) || isBothString(lValue, rValue) || isBothDate(lValue, rValue)) {
				return newBooleanOperation(lValue, operator, rValue);
			}
			throw new SemanticException(TYPE_UNMATCH, node);
		case EQ: case NE:
			if (isBothNumber(lValue, rValue) || isSameType(lValue, rValue)) {
				return newBooleanOperation(lValue, operator, rValue);
			}
			throw new SemanticException(TYPE_UNMATCH, node);
		case AND:
		case OR:
		case XOR:
			if (isBothBoolean(lValue, rValue)) {
				return newBooleanOperation(lValue, operator, rValue);
			}
			throw new SemanticException(TYPE_UNMATCH, node);
		default:
			throw new InternalError();
		}
	}

	private boolean isBothNumber(Expression lValue, Expression rValue) {
		return NUMBER_TYPES.contains(lValue.getType()) && NUMBER_TYPES.contains(rValue.getType());
	}

	private boolean isEitherString(Expression lValue, Expression rValue) {
		return lValue.getType() == DataType.STRING || rValue.getType() == DataType.STRING;
	}

	private boolean isBothString(Expression lValue, Expression rValue) {
		return lValue.getType() == DataType.STRING && rValue.getType() == DataType.STRING;
	}

	private boolean isBothDate(Expression lValue, Expression rValue) {
		return lValue.getType() == DataType.DATE && rValue.getType() == DataType.DATE;
	}

	private boolean isBothBoolean(Expression lValue, Expression rValue) {
		return lValue.getType() == DataType.BOOLEAN && rValue.getType() == DataType.BOOLEAN;
	}

	private boolean isSameType(Expression lValue, Expression rValue) {
		return lValue.getType() == rValue.getType();
	}

	private Operation newStringOperation(Expression lValue, Operation.Code code, Expression rValue) {
		Operation result;
		result = new Operation(code);
		result.addOperand(lValue);
		result.addOperand(rValue);
		result.setType(DataType.STRING);
		return result;
	}

	private Operation newArithmeticOperation(Expression lValue, Operation.Code code, Expression rValue) {
		Operation result;
		result = new Operation(code);
		result.addOperand(lValue);
		result.addOperand(rValue);
		if (lValue.getType() == DataType.NUMERIC || rValue.getType() == DataType.NUMERIC) {
			result.setType(DataType.NUMERIC);
			if (lValue.getType() == DataType.NUMERIC && rValue.getType() == DataType.NUMERIC) {
				result.setScale(Math.max(lValue.getScale(), rValue.getScale()));
				int lDigit = lValue.getWidth() - lValue.getScale();
				int rDigit = rValue.getWidth() - rValue.getScale();
				result.setWidth(Math.max(lDigit, rDigit) + result.getScale());
			} else if (lValue.getType() == DataType.NUMERIC) {
				result.setWidth(lValue.getWidth());
				result.setScale(lValue.getScale());
			} else {
				result.setWidth(rValue.getWidth());
				result.setScale(rValue.getScale());
			}
		} else if (lValue.getType() == DataType.REAL || rValue.getType() == DataType.REAL) {
			result.setType(DataType.REAL);
			if (lValue.getType() == DataType.REAL && rValue.getType() == DataType.REAL) {
				result.setWidth(Math.max(lValue.getWidth(), rValue.getWidth()));
			} else {
				result.setWidth(64);
			}
		} else {
			result.setType(DataType.INTEGER);
			result.setWidth(Math.max(32, Math.max(lValue.getWidth(), rValue.getWidth())));
		}
		return result;
	}

	private Operation newBooleanOperation(Expression lValue, Operation.Code code, Expression rValue) {
		Operation result;
		result = new Operation(code);
		result.addOperand(lValue);
		result.addOperand(rValue);
		result.setType(DataType.BOOLEAN);
		return result;
	}

	private static final Map<Integer, Operation.Code> BINARY_OP_MAP = new HashMap<>();
	static {
		BINARY_OP_MAP.put(ExprParserConstants.OR, Operation.Code.OR);
		BINARY_OP_MAP.put(ExprParserConstants.SC_OR, Operation.Code.OR);
		BINARY_OP_MAP.put(ExprParserConstants.BIT_OR, Operation.Code.OR);
		BINARY_OP_MAP.put(ExprParserConstants.AND, Operation.Code.AND);
		BINARY_OP_MAP.put(ExprParserConstants.SC_AND, Operation.Code.AND);
		BINARY_OP_MAP.put(ExprParserConstants.BIT_AND, Operation.Code.AND);
		BINARY_OP_MAP.put(ExprParserConstants.XOR, Operation.Code.XOR);
		BINARY_OP_MAP.put(ExprParserConstants.BIT_XOR, Operation.Code.XOR);
		BINARY_OP_MAP.put(ExprParserConstants.EQ, Operation.Code.EQ);
		BINARY_OP_MAP.put(ExprParserConstants.NE, Operation.Code.NE);
		BINARY_OP_MAP.put(ExprParserConstants.LTGT, Operation.Code.NE);
		BINARY_OP_MAP.put(ExprParserConstants.LT, Operation.Code.LT);
		BINARY_OP_MAP.put(ExprParserConstants.GT, Operation.Code.GT);
		BINARY_OP_MAP.put(ExprParserConstants.LE, Operation.Code.LE);
		BINARY_OP_MAP.put(ExprParserConstants.GE, Operation.Code.GE);
		BINARY_OP_MAP.put(ExprParserConstants.PLUS, Operation.Code.ADD);
		BINARY_OP_MAP.put(ExprParserConstants.MINUS, Operation.Code.SUB);
		BINARY_OP_MAP.put(ExprParserConstants.STAR, Operation.Code.MUL);
		BINARY_OP_MAP.put(ExprParserConstants.SLASH, Operation.Code.DIV);
		BINARY_OP_MAP.put(ExprParserConstants.REM, Operation.Code.MOD);
	}

	private Operation.Code getBinaryOperator(ExprNode node) {
		ExprToken t = (ExprToken) node.firstToken;
		int kind = (t.kind == ExprParserConstants.GT) ? t.realKind : t.kind;
		return BINARY_OP_MAP.get(kind);
	}

	private static final EnumSet<DataType> NUMBER_TYPES =
			EnumSet.of(DataType.INTEGER, DataType.REAL, DataType.NUMERIC);

	@Override
	public Expression visit(ASTUnaryExpression node, Expression unused) throws ParseException {
		ExprNode operator = node.getChild(0);
		Expression rValue = node.getChild(1).jjtAccept(this, unused);
		return newUnaryOperation(operator, rValue);
	}

	@Override
	public Expression visit(ASTUnaryOperator node, Expression unused) throws ParseException {
		throw new InternalError();
	}

	private Operation newUnaryOperation(
			ExprNode node, Expression rValue) throws ParseException {
		Operation.Code operator = getUnaryOperator(node);
		DataType rType = rValue.getType();
		Operation result;
		switch (operator) {
		case PLUS:
		case MINUS:
			if (!NUMBER_TYPES.contains(rType)) {
				throw new SemanticException(TYPE_UNMATCH, node);
			}
			result = new Operation(operator);
			result.addOperand(rValue);
			result.setType(rValue.getType());
			result.setWidth(rValue.getWidth());
			result.setScale(rValue.getScale());
			break;
		case NOT:
			if (rType != DataType.BOOLEAN) {
				throw new SemanticException(TYPE_UNMATCH, node);
			}
			result = new Operation(operator);
			result.addOperand(rValue);
			result.setType(DataType.BOOLEAN);
			break;
		default:
			throw new InternalError();
		}
		return result;
	}

	private Operation.Code getUnaryOperator(ExprNode node) {
		ExprToken t = (ExprToken) node.firstToken;
		int kind = t.kind;
		return UNARY_OP_MAP.get(kind);
	}

	private static final Map<Integer, Operation.Code> UNARY_OP_MAP = new HashMap<>();
	static {
		UNARY_OP_MAP.put(ExprParserConstants.PLUS, Operation.Code.PLUS);
		UNARY_OP_MAP.put(ExprParserConstants.MINUS, Operation.Code.MINUS);
		UNARY_OP_MAP.put(ExprParserConstants.NOT, Operation.Code.NOT);
		UNARY_OP_MAP.put(ExprParserConstants.BANG, Operation.Code.NOT);
	}

	@Override
	public Expression visit(ASTPrimaryExpression node, Expression unused) throws ParseException {
		int n = node.size();
		int i = 0;
		ExprNode child = node.getChild(i++);
		Expression lValue = child.jjtAccept(this, unused);
		switch (child.id) {
		case ExprParserTreeConstants.JJTPROPERTY:
			String name = ((UnresolvedProperty)lValue).getName();
			FieldDto fld;
			EnumDto enm;
			if ((fld = getField(name, context)) != null) {
				lValue = new FieldRef(fld);
			} else if ((enm = getEnum(name, project)) != null) {
				lValue = new EnumRef(enm);
			} else {
				throw new SemanticException(UNDEFINED_PROPERTY, child);
			}
			break;
		case ExprParserTreeConstants.JJTINVOCATION:
			throw new SemanticException(UNDER_CONSTRUCTION, child);
		default:
			break;
		}
		Expression last = lValue;
		while (i < n) {
			child = node.getChild(i++);
			Expression rValue = child.jjtAccept(this, unused);
			if (last instanceof FieldRef) {
				switch (child.id) {
				case ExprParserTreeConstants.JJTPROPERTY:
					String rName = ((UnresolvedProperty)rValue).getName();
					FieldDto fld = ((FieldRef) last).getDto();
					if (fld.getType() != DataType.FIELD_REF) {
						throw new SemanticException(TYPE_UNMATCH, child);
					}
					ClassDto retCls = getReturnClass(fld);
					if ((fld = getField(rName, retCls)) != null) {
						rValue = new FieldRef(fld);
					} else {
						throw new SemanticException(UNDEFINED_PROPERTY, child);
					}
					lValue = newPrimaryOperation(lValue, Operation.Code.GET, rValue);
					last = rValue;
					break;
				case ExprParserTreeConstants.JJTARRAYACCESS:
				case ExprParserTreeConstants.JJTINVOCATION:
					throw new SemanticException(UNDER_CONSTRUCTION, child);
				default:
					throw new InternalError();
				}
			} else if (last instanceof EnumRef) {
				switch (child.id) {
				case ExprParserTreeConstants.JJTPROPERTY:
					String rName = ((UnresolvedProperty)rValue).getName();
					EnumDto enm = ((EnumRef) last).getDto();
					EnumValueDto ev = getEnumValue(rName, enm);
					if (ev == null) {
						throw new SemanticException(UNDEFINED_PROPERTY, child);
					}
					rValue = new EnumValueRef(ev);
					lValue = newPrimaryOperation(lValue, Operation.Code.GET, rValue);
					last = rValue;
					break;
				case ExprParserTreeConstants.JJTARRAYACCESS:
				case ExprParserTreeConstants.JJTINVOCATION:
					throw new SemanticException(UNDER_CONSTRUCTION, child);
				default:
					throw new InternalError();
				}
			} else {
				throw new SemanticException(TYPE_UNMATCH, child);
			}
		}
		return lValue;
	}

	private Operation newPrimaryOperation(Expression lValue, Operation.Code code, Expression rValue) {
		Operation result;
		result = new Operation(code);
		result.addOperand(lValue);
		result.addOperand(rValue);
		result.setType(rValue.getType());
		return result;
	}

	@Override
	public Expression visit(ASTParenExpression node, Expression unused) throws ParseException {
		ExprNode child = node.getChild(0);
		Expression childExpr = child.jjtAccept(this, unused);
		Operation op = new Operation(Operation.Code.PAREN);
		op.setType(childExpr.getType());
		op.addOperand(childExpr);
		return op;
	}

	@Override
	public Expression visit(ASTIfExpression node, Expression unused) throws ParseException {
		throw new UnsupportedOperationException(UNDER_CONSTRUCTION);
	}

	@Override
	public Expression visit(ASTBlockExpression node, Expression unused) throws ParseException {
		throw new UnsupportedOperationException(UNDER_CONSTRUCTION);
	}

	@Override
	public Expression visit(ASTProperty node, Expression unused) throws ParseException {
		return new UnresolvedProperty(node.firstToken.image);
	}

	@Override
	public Expression visit(ASTInvocation node, Expression unused) throws ParseException {
		throw new UnsupportedOperationException(UNDER_CONSTRUCTION);
	}

	@Override
	public Expression visit(ASTArrayAccess node, Expression unused) throws ParseException {
		throw new UnsupportedOperationException(UNDER_CONSTRUCTION);
	}

	@Override
	public Expression visit(ASTIntLiteral node, Expression unused) throws ParseException {
		String image = node.firstToken.image;
		if (Strings.endsWith(image, "l", "L")) {
			image = image.substring(0, image.length() - 1);
			try {
				long value = Long.decode(image);
				int radix = getRadix(image);
				return new LongLiteral(value, radix);
			} catch (NumberFormatException err) {
				throw new SemanticException(err, node);
			}
		} else if (Strings.endsWith(image, "n", "N")) {
			image = image.substring(0, image.length() - 1);
			try {
				BigDecimal value = new BigDecimal(image);
				return new NumericLiteral(value);
			} catch (NumberFormatException err) {
				throw new SemanticException(err, node);
			}
		} else {
			try {
				long value = Integer.decode(image);
				int radix = getRadix(image);
				return new IntLiteral((int)value, radix);
			} catch (NumberFormatException err) {
				throw new SemanticException(err, node);
			}
		}
	}

	@Override
	public Expression visit(ASTRealLiteral node, Expression unused) throws ParseException {
		String image = node.firstToken.image;
		if (Strings.endsWith(image, "f", "F")) {
			image = image.substring(0, image.length() - 1);
			try {
				float value = Float.parseFloat(image);
				return new FloatLiteral(value);
			} catch (NumberFormatException err) {
				throw new SemanticException(err, node);
			}
		} else if (Strings.endsWith(image, "d", "D")) {
			image = image.substring(0, image.length() - 1);
			try {
				double value = Double.parseDouble(image);
				return new DoubleLiteral(value);
			} catch (NumberFormatException err) {
				throw new SemanticException(err, node);
			}
		} else if (Strings.endsWith(image, "n", "N")) {
			image = image.substring(0, image.length() - 1);
			try {
				BigDecimal value = new BigDecimal(image);
				return new NumericLiteral(value);
			} catch (NumberFormatException err) {
				throw new SemanticException(err, node);
			}
		} else {
			try {
				double value = Double.parseDouble(image);
				return new DoubleLiteral(value);
			} catch (NumberFormatException err) {
				throw new SemanticException(err, node);
			}
		}
	}

	@Override
	public Expression visit(ASTStringLiteral node, Expression unused) throws ParseException {
		String image = node.firstToken.image;
		if (node.firstToken.kind == ExprParserConstants.DOUBLE_STRING_LITERAL) {
			String text = image.substring(1, image.length() - 1);
			List<String> list = splitMustache(text);
			if (list.size() == 1) {
				return new StringLiteral(text);
			}
			Operation op = new Operation(Operation.Code.CONCAT);
			op.setType(DataType.STRING);
			for (int i = 0; i < list.size(); i++) {
				String str = list.get(i);
				if (str.length() > 0) {
					if (i % 2 == 0) {
						op.addOperand(new StringLiteral(str));
					} else {
						ExprNode subNode = parser.parse(str);
						Expression subExpr = subNode.jjtAccept(this, unused);
						op.addOperand(subExpr);
					}
				}
			}
			return op;
		}
		else if (Strings.endsWith(image, "t", "T")) {
			image = image.substring(0, image.length() - 1);
			String text = image.substring(1, image.length() - 1);
			Date date = parseDate(text);
			return new DateLiteral(date);
		} else {
			String text = image.substring(1, image.length() - 1);
			return new StringLiteral(text);
		}
	}

	@Override
	public Expression visit(ASTBooleanLiteral node, Expression unused) throws ParseException {
		int kind = node.firstToken.kind;
		return kind == ExprParserConstants.TRUE ? BooleanLiteral.TRUE : BooleanLiteral.FALSE;
	}

	@Override
	public Expression visit(ASTNullLiteral node, Expression unused) throws ParseException {
		return NullLiteral.INSTANCE;
	}

	private int getRadix(String str) {
		if (Strings.startsWith(str, "0x", "0X")) {
			return 16;
		} else if (Strings.startsWith(str, "0")) {
			return 8;
		}
		return 10;
	}

	private List<String> splitMustache(String str) {
		int top = 0;
		int beg = str.indexOf("{{", top);
		int end = str.indexOf("}}", top);
		if (!(top <= beg && beg + 2 <= end)) {
			return Collections.singletonList(str);
		}
		List<String> list = new ArrayList<>();
		while (top <= beg && beg + 2 <= end) {
			list.add(str.substring(top, beg));
			list.add(str.substring(beg + 2, end));
			top = end + 2;
			beg = str.indexOf("{{", top);
			end = str.indexOf("}}", top);
		}
		list.add(str.substring(top, str.length()));
		return list;
	}

	private Date parseDate(String dateText) throws NumberFormatException {
		//TODO 文字列から日付・時刻にする
		return new Date();
	}

}
