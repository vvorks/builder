package com.github.vvorks.builder.server.grammar;

public class ExprToken extends Token {

	/*package*/ int realKind = ExprParserConstants.GT;

	public ExprToken(int kind, String image) {
		this.kind = kind;
		this.image = image;
	}

}
