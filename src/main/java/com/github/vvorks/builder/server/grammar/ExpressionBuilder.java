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

import com.github.vvorks.builder.common.lang.Strings;
import com.github.vvorks.builder.common.logging.Logger;
import com.github.vvorks.builder.server.domain.ClassContent;
import com.github.vvorks.builder.server.domain.DataType;
import com.github.vvorks.builder.server.domain.EnumContent;
import com.github.vvorks.builder.server.domain.EnumValueContent;
import com.github.vvorks.builder.server.domain.FieldContent;
import com.github.vvorks.builder.server.domain.ProjectContent;
import com.github.vvorks.builder.server.expression.Argument;
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

	public static final Logger LOGGER = Logger.createLogger(ExpressionBuilder.class);

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

	private ProjectContent project;

	private ClassContent context;

	public Expression build(ExprNode main, ProjectContent project, ClassContent context) throws ParseException {
		this.project = project;
		this.context = context;
		return main.jjtAccept(this, null);
	}

	private FieldContent getField(String name, ClassContent cls) {
		List<FieldContent> fields = classMapper.listFieldsContentIfNameIs(cls, name, 0, 0);
		return (fields.size() == 1) ? fields.get(0) : null;
	}

	private ClassContent getReturnClass(FieldContent fld) {
		return fieldMapper.getCref(fld);
	}

	private EnumContent getEnum(String name, ProjectContent prj) {
		List<EnumContent> enums = projectMapper.listEnumsContentIfNameIs(prj, name, 0, 0);
		return (enums.size() == 1) ? enums.get(0) : null;
	}

	private EnumValueContent getEnumValue(String rName, EnumContent enm) {
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
		ExprNode lNode = node.getChild(i++);
		ExprNode opNode = node.getChild(i++);
		ExprNode rNode = node.getChild(i++);
		Expression lValue = lNode.jjtAccept(this, unused);
		Expression rValue = rNode.jjtAccept(this, unused);
		Operation op1 = newBinaryOperation(lValue, opNode, rValue);
		lValue = op1;
		while (i < n) {
			opNode = node.getChild(i++);
			rNode = node.getChild(i++);
			rValue = rNode.jjtAccept(this, unused);
			Operation op2 = newBinaryOperation(lValue, opNode, rValue);
			if (op1.getCode() == op2.getCode()) {
				op1.addOperand(rValue);
				op1.setType(op2.getType());
				op1.setWidth(op2.getWidth());
				op1.setScale(op2.getScale());
			} else {
				op1 = op2;
				lValue = op1;
			}
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
		Operation result;
		switch (operator) {
		case ADD:
			if (isEitherString(lValue, rValue)) {
				adjustArgumentType(lValue, rValue, DataType.STRING, 0, 0);
				result = newStringOperation(lValue, Operation.Code.CONCAT, rValue);
				break;
			}
			//fallthrough
		case SUB:
		case MUL:
		case DIV:
		case MOD:
			adjustArgumentType(lValue, rValue, DataType.INTEGER, 32, 0);
			if (!isBothNumber(lValue, rValue)) {
				throw new SemanticException(TYPE_UNMATCH, node);
			}
			result = newArithmeticOperation(lValue, operator, rValue);
			break;
		case GE: case GT:
		case LE: case LT:
			adjustArgumentType(lValue, rValue, DataType.INTEGER, 32, 0);
			if (!(isBothNumber(lValue, rValue) || isBothString(lValue, rValue) || isBothDate(lValue, rValue))) {
				throw new SemanticException(TYPE_UNMATCH, node);
			}
			result = newBooleanOperation(lValue, operator, rValue);
			break;
		case EQ: case NE:
			adjustArgumentType(lValue, rValue, DataType.INTEGER, 32, 0);
			if (!(isBothNumber(lValue, rValue) || isSameType(lValue, rValue) || isEnumComparation(lValue, rValue))) {
				throw new SemanticException(TYPE_UNMATCH, node);
			}
			result = newBooleanOperation(lValue, operator, rValue);
			break;
		case LIKE:
		case MATCH:
			adjustArgumentType(lValue, rValue, DataType.STRING, 0, 0);
			if (!isBothString(lValue, rValue)) {
				throw new SemanticException(TYPE_UNMATCH, node);
			}
			result = newBooleanOperation(lValue, operator, rValue);
			break;
		case AND:
		case OR:
		case XOR:
			adjustArgumentType(lValue, rValue, DataType.BOOLEAN, 0, 0);
			if (!isBothBoolean(lValue, rValue)) {
				throw new SemanticException(TYPE_UNMATCH, node);
			}
			result = newBooleanOperation(lValue, operator, rValue);
			break;
		default:
			throw new InternalError();
		}
		return result;
	}

	private void adjustArgumentType(Expression lValue, Expression rValue, DataType hint, int width, int scale) {
		if (lValue instanceof Argument && lValue.getType() == null) {
			if (rValue instanceof Argument && rValue.getType() == null) {
				//hintを使ってrValueを補正
				Argument rArg = (Argument) rValue;
				rArg.setType(hint);
				rArg.setWidth(width);
				rArg.setScale(scale);
			}
			//rValueからlValueへの型情報コピー
			Argument lArg = (Argument) lValue;
			lArg.setType(rValue.getType());
			lArg.setWidth(rValue.getWidth());
			lArg.setScale(rValue.getScale());
		} else if (rValue instanceof Argument && rValue.getType() == null) {
			//lValueからrValueへの型情報コピー
			Argument rArg = (Argument) rValue;
			rArg.setType(lValue.getType());
			rArg.setWidth(lValue.getWidth());
			rArg.setScale(lValue.getScale());
			if (rArg.getType() == DataType.ENUM) {
				Operation op = (Operation) lValue;
				FieldRef ref = (FieldRef) op.getLastOperand();
				FieldContent fld = ref.getContent();
				rArg.setErefId(fld.getErefEnumId());
			}
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

	private boolean isEnumComparation(Expression lValue, Expression rValue) {
		return	(lValue.getType() == DataType.ENUM && rValue.getType() == DataType.ENUM_VALUE) ||
				(rValue.getType() == DataType.ENUM && lValue.getType() == DataType.ENUM_VALUE)  ;
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
		BINARY_OP_MAP.put(ExprParserConstants.AND, Operation.Code.AND);
		BINARY_OP_MAP.put(ExprParserConstants.SC_AND, Operation.Code.AND);
		BINARY_OP_MAP.put(ExprParserConstants.XOR, Operation.Code.XOR);
		BINARY_OP_MAP.put(ExprParserConstants.BIT_XOR, Operation.Code.XOR);
		BINARY_OP_MAP.put(ExprParserConstants.EQ, Operation.Code.EQ);
		BINARY_OP_MAP.put(ExprParserConstants.NE, Operation.Code.NE);
		BINARY_OP_MAP.put(ExprParserConstants.LIKE, Operation.Code.LIKE);
		BINARY_OP_MAP.put(ExprParserConstants.MATCH, Operation.Code.MATCH);
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
		return BINARY_OP_MAP.get(t.kind);
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
		UNARY_OP_MAP.put(ExprParserConstants.TILDE, Operation.Code.NOT);
	}

	@Override
	public Expression visit(ASTPrimaryExpression node, Expression unused) throws ParseException {
		ExprNode child = node.getChild(0);
		Expression result;
		switch (child.id) {
		case ExprParserTreeConstants.JJTPROPERTY:
			result = visitProperty(node, unused);
			break;
		case ExprParserTreeConstants.JJTARGUMENT:
			result = visitArgument(node, unused);
			break;
		case ExprParserTreeConstants.JJTINVOCATION:
			throw new SemanticException(UNDER_CONSTRUCTION, child);
		default:
			result = child.jjtAccept(this, unused);
			break;
		}
		return result;
	}

	private Expression visitProperty(ASTPrimaryExpression node, Expression unused) throws ParseException {
		ExprNode child = node.getChild(0);
		Expression lValue = child.jjtAccept(this, unused);
		String name = ((UnresolvedProperty)lValue).getName();
		FieldContent fld;
		EnumContent enm;
		if ((fld = getField(name, context)) != null) {
			lValue = makeGetOperation(node, fld, unused);
		} else if ((enm = getEnum(name, project)) != null) {
			lValue = makeConstOperation(node, enm, unused);
		} else {
			throw new SemanticException(UNDEFINED_PROPERTY, child);
		}
		return lValue;
	}

	private Expression makeGetOperation(ASTPrimaryExpression node, FieldContent fld, Expression unused) throws ParseException {
		Operation op = new Operation(Operation.Code.GET);
		op.addOperandWithType(new FieldRef(fld));
		Expression lValue = op;
		int n = node.size();
		int i = 1;
		while (i < n) {
			ExprNode child = node.getChild(i++);
			Expression rValue = child.jjtAccept(this, unused);
			Expression last = op.getLastOperand();
			if (!(last instanceof FieldRef)) {
				throw new SemanticException(TYPE_UNMATCH, child);
			}
			if (child.id != ExprParserTreeConstants.JJTPROPERTY) {
				throw new SemanticException(TYPE_UNMATCH, child);
			}
			String rName = ((UnresolvedProperty)rValue).getName();
			fld = ((FieldRef) last).getContent();
			if (fld.getType() != DataType.REF) {
				throw new SemanticException(TYPE_UNMATCH, child);
			}
			ClassContent retCls = getReturnClass(fld);
			if ((fld = getField(rName, retCls)) != null) {
				rValue = new FieldRef(fld);
			} else {
				throw new SemanticException(UNDEFINED_PROPERTY, child);
			}
			if (op.getCode() == Operation.Code.GET) {
				op.addOperandWithType(rValue);
			} else {
				lValue = op = newPrimaryOperation(lValue, Operation.Code.GET, rValue);
			}
		}
		return lValue;
	}

	private Expression makeConstOperation(ASTPrimaryExpression node, EnumContent enm, Expression unused) throws ParseException {
		Operation op = new Operation(Operation.Code.CONST);
		op.addOperandWithType(new EnumRef(enm));
		Expression lValue = op;
		int n = node.size();
		int i = 1;
		while (i < n) {
			ExprNode child = node.getChild(i++);
			Expression rValue = child.jjtAccept(this, unused);
			Expression last = op.getLastOperand();
			if (!(last instanceof EnumRef)) {
				throw new SemanticException(TYPE_UNMATCH, child);
			}
			if (child.id != ExprParserTreeConstants.JJTPROPERTY) {
				throw new SemanticException(TYPE_UNMATCH, child);
			}
			String rName = ((UnresolvedProperty)rValue).getName();
			enm = ((EnumRef) last).getContent();
			EnumValueContent ev;
			if ((ev = getEnumValue(rName, enm)) != null) {
				rValue = new EnumValueRef(ev);
			} else {
				throw new SemanticException(UNDEFINED_PROPERTY, child);
			}
			if (op.getCode() == Operation.Code.CONST) {
				op.addOperandWithType(rValue);
			} else {
				lValue = op = newPrimaryOperation(lValue, Operation.Code.CONST, rValue);
			}
		}
		return lValue;
	}

	private Expression visitArgument(ASTPrimaryExpression node, Expression unused) throws ParseException {
		ExprNode child = node.getChild(0);
		Expression lValue = child.jjtAccept(this, unused);
		String name = ((UnresolvedProperty)lValue).getName();
		return new Argument(name);
	}

	private Operation newPrimaryOperation(Expression lValue, Operation.Code code, Expression rValue) {
		Operation result;
		result = new Operation(code);
		result.addOperand(lValue);
		result.addOperandWithType(rValue);
		return result;
	}

	@Override
	public Expression visit(ASTParenExpression node, Expression unused) throws ParseException {
		ExprNode child = node.getChild(0);
		Expression childExpr = child.jjtAccept(this, unused);
		Operation op = new Operation(Operation.Code.PAREN);
		op.addOperandWithType(childExpr);
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
		return new UnresolvedProperty(node.firstToken.image, false);
	}

	@Override
	public Expression visit(ASTArgument node, Expression unused) throws ParseException {
		return new UnresolvedProperty(node.firstToken.next.image, true);
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
