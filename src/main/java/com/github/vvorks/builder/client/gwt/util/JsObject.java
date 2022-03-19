package com.github.vvorks.builder.client.gwt.util;

import com.google.gwt.core.client.JavaScriptObject;

public class JsObject extends JavaScriptObject {

	public static native JsObject create()/*-{
		return {};
	}-*/;

	protected JsObject() {
	}

	public final native boolean getBoolean(String key)/*-{
		return this[key];
	}-*/;

	public final native void setBoolean(String key, boolean value)/*-{
		this[key] = value;
	}-*/;

	public final native double getNumber(String key)/*-{
		return this[key];
	}-*/;

	public final native void setNumber(String key, double value)/*-{
		this[key] = value;
	}-*/;

	public final native String getString(String key)/*-{
		return this[key];
	}-*/;

	public final native void setString(String key, String value)/*-{
		this[key] = value;
	}-*/;

	public final native JavaScriptObject getObject(String key)/*-{
		return this[key];
	}-*/;

	public final native void setObject(String key, JavaScriptObject value)/*-{
		this[key] = value;
	}-*/;

	public final native void clearProperty(String key)/*-{
		delete this[key];
	}-*/;

}
