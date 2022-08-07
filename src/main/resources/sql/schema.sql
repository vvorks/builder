CREATE TABLE IF NOT EXISTS T_PROJECT (
	F_PROJECT_ID INTEGER NOT NULL,
	F_PROJECT_NAME TEXT NOT NULL,
	F_GRADLE_NAME TEXT NOT NULL,
	F_TITLE TEXT NOT NULL,
	F_DESCRIPTION TEXT,
	F_NOTE TEXT,
	F_COPYRIGHTS TEXT,
	F__LAST_UPDATED_AT INTEGER NOT NULL,
	PRIMARY KEY (
		F_PROJECT_ID
	)
);
CREATE TABLE IF NOT EXISTS T_PROJECT_I18N (
	F_OWNER_PROJECT_ID INTEGER NOT NULL,
	F_TARGET_LOCALE_ID TEXT,
	F_TITLE TEXT,
	F_DESCRIPTION TEXT,
	F__LAST_UPDATED_AT INTEGER NOT NULL,
	PRIMARY KEY (
		F_OWNER_PROJECT_ID,
		F_TARGET_LOCALE_ID
	)
	FOREIGN KEY (
		F_OWNER_PROJECT_ID
	) REFERENCES T_PROJECT (
		F_PROJECT_ID
	)	ON UPDATE CASCADE
		ON DELETE CASCADE
	FOREIGN KEY (
		F_TARGET_LOCALE_ID
	) REFERENCES T_LOCALE (
		F_LOCALE_ID
	)	ON UPDATE CASCADE
		ON DELETE SET NULL
);
CREATE TABLE IF NOT EXISTS T_CLASS (
	F_CLASS_ID INTEGER NOT NULL,
	F_OWNER_PROJECT_ID INTEGER NOT NULL,
	F_CLASS_NAME TEXT NOT NULL,
	F_ORDER_EXPR TEXT NOT NULL,
	F_TITLE_EXPR TEXT NOT NULL,
	F_TITLE TEXT NOT NULL,
	F_DESCRIPTION TEXT,
	F_NOTE TEXT,
	F__LAST_UPDATED_AT INTEGER NOT NULL,
	PRIMARY KEY (
		F_CLASS_ID
	)
	FOREIGN KEY (
		F_OWNER_PROJECT_ID
	) REFERENCES T_PROJECT (
		F_PROJECT_ID
	)	ON UPDATE CASCADE
		ON DELETE CASCADE
);
CREATE TABLE IF NOT EXISTS T_CLASS_I18N (
	F_OWNER_CLASS_ID INTEGER NOT NULL,
	F_TARGET_LOCALE_ID TEXT,
	F_TITLE TEXT,
	F_DESCRIPTION TEXT,
	F__LAST_UPDATED_AT INTEGER NOT NULL,
	PRIMARY KEY (
		F_OWNER_CLASS_ID,
		F_TARGET_LOCALE_ID
	)
	FOREIGN KEY (
		F_OWNER_CLASS_ID
	) REFERENCES T_CLASS (
		F_CLASS_ID
	)	ON UPDATE CASCADE
		ON DELETE CASCADE
	FOREIGN KEY (
		F_TARGET_LOCALE_ID
	) REFERENCES T_LOCALE (
		F_LOCALE_ID
	)	ON UPDATE CASCADE
		ON DELETE SET NULL
);
CREATE TABLE IF NOT EXISTS T_FIELD (
	F_FIELD_ID INTEGER NOT NULL,
	F_OWNER_CLASS_ID INTEGER NOT NULL,
	F_FIELD_NAME TEXT NOT NULL,
	F_TYPE INTEGER NOT NULL,
	F_WIDTH INTEGER NOT NULL,
	F_SCALE INTEGER NOT NULL,
	F_CREF_CLASS_ID INTEGER,
	F_EREF_ENUM_ID INTEGER,
	F_FREF_FIELD_ID INTEGER,
	F_PK INTEGER NOT NULL,
	F_NULLABLE INTEGER NOT NULL,
	F_NEEDS_SUM INTEGER NOT NULL,
	F_NEEDS_AVG INTEGER NOT NULL,
	F_NEEDS_MAX INTEGER NOT NULL,
	F_NEEDS_MIN INTEGER NOT NULL,
	F_IS_CONTAINER INTEGER NOT NULL,
	F_FORMAT TEXT NOT NULL,
	F_TITLE TEXT NOT NULL,
	F_DESCRIPTION TEXT,
	F_NOTE TEXT,
	F__LAST_UPDATED_AT INTEGER NOT NULL,
	PRIMARY KEY (
		F_FIELD_ID
	)
	FOREIGN KEY (
		F_OWNER_CLASS_ID
	) REFERENCES T_CLASS (
		F_CLASS_ID
	)	ON UPDATE CASCADE
		ON DELETE CASCADE
	FOREIGN KEY (
		F_CREF_CLASS_ID
	) REFERENCES T_CLASS (
		F_CLASS_ID
	)	ON UPDATE CASCADE
		ON DELETE SET NULL
	FOREIGN KEY (
		F_EREF_ENUM_ID
	) REFERENCES T_ENUM (
		F_ENUM_ID
	)	ON UPDATE CASCADE
		ON DELETE SET NULL
	FOREIGN KEY (
		F_FREF_FIELD_ID
	) REFERENCES T_FIELD (
		F_FIELD_ID
	)	ON UPDATE CASCADE
		ON DELETE SET NULL
);
CREATE TABLE IF NOT EXISTS T_FIELD_I18N (
	F_OWNER_FIELD_ID INTEGER NOT NULL,
	F_TARGET_LOCALE_ID TEXT,
	F_FORMAT TEXT,
	F_TITLE TEXT,
	F_DESCRIPTION TEXT,
	F__LAST_UPDATED_AT INTEGER NOT NULL,
	PRIMARY KEY (
		F_OWNER_FIELD_ID,
		F_TARGET_LOCALE_ID
	)
	FOREIGN KEY (
		F_OWNER_FIELD_ID
	) REFERENCES T_FIELD (
		F_FIELD_ID
	)	ON UPDATE CASCADE
		ON DELETE CASCADE
	FOREIGN KEY (
		F_TARGET_LOCALE_ID
	) REFERENCES T_LOCALE (
		F_LOCALE_ID
	)	ON UPDATE CASCADE
		ON DELETE SET NULL
);
CREATE TABLE IF NOT EXISTS T_QUERY (
	F_QUERY_ID INTEGER NOT NULL,
	F_OWNER_CLASS_ID INTEGER NOT NULL,
	F_QUERY_NAME TEXT NOT NULL,
	F_FILTER TEXT NOT NULL,
	F_ORDER TEXT NOT NULL,
	F_TITLE TEXT NOT NULL,
	F_DESCRIPTION TEXT,
	F_NOTE TEXT,
	F__LAST_UPDATED_AT INTEGER NOT NULL,
	PRIMARY KEY (
		F_QUERY_ID
	)
	FOREIGN KEY (
		F_OWNER_CLASS_ID
	) REFERENCES T_CLASS (
		F_CLASS_ID
	)	ON UPDATE CASCADE
		ON DELETE CASCADE
);
CREATE TABLE IF NOT EXISTS T_ENUM (
	F_ENUM_ID INTEGER NOT NULL,
	F_OWNER_PROJECT_ID INTEGER NOT NULL,
	F_ENUM_NAME TEXT NOT NULL,
	F_ENCODE_STRING INTEGER NOT NULL,
	F_TITLE TEXT NOT NULL,
	F_DESCRIPTION TEXT,
	F_NOTE TEXT,
	F__LAST_UPDATED_AT INTEGER NOT NULL,
	PRIMARY KEY (
		F_ENUM_ID
	)
	FOREIGN KEY (
		F_OWNER_PROJECT_ID
	) REFERENCES T_PROJECT (
		F_PROJECT_ID
	)	ON UPDATE CASCADE
		ON DELETE CASCADE
);
CREATE TABLE IF NOT EXISTS T_ENUM_I18N (
	F_OWNER_ENUM_ID INTEGER NOT NULL,
	F_TARGET_LOCALE_ID TEXT,
	F_TITLE TEXT,
	F_DESCRIPTION TEXT,
	F__LAST_UPDATED_AT INTEGER NOT NULL,
	PRIMARY KEY (
		F_OWNER_ENUM_ID,
		F_TARGET_LOCALE_ID
	)
	FOREIGN KEY (
		F_OWNER_ENUM_ID
	) REFERENCES T_ENUM (
		F_ENUM_ID
	)	ON UPDATE CASCADE
		ON DELETE CASCADE
	FOREIGN KEY (
		F_TARGET_LOCALE_ID
	) REFERENCES T_LOCALE (
		F_LOCALE_ID
	)	ON UPDATE CASCADE
		ON DELETE SET NULL
);
CREATE TABLE IF NOT EXISTS T_ENUM_VALUE (
	F_OWNER_ENUM_ID INTEGER NOT NULL,
	F_VALUE_ID TEXT NOT NULL,
	F_CODE INTEGER NOT NULL,
	F_TITLE TEXT NOT NULL,
	F_DESCRIPTION TEXT,
	F_NOTE TEXT,
	F__LAST_UPDATED_AT INTEGER NOT NULL,
	PRIMARY KEY (
		F_OWNER_ENUM_ID,
		F_VALUE_ID
	)
	FOREIGN KEY (
		F_OWNER_ENUM_ID
	) REFERENCES T_ENUM (
		F_ENUM_ID
	)	ON UPDATE CASCADE
		ON DELETE CASCADE
);
CREATE TABLE IF NOT EXISTS T_ENUM_VALUE_I18N (
	F_OWNER_OWNER_ENUM_ID INTEGER NOT NULL,
	F_OWNER_VALUE_ID TEXT NOT NULL,
	F_TARGET_LOCALE_ID TEXT,
	F_TITLE TEXT,
	F_DESCRIPTION TEXT,
	F__LAST_UPDATED_AT INTEGER NOT NULL,
	PRIMARY KEY (
		F_OWNER_OWNER_ENUM_ID,
		F_OWNER_VALUE_ID,
		F_TARGET_LOCALE_ID
	)
	FOREIGN KEY (
		F_OWNER_OWNER_ENUM_ID,
		F_OWNER_VALUE_ID
	) REFERENCES T_ENUM_VALUE (
		F_OWNER_ENUM_ID,
		F_VALUE_ID
	)	ON UPDATE CASCADE
		ON DELETE CASCADE
	FOREIGN KEY (
		F_TARGET_LOCALE_ID
	) REFERENCES T_LOCALE (
		F_LOCALE_ID
	)	ON UPDATE CASCADE
		ON DELETE SET NULL
);
CREATE TABLE IF NOT EXISTS T_MESSAGE (
	F_MESSAGE_ID INTEGER NOT NULL,
	F_OWNER_PROJECT_ID INTEGER NOT NULL,
	F_MESSAGE_NAME TEXT NOT NULL,
	F_MESSAGE TEXT NOT NULL,
	F__LAST_UPDATED_AT INTEGER NOT NULL,
	PRIMARY KEY (
		F_MESSAGE_ID
	)
	FOREIGN KEY (
		F_OWNER_PROJECT_ID
	) REFERENCES T_PROJECT (
		F_PROJECT_ID
	)	ON UPDATE CASCADE
		ON DELETE CASCADE
);
CREATE TABLE IF NOT EXISTS T_MESSAGE_I18N (
	F_OWNER_MESSAGE_ID INTEGER NOT NULL,
	F_TARGET_LOCALE_ID TEXT,
	F_MESSAGE TEXT,
	F__LAST_UPDATED_AT INTEGER NOT NULL,
	PRIMARY KEY (
		F_OWNER_MESSAGE_ID,
		F_TARGET_LOCALE_ID
	)
	FOREIGN KEY (
		F_OWNER_MESSAGE_ID
	) REFERENCES T_MESSAGE (
		F_MESSAGE_ID
	)	ON UPDATE CASCADE
		ON DELETE CASCADE
	FOREIGN KEY (
		F_TARGET_LOCALE_ID
	) REFERENCES T_LOCALE (
		F_LOCALE_ID
	)	ON UPDATE CASCADE
		ON DELETE SET NULL
);
CREATE TABLE IF NOT EXISTS T_STYLE (
	F_STYLE_ID INTEGER NOT NULL,
	F_OWNER_PROJECT_ID INTEGER NOT NULL,
	F_STYLE_NAME TEXT NOT NULL,
	F_PARENT_STYLE_ID INTEGER,
	F_COND INTEGER NOT NULL,
	F_COND_PARAM TEXT,
	F_TEXT_COLOR TEXT,
	F_BACKGROUND_COLOR TEXT,
	F_BACKGROUND_IMAGE TEXT,
	F_BORDER_LEFT TEXT,
	F_BORDER_TOP TEXT,
	F_BORDER_RIGHT TEXT,
	F_BORDER_BOTTOM TEXT,
	F_BORDER_COLOR TEXT,
	F_BORDER_IMAGE TEXT,
	F_FONT_SIZE TEXT,
	F_FONT_FAMILY TEXT,
	F_LINE_HEIGHT TEXT,
	F_TEXT_ALIGN TEXT,
	F_VERTICAL_ALIGN TEXT,
	F__LAST_UPDATED_AT INTEGER NOT NULL,
	PRIMARY KEY (
		F_STYLE_ID
	)
	FOREIGN KEY (
		F_OWNER_PROJECT_ID
	) REFERENCES T_PROJECT (
		F_PROJECT_ID
	)	ON UPDATE CASCADE
		ON DELETE CASCADE
	FOREIGN KEY (
		F_PARENT_STYLE_ID
	) REFERENCES T_STYLE (
		F_STYLE_ID
	)	ON UPDATE CASCADE
		ON DELETE SET NULL
);
CREATE TABLE IF NOT EXISTS T_PAGE_SET (
	F_PAGE_SET_ID INTEGER NOT NULL,
	F_OWNER_PROJECT_ID INTEGER NOT NULL,
	F_PAGE_SET_NAME TEXT NOT NULL,
	F_TITLE TEXT NOT NULL,
	F_DESCRIPTION TEXT,
	F__LAST_UPDATED_AT INTEGER NOT NULL,
	PRIMARY KEY (
		F_PAGE_SET_ID
	)
	FOREIGN KEY (
		F_OWNER_PROJECT_ID
	) REFERENCES T_PROJECT (
		F_PROJECT_ID
	)	ON UPDATE CASCADE
		ON DELETE CASCADE
);
CREATE TABLE IF NOT EXISTS T_PAGE (
	F_PAGE_ID INTEGER NOT NULL,
	F_OWNER_PAGE_SET_ID INTEGER NOT NULL,
	F_CONTEXT_CLASS_ID INTEGER,
	F_WIDTH INTEGER NOT NULL,
	F_HEIGHT INTEGER NOT NULL,
	F__LAST_UPDATED_AT INTEGER NOT NULL,
	PRIMARY KEY (
		F_PAGE_ID
	)
	FOREIGN KEY (
		F_OWNER_PAGE_SET_ID
	) REFERENCES T_PAGE_SET (
		F_PAGE_SET_ID
	)	ON UPDATE CASCADE
		ON DELETE CASCADE
	FOREIGN KEY (
		F_CONTEXT_CLASS_ID
	) REFERENCES T_CLASS (
		F_CLASS_ID
	)	ON UPDATE CASCADE
		ON DELETE SET NULL
);
CREATE TABLE IF NOT EXISTS T_LAYOUT (
	F_LAYOUT_ID INTEGER NOT NULL,
	F_OWNER_PAGE_ID INTEGER NOT NULL,
	F_PARENT_LAYOUT_ID INTEGER,
	F_LAYOUT_NAME TEXT NOT NULL,
	F_LAYOUT_TYPE INTEGER NOT NULL,
	F_CREF_CLASS_ID INTEGER,
	F_EREF_ENUM_ID INTEGER,
	F_FREF_FIELD_ID INTEGER,
	F_MREF_MESSAGE_ID INTEGER,
	F_RELATED_LAYOUT_ID INTEGER,
	F_PARAM TEXT,
	F_LAYOUT_PARAM TEXT,
	F_STYLE_STYLE_ID INTEGER,
	F_LEFT TEXT,
	F_TOP TEXT,
	F_RIGHT TEXT,
	F_BOTTOM TEXT,
	F_WIDTH TEXT,
	F_HEIGHT TEXT,
	F__LAST_UPDATED_AT INTEGER NOT NULL,
	PRIMARY KEY (
		F_LAYOUT_ID
	)
	FOREIGN KEY (
		F_OWNER_PAGE_ID
	) REFERENCES T_PAGE (
		F_PAGE_ID
	)	ON UPDATE CASCADE
		ON DELETE CASCADE
	FOREIGN KEY (
		F_PARENT_LAYOUT_ID
	) REFERENCES T_LAYOUT (
		F_LAYOUT_ID
	)	ON UPDATE CASCADE
		ON DELETE SET NULL
	FOREIGN KEY (
		F_CREF_CLASS_ID
	) REFERENCES T_CLASS (
		F_CLASS_ID
	)	ON UPDATE CASCADE
		ON DELETE SET NULL
	FOREIGN KEY (
		F_EREF_ENUM_ID
	) REFERENCES T_ENUM (
		F_ENUM_ID
	)	ON UPDATE CASCADE
		ON DELETE SET NULL
	FOREIGN KEY (
		F_FREF_FIELD_ID
	) REFERENCES T_FIELD (
		F_FIELD_ID
	)	ON UPDATE CASCADE
		ON DELETE SET NULL
	FOREIGN KEY (
		F_MREF_MESSAGE_ID
	) REFERENCES T_MESSAGE (
		F_MESSAGE_ID
	)	ON UPDATE CASCADE
		ON DELETE SET NULL
	FOREIGN KEY (
		F_RELATED_LAYOUT_ID
	) REFERENCES T_LAYOUT (
		F_LAYOUT_ID
	)	ON UPDATE CASCADE
		ON DELETE SET NULL
	FOREIGN KEY (
		F_STYLE_STYLE_ID
	) REFERENCES T_STYLE (
		F_STYLE_ID
	)	ON UPDATE CASCADE
		ON DELETE SET NULL
);
CREATE TABLE IF NOT EXISTS T_LOCALE (
	F_LOCALE_ID TEXT NOT NULL,
	F_OWNER_PROJECT_ID INTEGER NOT NULL,
	F_TITLE TEXT NOT NULL,
	F_DESCRIPTION TEXT NOT NULL,
	F__LAST_UPDATED_AT INTEGER NOT NULL,
	PRIMARY KEY (
		F_LOCALE_ID
	)
	FOREIGN KEY (
		F_OWNER_PROJECT_ID
	) REFERENCES T_PROJECT (
		F_PROJECT_ID
	)	ON UPDATE CASCADE
		ON DELETE CASCADE
);
CREATE TABLE IF NOT EXISTS T_BUILDER (
	F_CLASS_NAME TEXT NOT NULL,
	F_SURROGATE_COUNT INTEGER NOT NULL,
	F__LAST_UPDATED_AT INTEGER NOT NULL,
	PRIMARY KEY (
		F_CLASS_NAME
	)
);
