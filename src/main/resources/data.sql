PRAGMA foreign_keys = false;
INSERT INTO T_PROJECT (
	F_PROJECT_ID,
	F_PROJECT_NAME,
	F_GRADLE_NAME,
	F_LABEL,
	F_TITLE,
	F_DESCRIPTION,
	F_NOTE,
	F_COPYRIGHTS,
	F__LAST_UPDATED_AT
) VALUES
	(1,'com.github.vvorks.builder','main','ビルダープロジェクト','ビルダープロジェクト','自己記述プロジェクト','','Apache License v2.0',(strftime('%s', 'now') * 1000)),
	(2,'test','test','テストプロジェクト','テストプロジェクト','記述テスト用プロジェクト','','Apache License v2.0',(strftime('%s', 'now') * 1000))
ON CONFLICT DO NOTHING;
INSERT INTO T_PROJECT_I18N (
	F_OWNER_PROJECT_ID,
	F_TARGET_LOCALE_ID,
	F_LABEL,
	F__LAST_UPDATED_AT
) VALUES
	(1,'en','builder project',(strftime('%s', 'now') * 1000)),
	(1,'ja','ビルダープロジェクト',(strftime('%s', 'now') * 1000))
ON CONFLICT DO NOTHING;
INSERT INTO T_CLASS (
	F_CLASS_ID,
	F_OWNER_PROJECT_ID,
	F_CLASS_NAME,
	F_ORDER_EXPR,
	F_TITLE_EXPR,
	F_LABEL,
	F_TITLE,
	F_DESCRIPTION,
	F_NOTE,
	F__LAST_UPDATED_AT
) VALUES
	(1,1,'Project','projectId','projectName+''(''+title+'')''','プロジェクト','プロジェクト',NULL,NULL,(strftime('%s', 'now') * 1000)),
	(2,1,'ProjectI18n','owner.projectId, target.localeId','owner.projectName+''(''+owner.title+''/I18n)''','プロジェクト(I18n)','プロジェクト(I18n)',NULL,NULL,(strftime('%s', 'now') * 1000)),
	(3,1,'Class','classId','className+''(''+title+'')''','クラス','クラス',NULL,NULL,(strftime('%s', 'now') * 1000)),
	(4,1,'ClassI18n','owner.classId','owner.className+''(''+owner.title+'')''','クラス(I18n)','クラス(I18n)',NULL,NULL,(strftime('%s', 'now') * 1000)),
	(5,1,'Field','fieldId','owner.className+''/''+fieldName','フィールド','フィールド',NULL,NULL,(strftime('%s', 'now') * 1000)),
	(6,1,'Query','queryId','title','クエリー','クエリー',NULL,NULL,(strftime('%s', 'now') * 1000)),
	(7,1,'Enum','enumId','enumName+''(''+title+'')''','列挙','列挙',NULL,NULL,(strftime('%s', 'now') * 1000)),
	(8,1,'EnumValue','code','valueId','列挙値','列挙値',NULL,NULL,(strftime('%s', 'now') * 1000)),
	(9,1,'Message','messageId','messageName','メッセージ','メッセージ',NULL,NULL,(strftime('%s', 'now') * 1000)),
	(10,1,'Locale','title','title','ロケール','ロケール','ロケールマスタ','必要な分だけ登録',(strftime('%s', 'now') * 1000)),
	(11,2,'Otamesshi','otameshiId','otameshiId','お試しクラス','お試しクラス','お試しクラスです。','お試しが終わったら消す事',(strftime('%s', 'now') * 1000)),
	(12,2,'Namihei','namiheiId','name','波平','波平',NULL,NULL,(strftime('%s', 'now') * 1000)),
	(13,2,'Sazae','sazaeId','name','サザエ','サザエ',NULL,NULL,(strftime('%s', 'now') * 1000)),
	(14,2,'Katsuo','katsuoId','name','カツオ','カツオ',NULL,NULL,(strftime('%s', 'now') * 1000)),
	(15,2,'Wakame','wakameId','name','ワカメ','ワカメ',NULL,NULL,(strftime('%s', 'now') * 1000)),
	(16,2,'Tara','taraId','name','タラ','タラ',NULL,NULL,(strftime('%s', 'now') * 1000))
