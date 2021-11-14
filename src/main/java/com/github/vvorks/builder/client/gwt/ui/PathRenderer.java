package com.github.vvorks.builder.client.gwt.ui;

import com.google.gwt.canvas.dom.client.Context2d;

/**
 * SVGのPATH文字列をCANVASに描画するためのクラス
 */
public class PathRenderer {

	private static final char EOL = 0;
	private static final String CMDS = "MmZzLlHhVvCcSsQqTtAa";
	private static final String SEPS = ", \t\r\n\f";

	private Context2d con;
	private String path;
	private int pos;
	private int len;
	private double baseX;
	private double baseY;
	private double lastX;
	private double lastY;
	private double prevX;
	private double prevY;
	private boolean relative;
	private boolean rendered;

	public void render(Context2d con, int x, int y, String path) {
		//引数からオブジェクト内部状態の初期化
		this.con = con;
		baseX = lastX = prevX = x;
		baseY = lastY = prevY = y;
		this.path = path;
		this.pos = 0;
		this.len = path.length();
		//コマンド解析とパス描画処理
		con.beginPath();
		char cmd = readCommand();
		while (cmd != EOL) {
			setRelative('a' <= cmd && cmd <= 'z');
			switch(cmd) {
			case 'M':	case 'm':		//Move To
				moveTo();
				break;
			case 'Z':	case 'z':		//Close Path
				closePath();
				break;
			case 'L':	case 'l':		//Line To
				lineTo();
				break;
			case 'H':	case 'h':		//Horizontal Line To
				horzTo();
				break;
			case 'V':	case 'v':		//Vertical Line To
				vertTo();
				break;
			case 'C':	case 'c':		//Cubic Curve To（三次ベジェ曲線）
				curveTo();
				break;
			case 'S':	case 's':		//Cubic Curve To（三次ベジェ曲線）※連続描画時短縮指定
				jointCurveTo();
				break;
			case 'Q':	case 'q':		//Quadratic Curve To（二次ベジェ曲線）
				quadTo();
				break;
			case 'T':	case 't':		//Quadratic Curve To（二次ベジェ曲線）※連続描画時短縮指定
				jointQuadTo();
				break;
			case 'A':	case 'a':		//Arc To
				arcTo();
				break;
			default:
				break;
			}
			cmd = readCommand();
		}
		if (rendered) {
			con.stroke();
		}
	}

	private void moveTo() {
		double x1 = readX();
		while (!Double.isNaN(x1)) {
			double y1 = readY();
			con.moveTo(x1, y1);
			rendered = true;
			prevX = lastX = x1;
			prevY = lastY = y1;
			x1 = readX();
		}
	}

	private void closePath() {
		con.fill();
		con.beginPath();
		rendered = false;
	}

	private void lineTo() {
		double x1 = readX();
		while (!Double.isNaN(x1)) {
			double y1 = readY();
			con.lineTo(x1, y1);
			rendered = true;
			prevX = lastX = x1;
			prevY = lastY = y1;
			x1 = readX();
		}
	}

	private void horzTo() {
		double x1 = readX();
		double y1 = lastY;
		while (!Double.isNaN(x1)) {
			con.lineTo(x1, y1);
			rendered = true;
			prevX = lastX = x1;
			prevY = y1;
			x1 = readX();
		}
	}

	private void vertTo() {
		double y1 = readY();
		double x1 = lastX;
		while (!Double.isNaN(y1)) {
			con.lineTo(x1, y1);
			rendered = true;
			prevX = x1;
			prevY = lastY = y1;
			y1 = readY();
		}
	}

	private void curveTo() {
		double x1 = readX();
		while (!Double.isNaN(x1)) {
			double y1 = readY();
			double x2 = readX();
			double y2 = readY();
			double x3 = readX();
			double y3 = readY();
			con.bezierCurveTo(x1, y1, x2, y2, x3, y3);
			rendered = true;
			prevX = x2;
			lastX = x3;
			prevY = y2;
			lastY = y3;
			x1 = readX();
		}
	}

	private void jointCurveTo() {
		double x2 = readX();
		while (!Double.isNaN(x2)) {
			double xt = lastX * 2 - prevX;
			double yt = lastY * 2 - prevY;
			double y2 = readY();
			double x3 = readX();
			double y3 = readY();
			con.bezierCurveTo(xt, yt, x2, y2, x3, y3);
			rendered = true;
			prevX = x2;
			lastX = x3;
			prevY = y2;
			lastY = y3;
			x2 = readX();
		}
	}

	private void quadTo() {
		double x1 = readX();
		while (!Double.isNaN(x1)) {
			double y1 = readY();
			double x2 = readX();
			double y2 = readY();
			con.quadraticCurveTo(x1, y1, x2, y2);
			rendered = true;
			prevX = x1;
			lastX = x2;
			prevY = y1;
			lastY = y2;
			x1 = readX();
		}
	}

	private void jointQuadTo() {
		double x2 = readX();
		while (!Double.isNaN(x2)) {
			double xt = lastX * 2 - prevX;
			double yt = lastY * 2 - prevY;
			double y2 = readY();
			con.quadraticCurveTo(xt, yt, x2, y2);
			rendered = true;
			prevX = xt;
			lastX = x2;
			prevY = yt;
			lastY = y2;
			x2 = readX();
		}
	}

	private void arcTo() {
		//arcToは現状未実装。必要となったら（例えば本当にSVG描画が必要になったら）対応
		throw new UnsupportedOperationException();
	}

	private char readCommand() {
		char ch = readChar();
		while (!isEol(ch) && !isCommand(ch)) {
			ch = readChar();
		}
		return ch;
	}

	private void setRelative(boolean relative) {
		this.relative = relative;
	}

	private double readX() {
		return readDouble() + (relative ? lastX : baseX);
	}

	private double readY() {
		return readDouble() + (relative ? lastY : baseY);
	}

	private double readDouble() {
		char ch = readChar();
		while (!isEol(ch) && isSeparator(ch)) {
			ch = readChar();
		}
		int spos = pos - 1;
		while (!isEol(ch) && !isCommand(ch) && !isSeparator(ch)) {
			ch = readChar();
		}
		int epos = pos - 1;
		double result = spos < epos ? Double.parseDouble(path.substring(spos, epos)) : Double.NaN;
		if (isCommand(ch)) {
			unread();
		}
		return result;
	}

	private char readChar() {
		char result = pos < len ? path.charAt(pos) : EOL;
		pos++;
		return result;
	}

	private void unread() {
		pos--;
	}

	private static boolean isCommand(char ch) {
		return CMDS.indexOf(ch) >= 0;
	}

	private static boolean isSeparator(char ch) {
		return SEPS.indexOf(ch) >= 0;
	}

	private static boolean isEol(char ch) {
		return ch == EOL;
	}

}
