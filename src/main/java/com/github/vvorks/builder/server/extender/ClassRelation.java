package com.github.vvorks.builder.server.extender;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.github.vvorks.builder.server.domain.ClassContent;
import com.github.vvorks.builder.server.domain.FieldContent;
import com.github.vvorks.builder.shared.common.lang.Asserts;
import com.github.vvorks.builder.shared.common.lang.RichIterable;

public class ClassRelation {

	private final ClassContent clazz;

	private ClassRelation owner;

	private FieldContent ownerSetField;

	private FieldContent ownerRefField;

	private List<ClassRelation> sets;

	public ClassRelation() {
		clazz = null;
	}

	public ClassRelation(ClassContent cls) {
		clazz = cls;
	}

	public ClassContent getContent() {
		return clazz;
	}

	public ClassRelation getOwner() {
		return owner;
	}

	public FieldContent getOwnerSetField() {
		return ownerSetField;
	}

	public FieldContent getOwnerRefField() {
		return ownerRefField;
	}

	public void addSet(ClassRelation r) {
		if (sets == null) {
			sets = new ArrayList<>();
		}
		sets.add(r);
		r.owner = this;
		r.ownerSetField = null;
		r.ownerRefField = null;
	}

	public void addSet(ClassRelation r, FieldContent setField, FieldContent refField) {
		if (sets == null) {
			sets = new ArrayList<>();
		}
		sets.add(r);
		if (setField.isIsContainer()) {
			Asserts.assume(r.owner == null);
			r.owner = this;
			r.ownerSetField = setField;
			r.ownerRefField = refField;
		}
	}

	public Iterable<ClassRelation> getOwns() {
		if (sets == null) {
			return Collections.emptyList();
		}
		final ClassRelation me = this;
		return RichIterable
				.from(sets)
				.filter(c -> c.getOwner() == me);
	}

	public boolean isContained() {
		return owner != null;
	}

	public List<ClassContent> getOwners() {
		if (this.owner == null) {
			return Collections.emptyList();
		}
		List<ClassContent> list = new ArrayList<>();
		ClassRelation r = this.owner;
		while (r != null && r.clazz != null) {
			list.add(r.clazz);
			r = r.owner;
		}
		return list;
	}

}