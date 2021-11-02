package com.github.vvorks.builder.server.controller;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.springframework.beans.factory.annotation.Autowired;

import com.github.vvorks.builder.server.common.net.annotation.JsonRpcController;
import com.github.vvorks.builder.server.common.net.annotation.JsonRpcMethod;
import com.github.vvorks.builder.server.common.net.annotation.JsonRpcParam;
import com.github.vvorks.builder.server.component.DebugWriter;
import com.github.vvorks.builder.server.component.JavaTextWriter;
import com.github.vvorks.builder.server.domain.ClassContent;
import com.github.vvorks.builder.server.domain.ProjectContent;
import com.github.vvorks.builder.server.expression.Expression;
import com.github.vvorks.builder.server.grammar.ExprNode;
import com.github.vvorks.builder.server.grammar.ExprParser;
import com.github.vvorks.builder.server.grammar.ExpressionBuilder;
import com.github.vvorks.builder.server.grammar.ParseException;
import com.github.vvorks.builder.server.mapper.ProjectMapper;

@JsonRpcController("/rpc")
public class RpcController {

	@Autowired
	private ExprParser parser;

	@Autowired
	private ExpressionBuilder builder;

	@Autowired
	private ProjectMapper projectMapper;

//	@Autowired
//	private JsonRpcServer server;

	@JsonRpcMethod("subtract")
	public int test(@JsonRpcParam("x") int x, @JsonRpcParam("y") int y) {
		return x - y;
	}

	@JsonRpcMethod("parse")
	public String parse(@JsonRpcParam("code") String code) {
		try {
			ExprNode result = parser.parse(code);
			//test
			ProjectContent prj = projectMapper.listIfNameIsContent("com.github.vvorks.builder", 0, 0).get(0);
			ClassContent cls = projectMapper.listClassesIfNameIsContent(prj, "Field", 0, 0).get(0);
			Expression exp = builder.build(result, prj, cls);
			//test
			String r = exp.accept(new JavaTextWriter(), null);
			String d = exp.accept(new DebugWriter(), 0);
			return r + "\n\n" + d + "\n\n" + result.dump(" ", 0);
		} catch (ParseException err) {
			StringWriter out = new StringWriter();
			PrintWriter writer = new PrintWriter(out);
			err.printStackTrace(writer);
			return out.toString();
		}
	}

}
