package com.github.vvorks.builder.server.extender;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Deque;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.github.vvorks.builder.common.lang.Encodable;
import com.github.vvorks.builder.common.lang.Iterables;
import com.github.vvorks.builder.common.lang.Strings;
import com.github.vvorks.builder.common.logging.Logger;
import com.github.vvorks.builder.server.common.sql.SqlHelper;
import com.github.vvorks.builder.server.domain.ClassContent;
import com.github.vvorks.builder.server.domain.DataType;
import com.github.vvorks.builder.server.domain.EnumContent;
import com.github.vvorks.builder.server.domain.EnumValueContent;
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
import com.github.vvorks.builder.server.mapper.ClassMapper;
import com.github.vvorks.builder.server.mapper.EnumMapper;
import com.github.vvorks.builder.server.mapper.EnumValueMapper;
import com.github.vvorks.builder.server.mapper.FieldMapper;
import com.github.vvorks.builder.server.mapper.ProjectMapper;
import com.github.vvorks.builder.server.mapper.QueryMapper;

@Component
public class ClassExtender {

	private static final Logger LOGGER = Logger.createLogger(ClassExtender.class);

	public static final String LAST_UPDATED_AT = "_lastUpdatedAt";

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
		private final Map<List<FieldContent>, Integer> joinMap;
		private final List<FieldContent> arguments;
		public ExprInfo(Expression expr, Map<List<FieldContent>, Integer> entryJoinMap) {
			this.expr = expr;
			this.joinMap = new LinkedHashMap<>(entryJoinMap);
			this.arguments = new ArrayList<>();
		}
		public Expression getExpr() {
			return expr;
		}
		public List<FieldContent> getArguments() {
			return arguments;
		}
		public Map<List<FieldContent>, Integer> getJoinMap() {
			return joinMap;
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
				return info.expr.accept(sqlWriter, null);
			} else {
				return "''";
			}
		}
	}

	private static final EnumSet<DataType> SCALER_TYPES = EnumSet.of(
			DataType.KEY,
			DataType.ENUM,
			DataType.BOOLEAN,
			DataType.INTEGER,
			DataType.REAL,
			DataType.NUMERIC,
			DataType.DATE,
			DataType.STRING);

	@Autowired
	private ProjectMapper projectMapper;

	@Autowired
	private ClassMapper classMapper;

	@Autowired
	private FieldMapper fieldMapper;

	@Autowired
	private QueryMapper queryMapper;

	@Autowired
	private EnumMapper enumMapper;

	@Autowired
	private EnumValueMapper enumValueMapper;

	@Autowired
	private FieldExtender fieldExtender;

	@Autowired
	private ExprParser parser;

	@Autowired
	private ExpressionBuilder builder;

	private SqlWriter sqlWriter = SqlWriter.getWriter();

	private SqlHelper sqlHelper = SqlHelper.getHelper();

	private final Map<Integer, ExprEntry> entries = new LinkedHashMap<>();

	/**
	 * （再）初期化
	 */
	public ClassExtender init() {
		entries.clear();
		for (ClassContent cls : classMapper.listContent(0, 0)) {
			String titleExpr = cls.getTitleExpr();
			if (!Strings.isEmpty(titleExpr)) {
				referExpr(cls, titleExpr, ExprParser.CODE_TYPE_SELECT);
			}
			String orderExpr = cls.getOrderExpr();
			if (!Strings.isEmpty(orderExpr)) {
				referExpr(cls, orderExpr, ExprParser.CODE_TYPE_ORDER);
			}
			for (FieldContent fld : getRefs(cls)) {
				ClassContent ref = fieldExtender.getCref(fld);
				String refExpr = ref.getTitleExpr();
				if (!Strings.isEmpty(refExpr)) {
					referExpr(cls, fld, refExpr, ExprParser.CODE_TYPE_SELECT);
				}
			}
			for (QueryContent q : classMapper.listQueriesContent(cls, 0, 0)) {
				String filter = q.getFilter();
				if (!Strings.isEmpty(filter)) {
					referExpr(cls, filter, ExprParser.CODE_TYPE_WHERE);
				}
			}
		}
		return this;
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

	public String getTableName(ClassContent cls) {
		return SqlWriter.TABLE_PREFIX + Strings.toUpperSnake(cls.getClassName());
	}

	public String getSqlOrder(ClassContent cls) {
		String orderExpr = cls.getOrderExpr();
		if (Strings.isEmpty(orderExpr)) {
			return null;
		}
		ExprInfo info = referExpr(cls, orderExpr, ExprParser.CODE_TYPE_ORDER);
		String result = info.expr.accept(sqlWriter, null);
		return result;
	}

	public List<TitleInfo> getTitles(ClassContent cls) {
		List<TitleInfo> result = new ArrayList<>();
		//本クラスのタイトルフィールド
		String titleExpr = cls.getTitleExpr();
		String name = "_title";
		String title = "タイトル";
		ExprInfo info;
		if (!Strings.isEmpty(titleExpr)) {
			info = referExpr(cls, titleExpr, ExprParser.CODE_TYPE_SELECT);
		} else {
			info = null;
		}
		result.add(new TitleInfo(name, title, info));
		//参照フィールドのタイトルフィールド
		for (FieldContent fld : getRefs(cls)) {
			name = fld.getFieldName() + "_title";
			title = fieldExtender.getTitleOrName(fld) + "のタイトル";
			ClassContent ref = fieldExtender.getCref(fld);
			String refExpr = ref.getTitleExpr();
			if (!Strings.isEmpty(refExpr)) {
				info = referExpr(cls, fld, refExpr, ExprParser.CODE_TYPE_SELECT);
			} else {
				info = null;
			}
			result.add(new TitleInfo(name, title, info));
		}
		return result;
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
				fieldExtender.extractKey(field, stack, props);
			}
		}
		return props;
	}

	public List<FieldContent> getRefs(ClassContent cls) {
		return getFields(cls, fld -> fld.getType() == DataType.REF);
	}

	public List<FieldContent> getSets(ClassContent cls) {
		return getFields(cls, fld -> fld.getType() == DataType.SET);
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
		List<FieldContent> fields = classMapper.listFieldsContent(cls, 0, 0);
		//追加フィールド挿入
		FieldContent lastUpdated = new FieldContent();
		lastUpdated.setFieldName(LAST_UPDATED_AT);
		lastUpdated.setTitle("最終更新時刻");
		lastUpdated.setType(DataType.DATE);
		lastUpdated.setPk(false);
		lastUpdated.setNeedsMax(true);
		fields.add(lastUpdated);
		return fields;
	}

	public List<QueryContent> getQueries(ClassContent cls) {
		return classMapper.listQueriesContent(cls, 0, 0);
	}

	private static class Joint {
		private final Class<?> type;
		private final Function<ClassExtender, List<?>> func;
		private FieldContent[] fields;
		private Method[] getters;
		private Joint(Class<?> type, Function<ClassExtender, List<?>> func) {
			this.type = type;
			this.func = func;
			this.fields = null;
			this.getters = null;
		}
		private boolean hasGetters() {
			return getters != null;
		}
	}

	private static final Map<String, Joint> JOINT_MAP = new HashMap<>();
	static {
		JOINT_MAP.put("Project", new Joint(ProjectContent.class, me -> me.projectMapper.listContent(0, 0)));
		JOINT_MAP.put("Class", new Joint(ClassContent.class, me -> me.classMapper.listContent(0, 0)));
		JOINT_MAP.put("Field", new Joint(FieldContent.class, me -> me.fieldMapper.listContent(0, 0)));
		JOINT_MAP.put("Query", new Joint(QueryContent.class, me -> me.queryMapper.listContent(0, 0)));
		JOINT_MAP.put("Enum", new Joint(EnumContent.class, me -> me.enumMapper.listContent(0, 0)));
		JOINT_MAP.put("EnumValue", new Joint(EnumValueContent.class, me -> me.enumValueMapper.listContent(0, 0)));
	}

	public List<String[]> getValues(ClassContent cls) {
		String name = cls.getClassName();
		Joint joint = JOINT_MAP.get(name);
		if (joint == null) {
			return Collections.emptyList();
		}
		if (!joint.hasGetters()) {
			setupJoint(joint, cls);
		}
		List<String[]> recs = new ArrayList<>();
		List<?> list = joint.func.apply(this);
		int n = joint.getters.length;
		for (Object rec : list) {
			String[] flds = new String[n];
			for (int i = 0; i < n; i++) {
				if (joint.fields[i].getFieldName().equals(LAST_UPDATED_AT)) {
					flds[i] = sqlHelper.getNow();
				} else {
					flds[i] = getConstant(rec, joint, i);
				}
			}
			recs.add(flds);
		}
		return recs;
	}

	private void setupJoint(Joint joint, ClassContent cls) {
		List<FieldContent> list = new ArrayList<>();
		Iterables.addAll(list, getProperties(cls));
		FieldContent[] fields = list.toArray(new FieldContent[list.size()]);
		int n = fields.length;
		Method[] getters = new Method[n];
		for (int i = 0; i < n; i++) {
			String getterName = fieldExtender.getGetterName(fields[i]);
			try {
				getters[i] = joint.type.getMethod(getterName);
			} catch (NoSuchMethodException|SecurityException err) {
				LOGGER.warn(err, "getter %s not found.", getterName);
				getters[i] = null;
			}
		}
		joint.fields = fields;
		joint.getters = getters;
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
			EnumContent enc = enumMapper.get(fld.getErefEnumId());
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
		return referExpr(cls, null, exprString, codeType);
	}

	public ExprInfo referExpr(ClassContent cls,
			FieldContent ctxRef, String exprString, int codeType) {
		ExprEntry entry = entries.computeIfAbsent(
				cls.getClassId(), id -> new ExprEntry());
		String key;
		ClassContent ctx;
		if (ctxRef != null) {
			key = ctxRef.getFieldName() + ":" + exprString;
			ctx = fieldExtender.getCref(ctxRef);
		} else {
			key = exprString;
			ctx = cls;
		}
		return entry.exprs.computeIfAbsent(
				key, k -> createExpr(ctx, ctxRef, exprString, codeType, entry));
	}

	private ExprInfo createExpr(ClassContent ctx,
			FieldContent ctxRef, String exprString, int codeType, ExprEntry entry) {
		try {
			ExprNode exprNode = parser.parse(exprString, codeType);
			ProjectContent prj = classMapper.getOwner(ctx);
			Expression expr = builder.build(exprNode, prj, ctx);
			ExprInfo info = new ExprInfo(expr, entry.joinMap);
			//exprType == 1はQueryでしか使用されないため、判定に流用
			Map<List<FieldContent>, Integer> joinMap =
					(codeType == 1) ? info.joinMap : entry.joinMap;
			expr.accept(e -> visitExpr(e, ctxRef, info, joinMap));
			return info;
		} catch (ParseException err) {
			throw new RuntimeException(err);
		}
	}

	private void visitExpr(Expression expr,
			FieldContent ctxRef, ExprInfo info, Map<List<FieldContent>, Integer> joinMap) {
		if (expr instanceof Operation) {
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
			//演算結果タイプがSCALER型でない場合は無視
			int n = operands.size();
			FieldRef last = (FieldRef) operands.get(n - 1);
			if (!SCALER_TYPES.contains(last.getType())) {
				return;
			}
			//join番号の参照（又は存在しない場合新たに定義）
			List<FieldContent> flds = new ArrayList<>();
			if (ctxRef != null) {
				flds.add(ctxRef);
			}
			Integer no = 1;
			for (int i = 0; i < n; i++) {
				FieldContent fld = ((FieldRef)operands.get(i)).getContent();
				flds.add(fld);
				int m = flds.size();
				if (m > 1) {
					List<FieldContent> subList = flds.subList(0, m - 1);
					no = joinMap.computeIfAbsent(subList, s -> joinMap.size() + 2);
				}
			}
			//opにjoin番号を設定
			op.setJoinNo(no);
		} else if (expr instanceof Argument) {
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
			info.arguments.add(fld);
		}
	}

	public List<JoinInfo> getJoins(ClassContent cls) {
		ExprEntry entry = entries.get(cls.getClassId());
		if (entry == null) {
			LOGGER.debug("what?");
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
			ClassContent nextClass = fieldMapper.getCref(lastField);
			int nextNo = e.getValue();
			result.add(new JoinInfo(lastField, lastNo, nextClass, nextNo));
		}
		return result;
	}

}
