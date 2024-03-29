package com.github.vvorks.builder.server.extender;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.github.vvorks.builder.server.common.sql.SqlHelper;
import com.github.vvorks.builder.server.domain.ClassContent;
import com.github.vvorks.builder.server.domain.DataType;
import com.github.vvorks.builder.server.domain.EnumContent;
import com.github.vvorks.builder.server.domain.FieldContent;
import com.github.vvorks.builder.server.domain.ProjectContent;
import com.github.vvorks.builder.server.domain.QueryContent;
import com.github.vvorks.builder.server.expression.Argument;
import com.github.vvorks.builder.server.expression.Expression;
import com.github.vvorks.builder.server.expression.FieldRef;
import com.github.vvorks.builder.server.expression.Operation;
import com.github.vvorks.builder.server.grammar.ExprNode;
import com.github.vvorks.builder.server.grammar.ExprParser;
import com.github.vvorks.builder.server.grammar.ExpressionBuilder;
import com.github.vvorks.builder.server.grammar.ParseException;
import com.github.vvorks.builder.server.mapper.MapperInterface;
import com.github.vvorks.builder.server.mapper.Mappers;
import com.github.vvorks.builder.shared.common.lang.Asserts;
import com.github.vvorks.builder.shared.common.lang.Encodable;
import com.github.vvorks.builder.shared.common.lang.RichIterable;
import com.github.vvorks.builder.shared.common.lang.Strings;
import com.github.vvorks.builder.shared.common.logging.Logger;

@Component
public class ClassExtender {

	private static final Logger LOGGER = Logger.createLogger(ClassExtender.class);

	private static class ExprEntry {
		private final Map<List<FieldContent>, Integer> joinMap;
		private final Map<String, ExprInfo> exprs;
		private ExprEntry() {
			joinMap = new LinkedHashMap<>();
			exprs = new LinkedHashMap<>();
		}
	}

	public static class ExprInfo {
		private final Expression expr;
		private final List<FieldContent> arguments;
		public ExprInfo(Expression expr) {
			this.expr = expr;
			this.arguments = new ArrayList<>();
		}
		public Expression getExpr() {
			return expr;
		}
		public List<FieldContent> getArguments() {
			return arguments;
		}
	}

	public static class JoinInfo {
		private final FieldContent lastField;
		private final int lastNo;
		private final ClassContent nextClass;
		private final int nextNo;
		public JoinInfo(FieldContent lastField, int lastNo, ClassContent nextClass, int nextNo) {
			this.lastField = lastField;
			this.lastNo = lastNo;
			this.nextClass = nextClass;
			this.nextNo = nextNo;
		}
		public FieldContent getLastField() {
			return lastField;
		}
		public int getLastNo() {
			return lastNo;
		}
		public ClassContent getNextClass() {
			return nextClass;
		}
		public int getNextNo() {
			return nextNo;
		}
	}

	public class TitleInfo {
		private final String fieldName;
		private final String title;
		private final ExprInfo info;
		private TitleInfo(String fieldName, String title, ExprInfo info) {
			this.fieldName = fieldName;
			this.title = title;
			this.info = info;
		}
		public String getTitleOrName() {
			return title;
		}
		public String getJavaType() {
			return "String";
		}
		public String getFieldName() {
			return fieldName;
		}
		public String getGetterName() {
			return "get" + Strings.toFirstUpper(fieldName);
		}
		public String getSetterName() {
			return "set" + Strings.toFirstUpper(fieldName);
		}
		public String getTitleExpr() {
			if (info != null) {
				return info.getExpr().accept(sqlWriter, extenders);
			} else {
				return "''";
			}
		}
	}

	@Autowired
	private Mappers mappers;

	@Autowired
	private Extenders extenders;

	@Autowired
	private ExprParser parser;

	@Autowired
	private ExpressionBuilder builder;

    @Autowired
    private AdditionalInformation adder;

	private SqlWriter sqlWriter = SqlWriter.get();

	private SqlHelper sqlHelper = SqlHelper.get();

	private final Map<Integer, ExprEntry> entries = new LinkedHashMap<>();

	public String getSql(ClassContent cls) {
		entries.clear();
		return null;
	}

	public String getTitleOrName(ClassContent cls) {
		if (!Strings.isEmpty(cls.getTitle())) {
			return cls.getTitle();
		} else {
			return cls.getClassName();
		}
	}

