package com.github.vvorks.builder.server.grammar;

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

	private void dump(String prefix, int depth, StringBuilder into) {
		into.append(Strings.repeat(prefix, depth));
		into.append(this.toString());
		into.append("\n");
		if (children != null) {
			for (int i = 0; i < children.length; i++) {
				ExprNode n = (ExprNode) children[i];
				if (n != null) {
					n.dump(prefix, depth + 1, into);
				}
			}
		}
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(ExprParserTreeConstants.jjtNodeName[id]);
		sb.append("(");
		ExprToken t = (ExprToken) this.firstToken;
		sb.append(t.image);
		while (t != this.lastToken) {
			t = (ExprToken) t.next;
			sb.append(", ");
			sb.append(t.image);
		}
		sb.append(")");
		return sb.toString();
	}

}
