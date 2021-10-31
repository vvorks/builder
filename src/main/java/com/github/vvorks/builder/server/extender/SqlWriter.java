package com.github.vvorks.builder.server.extender;

import com.github.vvorks.builder.server.expression.Expression;

public abstract class SqlWriter implements Expression.Visitor<Object, String> {

	public static final String TABLE_PREFIX = "T_";

	public static final String TABLE_ALIAS_PREFIX = "T";

	public static final String COLUMN_PREFIX = "F_";

	protected SqlWriter() {
	}

}