	public String getLowerName(ClassContent cls) {
		return Strings.toFirstLower(cls.getClassName());
	}

	public String getConstName(ClassContent cls) {
		return Strings.toUpperSnake(cls.getClassName());
	}

	public String getTableName(ClassContent cls) {
		return SqlWriter.TABLE_PREFIX + getConstName(cls);
	}

	public String getSqlOrder(ClassContent cls) {
		String orderExpr = cls.getOrderExpr();
		if (Strings.isEmpty(orderExpr)) {
			return null;
		}
		ExprInfo info = referExpr(cls, orderExpr, ExprParser.CODE_TYPE_ORDER);
		String result = info.getExpr().accept(sqlWriter, extenders);
		return result;
	}

	public boolean isSurrogateClass(ClassContent cls) {
		List<FieldContent> keys = getKeys(cls);
		if (keys.size() != 1) {
			return false;
		}
		FieldContent fld = keys.get(0);
		return fld.getType() == DataType.KEY && fld.isNeedsMax();
	}

	public FieldContent getSurrogateField(ClassContent cls) {
		if (!isSurrogateClass(cls)) {
			return null;
		}
		return getKeys(cls).get(0);
	}

	/**
	 * 指定クラスのタイトル一覧を取得する
	 *
	 * @param cls
	 * @return タイトル一覧
	 */
	public List<TitleInfo> get_titleFields(ClassContent cls) {
		List<TitleInfo> result = new ArrayList<>();
		result.add(get_titleField(cls));
		for (FieldContent fld : getRefs(cls)) {
			result.add(get_refTitleField(cls, fld));
		}
		for (FieldContent fld : getEnums(cls)) {
			result.add(get_refTitleField(cls, fld));
		}
		return result;
	}

	/**
	 * 指定クラスのタイトルフィールドを取得する
	 *
	 * @param cls
	 * @return タイトルフィールド
	 */
	public TitleInfo get_titleField(ClassContent cls) {
		String titleExpr = cls.getTitleExpr();
		String name = "_title";
		String title = "タイトル";
		if (Strings.isEmpty(titleExpr)) {
			return new TitleInfo(name, title, null);
		}
		ExprInfo info = referExpr(cls, titleExpr, ExprParser.CODE_TYPE_SELECT);
		return new TitleInfo(name, title, info);
	}

	/**
	 * 指定クラスの参照フィールドのタイトルフィールドを取得する
	 */
	public TitleInfo get_refTitleField(ClassContent cls, FieldContent fld) {
		String name = fld.getFieldName() + "_title";
		String title = extenders.getFieldExtender().getTitleOrName(fld) + "のタイトル"; //TODO I18N
		ClassContent ref = getReferClass(fld);
		String refExpr = ref.getTitleExpr();
		ExprInfo info = referExpr(cls, fld, refExpr, ExprParser.CODE_TYPE_SELECT);
		return new TitleInfo(name, title, info);
	}

	private ClassContent getReferClass(FieldContent fld) {
		ClassContent ref;
		if (fld.getType() == DataType.REF) {
			ref = extenders.getFieldExtender().getCref(fld);
		} else if (fld.getType() == DataType.ENUM) {
			ref = extenders.getEnumValueExtender().getThisContent();
		} else {
			throw new IllegalArgumentException();
		}
		return ref;
	}

	public List<FieldContent> getProperties(ClassContent cls) {
		return getProperties(cls, fld -> true);
	}

	public List<FieldContent> getKeys(ClassContent cls) {
		return getProperties(cls, fld ->  fld.isPk());
	}

	public List<FieldContent> getNotKeys(ClassContent cls) {
		return getProperties(cls, fld -> !fld.isPk());
	}

	private List<FieldContent> getProperties(ClassContent cls, Predicate<FieldContent> filter) {
		List<FieldContent> props = new ArrayList<>();
		Deque<FieldContent> stack = new ArrayDeque<>();
		for (FieldContent field : getFields(cls)) {
			if (filter.test(field)) {
				extenders.getFieldExtender().extractKey(field, stack, props);
			}
		}
		return props;
	}

	public List<FieldContent> getRefs(ClassContent cls) {
		return getFields(cls, fld -> fld.getType() == DataType.REF);
	}

	public List<FieldContent> getEnums(ClassContent cls) {
		return getFields(cls, fld -> fld.getType() == DataType.ENUM);
	}

