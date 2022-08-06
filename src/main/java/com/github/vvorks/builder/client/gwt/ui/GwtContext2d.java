package com.github.vvorks.builder.client.gwt.ui;

import java.util.ArrayList;
import java.util.List;

import com.github.vvorks.builder.client.common.ui.Colors;
import com.github.vvorks.builder.shared.common.lang.Strings;
import com.github.vvorks.builder.shared.common.logging.Logger;
import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.dom.client.CanvasElement;
import com.google.gwt.dom.client.ImageElement;
import com.google.gwt.user.client.ui.Image;

public class GwtContext2d {

	private static final Logger LOGGER = Logger.createLogger(GwtContext2d.class);

	/** 描画フラグ：中央寄せ */
	public static final int ALIGN_CENTER	= 0x00000000;

	/** 描画フラグ：左寄せ */
	public static final int ALIGN_LEFT		= 0x00000001;

	/** 描画フラグ：右寄せ */
	public static final int ALIGN_RIGHT		= 0x00000002;

	/** 描画フラグ：（単語ごとの）均等割付 */
	public static final int ALIGN_JUSTIFY	= 0x00000003;

	/** 描画フラグ：水平指定マスク */
	public static final int ALIGN_MASK		= 0x00000003;

	/** 描画フラグ：上下中央寄せ */
	public static final int VALIGN_MIDDLE	= 0x00000000;

	/** 描画フラグ：上寄せ */
	public static final int VALIGN_TOP		= 0x00000004;

	/** 描画フラグ：下寄せ */
	public static final int VALIGN_BOTTOM	= 0x00000008;

	/** 描画フラグ：上下均等割 */
	public static final int VALIGN_EXPAND	= 0x0000000C;

	/** 描画フラグ：垂直指定マスク */
	public static final int VALIGN_MASK		= 0x0000000C;

	/** 文字列描画フラグ：三点リーダー付与の有無 */
	public static final int DRAW_ELLIPSIS	= 0x00000010;

	/** 描画フラグ：左上寄せ */
	public static final int ALIGN_LEFTTOP		= ALIGN_LEFT|VALIGN_TOP;

	/** 描画フラグ：右下寄せ */
	public static final int ALIGN_RIGHTBOTTOM	= ALIGN_RIGHT|VALIGN_BOTTOM;

	/** 描画フラグ：中央 */
	public static final int ALIGN_CENTERMIDDLE	= ALIGN_CENTER|VALIGN_MIDDLE;

	/** 描画フラグ：中上寄せ */
	public static final int ALIGN_CENTERTOP		= ALIGN_CENTER|VALIGN_TOP;

	/** 0度のラジアン表現 */
	private static final double RAD0   = Math.PI * 0 / 4;

	/** 90度のラジアン表現 */
	private static final double RAD90  = Math.PI * 2 / 4;

	/** 180度のラジアン表現 */
	private static final double RAD180 = Math.PI * 4 / 4;

	/** 270度のラジアン表現 */
	private static final double RAD270 = Math.PI * 6 / 4;

	/** 360度のラジアン表現 */
	private static final double RAD360 = Math.PI * 8 / 4;

	/** KAPPA値（ベジェ曲線で90度の円弧を描画する時に使用する係数） */
	private static final double KAPPA = (4.0 * (Math.sqrt(2.0) - 1.0)) / 3.0;

	/** 行頭禁則文字 */
	private static final String NO_HEAD = "»)）]｝〕〉》」』】〙〗〟’”｠!,.:;?、。ゝゞ々ァィゥェォッャュョヵヶぁぃぅぇぉっゃゅょゕゖ";

	/** 行末禁則文字 */
	private static final String NO_TAIL = "«(（[｛〔〈《「『【〘〖〝‘“｟";

	/** 三点リーダー */
	private static final String ELLIPSIS = "…";

	/** 空行配列 */
	private static final String[] EMPTY_LINES = {""};

	/** 2Dコンテキスト */
	private Context2d con;

	/** パスレンダラ */
	private PathRenderer pathRenderer;

	/** 基準点（X座標） */
	private int xOrigin;

	/** 基準点（Y座標） */
	private int yOrigin;

	/** 塗り色（rgba値） */
	protected int fillColor;

	/** ストローク色（rgba値） */
	protected int strokeColor;

	/** ストローク幅（ピクセル単位） */
	protected int strokeWidth;

	/** フォントファミリー名 */
	protected String fontFamily;

	/** フォントサイズ */
	protected int fontSize;

	public GwtContext2d(CanvasElement canvas, int width, int height) {
		reinit(canvas, width, height);
	}

	public void reinit(CanvasElement canvas, int width, int height) {
		double dpr = getDevicePixelRatio();
		LOGGER.info("GwtContext2d.reinit(w:%d, h:%d, dpr:%g)", width, height, dpr);
		//キャンバス、コンテキストの設定
		canvas.setWidth(round(width * dpr));
		canvas.setHeight(round(height * dpr));
		con = canvas.getContext2d();
		con.scale(dpr, dpr);
		//属性値の初期化
		xOrigin = 0;
		yOrigin = 0;
		fillColor = Colors.BLACK;
		strokeColor = Colors.BLACK;
		strokeWidth = 1;
		fontFamily = "sans-serif";
		fontSize = 10;
		setImageSmoothing(con, false);
	}