ON CONFLICT DO NOTHING;
INSERT INTO T_CLASS_I18N (
	F_OWNER_CLASS_ID,
	F_TARGET_LOCALE_ID,
	F_LABEL,
	F__LAST_UPDATED_AT
) VALUES
	(1,'ja','プロジェクト',(strftime('%s', 'now') * 1000)),
	(1,'en','project',(strftime('%s', 'now') * 1000)),
	(2,'ja','プロジェクト(I18n)',(strftime('%s', 'now') * 1000)),
	(2,'en','project(I18n)',(strftime('%s', 'now') * 1000)),
	(3,'ja','クラス',(strftime('%s', 'now') * 1000)),
	(3,'en','class',(strftime('%s', 'now') * 1000)),
	(4,'ja','フィールド',(strftime('%s', 'now') * 1000)),
	(4,'en','field',(strftime('%s', 'now') * 1000)),
	(5,'ja','クエリー',(strftime('%s', 'now') * 1000)),
	(5,'en','query',(strftime('%s', 'now') * 1000)),
	(6,'ja','列挙',(strftime('%s', 'now') * 1000)),
	(6,'en','enum',(strftime('%s', 'now') * 1000)),
	(7,'ja','列挙値',(strftime('%s', 'now') * 1000)),
	(7,'en','enum value',(strftime('%s', 'now') * 1000)),
	(8,'ja','メッセージ',(strftime('%s', 'now') * 1000)),
	(8,'en','message',(strftime('%s', 'now') * 1000)),
	(9,'ja','ロケール',(strftime('%s', 'now') * 1000)),
	(9,'en','locale',(strftime('%s', 'now') * 1000)),
	(10,'ja','お試しクラス',(strftime('%s', 'now') * 1000)),
	(10,'en','test',(strftime('%s', 'now') * 1000)),
	(11,'ja','波平',(strftime('%s', 'now') * 1000)),
	(11,'en','namihei',(strftime('%s', 'now') * 1000)),
	(12,'ja','サザエ',(strftime('%s', 'now') * 1000)),
	(12,'en','sazae',(strftime('%s', 'now') * 1000)),
	(13,'ja','カツオ',(strftime('%s', 'now') * 1000)),
	(13,'en','katsuo',(strftime('%s', 'now') * 1000)),
	(14,'ja','ワカメ',(strftime('%s', 'now') * 1000)),
	(14,'en','wakame',(strftime('%s', 'now') * 1000)),
	(15,'ja','タラ',(strftime('%s', 'now') * 1000)),
	(15,'en','tara',(strftime('%s', 'now') * 1000))