	public List<FieldContent> getSets(ClassContent cls) {
		return getFields(cls, fld -> fld.getType() == DataType.SET);
	}

	public List<FieldContent> getContainsSets(ClassContent cls) {
		return getFields(cls, fld -> fld.getType() == DataType.SET && fld.isIsContainer());
	}

	public List<FieldContent> getFields(ClassContent cls, Predicate<FieldContent> filter) {
		List<FieldContent> result = new ArrayList<>();
		for (FieldContent field : getFields(cls)) {
			if (filter.test(field)) {
				result.add(field);
			}
		}
		return result;
	}

	public List<FieldContent> getFields(ClassContent cls) {
		List<FieldContent> fields = mappers.getClassMapper().listFieldsContent(cls, 0, 0);
		fields.addAll(adder.getAdditionalFields(cls));
		return fields;
	}

	public List<QueryContent> getQueries(ClassContent cls) {
		return mappers.getClassMapper().listQueriesContent(cls, 0, 0);
	}

	private static class Joint {
		private FieldContent[] fields;
		private Method[] getters;
		private Joint(FieldContent[] fields, Method[] getters) {
			this.fields = fields;
			this.getters = getters;
		}
	}

	private Map<String, Joint> joints = new HashMap<>();

	public List<String[]> getValues(ClassContent cls) {
		String name = cls.getClassName();
		ProjectContent prj = mappers.getClassMapper().getOwner(cls);
		String prjName = extenders.getProjectExtender().getUpperLastName(prj);
		if (name.equals(prjName)) {
			return adder.getAdditionalValues(cls);
		} else {
			return getClassValues(cls);
		}
	}

	private List<String[]> getClassValues(ClassContent cls) {
		String name = cls.getClassName();
		MapperInterface<?> mapper = mappers.getMapperOf(name);
		if (mapper == null) {
			return Collections.emptyList();
		}
		List<?> list = mapper.listAll();
		if (list.isEmpty()) {
			return Collections.emptyList();
		}
		Class<?> type = list.get(0).getClass();
		Joint joint = joints.computeIfAbsent(name, e -> createJoint(cls, type));
		List<String[]> recs = new ArrayList<>();
		int n = joint.getters.length;
		for (Object rec : list) {
			String[] flds = new String[n];
			for (int i = 0; i < n; i++) {
				if (joint.fields[i].getFieldName().equals(AdditionalInformation.LAST_UPDATED_AT)) {
					flds[i] = sqlHelper.getNow();
				} else {
					flds[i] = getConstant(rec, joint, i);
				}
			}
			recs.add(flds);
		}
		return recs;
	}

	private Joint createJoint(ClassContent cls, Class<?> type) {
		List<FieldContent> list = new ArrayList<>();
		RichIterable.from(getProperties(cls)).into(list);
		FieldContent[] fields = list.toArray(new FieldContent[list.size()]);
		int n = fields.length;
		Method[] getters = new Method[n];
		for (int i = 0; i < n; i++) {
			String getterName = extenders.getFieldExtender().getGetterName(fields[i]);
			try {
				getters[i] = type.getMethod(getterName);
			} catch (NoSuchMethodException|SecurityException err) {
				LOGGER.warn(err, "getter %s not found.", getterName);
				getters[i] = null;
			}
		}
		return new Joint(fields, getters);
	}

	private String getConstant(Object rec, Joint joint, int index) {
		Object value;
		Method m = joint.getters[index];
		try {
			if (m == null) {
				value = null;
			} else {
				value = m.invoke(rec);
			}
		} catch (IllegalAccessException|IllegalArgumentException|InvocationTargetException err) {
			LOGGER.error(err, "getter %s invoke error.", m.getName());
			throw new RuntimeException(err);
		}
		return toSqlConstants(value, joint.fields[index]);
	}