	private static native double getDevicePixelRatio()/*-{
		return $wnd.devicePixelRatio || 1;
	}-*/;

	private static int round(double value) {
		return (int) Math.round(value);
	}

	public void moveOrigin(int xMove, int yMove) {
		xOrigin += xMove;
		yOrigin += yMove;
	}

	/**
	 * 塗り色を設定する
	 *
	 * @param fillColor
	 * 		塗り色（RGBA値）
	 */
	public void setFillColor(int fillColor) {
		this.fillColor = fillColor;
	}

	/**
	 * ストローク色を設定する
	 *
	 * @param strokeColor
	 * 		ストローク色（RGBA値）
	 */
	public void setStrokeColor(int strokeColor) {
		this.strokeColor = strokeColor;
	}

	/**
	 * ストローク幅を設定する
	 *
	 * @param strokeWidth
	 * 		ストローク幅（ピクセル単位）
	 */
	public void setStrokeWidth(int strokeWidth) {
		this.strokeWidth = strokeWidth;
	}

	/**
	 * フォントファミリ名を設定する
	 *
	 * @param fontFamily
	 * 		フォントファミリ名
	 */
	public void setFontFamily(String fontFamily) {
		this.fontFamily = fontFamily;
	}

	/**
	 * フォントサイズを設定する
	 *
	 * @param fontSize
	 * 		フォントサイズ（ピクセル単位）
	 */
	public void setFontSize(int fontSize) {
		this.fontSize = fontSize;
	}

	public void save() {
		con.save();
	}

	public void restore() {
		con.restore();
	}

	public void clipRect(int x, int y, int w, int h) {
		x += xOrigin;
		y += yOrigin;
		con.beginPath();
		con.moveTo(x, y);
		con.lineTo(x + w, y);
		con.lineTo(x + w, y + h);
		con.lineTo(x, y + h);
		con.closePath();
		con.clip();
	}


	/**
	 * 指定範囲を全消去する
	 *
	 * @param x
	 * 		X座標（ピクセル単位）
	 * @param y
	 * 		Y座標（ピクセル単位）
	 * @param w
	 * 		幅（ピクセル単位）
	 * @param h
	 * 		高さ（ピクセル単位）
	 */
	public void clearRect(int x, int y, int w, int h) {
		x += xOrigin;
		y += yOrigin;
		con.clearRect(x, y, w, h);
	}

	/**
	 * 指定した塗り色、ストローク色／幅の矩形を描く
	 *
	 * @param x
	 * 		X座標（ピクセル単位）
	 * @param y
	 * 		Y座標（ピクセル単位）
	 * @param width
	 * 		幅（ピクセル単位）
	 * @param height
	 * 		高さ（ピクセル単位）
	 */
	public void drawRect(int x, int y, int w, int h) {
		if (!(w > 0 && h > 0)) {
			return;
		}
		x += xOrigin;
		y += yOrigin;
		if (!Colors.isTransparent(fillColor)) {
			con.setFillStyle(toCssColor(fillColor));
			con.fillRect(x, y, w, h);
		}
		if (!Colors.isTransparent(strokeColor) && strokeWidth > 0) {
			con.setStrokeStyle(toCssColor(strokeColor));
			con.setLineWidth(strokeWidth);
			con.strokeRect(x, y, w, h);
		}
	}

	/**
	 * 指定した塗り色、ストローク色／幅の矩形を描く
	 *
	 * @param x
	 * 		X座標（ピクセル単位）
	 * @param y
	 * 		Y座標（ピクセル単位）
	 * @param width
	 * 		幅（ピクセル単位）
	 * @param height
	 * 		高さ（ピクセル単位）
	 * @param drx
	 * 		角丸のx半径
	 * @param dry
	 * 		角丸のy半径
	 */
	public void drawRoundRect(int x, int y, int w, int h, int rx, int ry) {
		if (!(w > 0 && h > 0)) {
			return;
		}
		x += xOrigin;
		y += yOrigin;
		double drx;
		double dry;
		//rx, ryの補正
		if (rx <= 0 && ry <= 0) {
			drx = dry = 0.0;
		} else if (rx <= 0) {
			drx = dry = ry;
		} else if (ry <= 0) {
			drx = dry = rx;
		} else {
			drx = rx;
			dry = ry;
		}
		double rMin = Math.min(w / 2.0, h / 2.0);
		drx = Math.min(drx, rMin);
		dry = Math.min(dry, rMin);
		//角丸矩形の描画（左上から描画）
		con.beginPath();
		con.moveTo(x, y + dry);
		if (drx > 0) {
			arcTo(con, x + drx, y + dry, drx, dry, RAD180, RAD90);
		}
		if (drx < w / 2) {
			con.lineTo(x + w - drx, y);
		}
		if (dry > 0) {
			arcTo(con, x + w - drx, y + dry, drx, dry, RAD270, RAD90);
		}
		if (dry < h / 2) {
			con.lineTo(x + w + 0.0, y + h - dry);
		}
		if (drx > 0) {
			arcTo(con, x + w - drx, y + h - dry, drx, dry, RAD0, RAD90);
		}
		if (drx < w / 2.0) {
			con.lineTo(x + drx, y + h + 0.0);
		}
		if (dry > 0) {
			arcTo(con, x + drx, y + h - dry, drx, dry, RAD90 * 1, RAD90);
		}
		con.closePath();
		fillAndOrStroke(con);
	}

