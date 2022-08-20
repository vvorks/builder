package com.github.vvorks.builder.client.common.ui;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.github.vvorks.builder.shared.common.json.Json;
import com.github.vvorks.builder.shared.common.lang.Asserts;
import com.github.vvorks.builder.shared.common.lang.RichIterable;

public abstract class UiPage extends UiNode implements DataRecord {

	/** JSONデータ変更済みフラグ */
	protected static final int FLAGS_UPDATED = 0x00010000;

	/** データ更新を直ちにコミットするか否か */
	protected static final int FLAGS_COMMIT_SOON = 0x00020000;

	private UiApplication application;

	private boolean initialized;

	private Json json;

	protected UiPage(String name, UiApplication app) {
		super(name);
		Asserts.requireNotNull(app);
		this.application = app;
		setLeft(Length.ZERO);
		setTop(Length.ZERO);
		setRight(Length.ZERO);
		setBottom(Length.ZERO);
	}

	protected UiPage(UiPage src) {
		super(src);
		this.application = src.application;
		this.initialized = src.initialized;
		this.json = src.json;
	}

	protected abstract void initialize();

	@Override
	protected UiApplication getApplication() {
		return application;
	}

	@Override
	protected DomDocument getDocument() {
		return application.getDocument();
	}

	@Override
	public UiPage getPage() {
		return this;
	}

	@Override
	public void onMount() {
		if (!initialized) {
			initialize();
			initialized = true;
		}
		super.onMount();
		injectRegisteredStyles();
	}

	@Override
	public void onUnmount() {
		deinjectStyleInPage();
		super.onUnmount();
	}

	private void injectRegisteredStyles() {
		List<Iterable<UiStyle>> list = new ArrayList<>();
		list.add(this.getRegisteredStyles());
		for (UiNode d : getDescendants()) {
			list.add(d.getRegisteredStyles());
		}
		Map<String, CssStyle> cssMap = new LinkedHashMap<>();
		RichIterable
				.from(list)
				.forEach(s -> s.toCssStyle(cssMap));
		UiApplication app = getApplication();
		DomDocument doc = app.getDocument();
		doc.injectStyleSheet(getClass(), cssMap);
	}

	public void deinjectStyleInPage() {
		UiApplication app = getApplication();
		DomDocument doc = app.getDocument();
		doc.deinjectStyleSheet(getClass());
	}

	@Override
	public int onDataSourceUpdated(DataSource ds) {
		int result = super.onDataSourceUpdated(ds);
		if (ds.isLoaded()) {
			int count = ds.getCount();
			if (count > 0) {
				json = ds.getData(0);
				getDataFields().forEach(field -> field.setRecord(this));
			} else {
				json = null;
			}
		}
		result |= EVENT_AFFECTED;
		return result;
	}

	public boolean isFlushSoon() {
		return isFlagsOn(FLAGS_COMMIT_SOON);
	}

	public void setFlushSoon(boolean soon) {
		setFlags(FLAGS_COMMIT_SOON, soon, 0);
	}

	public void flush() {
		if (exists() && isFlagsOn(FLAGS_UPDATED)) {
			DataSource ds = getDataSource();
			ds.update(json);
			setFlags(FLAGS_UPDATED, false, 0);
		}
	}

	@Override
	public DataRecord getDataRecord() {
		return this;
	}

	@Override
	public boolean exists() {
		return json != null;
	}

	@Override
	public Json getData() {
		return json;
	}

	@Override
	public Class<?> getType(String column) {
		Asserts.assume(exists());
		return json.getType().asClass();
	}

	@Override
	public boolean getBoolean(String column, boolean defaultValue) {
		Asserts.assume(exists());
		return json.getBoolean(column, defaultValue);
	}

	@Override
	public int getInt(String column, int defaultValue) {
		Asserts.assume(exists());
		return json.getInt(column, defaultValue);
	}

	@Override
	public long getLong(String column, long defaultValue) {
		Asserts.assume(exists());
		return json.getLong(column, defaultValue);
	}

	@Override
	public float getFloat(String column, float defaultValue) {
		Asserts.assume(exists());
		return json.getFloat(column, defaultValue);
	}

	@Override
	public double getDouble(String column, double defaultValue) {
		Asserts.assume(exists());
		return json.getDouble(column, defaultValue);
	}

	@Override
	public BigDecimal getDecimal(String column, BigDecimal defaultValue) {
		Asserts.assume(exists());
		return json.getDecimal(column, defaultValue);
	}

	@Override
	public Date getDate(String column, Date defaultValue) {
		Asserts.assume(exists());
		return json.getDate(column, defaultValue);
	}

	@Override
	public String getString(String column, String defaultValue) {
		Asserts.assume(exists());
		return json.getString(column, defaultValue);
	}

	@Override
	public void setNull(String column) {
		Asserts.assume(exists());
		json.setNull(column);
		onFieldUpdated();
	}

	@Override
	public void setBoolean(String column, boolean value) {
		Asserts.assume(exists());
		json.setBoolean(column, value);
		onFieldUpdated();
	}

	@Override
	public void setInt(String column, int value) {
		Asserts.assume(exists());
		json.setInt(column, value);
		onFieldUpdated();
	}

	@Override
	public void setLong(String column, long value) {
		Asserts.assume(exists());
		json.setLong(column, value);
		onFieldUpdated();
	}

	@Override
	public void setFloat(String column, float value) {
		Asserts.assume(exists());
		json.setFloat(column, value);
		onFieldUpdated();
	}

	@Override
	public void setDouble(String column, double value) {
		Asserts.assume(exists());
		json.setDouble(column, value);
		onFieldUpdated();
	}

	@Override
	public void setDecimal(String column, BigDecimal value) {
		Asserts.assume(exists());
		json.setDecimal(column, value);
		onFieldUpdated();
	}

	@Override
	public void setDate(String column, Date value) {
		Asserts.assume(exists());
		json.setDate(column, value);
		onFieldUpdated();
	}

	@Override
	public void setString(String column, String value) {
		Asserts.assume(exists());
		json.setString(column, value);
		onFieldUpdated();
	}

	private void onFieldUpdated() {
		setFlags(FLAGS_UPDATED, true, 0);
		if (isFlushSoon()) {
			flush();
		}
	}

}
