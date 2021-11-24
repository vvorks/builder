package com.github.vvorks.builder.client.common.ui;

public interface EventHandler {

	int onKeyDown(UiNode target, int keyCode, int charCode, int mods, int time);
	int onKeyPress(UiNode target, int keyCode, int charCode, int mods, int time);
	int onKeyUp(UiNode target, int keyCode, int charCode, int mods, int time);
	int onMouseMove(UiNode target, int x, int y, int mods, int time);
	int onMouseDown(UiNode target, int x, int y, int mods, int time);
	int onMouseUp(UiNode target, int x, int y, int mods, int time);
	int onMouseClick(UiNode target, int x, int y, int mods, int time);
	int onMouseWheel(UiNode target, int x, int y, int dx, int dy, int mods, int time);
	void onResize(int screenWidth, int screenHeight);
	int onImageLoaded(String url);
	int onDataSourceUpdated(DataSource ds);

}