	/**
	 * 指定した塗り色、ストローク色／幅の（楕）円を描く
	 *
	 * @param x
	 * 		X座標（ピクセル単位）
	 * @param y
	 * 		Y座標（ピクセル単位）
	 * @param width
	 * 		幅（ピクセル単位）
	 * @param height
	 * 		高さ（ピクセル単位）
	 */
	public void drawOval(int x, int y, int width, int height) {
		drawFan(x, y, width, height, 0.0, 0.0, 360.0, true);
	}

	/**
	 * 指定した塗り色、ストローク色／幅の輪を描く
	 *
	 * @param x
	 * 		X座標（ピクセル単位）
	 * @param y
	 * 		Y座標（ピクセル単位）
	 * @param width
	 * 		幅（ピクセル単位）
	 * @param height
	 * 		高さ（ピクセル単位）
	 * @param ratio
	 * 		内周の比率
	 */
	public void drawRing(int x, int y, int width, int height, double ratio) {
		drawFan(x, y, width, height, ratio, 0.0, 360.0, true);
	}

	/**
	 * 指定した塗り色、ストローク色／幅の弓型を描く
	 *
	 * @param x
	 * 		X座標（ピクセル単位）
	 * @param y
	 * 		Y座標（ピクセル単位）
	 * @param width
	 * 		幅（ピクセル単位）
	 * @param height
	 * 		高さ（ピクセル単位）
	 * @param startDegree
	 * 		弓型の開始角度を（12時を０とした時計回りの）度数で与える。
	 * @param arcDegree
	 * 		弓型の角度を度数で与える。
	 * 		正の値の場合、弓は開始角度から時計回りに描画され、
	 * 		負の値の場合、弓は開始角度から反時計回りに描画される
	 */
	public void drawChord(int x, int y, int width, int height, double startDegree, double arcDegree) {
		drawFan(x, y, width, height, 0.0, startDegree, arcDegree, true);
	}

	/**
	 * 指定した塗り色、ストローク色／幅の扇型を描く
	 *
	 * @param x
	 * 		X座標（ピクセル単位）
	 * @param y
	 * 		Y座標（ピクセル単位）
	 * @param width
	 * 		幅（ピクセル単位）
	 * @param height
	 * 		高さ（ピクセル単位）
	 * @param startDegree
	 * 		扇型の開始角度を（12時を０とした時計回りの）度数で与える。
	 * @param arcDegree
	 * 		扇形の角度を度数で与える。
	 * 		正の値の場合、扇は開始角度から時計回りに描画され、
	 * 		負の値の場合、扇は開始角度から反時計回りに描画される
	 */
	public void drawPie(int x, int y, int width, int height, double startDegree, double arcDegree) {
		drawFan(x, y, width, height, 0.0, startDegree, arcDegree, false);
	}

	/**
	 * 指定した塗り色、ストローク色／幅の扇型を描く
	 *
	 * @param x
	 * 		X座標（ピクセル単位）
	 * @param y
	 * 		Y座標（ピクセル単位）
	 * @param w
	 * 		幅（ピクセル単位）
	 * @param h
	 * 		高さ（ピクセル単位）
	 * @param ratio
	 * 		内周比（0.0 <= ratio < 1.0) 0.0を指定するとdrawPieと同じ事になる
	 * @param startDegree
	 * 		アーチ型の開始角度を（12時を０とした時計回りの）度数で与える。
	 * @param arcDegree
	 * 		扇形の角度を度数で与える。
	 * 		正の値の場合、扇は開始角度から時計回りに描画され、
	 * 		負の値の場合、扇は開始角度から反時計回りに描画される
	 */
	public void drawFan(int x, int y, int w, int h, double ratio, double startDegree, double arcDegree) {
		drawFan(x, y, w, h, ratio, startDegree, arcDegree, false);
	}