ON CONFLICT DO NOTHING;
INSERT INTO T_FIELD (
	F_FIELD_ID,
	F_OWNER_CLASS_ID,
	F_FIELD_NAME,
	F_TYPE,
	F_WIDTH,
	F_SCALE,
	F_CREF_CLASS_ID,
	F_EREF_ENUM_ID,
	F_FREF_FIELD_ID,
	F_PK,
	F_NULLABLE,
	F_NEEDS_SUM,
	F_NEEDS_AVG,
	F_NEEDS_MAX,
	F_NEEDS_MIN,
	F_LABEL,
	F_FORMAT,
	F_TITLE,
	F_DESCRIPTION,
	F_NOTE,
	F__LAST_UPDATED_AT
) VALUES
	(1,1,'projectId','KEY',0,0,0,0,0,1,0,0,0,0,0,'プロジェクトID','','プロジェクトID','','（代理キー）',(strftime('%s', 'now') * 1000)),
	(2,1,'projectName','STRING',0,0,0,0,0,0,0,0,0,0,0,'プロジェクト名','','プロジェクト名','','ベースパッケージ名を兼ねる',(strftime('%s', 'now') * 1000)),
	(3,1,'gradleName','STRING',0,0,0,0,0,0,0,0,0,0,0,'gradleサブプロジェクト名','','gradleサブプロジェクト名','','',(strftime('%s', 'now') * 1000)),
	(4,1,'label','STRING',0,0,0,0,0,0,1,0,0,0,0,'ラベル','','ラベル',NULL,NULL,(strftime('%s', 'now') * 1000)),
	(5,1,'title','STRING',0,0,0,0,0,0,0,0,0,0,0,'タイトル','','タイトル','仕様書生成用見出し',NULL,(strftime('%s', 'now') * 1000)),
	(6,1,'description','STRING',0,0,0,0,0,0,1,0,0,0,0,'説明','','説明','仕様書生成用説明文',NULL,(strftime('%s', 'now') * 1000)),
	(7,1,'note','STRING',0,0,0,0,0,0,1,0,0,0,0,'メモ','','メモ',NULL,NULL,(strftime('%s', 'now') * 1000)),
	(8,1,'copyrights','STRING',0,0,0,0,0,0,1,0,0,0,0,'著作権','','著作権',NULL,NULL,(strftime('%s', 'now') * 1000)),
	(9,1,'classes','SET',0,0,0,0,18,0,0,0,0,0,0,'クラス一覧','','クラス一覧',NULL,NULL,(strftime('%s', 'now') * 1000)),
	(10,1,'enums','SET',0,0,0,0,60,0,0,0,0,0,0,'列挙一覧','','列挙一覧',NULL,NULL,(strftime('%s', 'now') * 1000)),
	(11,1,'messages','SET',0,0,0,0,74,0,0,0,0,0,0,'メッセージ一覧','','メッセージ一覧',NULL,NULL,(strftime('%s', 'now') * 1000)),
	(12,1,'locales','SET',0,0,0,0,78,0,0,0,0,0,0,'ロケール一覧','','ロケール一覧',NULL,NULL,(strftime('%s', 'now') * 1000)),
	(13,1,'i18ns','SET',0,0,0,0,14,0,0,0,0,0,0,'I18n一覧','','I18n一覧',NULL,NULL,(strftime('%s', 'now') * 1000)),
	(14,2,'owner','REF',0,0,1,0,0,1,0,0,0,0,0,'所属プロジェクト','','所属プロジェクト','','',(strftime('%s', 'now') * 1000)),
	(15,2,'target','REF',0,0,10,0,0,1,0,0,0,0,0,'対象ロケール','','対象ロケール',NULL,NULL,(strftime('%s', 'now') * 1000)),
	(16,2,'label','STRING',0,0,0,0,0,0,1,0,0,0,0,'I18nラベル','','I18nラベル',NULL,NULL,(strftime('%s', 'now') * 1000)),
	(17,3,'classId','KEY',0,0,0,0,0,1,0,0,0,0,0,'クラスID','','クラスID','','（代理キー）',(strftime('%s', 'now') * 1000)),
	(18,3,'owner','REF',0,0,1,0,0,0,0,0,0,0,0,'所属プロジェクト','','所属プロジェクト','','',(strftime('%s', 'now') * 1000)),
	(19,3,'className','STRING',0,0,0,0,0,0,0,0,0,0,0,'クラス名','','クラス名','','',(strftime('%s', 'now') * 1000)),
	(20,3,'orderExpr','STRING',0,0,0,0,0,0,0,0,0,0,0,'オーダー式','','オーダー式','デフォルトの表示順を表す式',NULL,(strftime('%s', 'now') * 1000)),
	(21,3,'titleExpr','STRING',0,0,0,0,0,0,0,0,0,0,0,'タイトル表示式','','タイトル表示式','オブジェクトのタイトルを表現する式',NULL,(strftime('%s', 'now') * 1000)),
	(22,3,'label','STRING',0,0,0,0,0,0,0,0,0,0,0,'表示ラベル','','表示ラベル',NULL,NULL,(strftime('%s', 'now') * 1000)),
	(23,3,'title','STRING',0,0,0,0,0,0,0,0,0,0,0,'タイトル','','タイトル','仕様書生成用見出し',NULL,(strftime('%s', 'now') * 1000)),
	(24,3,'description','STRING',0,0,0,0,0,0,1,0,0,0,0,'説明','','説明','仕様書生成用説明文',NULL,(strftime('%s', 'now') * 1000)),
	(25,3,'note','STRING',0,0,0,0,0,0,1,0,0,0,0,'メモ','','メモ',NULL,NULL,(strftime('%s', 'now') * 1000)),
	(26,3,'fields','SET',0,0,0,0,32,0,0,0,0,0,0,'フィールド一覧','','フィールド一覧',NULL,NULL,(strftime('%s', 'now') * 1000)),
	(27,3,'queries','SET',0,0,0,0,52,0,0,0,0,0,0,'クエリー一覧','','クエリー一覧',NULL,NULL,(strftime('%s', 'now') * 1000)),
	(28,4,'owner','REF',0,0,3,0,0,1,0,0,0,0,0,'所属クラス','','所属クラス','','',(strftime('%s', 'now') * 1000)),
	(29,4,'target','REF',0,0,10,0,0,1,0,0,0,0,0,'対象ロケール','','対象ロケール',NULL,NULL,(strftime('%s', 'now') * 1000)),
	(30,4,'label','STRING',0,0,0,0,0,0,1,0,0,0,0,'I18nテキスト','','I18nテキスト',NULL,NULL,(strftime('%s', 'now') * 1000)),
	(31,5,'fieldId','KEY',0,0,0,0,0,1,0,0,0,0,0,'フィールドID','','フィールドID','','（代理キー）',(strftime('%s', 'now') * 1000)),
	(32,5,'owner','REF',0,0,3,0,0,0,0,0,0,0,0,'所属クラス','','所属クラス','','',(strftime('%s', 'now') * 1000)),
	(33,5,'fieldName','STRING',0,0,0,0,0,0,0,0,0,0,0,'フィールド名','','フィールド名',NULL,NULL,(strftime('%s', 'now') * 1000)),
	(34,5,'type','ENUM',0,0,0,1,0,0,0,0,0,0,0,'フィールド型','','フィールド型',NULL,NULL,(strftime('%s', 'now') * 1000)),
	(35,5,'width','INTEGER',32,0,0,0,0,0,0,0,0,0,0,'フィールド型の幅','','フィールド型の幅',NULL,NULL,(strftime('%s', 'now') * 1000)),
	(36,5,'scale','INTEGER',32,0,0,0,0,0,0,0,0,0,0,'フィールド型精度','','フィールド型精度',NULL,NULL,(strftime('%s', 'now') * 1000)),
	(37,5,'cref','REF',0,0,3,0,0,0,0,0,0,0,0,'クラス参照先','','クラス参照先',NULL,NULL,(strftime('%s', 'now') * 1000)),
	(38,5,'eref','REF',0,0,7,0,0,0,0,0,0,0,0,'列挙参照先','','列挙参照先',NULL,NULL,(strftime('%s', 'now') * 1000)),
	(39,5,'fref','REF',0,0,5,0,0,0,0,0,0,0,0,'フィールド参照先','','フィールド参照先',NULL,NULL,(strftime('%s', 'now') * 1000)),
	(40,5,'pk','BOOLEAN',0,0,0,0,0,0,0,0,0,0,0,'プライマリキー','','プライマリキー',NULL,NULL,(strftime('%s', 'now') * 1000)),
	(41,5,'nullable','BOOLEAN',0,0,0,0,0,0,0,0,0,0,0,'NULL許容','','NULL許容',NULL,NULL,(strftime('%s', 'now') * 1000)),
	(42,5,'needsSum','BOOLEAN',0,0,0,0,0,0,0,0,0,0,0,'要合計値','','要合計値',NULL,NULL,(strftime('%s', 'now') * 1000)),
	(43,5,'needsAvg','BOOLEAN',0,0,0,0,0,0,0,0,0,0,0,'要平均値','','要平均値',NULL,NULL,(strftime('%s', 'now') * 1000)),
	(44,5,'needsMax','BOOLEAN',0,0,0,0,0,0,0,0,0,0,0,'要最大値','','要最大値',NULL,NULL,(strftime('%s', 'now') * 1000)),
	(45,5,'needsMin','BOOLEAN',0,0,0,0,0,0,0,0,0,0,0,'要最小値','','要最小値',NULL,NULL,(strftime('%s', 'now') * 1000)),
	(46,5,'label','STRING',0,0,0,0,0,0,0,0,0,0,0,'ラベル','','ラベル','画面用項目ラベル',NULL,(strftime('%s', 'now') * 1000)),
	(47,5,'format','STRING',0,0,0,0,0,0,0,0,0,0,0,'書式','','書式','画面用項目書式',NULL,(strftime('%s', 'now') * 1000)),
	(48,5,'title','STRING',0,0,0,0,0,0,0,0,0,0,0,'タイトル','','タイトル','仕様書生成用見出し',NULL,(strftime('%s', 'now') * 1000)),
	(49,5,'description','STRING',0,0,0,0,0,0,1,0,0,0,0,'説明','','説明','仕様書生成用説明文',NULL,(strftime('%s', 'now') * 1000)),
	(50,5,'note','STRING',0,0,0,0,0,0,1,0,0,0,0,'メモ','','メモ',NULL,NULL,(strftime('%s', 'now') * 1000)),
	(51,6,'queryId','KEY',0,0,0,0,0,1,0,0,0,0,0,'クエリーID','','クエリーID','','（代理キー）',(strftime('%s', 'now') * 1000)),
	(52,6,'owner','REF',0,0,3,0,0,0,0,0,0,0,0,'所属クラス','','所属クラス','','',(strftime('%s', 'now') * 1000)),
	(53,6,'queryName','STRING',0,0,0,0,0,0,0,0,0,0,0,'クエリー名','','クエリー名',NULL,NULL,(strftime('%s', 'now') * 1000)),
	(54,6,'filter','STRING',0,0,0,0,0,0,0,0,0,0,0,'抽出条件','','抽出条件','抽出条件を式形式で記載する',NULL,(strftime('%s', 'now') * 1000)),
	(55,6,'order','STRING',0,0,0,0,0,0,0,0,0,0,0,'ソート条件','','ソート条件','ソート条件を式形式で記載する',NULL,(strftime('%s', 'now') * 1000)),
	(56,6,'title','STRING',0,0,0,0,0,0,0,0,0,0,0,'タイトル','','タイトル','仕様書生成用見出し',NULL,(strftime('%s', 'now') * 1000)),
	(57,6,'description','STRING',0,0,0,0,0,0,1,0,0,0,0,'説明','','説明','仕様書生成用説明文',NULL,(strftime('%s', 'now') * 1000)),
	(58,6,'note','STRING',0,0,0,0,0,0,1,0,0,0,0,'メモ','','メモ',NULL,NULL,(strftime('%s', 'now') * 1000)),
	(59,7,'enumId','KEY',0,0,0,0,0,1,0,0,0,0,0,'列挙ID','','列挙ID','','（代理キー）',(strftime('%s', 'now') * 1000)),
	(60,7,'owner','REF',0,0,1,0,0,0,0,0,0,0,0,'所属プロジェクト','','所属プロジェクト','','',(strftime('%s', 'now') * 1000)),
	(61,7,'enumName','STRING',0,0,0,0,0,0,0,0,0,0,0,'列挙名','','列挙名',NULL,NULL,(strftime('%s', 'now') * 1000)),
	(62,7,'encodeString','BOOLEAN',0,0,0,0,0,0,0,0,0,0,0,'文字列エンコード','','文字列エンコード','文字列エンコードする場合、真',NULL,(strftime('%s', 'now') * 1000)),
	(63,7,'title','STRING',0,0,0,0,0,0,0,0,0,0,0,'タイトル','','タイトル','仕様書生成用見出し',NULL,(strftime('%s', 'now') * 1000)),
	(64,7,'description','STRING',0,0,0,0,0,0,1,0,0,0,0,'説明','','説明','仕様書生成用説明文',NULL,(strftime('%s', 'now') * 1000)),
	(65,7,'note','STRING',0,0,0,0,0,0,1,0,0,0,0,'メモ','','メモ',NULL,NULL,(strftime('%s', 'now') * 1000)),
	(66,7,'values','SET',0,0,0,0,67,0,0,0,0,0,0,'列挙値一覧','','列挙値一覧','','',(strftime('%s', 'now') * 1000)),
	(67,8,'owner','REF',0,0,7,0,0,1,0,0,0,0,0,'所属列挙','','所属列挙','','',(strftime('%s', 'now') * 1000)),
	(68,8,'valueId','STRING',0,0,0,0,0,1,0,0,0,0,0,'列挙名','','列挙名','','',(strftime('%s', 'now') * 1000)),
	(69,8,'code','INTEGER',32,0,0,0,0,0,0,0,0,0,0,'列挙コード','','列挙コード',NULL,NULL,(strftime('%s', 'now') * 1000)),
	(70,8,'title','STRING',0,0,0,0,0,0,0,0,0,0,0,'タイトル','','タイトル','仕様書生成用見出し',NULL,(strftime('%s', 'now') * 1000)),
	(71,8,'description','STRING',0,0,0,0,0,0,1,0,0,0,0,'説明','','説明','仕様書生成用説明文',NULL,(strftime('%s', 'now') * 1000)),
	(72,8,'note','STRING',0,0,0,0,0,0,1,0,0,0,0,'メモ','','メモ',NULL,NULL,(strftime('%s', 'now') * 1000)),
	(73,9,'messageId','KEY',0,0,0,0,0,1,0,0,0,0,0,'メッセージID','','メッセージID','','',(strftime('%s', 'now') * 1000)),
	(74,9,'owner','REF',0,0,1,0,0,0,0,0,0,0,0,'所属プロジェクト','','所属プロジェクト','','',(strftime('%s', 'now') * 1000)),
	(75,9,'messageName','STRING',0,0,0,0,0,0,0,0,0,0,0,'メッセージ名','','メッセージ名','','',(strftime('%s', 'now') * 1000)),
	(76,9,'message','STRING',0,0,0,0,0,0,0,0,0,0,0,'メッセージ','','メッセージ',NULL,NULL,(strftime('%s', 'now') * 1000)),
	(77,10,'localeId','STRING',0,0,0,0,0,1,0,0,0,0,0,'ロケールID','','ロケールID','','',(strftime('%s', 'now') * 1000)),
	(78,10,'owner','REF',0,0,1,0,0,0,0,0,0,0,0,'所属プロジェクト','','所属プロジェクト',NULL,NULL,(strftime('%s', 'now') * 1000)),
	(79,10,'title','STRING',0,0,0,0,0,0,0,0,0,0,0,'タイトル','','タイトル',NULL,NULL,(strftime('%s', 'now') * 1000)),
	(80,10,'description','STRING',0,0,0,0,0,0,0,0,0,0,0,'説明','','説明',NULL,NULL,(strftime('%s', 'now') * 1000)),
	(81,11,'otameshiId','KEY',0,0,0,0,0,1,0,0,0,0,0,'','','お試しId','お試しIdです','お試しが終わったら消す事',(strftime('%s', 'now') * 1000)),
	(82,11,'owner','REF',0,0,11,0,0,0,0,0,0,0,0,'','','','',NULL,(strftime('%s', 'now') * 1000)),
	(83,11,'children','SET',0,0,0,0,82,0,0,0,0,0,0,'','','','',NULL,(strftime('%s', 'now') * 1000)),
	(84,11,'related','REF',0,0,11,0,0,0,0,0,0,0,0,'','','','',NULL,(strftime('%s', 'now') * 1000)),
	(85,11,'relations','SET',0,0,0,0,84,0,0,0,0,0,0,'','','','',NULL,(strftime('%s', 'now') * 1000)),
	(86,11,'booleanValue','BOOLEAN',0,0,0,0,0,0,0,0,0,0,0,'','','booleanValue','booleanValueです','お試しが終わったら消す事',(strftime('%s', 'now') * 1000)),
	(87,11,'byteValue','INTEGER',8,0,0,0,0,0,0,1,0,0,0,'','','',NULL,'お試しが終わったら消す事',(strftime('%s', 'now') * 1000)),
	(88,11,'shortValue','INTEGER',16,0,0,0,0,0,0,0,1,0,0,'','','shortValue',NULL,'お試しが終わったら消す事',(strftime('%s', 'now') * 1000)),
	(89,11,'intValue','INTEGER',32,0,0,0,0,0,0,0,0,1,0,'','','intValue','intValueです','お試しが終わったら消す事',(strftime('%s', 'now') * 1000)),
	(90,11,'nullableValue','INTEGER',32,0,0,0,0,0,1,0,0,0,1,'','','nullableValue','nullableValueです','お試しが終わったら消す事',(strftime('%s', 'now') * 1000)),
	(91,11,'longValue','INTEGER',64,0,0,0,0,0,0,1,1,1,1,'','','longValue','longValueです','お試しが終わったら消す事',(strftime('%s', 'now') * 1000)),
	(92,11,'floatValue','REAL',32,0,0,0,0,0,0,1,1,0,0,'','','floatValue','floatValueです','お試しが終わったら消す事',(strftime('%s', 'now') * 1000)),
	(93,11,'doubleValue','REAL',64,0,0,0,0,0,0,0,0,1,1,'','','doubleValue',NULL,'お試しが終わったら消す事',(strftime('%s', 'now') * 1000)),
	(94,11,'numericValue','NUMERIC',10,3,0,0,0,0,0,1,1,1,1,'','','','numericValueです','お試しが終わったら消す事',(strftime('%s', 'now') * 1000)),
	(95,11,'dateValue','DATE',0,0,0,0,0,0,0,0,0,1,1,'','','dateValue','dateValueです','お試しが終わったら消す事',(strftime('%s', 'now') * 1000)),
	(96,11,'strValue','STRING',0,0,0,0,0,0,0,0,0,0,0,'','','strValue','strValueです','お試しが終わったら消す事',(strftime('%s', 'now') * 1000)),
	(97,12,'namiheiId','KEY',0,0,0,0,0,1,0,0,0,0,0,'','','namiheiId','namiheiIdです','',(strftime('%s', 'now') * 1000)),
	(98,12,'name','STRING',0,0,0,0,0,0,0,0,0,0,0,'','','name','nameです',NULL,(strftime('%s', 'now') * 1000)),
	(99,13,'sazaeId','KEY',0,0,0,0,0,1,0,0,0,0,0,'','','','','',(strftime('%s', 'now') * 1000)),
	(100,13,'owner','REF',0,0,12,0,0,1,0,0,0,0,0,'','','',NULL,NULL,(strftime('%s', 'now') * 1000)),
	(101,13,'name','STRING',0,0,0,0,0,0,0,0,0,0,0,'','','name','nameです',NULL,(strftime('%s', 'now') * 1000)),
	(102,14,'katsuoId','KEY',0,0,0,0,0,1,0,0,0,0,0,'','','','','',(strftime('%s', 'now') * 1000)),
	(103,14,'owner','REF',0,0,12,0,0,1,0,0,0,0,0,'','','',NULL,NULL,(strftime('%s', 'now') * 1000)),
	(104,14,'name','STRING',0,0,0,0,0,0,0,0,0,0,0,'','','name','nameです',NULL,(strftime('%s', 'now') * 1000)),
	(105,15,'wakameId','KEY',0,0,0,0,0,1,0,0,0,0,0,'','','','','',(strftime('%s', 'now') * 1000)),
	(106,15,'owner','REF',0,0,12,0,0,1,0,0,0,0,0,'','','',NULL,NULL,(strftime('%s', 'now') * 1000)),
	(107,15,'name','STRING',0,0,0,0,0,0,0,0,0,0,0,'','','name','nameです',NULL,(strftime('%s', 'now') * 1000)),
	(108,16,'taraId','KEY',0,0,0,0,0,1,0,0,0,0,0,'','','','','',(strftime('%s', 'now') * 1000)),
	(109,16,'owner','REF',0,0,13,0,0,1,0,0,0,0,0,'','','',NULL,NULL,(strftime('%s', 'now') * 1000)),
	(110,16,'name','STRING',0,0,0,0,0,0,0,0,0,0,0,'','','name','nameです',NULL,(strftime('%s', 'now') * 1000))
