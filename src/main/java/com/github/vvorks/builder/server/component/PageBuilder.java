package com.github.vvorks.builder.server.component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import org.springframework.stereotype.Component;

import com.github.vvorks.builder.common.lang.Asserts;
import com.github.vvorks.builder.common.lang.Iterables;
import com.github.vvorks.builder.common.logging.Logger;
import com.github.vvorks.builder.server.domain.ClassContent;
import com.github.vvorks.builder.server.domain.DataType;
import com.github.vvorks.builder.server.domain.FieldContent;
import com.github.vvorks.builder.server.domain.PageSetContent;
import com.github.vvorks.builder.server.domain.ProjectContent;
import com.github.vvorks.builder.server.mapper.Mappers;

@Component
public class PageBuilder {

	private static final Logger LOGGER = Logger.createLogger(SourceWriter.class);

	private static final String PAGESET_SHOW = "show";

	private static final String PAGESET_EDIT = "edit";

	public static class ClassRelation {

		private final ClassContent clazz;

		private ClassRelation owner;

		private List<ClassRelation> sets;

		public ClassRelation() {
			clazz = null;
		}

		public ClassRelation(ClassContent cls) {
			clazz = cls;
		}

		public boolean isRoot() {
			return clazz == null && owner == null;
		}

		public ClassContent getContent() {
			return clazz;
		}

		public ClassRelation getOwner() {
			return owner;
		}

		public void addSet(ClassRelation r, boolean isContainer) {
			if (sets == null) {
				sets = new ArrayList<>();
			}
			sets.add(r);
			if (isContainer) {
				Asserts.assume(r.owner == null);
				r.owner = this;
			}
		}

		public Iterable<ClassRelation> getOwns() {
			if (sets == null) {
				return Collections.emptyList();
			}
			final ClassRelation me = this;
			return Iterables.filter(sets, c -> c.getOwner() == me);
		}

	}

	public void process() throws IOException {
		Mappers m = Mappers.get();
		List<ProjectContent> list = m.getProjectMapper().listAll();
		for (ProjectContent c : list) {
			processProject(m, c);
		}
	}

	private void processProject(Mappers m, ProjectContent prj) {
		List<PageSetContent> sets = m.getProjectMapper().listPageSetsContent(prj, 0, 0);
		//delete previous outputs
		for (PageSetContent s : sets) {
			String setName = s.getPageSetName();
			if (PAGESET_SHOW.equals(setName) || PAGESET_EDIT.equals(setName)) {
				m.getPageSetMapper().delete(s);
			}
		}
		insertShowPageSet(m, prj);
		insertEditPageSet(m, prj);
	}

	private void insertShowPageSet(Mappers m, ProjectContent prj) {
		ClassRelation rel = getRelation(m, prj);
		insertShowPage(m, prj);
		visitRelation(rel, r -> insertShowPage(m, r.getContent()));
	}

	private void visitRelation(ClassRelation rel, Consumer<ClassRelation> method) {
		for (ClassRelation r : rel.getOwns()) {
			method.accept(r);
			visitRelation(r, method);
		}
	}

	private void insertShowPage(Mappers m, ProjectContent prj) {
		LOGGER.warn("insertShowPage(root)");
//		//insert pageset
//		PageSetContent ps = new PageSetContent();
//		PageSetSummary<PageSetContent> summary = m.getPageSetMapper().listSummary();
//		ps.setPageSetName(PAGESET_SHOW);
//		ps.setOwnerProjectId(prj.getProjectId());
//		m.getPageSetMapper().insert(null);
	}

	private void insertShowPage(Mappers m, ClassContent cls) {
		LOGGER.warn("insertShowPage(class) %s", cls.getClassName());
	}

	private ClassRelation getRelation(Mappers m, ProjectContent prj) {
		Map<Integer, ClassRelation> relations = new LinkedHashMap<>();
		for (ClassContent cls : m.getProjectMapper().listClassesContent(prj, 0, 0)) {
			relations.put(cls.getClassId(), new ClassRelation(cls));
		}
		for (ClassRelation owner : relations.values()) {
			ClassContent cls = owner.clazz;
			for (FieldContent fld : m.getClassMapper().listFieldsContent(cls, 0, 0)) {
				if (fld.getType() == DataType.SET) {
					FieldContent fref = m.getFieldMapper().getFref(fld);
					ClassRelation set = relations.get(fref.getOwnerClassId());
					owner.addSet(set, fld.isIsContainer());
				}
			}
		}
		ClassRelation root = new ClassRelation();
		for (ClassRelation r : relations.values()) {
			if (r.owner == null) {
				root.addSet(r, true);
			}
		}
		return root;
	}

	private void insertEditPageSet(Mappers m, ProjectContent prj) {
	}

}
