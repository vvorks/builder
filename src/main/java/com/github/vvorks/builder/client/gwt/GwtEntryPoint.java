package com.github.vvorks.builder.client.gwt;

import java.util.Date;

import com.github.vvorks.builder.client.ClientSettings;
import com.github.vvorks.builder.client.common.ui.Metrics;
import com.github.vvorks.builder.client.gwt.ui.DomPanel;
import com.github.vvorks.builder.client.gwt.ui.ImePanel;
import com.github.vvorks.builder.client.gwt.ui.InputEvent;
import com.github.vvorks.builder.client.gwt.util.GlobalizeJs;
import com.github.vvorks.builder.client.gwt.util.GlobalizeResource;
import com.github.vvorks.builder.common.logging.Logger;
import com.github.vvorks.builder.common.net.URLFragment;
import com.github.vvorks.builder.common.util.DelayedExecuter;
import com.google.gwt.animation.client.AnimationScheduler;
import com.google.gwt.animation.client.AnimationScheduler.AnimationCallback;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style;
import com.google.gwt.dom.client.Style.Overflow;
import com.google.gwt.dom.client.Style.Position;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.dom.client.Style.Visibility;
import com.google.gwt.event.dom.client.FocusEvent;
import com.google.gwt.event.dom.client.FocusHandler;
import com.google.gwt.http.client.URL;
import com.google.gwt.i18n.client.LocaleInfo;
import com.google.gwt.layout.client.Layout;
import com.google.gwt.media.client.Video;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.FocusWidget;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.LayoutPanel;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.google.gwt.user.client.ui.Widget;

public class GwtEntryPoint implements EntryPoint {

	public static interface Resource extends ClientBundle {
		@GlobalizeResource.Require({
			"number", "currency", "date" //, "message", "plural", "relativeTime", "unit"
		})
		GlobalizeResource globalizeResource();
	}

	public void onModuleLoad() {
		//クライアント構成セットアップ
		String locale = LocaleInfo.getCurrentLocale().getLocaleName();
		ClientSettings.setup(locale);
		Logger logger = Logger.createLogger(GwtEntryPoint.class);
		logger.info("START %s(locale=%s)", GWT.getModuleName(), locale);
		//Globalize初期化
		logger.info("INTL %s", testIntl());
		GlobalizeResource res = ((Resource) GWT.create(Resource.class)).globalizeResource();
		GlobalizeJs.install(res.getJsContents());
		GlobalizeJs.load(res.getJsonContents());
		GlobalizeJs.setLocale(res.getLocale());
		{
			//test globalize
			GlobalizeJs g = GlobalizeJs.create("ja-JP-u-ca-japanese");
			String s = g.formatDate(new Date());
			logger.info("date %s", s);
		}
		//ルート要素の初期化
		String bgColor = ClientSettings.DEBUG ? "#000040" : "rgba(0, 0, 0, 0.0)";
		RootLayoutPanel root = RootLayoutPanel.get();
		Style rootStyle = root.getElement().getStyle();
		rootStyle.setOverflow(Overflow.HIDDEN);
		rootStyle.setBackgroundColor(bgColor);
		//メトリックス計測用のダミー要素を作成
		final Widget em = new HTML("");
		final Widget ex = new HTML("");
		final Widget in = new HTML("");
		root.add(em);
		root.add(ex);
		root.add(in);
		setWidgetSize(em, 10, Unit.EM);
		setWidgetSize(ex, 10, Unit.EX);
		setWidgetSize(in,  1, Unit.IN);
		//ロード後処理
		DelayedExecuter.get().runLator(() -> {
			//メトリックス計測結果を保存
			Metrics met = Metrics.get();
			met.setEmSize(em.getOffsetWidth() / 10.0);
			met.setExSize(ex.getOffsetWidth() / 10.0);
			met.setInSize(in.getOffsetWidth() /  1.0);
			//メトリックス計測用のダミー要素を削除
			root.remove(em);
			root.remove(ex);
			root.remove(in);
			//実初期化開始
			initialize(root);
		});
	}