	/**
	 * 指定した塗り色、ストローク色／幅の扇型を描く
	 *
	 * @param x
	 * 		X座標（ピクセル単位）
	 * @param y
	 * 		Y座標（ピクセル単位）
	 * @param w
	 * 		幅（ピクセル単位）
	 * @param h
	 * 		高さ（ピクセル単位）
	 * @param ratio
	 * 		内周比（0.0 <= ratio < 1.0) 0.0を指定するとdrawPieと同じ事になる
	 * @param startDegree
	 * 		扇型の開始角度を（12時を０とした時計回りの）度数で与える。
	 * @param arcDegree
	 * 		扇形の角度を度数で与える。
	 * 		正の値の場合、扇は開始角度から時計回りに描画され、
	 * 		負の値の場合、扇は開始角度から反時計回りに描画される
	 * @param skipEdge
	 *		扇の開始辺、終了辺を描画するか否かを指定する。
	 *		真の場合、辺は描画されない。
	 */
	public void drawFan(int x, int y, int w, int h,
			double ratio, double startDegree, double arcDegree, boolean skipEdge) {
		if (!(w > 0 && h > 0 && (-360.0 <= arcDegree && arcDegree <= 360.0) && arcDegree != 0.0)) {
			return;
		}
		x += xOrigin;
		y += yOrigin;
		double startAngle = ((startDegree + 270) % 360) * Math.PI / 180;
		double arcAngle = arcDegree * Math.PI / 180;
		double endAngle = (RAD360 + startAngle + arcAngle) % RAD360;
		double orx = w / 2.0;
		double ory = h / 2.0;
		double cx = x + orx;
		double cy = y + ory;
		double irx = orx * ratio;
		double iry = ory * ratio;
		con.beginPath();
		if (skipEdge) {
			con.moveTo(cx + orx * Math.cos(startAngle), cy + ory * Math.sin(startAngle));
		} else {
			con.moveTo(cx + irx * Math.cos(startAngle), cy + iry * Math.sin(startAngle));
			con.lineTo(cx + orx * Math.cos(startAngle), cy + ory * Math.sin(startAngle));
		}
		arcTo(con, cx, cy, orx, ory, startAngle, +arcAngle);
		if (ratio > 0.0) {
			if (skipEdge) {
				con.moveTo(cx + irx * Math.cos(endAngle), cy + iry * Math.sin(endAngle));
			} else {
				con.lineTo(cx + irx * Math.cos(endAngle), cy + iry * Math.sin(endAngle));
			}
			arcTo(con, cx, cy, irx, iry, endAngle, -arcAngle);
		}
		con.closePath();
		fillAndOrStroke(con);
	}

	/**
	 * 指定した塗り色、ストローク色／幅の正多角形を描く
	 *
	 * @param x
	 * 		X座標（ピクセル単位）
	 * @param y
	 * 		Y座標（ピクセル単位）
	 * @param width
	 * 		幅（ピクセル単位）
	 * @param height
	 * 		高さ（ピクセル単位）
	 * @param p
	 * 		頂点の数（３以上である事）
	 * @param startAngle
	 * 		描画開始位置を（12時を０とした時計回りの）度数で与える
	 */
	public void drawRegularPolygon(int x, int y, int w, int h, int n, double startAngle) {
		x += xOrigin;
		y += yOrigin;
		double rx = (w / 2);
		double ry = (h / 2);
		double angle = ((startAngle + 90) % 360) * Math.PI / 180;
		double step = Math.PI * 2 / n;
		double ox = x + rx;
		double oy = y + ry;
		con.beginPath();
		con.moveTo(ox - (rx * Math.cos(angle)), oy - (ry * Math.sin(angle)));
		angle += step;
		for (int i = 1; i < n; i++) {
			con.lineTo(ox - (rx * Math.cos(angle)), oy - (ry * Math.sin(angle)));
			angle += step;
		}
		con.closePath();
		fillAndOrStroke(con);
	}

	/**
	 * 指定した塗り色、ストローク色／幅の星型多角形を描く
	 *
	 * @param x
	 * 		X座標（ピクセル単位）
	 * @param y
	 * 		Y座標（ピクセル単位）
	 * @param width
	 * 		幅（ピクセル単位）
	 * @param height
	 * 		高さ（ピクセル単位）
	 * @param p
	 * 		頂点の数（５以上である事）
	 * @param ratio
	 * 		内周／外周の比率（0.0～1.0）
	 *
	 * @param startAngle
	 * 		描画開始位置を度数で与える（12時を０とする）
	 */
	public void drawStar(int x, int y, int w, int h, int n, double ratio, double startAngle) {
		x += xOrigin;
		y += yOrigin;
		n *= 2;
		double[] rx = {(w / 2.0), (w / 2.0) * ratio};
		double[] ry = {(h / 2.0), (h / 2.0) * ratio};
		double angle = (startAngle + 90) * Math.PI / 180;
		double step = Math.PI * 2 / n;
		double ox = x + rx[0];
		double oy = y + ry[0];
		con.beginPath();
		con.moveTo(ox - (rx[0] * Math.cos(angle)), oy - (ry[0] * Math.sin(angle)));
		angle += step;
		for (int i = 1; i < n; i++) {
			con.lineTo(ox - (rx[i % 2] * Math.cos(angle)), oy - (ry[i % 2] * Math.sin(angle)));
			angle += step;
		}
		con.closePath();
		fillAndOrStroke(con);
	}

