package com.github.vvorks.builder.server.expression;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.github.vvorks.builder.server.domain.DataType;

public class Operation extends Expression {

	public static enum Code {
		OR,
		AND,
		XOR,
		EQ, NE,
		LIKE, MATCH,
		LT, GT, LE, GE,
		ADD, SUB, CONCAT, COMMA,
		MUL, DIV, MOD,
		PLUS, MINUS,
		NOT,
		GET, CONST, CALL,
		PAREN, BLOCK, IF;
	}

	private final Code code;

	private DataType type;

	private int width;

	private int scale;

	private int joinNo;

	private final List<Expression> operands;

	public Operation(Code code) {
		this.code = code;
		this.operands = new ArrayList<>();
	}

	@Override
	public DataType getType() {
		return type;
	}

	public void setType(DataType type) {
		this.type = type;
	}

	public void addOperand(Expression operand) {
		this.operands.add(operand);
	}

	public void addOperandWithType(Expression operand) {
		this.operands.add(operand);
		this.type = operand.getType();
		this.width = operand.getWidth();
		this.scale = operand.getScale();
	}

	@Override
	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	@Override
	public int getScale() {
		return scale;
	}

	public void setScale(int scale) {
		this.scale = scale;
	}

	public Code getCode() {
		return code;
	}

	public int getJoinNo() {
		return joinNo;
	}

	public void setJoinNo(int joinNo) {
		this.joinNo = joinNo;
	}

	@Override
	public Iterable<? extends Expression> getChildren() {
		return getOperands();
	}


	public List<Expression> getOperands() {
		return Collections.unmodifiableList(operands);
	}

	public Expression getLastOperand() {
		return operands.get(operands.size() - 1);
	}

	@Override
	public <A,R> R accept(Visitor<A,R> visitor, A option) {
		return visitor.visit(this, option);
	}

}
