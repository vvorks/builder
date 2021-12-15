CREATE TABLE IF NOT EXISTS T_PROJECT (
	F_PROJECT_ID INTEGER NOT NULL,
	F_PROJECT_NAME TEXT NOT NULL,
	F_GRADLE_NAME TEXT NOT NULL,
	F_TITLE TEXT NOT NULL,
	F_DESCRIPTION TEXT ,
	F_NOTE TEXT ,
	F_COPYRIGHTS TEXT ,
	F__LAST_UPDATED_AT INTEGER NOT NULL,
	PRIMARY KEY (
		F_PROJECT_ID
	)
);
CREATE TABLE IF NOT EXISTS T_CLASS (
	F_CLASS_ID INTEGER NOT NULL,
	F_OWNER_PROJECT_ID INTEGER NOT NULL,
	F_CLASS_NAME TEXT NOT NULL,
	F_ORDER_EXPR TEXT NOT NULL,
	F_TITLE_EXPR TEXT NOT NULL,
	F_TITLE TEXT NOT NULL,
	F_DESCRIPTION TEXT ,
	F_NOTE TEXT ,
	F__LAST_UPDATED_AT INTEGER NOT NULL,
	PRIMARY KEY (
		F_CLASS_ID
	)
	FOREIGN KEY (
		F_OWNER_PROJECT_ID
	) REFERENCES T_PROJECT (
		F_PROJECT_ID
	)
);
CREATE TABLE IF NOT EXISTS T_FIELD (
	F_FIELD_ID INTEGER NOT NULL,
	F_OWNER_CLASS_ID INTEGER NOT NULL,
	F_FIELD_NAME TEXT NOT NULL,
	F_TYPE INTEGER NOT NULL,
	F_WIDTH INTEGER NOT NULL,
	F_SCALE INTEGER NOT NULL,
	F_CREF_CLASS_ID INTEGER NOT NULL,
	F_EREF_ENUM_ID INTEGER NOT NULL,
	F_FREF_FIELD_ID INTEGER NOT NULL,
	F_PK INTEGER NOT NULL,
	F_NULLABLE INTEGER NOT NULL,
	F_NEEDS_SUM INTEGER NOT NULL,
	F_NEEDS_AVG INTEGER NOT NULL,
	F_NEEDS_MAX INTEGER NOT NULL,
	F_NEEDS_MIN INTEGER NOT NULL,
	F_TITLE TEXT NOT NULL,
	F_DESCRIPTION TEXT ,
	F_NOTE TEXT ,
	F__LAST_UPDATED_AT INTEGER NOT NULL,
	PRIMARY KEY (
		F_FIELD_ID
	)
	FOREIGN KEY (
		F_OWNER_CLASS_ID
	) REFERENCES T_CLASS (
		F_CLASS_ID
	)
	FOREIGN KEY (
		F_CREF_CLASS_ID
	) REFERENCES T_CLASS (
		F_CLASS_ID
	)
	FOREIGN KEY (
		F_EREF_ENUM_ID
	) REFERENCES T_ENUM (
		F_ENUM_ID
	)
	FOREIGN KEY (
		F_FREF_FIELD_ID
	) REFERENCES T_FIELD (
		F_FIELD_ID
	)
);
CREATE TABLE IF NOT EXISTS T_QUERY (
	F_QUERY_ID INTEGER NOT NULL,
	F_OWNER_CLASS_ID INTEGER NOT NULL,
	F_QUERY_NAME TEXT NOT NULL,
	F_FILTER TEXT NOT NULL,
	F_ORDER TEXT NOT NULL,
	F_TITLE TEXT NOT NULL,
	F_DESCRIPTION TEXT ,
	F_NOTE TEXT ,
	F__LAST_UPDATED_AT INTEGER NOT NULL,
	PRIMARY KEY (
		F_QUERY_ID
	)
	FOREIGN KEY (
		F_OWNER_CLASS_ID
	) REFERENCES T_CLASS (
		F_CLASS_ID
	)
);
CREATE TABLE IF NOT EXISTS T_ENUM (
	F_ENUM_ID INTEGER NOT NULL,
	F_OWNER_PROJECT_ID INTEGER NOT NULL,
	F_ENUM_NAME TEXT NOT NULL,
	F_ENCODE_STRING INTEGER NOT NULL,
	F_TITLE TEXT NOT NULL,
	F_DESCRIPTION TEXT ,
	F_NOTE TEXT ,
	F__LAST_UPDATED_AT INTEGER NOT NULL,
	PRIMARY KEY (
		F_ENUM_ID
	)
	FOREIGN KEY (
		F_OWNER_PROJECT_ID
	) REFERENCES T_PROJECT (
		F_PROJECT_ID
	)
);
CREATE TABLE IF NOT EXISTS T_ENUM_VALUE (
	F_OWNER_ENUM_ID INTEGER NOT NULL,
	F_VALUE_ID TEXT NOT NULL,
	F_CODE INTEGER NOT NULL,
	F_TITLE TEXT NOT NULL,
	F_DESCRIPTION TEXT ,
	F_NOTE TEXT ,
	F__LAST_UPDATED_AT INTEGER NOT NULL,
	PRIMARY KEY (
		F_OWNER_ENUM_ID,
		F_VALUE_ID
	)
	FOREIGN KEY (
		F_OWNER_ENUM_ID
	) REFERENCES T_ENUM (
		F_ENUM_ID
	)
);