	/**
	 * 指定した塗り色、ストローク色／幅の星型正多角形を描く
	 *
	 * 五芒星（ソロモンの星）を描く場合には、 p＝５, q=2 とする
	 * 六芒星（ダビデの星）を描く場合には、 p=6, q=2 とする
	 *
	 * @param x
	 * 		X座標（ピクセル単位）
	 * @param y
	 * 		Y座標（ピクセル単位）
	 * @param width
	 * 		幅（ピクセル単位）
	 * @param height
	 * 		高さ（ピクセル単位）
	 * @param p
	 * 		頂点の数（５以上である事）
	 * @param q
	 * 		スキップする頂点の数（２以上である事）
	 * @param startAngle
	 * 		描画開始位置。上（時計の12時）を０とした時計回りの度数（０°～３６０°）で与える
	 */
	public void drawRegularStar(int x, int y, int width, int height, int p, int q, double startAngle) {
		double ratio = getRegularStarRatio(p, q);
		drawStar(x, y, width, height, p, ratio, startAngle);
	}

	/**
	 * パスを描画する
	 *
	 * パス文字列はSVGのPATH記法に準拠する。（ただし、楕円弧は現状未実装）
	 *
	 * @param x
	 * 		X座標（ピクセル単位）
	 * @param y
	 * 		Y座標（ピクセル単位）
	 * @param path
	 * 		SVGのパス文字列
	 */
	public void drawPath(int x, int y, String path) {
		//描画環境の設定
		x += xOrigin;
		y += yOrigin;
		setupCanvas();
		if (pathRenderer == null) {
			pathRenderer = new PathRenderer();
		}
		pathRenderer.render(con, x, y, path);
	}

	public void drawImage(Image image, int x, int y, int w, int h) {
		x += xOrigin;
		y += yOrigin;
		ImageElement elem = ImageElement.as(image.getElement());
		int sw = image.getWidth();
		int sh = image.getHeight();
		con.drawImage(elem, 0, 0, sw, sh, x, y, w, h);
	}

	public void drawBorderImage(Image image, int x, int y, int w, int h, int bl, int bt, int br, int bb) {
		x += xOrigin;
		y += yOrigin;
		ImageElement elem = ImageElement.as(image.getElement());
		int dw = image.getWidth();
		int dh = image.getHeight();
		if (dw <= bl + br || dh <= bt + bb) {
			return;
		}
		int[] sxs = new int[] {0, 0 + bl, 0 + dw - br, 0 + dw};
		int[] sys = new int[] {0, 0 + bt, 0 + dh - bb, 0 + dh};
		int[] dxs = new int[] {x, x + bl, x + w  - br, x + w };
		int[] dys = new int[] {y, y + bt, y + h  - bb, y + h };
		for (int r = 0 ;r < 3; r++) {
			for (int c = 0; c < 3; c++) {
				con.drawImage(elem,
					sxs[c], sys[r], sxs[c + 1] - sxs[c], sys[r + 1] - sys[r],
					dxs[c], dys[r], dxs[c + 1] - dxs[c], dys[r + 1] - dys[r]);
			}
		}
	}

	/**
	 * 文字列をdrawStringで描画した場合に必要となる「幅」をピクセル単位で取得する
	 *
	 * @param str
	 * 		対象文字列
	 * @return
	 * 		文字列描画した場合に必要となる幅（ピクセル単位）
	 */
	public int measureString(String str) {
		setupCanvas();
		return measureStringImpl(str);
	}

	/**
	 * 指定位置から、指定の塗り色、ストローク色／幅の文字列を描く
	 *
	 * @param x
	 * 		X座標（ピクセル単位）
	 * @param y
	 * 		Y座標（ピクセル単位）
	 * @param str
	 * 		描画対象文字列（一切の折り返し処理は行われない）
	 */
	public void drawString(int x, int y, String str) {
		x += xOrigin;
		y += yOrigin;
		setupCanvas();
		drawStringImpl(x, y, str);
	}

	/**
	 * 文字列をdrawTextで描画した場合に必要となる「高さ」をピクセル単位で算出する
	 *
	 * @param w
	 * 		基準幅（ピクセル単位）
	 * @param text
	 * 		描画対象文字列（段落区切りとして\nが含まれていてよい。一方、タブは無視される）
	 * @param lineHeight
	 * 		行ごとの高さ
	 * @param flags
	 * 		描画フラグ DRAW_* フラグ値の組み合わせを指定する
	 * 		但し、垂直方向の指定、および {@link #DRAW_ELLIPSIS} は無視される
	 * @return
	 * 		折り返し描画した場合に必要となる高さ（ピクセル単位）
	 */
	public int measureText(int width, String text, int lineHeight, int flags) {
		setupCanvas();
		String[] lines = foldText(width, Integer.MAX_VALUE, text, lineHeight, flags);
		return (lines.length - 1) * lineHeight + fontSize;
	}