	private String toSqlConstants(Object value, FieldContent fld) {
		String result;
		switch (fld.getType()) {
		case BOOLEAN:
			if (value != null) {
				result = sqlHelper.getBooleanValue((Boolean) value);
			} else if (!fld.isNullable()) {
				result = sqlHelper.getBooleanValue(false);
			} else {
				result = sqlHelper.getNullValue();
			}
			break;
		case KEY:
		case INTEGER:
		case REAL:
		case NUMERIC:
			if (value != null) {
				result = String.valueOf(value);
			} else if (!fld.isNullable()) {
				result = "0";
			} else {
				result = sqlHelper.getNullValue();
			}
			break;
		case DATE:
			if (value != null) {
				result = sqlHelper.getDateValue((Date)value);
			} else if (!fld.isNullable()) {
				result = "0";
			} else {
				result = sqlHelper.getNullValue();
			}
			break;
		case STRING:
			if (value != null) {
				result = sqlHelper.quote((String) value);
			} else if (!fld.isNullable()) {
				result = sqlHelper.quote("");
			} else {
				result = sqlHelper.getNullValue();
			}
			break;
		case ENUM:
			EnumContent enc = mappers.getEnumMapper().get(fld.getErefEnumId());
			if (value != null) {
				if (enc.isEncodeString()) {
					@SuppressWarnings("unchecked")
					Encodable<String> s = (Encodable<String>) value;
					result = sqlHelper.quote(s.encode());
				} else {
					@SuppressWarnings("unchecked")
					Encodable<Integer> i = (Encodable<Integer>) value;
					result = String.valueOf(i.encode());
				}
			} else if (!fld.isNullable()) {
				if (enc.isEncodeString()) {
					result = sqlHelper.quote("");
				} else {
					result = "0";
				}
			} else {
				result = sqlHelper.getNullValue();
			}
			break;
		default:
			result = "0";
			break;
		}
		return result;
	}

	public ExprInfo referExpr(ClassContent cls, String exprString, int codeType) {
		return referExpr(cls, Collections.emptyList(), exprString, codeType);
	}

	public ExprInfo referExpr(ClassContent cls,
			FieldContent ctxRef, String exprString, int codeType) {
		return referExpr(cls, Collections.singletonList(ctxRef), exprString, codeType);
	}

	public ExprInfo referExpr(ClassContent cls,
			List<FieldContent> ctxRefs, String exprString, int codeType) {
		ExprEntry entry = entries.computeIfAbsent(
				cls.getClassId(), id -> new ExprEntry());
		String key;
		final ClassContent ctx;
		if (ctxRefs.isEmpty()) {
			key = exprString;
			ctx = cls;
		} else {
			int n = ctxRefs.size();
			ctxRefs.subList(0, n - 1).forEach(r -> {
				Asserts.require(r.getType() == DataType.REF);
			});
			ctxRefs.subList(n - 1, n).forEach(r -> {
				Asserts.require(r.getType() == DataType.REF || r.getType() == DataType.ENUM);
			});
			StringBuilder sb = new StringBuilder();
			ClassContent ctxTmp = null;
			for (FieldContent ctxRef : ctxRefs) {
				ctxTmp = getReferClass(ctxRef);
				sb.append(ctxTmp.getClassName());
				sb.append("#");
				sb.append(ctxRef.getFieldName());
				sb.append(".");
			}
			sb.append(exprString);
			key = sb.toString();
			ctx = ctxTmp;
		}
		return entry.exprs.computeIfAbsent(
				key, k -> createExpr(ctx, ctxRefs, exprString, codeType, entry));
	}

	private ExprInfo createExpr(ClassContent ctx,
			List<FieldContent> ctxRefs, String exprString, int codeType, ExprEntry entry) {
		try {
			ExprNode exprNode = parser.parse(exprString, codeType);
			ProjectContent prj = mappers.getClassMapper().getOwner(ctx);
			Expression expr = builder.build(exprNode, prj, ctx);
			ExprInfo info = new ExprInfo(expr);
			expr.accept(e -> visitExpr(e, ctxRefs, info, entry.joinMap));
			return info;
		} catch (ParseException err) {
			LOGGER.error(err, "EXPR is {%s}", exprString);
			throw new RuntimeException(err);
		}
	}

	private void visitExpr(Expression expr,
			List<FieldContent> ctxRefs, ExprInfo info, Map<List<FieldContent>, Integer> joinMap) {
		if (expr instanceof Operation) {
			visitOperation(expr, ctxRefs, joinMap);
		} else if (expr instanceof Argument) {
			visitArgument(expr, info);
		}
	}

