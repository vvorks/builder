package com.github.vvorks.builder.server.component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import org.springframework.stereotype.Component;

import com.github.vvorks.builder.server.domain.ClassContent;
import com.github.vvorks.builder.server.domain.DataType;
import com.github.vvorks.builder.server.domain.FieldContent;
import com.github.vvorks.builder.server.domain.PageContent;
import com.github.vvorks.builder.server.domain.PageSetContent;
import com.github.vvorks.builder.server.domain.ProjectContent;
import com.github.vvorks.builder.server.mapper.Mappers;
import com.github.vvorks.builder.shared.common.lang.Asserts;
import com.github.vvorks.builder.shared.common.lang.Iterables;
import com.github.vvorks.builder.shared.common.logging.Logger;

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
	}

	private void insertShowPageSet(Mappers m, ProjectContent prj) {
		ClassRelation rel = getRelation(m, prj);
		PageSetContent ps = insertPageSet(m, prj, PAGESET_SHOW);
		insertShowPage(m, ps, prj);
		visitRelation(rel, r -> insertShowPage(m, ps, r.getContent()));
	}

	private void visitRelation(ClassRelation rel, Consumer<ClassRelation> method) {
		for (ClassRelation r : rel.getOwns()) {
			method.accept(r);
			visitRelation(r, method);
		}
	}

	private PageSetContent insertPageSet(Mappers m, ProjectContent prj, String pageSetName) {
		//insert pageset
		PageSetContent ps = new PageSetContent();
		ps.setPageSetId(m.newPageSetId());
		ps.setPageSetName(pageSetName);
		ps.setOwnerProjectId(prj.getProjectId());
		ps.setTitle(pageSetName + " " + prj.getProjectName());
		m.getPageSetMapper().insert(ps);
		return ps;
	}

	private void insertShowPage(Mappers m, PageSetContent ps, ProjectContent prj) {
		//insert page
		PageContent pg = new PageContent();
		pg.setPageId(m.newPageId());
		pg.setOwnerPageSetId(ps.getPageSetId());
		pg.setContextClassId(null);
		pg.setWidth(0);
		pg.setHeight(0);
		m.getPageMapper().insert(pg);
		//TODO insert layouts
	}

	private void insertShowPage(Mappers m, PageSetContent ps, ClassContent cls) {
		//insert page
		PageContent pg = new PageContent();
		pg.setPageId(m.newPageId());
		pg.setOwnerPageSetId(ps.getPageSetId());
		pg.setContextClassId(cls.getClassId());
		pg.setWidth(0);
		pg.setHeight(0);
		m.getPageMapper().insert(pg);
		//TODO insert layouts
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

}
