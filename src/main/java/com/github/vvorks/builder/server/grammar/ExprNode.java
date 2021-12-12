package com.github.vvorks.builder.server.grammar;

import java.util.Collections;
import java.util.stream.Stream;

import com.github.vvorks.builder.common.lang.Strings;

public class ExprNode extends SimpleNode {

	public ExprNode(ExprParser p, int i) {
		super(p, i);
	}

	public ExprNode(int i) {
		super(i);
	}

	public String dump(String prefix, int depth) {
		StringBuilder sb = new StringBuilder();
		dump(prefix, depth, sb);
		return sb.toString();
	}

	public int size() {
		return children != null ? children.length : 0;
	}

	public ExprNode getChild(int index) {
		if (!(children != null && 0 <= index && index < children.length)) {
			return null;
		}
		return (ExprNode) children[index];
	}

	public Iterable<ExprNode> getChildren() {
		if (children == null) {
			return Collections.emptyList();
		} else {
			return () -> Stream.of(children)
					.filter(e -> e != null)
					.map(e -> (ExprNode) e)
					.iterator();
		}
	}

	private void dump(String prefix, int depth, StringBuilder into) {
		into.append(Strings.repeat(prefix, depth));
		into.append(this.toString());
		into.append("\n");
		for (ExprNode c : getChildren()) {
			c.dump(prefix, depth + 1, into);
		}
	}

	public String getImages() {
		StringBuilder sb = new StringBuilder();
		ExprToken t = (ExprToken) this.firstToken;
		sb.append(t.image);
		while (t != this.lastToken) {
			t = (ExprToken) t.next;
			sb.append(t.image);
		}
		return sb.toString();
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(ExprParserTreeConstants.jjtNodeName[id]);
		sb.append("(");
		sb.append(getImages());
		sb.append(")");
		return sb.toString();
	}

}
