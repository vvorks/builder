package com.github.vvorks.builder.server.extender;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.github.vvorks.builder.common.lang.Factory;
import com.github.vvorks.builder.common.lang.Strings;
import com.github.vvorks.builder.common.util.CacheMap;
import com.github.vvorks.builder.common.util.Logger;
import com.github.vvorks.builder.server.domain.ClassContent;
import com.github.vvorks.builder.server.domain.DataType;
import com.github.vvorks.builder.server.domain.FieldContent;
import com.github.vvorks.builder.server.domain.ProjectContent;
import com.github.vvorks.builder.server.domain.QueryContent;
import com.github.vvorks.builder.server.expression.Expression;
import com.github.vvorks.builder.server.expression.FieldRef;
import com.github.vvorks.builder.server.expression.Operation;
import com.github.vvorks.builder.server.grammar.ExprNode;
import com.github.vvorks.builder.server.grammar.ExprParser;
import com.github.vvorks.builder.server.grammar.ExpressionBuilder;
import com.github.vvorks.builder.server.grammar.ParseException;
import com.github.vvorks.builder.server.mapper.ClassMapper;
import com.github.vvorks.builder.server.mapper.FieldMapper;
import com.github.vvorks.builder.server.mapper.QueryMapper;

@Component
public class QueryExtender {

	private static final Class<?> THIS = QueryExtender.class;
	@SuppressWarnings("unused")
	private static final Logger LOGGER = Factory.newInstance(Logger.class, THIS);

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

	public static class FilterInfo {
		private final Expression expr;
		private final Map<List<FieldContent>, Integer> joins;
		public FilterInfo(Expression expr) {
			this.expr = expr;
			this.joins = new LinkedHashMap<>();
		}
	}

	private Map<Integer, FilterInfo> cache = new CacheMap<>();

	@Autowired
	private ExprParser parser;

	@Autowired
	private QueryMapper queryMapper;

	@Autowired
	private FieldMapper fieldMapper;

	@Autowired
	private ClassMapper classMapper;

	@Autowired
	private ExpressionBuilder builder;

	private SqlWriter sqlWriter = new SqliteWriter();

	public String getTitleOrName(QueryContent q) {
		if (!Strings.isEmpty(q.getTitle())) {
			return q.getTitle();
		} else {
			return q.getQueryName();
		}
	}

	public String getUpperName(QueryContent q) {
		return Strings.toFirstUpper(q.getQueryName());
	}

	public List<JoinInfo> getJoins(QueryContent q) {
		FilterInfo item = referFilterInfo(q);
		List<JoinInfo> result = new ArrayList<>();
		for (Map.Entry<List<FieldContent>, Integer> e : item.joins.entrySet()) {
			List<FieldContent> fields = e.getKey();
			int n = fields.size();
			FieldContent lastField = fields.get(n - 1);
			int lastNo = n <= 1 ? 1 : item.joins.get(fields.subList(0, n - 1));
			ClassContent nextClass = fieldMapper.getCref(lastField);
			int nextNo = e.getValue();
			result.add(new JoinInfo(lastField, lastNo, nextClass, nextNo));
		}
		return result;
	}

	private FilterInfo referFilterInfo(QueryContent q) {
		try {
			FilterInfo item = cache.get(q.getQueryId());
			if (item == null) {
				item = createFilterInfo(q);
				cache.put(null, item);
			}
			return item;
		} catch (ParseException err) {
			throw new RuntimeException(err);
		}
	}

	private FilterInfo createFilterInfo(QueryContent q) throws ParseException {
		String exprString = q.getFilter();
		ExprNode exprNode = parser.parse(exprString);
		ClassContent cls = queryMapper.getOwner(q);
		ProjectContent prj = classMapper.getOwner(cls);
		Expression expr = builder.build(exprNode, prj, cls);
		FilterInfo item = new FilterInfo(expr);
		expr.accept(e -> numberJoin(e, item));
		return item;
	}

	private static final EnumSet<DataType> SCALER_TYPES = EnumSet.of(
			DataType.KEY,
			DataType.ENUM,		//TODO 要確認
			DataType.BOOLEAN,
			DataType.INTEGER,
			DataType.REAL,
			DataType.NUMERIC,
			DataType.DATE,
			DataType.STRING);

	private void numberJoin(Expression expr, FilterInfo info) {
		//Operation以外は無視
		if (!(expr instanceof Operation)) {
			return;
		}
		Operation op = (Operation) expr;
		//フィールド参照Operation以外は無視
		List<Expression> operands = op.getOperands();
		for (Expression c : operands) {
			if (!(c instanceof FieldRef)) {
				return;
			}
		}
		int n = operands.size();
		FieldRef last = (FieldRef) operands.get(n - 1);
		if (!SCALER_TYPES.contains(last.getType())) {
			return;
		}
		//join エントリーを作成
		List<FieldContent> flds = new ArrayList<>();
		Integer no = 1;
		for (int i = 0; i < n - 1; i++) {
			FieldContent fld = ((FieldRef)operands.get(i)).getContent();
			flds.add(fld);
			List<FieldContent> subList = flds.subList(0, i+1);
			no = info.joins.get(subList);
			if (no == null) {
				no = info.joins.size() + 2;
				info.joins.put(subList, no);
			}
		}
		op.setJoinNo(no);
	}

	public String getSqlExpr(QueryContent q) {
		FilterInfo item = referFilterInfo(q);
		return item.expr.accept(sqlWriter, null);
	}

}