ON CONFLICT DO NOTHING;
INSERT INTO T_QUERY (
	F_QUERY_ID,
	F_OWNER_CLASS_ID,
	F_QUERY_NAME,
	F_FILTER,
	F_ORDER,
	F_TITLE,
	F_DESCRIPTION,
	F_NOTE,
	F__LAST_UPDATED_AT
) VALUES
	(1,1,'nameIs','projectName == $name','','名前',NULL,NULL,(strftime('%s', 'now') * 1000)),
	(2,3,'nameIs','className == $name','','名前',NULL,NULL,(strftime('%s', 'now') * 1000)),
	(3,5,'nameIs','fieldName == $name','','名前',NULL,NULL,(strftime('%s', 'now') * 1000)),
	(4,7,'nameIs','enumName == $name','','名前',NULL,NULL,(strftime('%s', 'now') * 1000)),
	(5,11,'startsFoo','owner.strValue =% ''Foo%'' && owner.owner.strValue =% ''%Bar''','','条件：父がFooで始まり祖父がBarで終わる',NULL,NULL,(strftime('%s', 'now') * 1000)),
	(6,16,'containSazae','owner.name =% ''%サザエ%'' && owner.owner.name =~ ''^[0-9]*$''','','条件：父がサザエを含み祖父が数字',NULL,NULL,(strftime('%s', 'now') * 1000))
