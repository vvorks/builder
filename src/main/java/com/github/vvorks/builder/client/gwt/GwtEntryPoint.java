package com.github.vvorks.builder.client.gwt;

import com.github.vvorks.builder.client.ClientSettings;
import com.github.vvorks.builder.client.common.ui.Metrics;
import com.github.vvorks.builder.client.gwt.ui.DomPanel;
import com.github.vvorks.builder.client.gwt.ui.ImePanel;
import com.github.vvorks.builder.client.gwt.ui.InputEvent;
import com.github.vvorks.builder.common.net.URLFragment;
import com.github.vvorks.builder.common.util.DelayedExecuter;
import com.google.gwt.animation.client.AnimationScheduler;
import com.google.gwt.animation.client.AnimationScheduler.AnimationCallback;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.dom.client.Style;
import com.google.gwt.dom.client.Style.Overflow;
import com.google.gwt.dom.client.Style.Position;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.dom.client.Style.Visibility;
import com.google.gwt.http.client.URL;
import com.google.gwt.media.client.Video;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.LayoutPanel;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.google.gwt.user.client.ui.Widget;

public class GwtEntryPoint implements EntryPoint {

	public void onModuleLoad() {
		//クライアント構成セットアップ
		ClientSettings.setup();
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
		//イベントハンドラの初期化
		FocusPanel p = imePanel;
		p.addKeyDownHandler(event -> domPanel.onKeyDown(event));
		p.addKeyPressHandler(event -> domPanel.onKeyPress(event));
		p.addKeyUpHandler(event -> domPanel.onKeyUp(event));
		p.addDomHandler(event -> domPanel.onInput(event), InputEvent.getType());
		p.addMouseDownHandler(event -> domPanel.onMouseDown(event));
		p.addMouseMoveHandler(event -> domPanel.onMouseMove(event));
		p.addMouseUpHandler(event -> domPanel.onMouseUp(event));
		p.addClickHandler(event -> domPanel.onClick(event));
		p.addDoubleClickHandler(event -> domPanel.onDoubleClick(event));
		p.addMouseWheelHandler(event -> domPanel.onMouseWheel(event));
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
