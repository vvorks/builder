package com.github.vvorks.builder.client.gwt.ui;

import com.google.gwt.dom.client.BrowserEvents;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.event.dom.client.DomEvent;

public class InputEvent extends DomEvent<InputHandler> {

	private static final Type<InputHandler> TYPE = new Type<>(
			BrowserEvents.INPUT, new InputEvent());

	public static Type<InputHandler> getType() {
		return TYPE;
	}

	protected InputEvent() {
	}

	@Override
	public Type<InputHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(InputHandler handler) {
		handler.onInput(this);
	}

	public String getContent() {
		return this.getRelativeElement().getInnerText();
	}

	public String getData() {
		return getData0(getNativeEvent());
	}

	private static native String getData0(NativeEvent event)/*-{
		return event.data;
	}-*/;

}