	/**
	 * 指定した矩形内に、指定の塗り色、ストローク色／幅の文字列を描く
	 *
	 * @param x
	 * 		X座標（ピクセル単位）
	 * @param y
	 * 		Y座標（ピクセル単位）
	 * @param w
	 * 		幅（ピクセル単位）
	 * @param h
	 * 		高さ（ピクセル単位）
	 * @param text
	 * 		描画対象文字列（段落区切りとして\nが含まれていてよい。一方、タブは無視される）
	 * @param lineHeight
	 * 		行ごとの高さ
	 * @param flags
	 * 		描画フラグ DRAW_* フラグ値の組み合わせを指定する
	 * @return
	 * 		実描画した高さ
	 */
	public int drawText(int x, int y, int width, int height, String text, int lineHeight, int flags) {
		//描画環境の設定
		x += xOrigin;
		y += yOrigin;
		setupCanvas();
		//縦拡張の場合、一旦lineHeight == fontSizeとして折り返し計算
		if ((flags & VALIGN_MASK) == VALIGN_EXPAND) {
			lineHeight = fontSize;
		}
		//指定の幅で折り返し計算する（ELLIPSIS付与も行う）
		String[] lines = foldText(width, height, text, lineHeight, flags);
		//上下位置の調整
		int blockHeight = lines.length * lineHeight - Math.max(0, lineHeight - fontSize);
		switch (flags & VALIGN_MASK) {
		case VALIGN_TOP:
			break;
		case VALIGN_BOTTOM:
			y += (height - blockHeight);
			break;
		case VALIGN_MIDDLE:
			y += (height - blockHeight) / 2;
			break;
		case VALIGN_EXPAND:
			if (lines.length == 1) {
				y += (height - blockHeight) / 2;
			} else {
				lineHeight = fontSize + (height - blockHeight) / (lines.length - 1);
			}
			break;
		default:
			break;
		}
		//行単位の描画
		for (int i = 0; i < lines.length; i++) {
			String line = lines[i];
			drawTextLine(x, y, width, line, flags);
			y += lineHeight;
		}
		return lines.length * lineHeight;
	}

	/**
	 * 描画条件をContextに設定
	 */
	private void setupCanvas() {
		con.setTextBaseline(Context2d.TextBaseline.TOP);
		if (fontFamily != null && fontSize > 0) {
			con.setFont(fontSize + "px " + fontFamily);
		}
		if (!Colors.isTransparent(fillColor)) {
			con.setFillStyle(toCssColor(fillColor));
		}
		if (!Colors.isTransparent(strokeColor) && strokeWidth > 0) {
			con.setStrokeStyle(toCssColor(strokeColor));
			con.setLineWidth(strokeWidth);
		}
		setImageSmoothing(con, false);
	}

	private static native void setImageSmoothing(Context2d con, boolean enable)/*-{
		con.imageSmoothingEnabled = enable;
	}-*/;

	/**
	 * 円弧を描く
	 *
	 * CANVASが楕円のARCを直接サポートしていないので、ベジェ曲線で代替
	 * ※TODO INFO SVGのPATHに使いたければ、さらに、少なくとも軸回転はサポートの必要あり
	 *
	 * @param cx
	 * 		中心X座標
	 * @param cy
	 * 		中心Y座標
	 * @param rx
	 * 		X半径
	 * @param ry
	 * 		Y半径
	 * @param startAngle
	 * 		開始角度（ラジアン）
	 * @param arcAngle
	 * 		弧の角度（ラジアン）
	 */
	private void arcTo(Context2d con, double cx, double cy, double rx, double ry,
			double startAngle, double arcAngle) {
		//処理準備
		double dir = Math.signum(arcAngle);
		double deltaAngle = Math.abs(arcAngle);
		int count = (int) Math.floor(deltaAngle / RAD90);
		double remainAngle = deltaAngle % RAD90;
		double ratio = remainAngle / RAD90;
		//最初に1/4円弧をcount回描画
		double a = startAngle;
		double b = (a + dir * RAD90) % RAD360;
		for (int i = 0; i < count; i++) {
			con.bezierCurveTo(
					cx + rx * Math.cos(a) - dir * KAPPA * rx * Math.sin(a),
					cy + ry * Math.sin(a) + dir * KAPPA * ry * Math.cos(a),
					cx + rx * Math.cos(b) + dir * KAPPA * rx * Math.sin(b),
					cy + ry * Math.sin(b) - dir * KAPPA * ry * Math.cos(b),
					cx + rx * Math.cos(b),
					cy + ry * Math.sin(b));
			a = b;
			b = (a + dir * RAD90) % RAD360;
		}
		//最後の円弧は1/4円弧を分割した制御点から描画
		//まず、1/4円弧の端点および制御点を生成
		double x0 = cx + rx * Math.cos(a);
		double y0 = cy + ry * Math.sin(a);
		double x1 = cx + rx * Math.cos(a) - dir * KAPPA * rx * Math.sin(a);
		double y1 = cy + ry * Math.sin(a) + dir * KAPPA * ry * Math.cos(a);
		double x2 = cx + rx * Math.cos(b) + dir * KAPPA * rx * Math.sin(b);
		double y2 = cy + ry * Math.sin(b) - dir * KAPPA * ry * Math.cos(b);
		//端点および制御点間の中点座標を計算
		double x4 = x0 + (x1 - x0) * ratio;
		double y4 = y0 + (y1 - y0) * ratio;
		double x5 = x1 + (x2 - x1) * ratio;
		double y5 = y1 + (y2 - y1) * ratio;
		//中点間の中点を計算
		double x7 = x4 + (x5 - x4) * ratio;
		double y7 = y4 + (y5 - y4) * ratio;
		//描画
		con.bezierCurveTo(
				x4, y4, x7, y7,
				cx + rx * Math.cos(startAngle + arcAngle),
				cy + ry * Math.sin(startAngle + arcAngle));
	}

