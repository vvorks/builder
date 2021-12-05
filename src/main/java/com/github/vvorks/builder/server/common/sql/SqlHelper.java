package com.github.vvorks.builder.server.common.sql;

import java.util.Arrays;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;

import com.github.vvorks.builder.common.lang.Factory;
import com.github.vvorks.builder.common.logging.Logger;

public abstract class SqlHelper {

	public static final Logger LOGGER = Logger.createLogger(SqlHelper.class);

	/**
	 * SQL予約語
	 *
	 * @see http://itdoc.hitachi.co.jp/manuals/3020/3020635743/W3570261.HTM
	 */
	private static final String[] KEYWORD_ARRAY = {
		//A
		"ABS",			"ABSOLUTE",		"ACCESS",		"ACTION",
		"ADD",			"ADMIN",		"AFTER",		"AGGREGATE",
		"ALIAS",		"ALL",			"ALLOCATE",		"ALTER",
		"AMOUNT",		"AND",			"ANDNOT",		"ANSI",
		"ANY",			"ARE",			"ARRAY",		"AS",
		"ASC",			"ASSERTION",	"ASSIGN",		"ASYNC",
		"AT",			"AUTHORIZATION","AUTO",			"AVG",
		//B
		"BASE",			"BEFORE",		"BEGIN",		"BETWEEN",
		"BINARY",		"BIT",			"BIT_AND_TEST",	"BIT_LENGTH",
		"BLOB",			"BOOLEAN",		"BOTH",			"BREADTH",
		"BTREE",		"BUFFER",		"BY",			"BYTE",
		//C
		"CALL",			"CASCADE",		"CASCADED",		"CASE",
		"CAST",			"CATALOG",		"CHANGE",		"CHAR",
		"CHARACTER",	"CHAR_LENGTH",	"CHARACTER_LENGTH",
		"CHECK",		"CLASS",		"CLOB",			"CLOSE",
		"CLUSTER",		"COALESCE",		"COLLATE",		"COLLATION",
		"COLUMN",		"COLUMNS",		"COMMENT",		"COMMIT",
		"COMPLETION",	"COMPRESSED",	"CONDITION",	"CONFIGURATION",
		"CONNECT",		"CONNECTION",	"CONST",		"CONSTRAINT",
		"CONSTRAINTS",	"CONSTRUCTOR",	"CONTIGUOUS",	"CONTINUE",
		"CONVERT",		"CORR",			"CORRESPONDING",
		"COUNT",		"COUNT_FLOAT",	"COVAR_POP",	"COVAR_SAMP",
		"CREATE",		"CROSS",		"CUBE",			"CUME_DIST",
		"CURAID",		"CURRENT",		"CURRENT_DATE",	"CURRENT_DEFAULT_TRANSFORM_GROUP",
		"CURRENT_PATH",	"CURRENT_ROLL",	"CURRENT_TIME",	"CURRENT_TIMESTAMP",
		"CURRENT_TRANSFORM_GROUP_FOR_TYPE",				"CURRENT_USER",
		"CURSOR",		"CYCLE",
		//D
		"DATA",			"DATABASE",		"DATE",			"DAY",
		"DAYS",			"DBA",			"DEALLOCATE",	"DEC",
		"DECIMAL",		"DECLARE",		"DEFAULT",		"DEFER",
		"DEFERRABLE",	"DEFERRED",		"DELETE",		"DEMOTING",
		"DENSE_RANK",	"DEPTH",		"DEREF",		"DESC",
		"DESCRIBE",		"DESCRIPTION",	"DESCRIPTOR",	"DESTROY",
		"DESTRUCTOR",	"DETERMINISTIC",				"DEVICE",
		"DIAGNOSTICS",	"DICTIONARY",	"DIGITS",		"DIRECT",
		"DISCONNECT",	"DISPLAY",		"DISTINCT",		"DO",
		"DOMAIN",		"DOUBLE",		"DOUBLE_PRECISION",
		"DROP",			"DYNAMIC",
		//E
		"EACH",			"EDIT",			"ELSE",			"ELSEIF",
		"ENCRYPT",		"END",			"END-EXEC",		"EQUALS",
		"ESCAPE",		"ESTIMATED",	"EVERY",		"EXCEPT",
		"EXCEPTION",	"EXCLUSIVE",	"EXEC",			"EXECUTE",
		"EXISTS",		"EXIT",			"EXTERN",		"EXTERNAL",
		"EXTRACT",
		//F
		"FALSE",		"FETCH",		"FILE",			"FILTER",
		"FIRST",		"FIX",			"FIXED",		"FLAT",
		"FLOAT",		"FOR",			"FORCE",		"FOREIGN",
		"FOUND",		"FREE",			"FROM",			"FULL",
		"FUNCTION",
		//G
		"GENERAL",		"GET",			"GET_JAVA_STORED_ROUTINE_SOURCE",
		"GLOBAL",		"GO",			"GOTO",			"GRANT",
		"GROUP",		"GROUPING",
		//H
		"HANDLER",		"HASH",			"HAVING",		"HELP",
		"HEX",			"HOST",			"HOUR",			"HOURS",
		"HUGE",
		//I
		"IDENTIFIED",	"IDENTITY",		"IF",			"IGNORE",
		"IMMEDIATE",	"IN",			"INDEX",		"INDICATOR",
		"INITIALIZE",	"INITIALLY",	"INNER",		"INOUT",
		"INPUT",		"INSENSITIVE",	"INSERT",		"INT",
		"INTEGER",		"INTERSECT",	"INTERVAL",		"INTO",
		"IS",			"ISOLATION",	"IS_USER_CONTAINED_IN_HDS_GROUP",
		"ITERATE",
		//J
		"JOIN",
		//K
		"KEY",
		//L
		"LABEL",		"LANGUAGE",		"LARGE",		"LAST",
		"LATERAL",		"LEADING",		"LEAVE",		"LEFT",
		"LENGTH",		"LESS",			"LEVEL",		"LIKE",
		"LIMIT",		"LINES",		"LINK",			"LIST",
		"LOCAL",		"LOCALTIME",	"LOCALTIMESTAMP",
		"LOCATOR",		"LOCK",			"LOCKS",		"LOGID",
		"LOGNAME",		"LONG",			"LOOP",			"LOWER",
		//M
		"MAP",			"MATCH",		"MAX",			"MAXUSAGES",
		"MCHAR",		"MICROSECOND",	"MICROSECONDS",	"MIN",
		"MINUTE",		"MINUTES",		"MOD",			"MODE",
		"MODIFIES",		"MODIFY",		"MODULE",		"MONTH",
		"MONTHS",		"MOVE",			"MVARCHAR",
		//N
		"NAMES",		"NATIONAL",		"NATURAL",		"NCHAR",
		"NCLOB",		"NESTING",		"NEW",			"NEXT",
		"NO",			"NONE",			"NONLOCAL",		"NOT",
		"NOWAIT",		"NULL",			"NULLABLE",		"NULLIF",
		"NUMERIC",		"NVARCHAR",
		//O
		"OBJECT",		"OCTET_LENGTH",	"OF",			"OFF",
		"OFFSET",		"OID",			"OLD",			"ON",
		"ONLY",			"OPEN",			"OPERATION",	"OPERATORS",
		"OPTION",		"OPTIMIZE",		"OR",			"ORDER",
		"ORDINALITY",	"OTHERS",		"OUT",			"OUTER",
		"OUTPUT",		"OVER",			"OVERFLOW",		"OVERLAPS",
		"OVERWRITE",	"OWN",			"OWNER",
		//P
		"PAD",			"PAGE",			"PARAMETER",	"PARAMETERS",
		"PARTIAL",		"PARTITION",	"PARTITIONED",	"PATH",
		"PCTFREE",		"PENDANT",		"PERCENT_RANK",	"PERCENTILE_CONT",
		"PERCENTILE_DISC",				"PIC",			"PICTURE",
		"POSITION",		"POSTFIX",		"PREALLOCATED",	"PRECISION",
		"PREFERRED",	"PREFIX",		"PREORDER",		"PREPARE",
		"PRESERVE",		"PRIMARY",		"PRIMLEGES",	"PRIOR",
		"PRIVATE",		"PRIVILEGES",	"PROCEDURE",	"PROGRAM",
		"PROTECTED",	"PUBLIC",		"PURGE",
		//Q
		//R
		"RANDOM",		"RANGE",		"RANK",			"RD",
		"RDAREA",		"RDNODE",		"READ",			"READS",
		"REAL",			"RECOMPILE",	"RECOVERABLE",	"RECOVERY",
		"RECURSIVE",	"REDO",			"REF",			"REFERENCES",
		"REFERENCING",	"REGLIKE",		"REGR_AVGX",	"REGR_AVGY",
		"REGR_COUNT",	"REGR_INTERCEPT",				"REGR_R2",
		"REGR_SLOPE",	"REGR_SXX",		"REGR_SXY",		"REGR_SYY",
		"RELATIVE",		"RELEASE",		"RELEASING",	"RENAME",
		"REPEAT",		"RESERVED",		"RESIGNAL",		"RESTART",
		"RESTRICT",		"RESULT",		"RETURN",		"RETURNS",
		"REVOKE",		"RIGHT",		"ROLE",			"ROLLBACK",
		"ROLLUP",		"ROOT",			"ROUTINE",		"ROW",
		"ROW_NUMBER",	"ROWID",		"ROWS",
		//S
		"SAVEPOINT",	"SCALE",		"SCAN",			"SCATTERED",
		"SCHEMA",		"SCHEMAS",		"SCOPE",		"SCROLL",
		"SD",			"SEARCH",		"SECOND",		"SECONDS",
		"SECTION",		"SEGMENT",		"SELECT",		"SENSITIVE",
		"SEPARATE",		"SEPARATOR",	"SEQUENCE",		"SESSION",
		"SESSION_USER",	"SET",			"SETS",			"SFLIKE",
		"SHARE",		"SHLIKE",		"SHORT",		"SIGN",
		"SIGNAL",		"SIMILAR",		"SIZE",			"SLOCK",
		"SMALLFLT",		"SMALLINT",		"SOME",			"SPACE",
		"SPECIFIC",		"SPECIFICTYPE",	"SPLIT",		"SQL",
		"SQL_STANDARD",	"SQLCODE",		"SQLCODE_OF_LAST_CONDITION",
		"SQLCODE_TYPE",	"SQLCOUNT",		"SQLDA",		"SQLERRM",
		"SQLERRM_OF_LAST_CONDITION",	"SQLERRMC",		"SQLERRML",
		"SQLERROR",		"SQLEXCEPTION",	"SQLNAME",		"SQLSTATE",
		"SQLWARN",		"SQLWARNING",	"START",		"STATE",
		"STATEMENT",	"STATIC",		"STDDEV_POP",	"STOP",
		"STOPPING",		"STRUCTURE",	"SUBSTR",		"SUBSTRING",
		"SUM",			"SUPPRESS",		"SYNONYM",		"SYSTEM_USER",
		//T
		"TABLE",		"TABLES",		"TEMPORARY",	"TERMINATE",
		"TEST",			"TEXT",			"THAN",			"THEN",
		"THERE",		"TIME",			"TIMESTAMP",	"TIMESTAMP_FORMAT",
		"TIMEZONE_HOUR",				"TIMEZONE_MINUTE",
		"TO",			"TRAILING",		"TRANSACTION",	"TRANSLATE",
		"TRANSLATION",	"TREAT",		"TRIGGER",		"TRIM",
		"TRUE",			"TYPE",
		//U
		"UAMT",			"UBINBUF",		"UCHAR",		"UDATE",
		"UHAMT",		"UHANT",		"UHDATE",		"UNBOUNDED",
		"UNDER",		"UNDO",			"UNIFY_2000",	"UNION",
		"UNIONALL",		"UNIQUE",		"UNKNOWN",		"UNLIMITED",
		"UNLOCK",		"UNTIL",		"UNNEST",		"UPDATE",
		"UPPER",		"USAGE",		"USE",			"USER",
		"USER_GROUP",	"USER_LEVEL",	"USING",		"UTIME",
		"UTXTBUF",
		//V
		"VALUE",		"VALUES",		"VAR_POP",		"VAR_SAMP",
		"VARCHAR",		"VARCHAR_FORMAT",				"VARIABLE",
		"VARYING",		"VIEW",			"VIRTUAL",		"VISIBLE",
		"VOLATILE",		"VOLUME",		"VOLUMES",
		//W
		"WAIT",			"WHEN",			"WHENEVER",		"WHERE",
		"WHILE",		"WINDOW",		"WITH",			"WITHIN",
		"WITHOUT",		"WORK",			"WRITE",
		//X
		"XLIKE",		"XLOCK",		"XML",			"XMLAGG",
		"XMLEXISTS",	"XMLPARSE",		"XMLQUERY",		"XMLSERIALIZE",
		//Y
		"YEAR",			"YEARS",
		//Z
		"ZONE",
	};

	private static final Set<String> KEYWORDS = new LinkedHashSet<>(Arrays.asList(KEYWORD_ARRAY));

	protected static final char SINGLE_QUOTE = '\'';

	public static SqlHelper getHelper() {
		return Factory.getInstance(SqlHelper.class);
	}

	public boolean isKeywords(String s) {
		return KEYWORDS.contains(s);
	}

	public String getNullValue() {
		return "NULL";
	}

	public abstract String getBooleanValue(boolean b);

	public abstract String quote(String str);

	public abstract String getDateValue(Date d);

	public abstract String getNow();

	public abstract String disableForeignKey();

	public abstract String enableForeignKey();

}