	private static native String testIntl()/*-{
		var date = new Date(2019, 4, 1, 0, 0, 00);
		var options = {era: 'long'};
		return new Intl.DateTimeFormat('ja-JP-u-ca-japanese', options).format(date);
	}-*/;

	private void initialize(RootLayoutPanel root) {
		//背景ビデオ(FOR DEBUG/DEMO)
		if (ClientSettings.DEBUG) {
			Video video = Video.createIfSupported();
			if (video != null) {
				video.setWidth("100%");
				video.setHeight("100%");
				video.setLoop(true);
				video.setAutoplay(true);
				video.setMuted(true);
				video.addSource("video/sample_1920x1080.mp4");
				root.add(video);
			}
		}
		//backyard準備
		LayoutPanel backyard = new LayoutPanel();
		root.add(backyard);
		setWidgetHidden(backyard);
		//ImePanelの作成
		ImePanel imePanel = new ImePanel();
		//メインパネル準備
		DomPanel domPanel = new DomPanel(backyard, imePanel);
		root.add(domPanel);
		//ImePanelの追加
		root.add(imePanel);
		imePanel.hide();
		((Layout.Layer) imePanel.getLayoutData())
				.getContainerElement()
				.getStyle()
				.setProperty("pointerEvents", "none");
		//イベントハンドラの初期化
		FocusWidget p = imePanel;
		FocusWidget q = domPanel;
		p.addKeyDownHandler(event -> domPanel.onKeyDown(event));
		p.addKeyPressHandler(event -> domPanel.onKeyPress(event));
		p.addKeyUpHandler(event -> domPanel.onKeyUp(event));
		p.addDomHandler(event -> domPanel.onInput(event), InputEvent.getType());
		q.addMouseDownHandler(event -> domPanel.onMouseDown(event));
		q.addMouseMoveHandler(event -> domPanel.onMouseMove(event));
		q.addMouseUpHandler(event -> domPanel.onMouseUp(event));
		q.addClickHandler(event -> domPanel.onClick(event));
		q.addDoubleClickHandler(event -> domPanel.onDoubleClick(event));
		q.addMouseWheelHandler(event -> domPanel.onMouseWheel(event));
		Window.addResizeHandler(event -> domPanel.onResize(event));
		final AnimationScheduler scheduler = AnimationScheduler.get();
		scheduler.requestAnimationFrame(new AnimationCallback() {
			public void execute(double timestamp) {
				domPanel.onAnimationFrame();
				scheduler.requestAnimationFrame(this);
			}
		});
		//フォーカス設定
		p.setFocus(true);
		q.addFocusHandler(new FocusHandler() {
			public void onFocus(FocusEvent event) {
				p.setFocus(true);
			}
		});
		//起動パラメータの取得
		String encStr = Window.Location.getHash();
		String hashStr = URL.decodeQueryString(encStr);
		URLFragment fragment = new URLFragment(hashStr);
		//ページ読み込み
		domPanel.load(fragment.getTag(), fragment.getParameters());
	}

	private void setWidgetSize(Widget widget, int value, Unit unit) {
		Style style = widget.getElement().getStyle();
		style.setPosition(Position.ABSOLUTE);
		style.setMargin(0, Unit.PX);
		style.setBorderWidth(0, Unit.PX);
		style.setPadding(0, Unit.PX);
		style.setLeft(0, Unit.PX);
		style.setTop(0, Unit.PX);
		style.setWidth(value, unit);
		style.setHeight(value, unit);
	}

	private void setWidgetHidden(Widget widget) {
		Style style = widget.getElement().getStyle();
		style.setVisibility(Visibility.HIDDEN);
		style.setPosition(Position.ABSOLUTE);
		style.setMargin(0, Unit.PX);
		style.setBorderWidth(0, Unit.PX);
		style.setPadding(0, Unit.PX);
		style.setLeft(0, Unit.PCT);
		style.setTop(0, Unit.PCT);
		style.setWidth(100, Unit.PCT);
		style.setHeight(100, Unit.PCT);
	}

}