	private void fillAndOrStroke(Context2d con) {
		if (!Colors.isTransparent(fillColor)) {
			con.setFillStyle(toCssColor(fillColor));
			con.fill();
		}
		if (!Colors.isTransparent(strokeColor) && strokeWidth > 0) {
			con.setStrokeStyle(toCssColor(strokeColor));
			con.setLineWidth(strokeWidth);
			con.stroke();
		}
	}

	/**
	 * 文字列を指定矩形に合うように折り返す
	 *
	 * @param width
	 * 		矩形幅
	 * @param height
	 * 		矩形高さ
	 * @param text
	 * 		文字列
	 * @param lineHeight
	 * 		行高さ
	 * @param flags
	 * 		描画フラグ
	 * @return
	 * 		表示行配列
	 */
	private String[] foldText(int width, int height, String text, int lineHeight, int flags) {
		//パラメータチェック
		if (text == null || text.length() == 0) {
			return EMPTY_LINES;
		}
		// 段落ごとに分割
		String[] paras = text.split("\n");
		//各段落をさらに表示可能な行に分割する処理
		List<String> lines = new ArrayList<>();
		int remainHeight = height;
		for (int i = 0; i < paras.length && lineHeight <= remainHeight; i++) {
			String para = paras[i];
			int s = 0;
			int n = para.length();
			while (s < n && lineHeight <= remainHeight) {
				//先頭の空白をスキップする
				while (s < n && para.charAt(s) == 0x20) {
					s++;
				}
				int w = 0;
				int e = s;
				int be = e;
				int chPrev = 0;
				while (e < n && w <= width) {
					int chCurr = para.charAt(e);
					if(	!isProhibitedEndChar(chPrev)   &&
						!isProhibitedStartChar(chCurr) &&
						!isLatinChar(chCurr) ) {
						be = e;
					}
					e = e + 1;
					w = measureStringImpl(para.substring(s, e));
					chPrev = chCurr;
				}
				if (w > width) {
					if (s < be) {
						//行途中にワードブレークがあれば、そこまでを描画
						e = be;
					} else {
						//１余計に加算しているので減算
						e = e - 1;
					}
				}
				//描画候補文字列
				String line = para.substring(s, e);
				//おさまりきれない場合、末尾文字に ELLIPSIS を付与する場合
				if ((flags & DRAW_ELLIPSIS) != 0 &&
					(remainHeight - lineHeight) < lineHeight &&
					(e < n || i + 1 < paras.length) &&
					measureStringImpl(ELLIPSIS) <= width
				) {
					line += ELLIPSIS;
					while (measureStringImpl(line) > width) {
						line = line.substring(0, line.length() - 2) + ELLIPSIS;
					}
				}
				if (!(e < n)) {
					line += "\n";
				}
				lines.add(line);
				//次段落処理の準備
				s = e;
				remainHeight -= lineHeight;
			}
		}
		return lines.toArray(new String[lines.size()]);
	}

	/**
	 * 一行を描画する
	 *
	 * @param x
	 * 		描画開始X位置
	 * @param y
	 * 		描画開始Y位置
	 * @param width
	 * 		描画幅
	 * @param line
	 * 		行
	 * @param flags
	 * 		描画フラグ
	 */
	private void drawTextLine(int x, int y, int width, String line, int flags) {
		boolean endLine = false;
		if (line.charAt(line.length() - 1) == '\n') {
			endLine = true;
			line = line.substring(0, line.length() - 1);
		}
		int w = measureStringImpl(line);
		switch (flags & ALIGN_MASK) {
		case ALIGN_LEFT:
			drawStringImpl(x, y, line);
			break;
		case ALIGN_RIGHT:
			drawStringImpl(x + (width - w), y, line);
			break;
		case ALIGN_CENTER:
			drawStringImpl(x + (width - w) / 2, y, line);
			break;
		case ALIGN_JUSTIFY:
			if (endLine) {
				drawStringImpl(x, y, line);
			} else {
				drawJustifyString(x, y, width, line);
			}
			break;
		default:
			break;
		}
	}

