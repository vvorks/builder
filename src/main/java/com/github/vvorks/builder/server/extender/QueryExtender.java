package com.github.vvorks.builder.server.extender;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.github.vvorks.builder.server.domain.ClassContent;
import com.github.vvorks.builder.server.domain.FieldContent;
import com.github.vvorks.builder.server.domain.QueryContent;
import com.github.vvorks.builder.server.grammar.ExprParser;
import com.github.vvorks.builder.server.mapper.QueryMapper;
import com.github.vvorks.builder.shared.common.lang.Strings;

@Component
public class QueryExtender {

	private SqlWriter sqlWriter = SqlWriter.get();

	@Autowired
	private QueryMapper queryMapper;

	@Autowired
	private Extenders extenders;

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

	public List<ClassExtender.JoinInfo> getJoins(QueryContent q) {
		return extenders.getClassExtender().getJoins(queryMapper.getOwner(q));
	}

	public String getSqlExpr(QueryContent q) {
		ClassContent cls = queryMapper.getOwner(q);
		ClassExtender.ExprInfo info = extenders.getClassExtender().referExpr(
				cls, q.getFilter(), ExprParser.CODE_TYPE_WHERE);
		return info.getExpr().accept(sqlWriter, extenders);
	}

	public List<FieldContent> getArguments(QueryContent q) {
		ClassContent cls = queryMapper.getOwner(q);
		ClassExtender.ExprInfo info = extenders.getClassExtender().referExpr(
				cls, q.getFilter(), ExprParser.CODE_TYPE_WHERE);
		return info.getArguments();
	}

}