ON CONFLICT DO NOTHING;
INSERT INTO T_ENUM (
	F_ENUM_ID,
	F_OWNER_PROJECT_ID,
	F_ENUM_NAME,
	F_ENCODE_STRING,
	F_TITLE,
	F_DESCRIPTION,
	F_NOTE,
	F__LAST_UPDATED_AT
) VALUES
	(1,1,'DataType',1,'データ型',NULL,NULL,(strftime('%s', 'now') * 1000))
ON CONFLICT DO NOTHING;
INSERT INTO T_ENUM_VALUE (
	F_OWNER_ENUM_ID,
	F_VALUE_ID,
	F_CODE,
	F_TITLE,
	F_DESCRIPTION,
	F_NOTE,
	F__LAST_UPDATED_AT
) VALUES
	(1,'KEY',10,'（自動）キー',NULL,NULL,(strftime('%s', 'now') * 1000)),
	(1,'REF',11,'クラス参照型',NULL,NULL,(strftime('%s', 'now') * 1000)),
	(1,'ENUM',12,'列挙参照型',NULL,NULL,(strftime('%s', 'now') * 1000)),
	(1,'SET',13,'集合（被参照）型',NULL,NULL,(strftime('%s', 'now') * 1000)),
	(1,'ENUM_VALUE',14,'列挙値型',NULL,'内部用。モデル定義での使用不可',(strftime('%s', 'now') * 1000)),
	(1,'BOOLEAN',20,'真偽値',NULL,NULL,(strftime('%s', 'now') * 1000)),
	(1,'INTEGER',30,'整数',NULL,NULL,(strftime('%s', 'now') * 1000)),
	(1,'REAL',40,'浮動小数',NULL,NULL,(strftime('%s', 'now') * 1000)),
	(1,'NUMERIC',50,'１０進小数',NULL,NULL,(strftime('%s', 'now') * 1000)),
	(1,'DATE',60,'日付・時刻',NULL,NULL,(strftime('%s', 'now') * 1000)),
	(1,'STRING',70,'文字列',NULL,NULL,(strftime('%s', 'now') * 1000))
ON CONFLICT DO NOTHING;
INSERT INTO T_MESSAGE (
	F_MESSAGE_ID,
	F_OWNER_PROJECT_ID,
	F_MESSAGE_NAME,
	F_MESSAGE,
	F__LAST_UPDATED_AT
) VALUES
	(1,1,'hello','こんにちは',(strftime('%s', 'now') * 1000)),
	(2,1,'bye','さようなら',(strftime('%s', 'now') * 1000))
ON CONFLICT DO NOTHING;
INSERT INTO T_LOCALE (
	F_LOCALE_ID,
	F_OWNER_PROJECT_ID,
	F_TITLE,
	F_DESCRIPTION,
	F__LAST_UPDATED_AT
) VALUES
	('en_US',1,'engish(united status of ameria)','',(strftime('%s', 'now') * 1000)),
	('en',1,'english','',(strftime('%s', 'now') * 1000)),
	('ja',1,'japanease','',(strftime('%s', 'now') * 1000)),
	('ja_JP',1,'japanease(japan)','',(strftime('%s', 'now') * 1000))
ON CONFLICT DO NOTHING;
PRAGMA foreign_keys = true;