	private void drawJustifyString(int x, int y, int width, String line) {
		String[] words = line.split(" ");
		if (words.length > 1) {
			//欧文JUSTIFY
			int nword = words.length;
			int[] widths = new int[nword + 1];
			widths[0] = 0;
			for (int j = 0; j < nword; j++) {
				widths[j + 1] = widths[j] + measureStringImpl(words[j]);
			}
			int spacing = (width - widths[nword]) / (nword - 1);
			for (int j = 0; j < nword; j++) {
				drawStringImpl(x + widths[j] + spacing * j, y, words[j]);
			}
		} else {
			//和文JUSTIFY
			int nword = line.length();
			int[] widths = new int[nword + 1];
			widths[0] = 0;
			for (int j = 0; j < nword; j++) {
				widths[j + 1] = widths[j] + measureStringImpl(line.substring(j, j + 1));
			}
			int spacing = (width - widths[nword]) / (nword - 1);
			for (int j = 0; j < nword; j++) {
				drawStringImpl(x + widths[j] + spacing * j, y, line.substring(j, j + 1));
			}
		}
	}

	private int measureStringImpl(String str) {
		return (int) Math.ceil(con.measureText(str).getWidth());
	}

	private void drawStringImpl(int x, int y, String str) {
		if (!Colors.isTransparent(fillColor)) {
			con.fillText(str, x, y);
		}
		if (!Colors.isTransparent(strokeColor) && strokeWidth > 0) {
			con.strokeText(str, x, y);
		}
	}

	/**
	 * p, q から外周／内周の比を取得する
	 *
	 * 0番目の点と0+q番目の点を結んだ線分ABと、
	 * 1番目の点と1-q番目の点を結んだ線分CDから
	 * 「２線分の交点」を計算して交点を求め、
	 * その交点と原点との距離が外周／内周の比となる。
	 *
	 * 「２線分の交点」の計算の際、
	 * （パラメータが正しければ）２つの線分は必ず交差するので、
	 * 平行チェック、重なりチェック、線分交差チェックは省略している。
	 *
	 * @param p
	 * 		頂点の数（５以上である事）
	 * @param q
	 * 		スキップする頂点の数（２以上である事）
	 * @return
	 * 		内周／外周 比率
	 */
	private static double getRegularStarRatio(int p, int q) {
		double baseAngle = (Math.PI * 2) / p;
		double ax = Math.cos(0 * baseAngle);			//点AのX座標
		double ay = Math.sin(0 * baseAngle);			//点AのY座標
		double bx = Math.cos(q * baseAngle);			//点BのX座標
		double by = Math.sin(q * baseAngle);			//点BのY座標
		double cx = Math.cos(1 * baseAngle);			//点CのX座標
		double cy = Math.sin(1 * baseAngle);			//点CのY座標
		double dx = Math.cos((p + 1 - q) * baseAngle);	//点DのX座標
		double dy = Math.sin((p + 1 - q) * baseAngle);	//点DのY座標
		double denom = (bx-ax)*(dy-cy)-(by-ay)*(dx-cx);	//線分AB中の交点iの比率の分母
		double numer = (dy-cy)*(cx-ax)-(dx-cx)*(cy-ay);	//線分AB中の交点iの比率の分子
		double r = numer / denom;						//線分AB中の交点iの比率
		double ix = ax + r * (bx - ax);					//交点iのX座標
		double iy = ay + r * (by - ay);					//交点iのY座標
		double ratio = Math.sqrt(ix*ix+iy*iy);			//原点と交点iとの距離
		return ratio;
	}

	/**
	 * intのRGBA値をCssの色指定文字列に変換する
	 *
	 * @param rgba
	 * 		rgba値
	 * @return
	 * 		CSSの色指定文字列
	 */
	private static String toCssColor(int rgba) {
		int r = Colors.getRValue(rgba);
		int g = Colors.getGValue(rgba);
		int b = Colors.getBValue(rgba);
		double a = Colors.getAValue(rgba);
		return Strings.sprintf("rgba(%d,%d,%d,%g)", r, g, b, a);
	}

	/**
	 * 行頭禁則文字判定
	 *
	 * @param ch
	 * 		対象文字
	 * @return
	 * 		対象文字が行頭禁則文字の場合、真
	 */
	private static boolean isProhibitedStartChar(int ch) {
		return NO_HEAD.indexOf(ch) >= 0;
	}

	/**
	 * 行末禁則文字判定
	 *
	 * @param ch
	 * 		対象文字
	 * @return
	 * 		対象文字が行末禁則文字の場合、真
	 */
	private static boolean isProhibitedEndChar(int ch) {
		return NO_TAIL.indexOf(ch) >= 0;
	}

	/**
	 * 欧文文字判定
	 *
	 * @param ch
	 * 		対象文字
	 * @return
	 * 		対象文字が欧文文字の場合、真
	 */
	private static boolean isLatinChar(int ch) {
		return 0x0021 <= ch && ch <= 0x02AF;
	}

}