	private void visitOperation(Expression expr,
			List<FieldContent> ctxRefs, Map<List<FieldContent>, Integer> joinMap) {
		Operation op = (Operation) expr;
		//フィールド参照Operation以外は無視
		if (op.getCode() != Operation.Code.GET) {
			return;
		}
		List<Expression> operands = op.getOperands();
		for (Expression c : operands) {
			if (!(c instanceof FieldRef)) {
				return;
			}
		}
		//join番号の参照（又は存在しない場合新たに定義）
		int n = operands.size();
		List<FieldContent> flds = new ArrayList<>();
		if (!ctxRefs.isEmpty()) {
			flds.addAll(ctxRefs);
		}
		Integer no = 1;
		for (int i = 0; i < n; i++) {
			FieldContent fld = ((FieldRef)operands.get(i)).getContent();
			flds.add(fld);
			int m = flds.size();
			if (m > 1) {
				List<FieldContent> subList = new ArrayList<>(flds.subList(0, m - 1));
				int size = joinMap.size();
				no = joinMap.computeIfAbsent(subList, s -> size + 2);
			}
		}
		//opにjoin番号を設定
		op.setJoinNo(no);
	}

	private void visitArgument(Expression expr, ExprInfo info) {
		//引数を疑似フィールドとして格納
		Argument arg = (Argument) expr;
		FieldContent fld = new FieldContent();
		fld.setFieldName(arg.getName());
		fld.setType(arg.getType());
		fld.setWidth(arg.getWidth());
		fld.setScale(arg.getScale());
		if (fld.getType() == DataType.ENUM) {
			fld.setErefEnumId(arg.getErefId());
		}
		info.getArguments().add(fld);
	}

	public List<JoinInfo> getJoins(ClassContent cls) {
		ExprEntry entry = entries.get(cls.getClassId());
		if (entry == null || entry.joinMap.isEmpty()) {
			return Collections.emptyList();
		}
		return toJoins(entry.joinMap);
	}

	public List<JoinInfo> toJoins(Map<List<FieldContent>, Integer> joinMap) {
		List<JoinInfo> result = new ArrayList<>();
		for (Map.Entry<List<FieldContent>, Integer> e : joinMap.entrySet()) {
			List<FieldContent> fields = e.getKey();
			int n = fields.size();
			FieldContent lastField = fields.get(n - 1);
			int lastNo = n <= 1 ? 1 : joinMap.get(fields.subList(0, n - 1));
			ClassContent nextClass = getReferClass(lastField);
			int nextNo = e.getValue();
			result.add(new JoinInfo(lastField, lastNo, nextClass, nextNo));
		}
		return result;
	}

	public ClassRelation getRelation(ClassContent cls) {
		ProjectContent prj = mappers.getClassMapper().getOwner(cls);
		Map<Integer, ClassRelation> map = extenders.getProjectExtender().getRelationMap(prj);
		return map.get(cls.getClassId());

	}

	public static class TopicInfo {
		private ClassContent topicClass;
		private int topicNo;
		private TitleInfo title;
		private TopicInfo(ClassContent topicClass, int topicNo, TitleInfo title) {
			this.topicClass = topicClass;
			this.topicNo = topicNo;
			this.title = title;
		}
		public ClassContent getTopicClass() {
			return topicClass;
		}
		public int getTopicNo() {
			return topicNo;
		}
		public TitleInfo getTitle() {
			return title;
		}
	}

	public List<TopicInfo> getTopics(ClassContent cls) {
		ClassRelation rel = getRelation(cls);
		if (rel == null) {
			return Collections.emptyList();
		}
		List<TopicInfo> result = new ArrayList<>();
		String name = "_title";
		String title = "タイトル";
		String titleExpr = cls.getTitleExpr();
		ExprInfo info = referExpr(cls, titleExpr, ExprParser.CODE_TYPE_SELECT);
		TitleInfo titleInfo = new TitleInfo(name, title, info);
		int no = 1;
		result.add(new TopicInfo(cls, no++, titleInfo));
		List<FieldContent> refs = new ArrayList<>();
		refs.add(rel.getOwnerRefField());
		rel = rel.getOwner();
		while (rel != null) {
			ClassContent c = rel.getContent();
			titleExpr = c.getTitleExpr();
			info = referExpr(cls, refs, titleExpr, ExprParser.CODE_TYPE_SELECT);
			titleInfo = new TitleInfo(name, title, info);
			result.add(new TopicInfo(c, no++, titleInfo));
			refs.add(rel.getOwnerRefField());
			rel = rel.getOwner();
		}
		return result;
	}

}
