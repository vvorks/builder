package com.github.vvorks.builder.server.grammar;

public class SemanticException extends ParseException {

	private static String getLocation(ExprNode node) {
		Token t = node.firstToken;
		return t.beginLine + ":" + t.beginColumn;
	}

	public SemanticException(String message, ExprNode node) {
		super(message + " at " + getLocation(node));
	}

	public SemanticException(Throwable err, ExprNode node) {
		super(err.getClass().getSimpleName()  + " at " + getLocation(node));
	}

}
