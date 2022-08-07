package com.github.vvorks.builder.server.component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.github.vvorks.builder.server.domain.ClassContent;
import com.github.vvorks.builder.server.domain.DataType;
import com.github.vvorks.builder.server.domain.FieldContent;
import com.github.vvorks.builder.server.domain.LayoutType;
import com.github.vvorks.builder.server.domain.PageContent;
import com.github.vvorks.builder.server.domain.PageSetContent;
import com.github.vvorks.builder.server.domain.ProjectContent;
import com.github.vvorks.builder.server.mapper.Mappers;
import com.github.vvorks.builder.shared.common.lang.Asserts;
import com.github.vvorks.builder.shared.common.lang.Iterables;

@Component
public class PageBuilder {

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

	@Autowired
	private Mappers mappers;

	public void process() throws IOException {
		List<ProjectContent> list = mappers.getProjectMapper().listAll();
		for (ProjectContent c : list) {
			processProject(c);
		}
	}

	private void processProject(ProjectContent prj) {
		List<PageSetContent> sets = mappers.getProjectMapper().listPageSetsContent(prj, 0, 0);
		//delete previous outputs
		for (PageSetContent s : sets) {
			String setName = s.getPageSetName();
			if (PAGESET_SHOW.equals(setName) || PAGESET_EDIT.equals(setName)) {
				mappers.getPageSetMapper().delete(s);
			}
		}
		insertShowPageSet(prj);
	}

	private void insertShowPageSet(ProjectContent prj) {
		ClassRelation rel = getRelation(prj);
		PageSetContent ps = insertPageSet(prj, PAGESET_SHOW);
		insertShowPage(ps, prj, rel);
		visitRelation(rel, r -> insertShowPage(ps, r.getContent(), rel));
	}

	private void visitRelation(ClassRelation rel, Consumer<ClassRelation> method) {
		for (ClassRelation r : rel.getOwns()) {
			method.accept(r);
			visitRelation(r, method);
		}
	}

	private PageSetContent insertPageSet(ProjectContent prj, String pageSetName) {
		//insert pageset
		PageSetContent ps = new PageSetContent();
		ps.setPageSetId(mappers.newPageSetId());
		ps.setPageSetName(pageSetName);
		ps.setOwnerProjectId(prj.getProjectId());
		ps.setTitle(pageSetName + " " + prj.getProjectName());
		mappers.getPageSetMapper().insert(ps);
		return ps;
	}

	private double NA = LayoutBuilder.NA;

	private void insertShowPage(PageSetContent ps, ProjectContent prj, ClassRelation rel) {
		//insert page
		PageContent pg = new PageContent();
		pg.setPageId(mappers.newPageId());
		pg.setOwnerPageSetId(ps.getPageSetId());
		pg.setContextClassId(null);
		pg.setWidth(0);
		pg.setHeight(0);
		mappers.getPageMapper().insert(pg);
		//build layout
		LayoutBuilder b = new LayoutBuilder(pg, "em", mappers);
		//insert root layouts
		b.enter(LayoutType.BASIC_LAYOUT, "frame");
			insertOwnsLists(b, rel);
		b.leave();
		b.finish();
	}

	private void insertOwnsLists(LayoutBuilder b, ClassRelation rel) {
		int left;
		int width;
		Iterable<ClassContent> classes = Iterables.from(rel.getOwns(), (r) -> r.getContent());
		b.enter(LayoutType.BASIC_LAYOUT, "head");
			b.locate(0, 0, 0, NA, NA, 2);
			left = 0;
			width = 10;
			for (ClassContent cls : classes) {
				b.enter(LayoutType.TAB, cls.getClassName());
					b.locate(left, 0, NA, 0, width, NA);
					b.related("../body/" + cls.getClassName());
				b.leave();
				left += width;
			}
		b.leave();
		b.enter(LayoutType.PILED_LAYOUT, "body");
			b.locate(0, 2, 0, 0, NA, NA);
			for (ClassContent cls : classes) {
				List<FieldContent> fields = mappers.getClassMapper().listFieldsContent(cls, 0, 0);
				b.enter(LayoutType.BASIC_LAYOUT, cls.getClassName());
					b.enter(LayoutType.BASIC_LAYOUT, "head");
						b.locate(0, 0, 0, NA, NA, 2);
						b.related("body");
						left = 0;
						for (FieldContent fld : fields) {
							width = 10; //TODO 仮。本当はField書式から設定
							b.enter(LayoutType.LABEL, fld.getFieldName());
								b.locate(left, 0, NA, 0, width, NA);
								b.field(fld);
							b.leave();
							left += width;
						}
					b.leave();
					b.enter(LayoutType.BASIC_LAYOUT, "body");
						b.locate(0, 2, 0, 0, NA, NA);
						left = 0;
						for (FieldContent fld : fields) {
							width = 10; //TODO 仮。本当はField書式から設定
							b.enter(LayoutType.FIELD, fld.getFieldName());
								b.locate(left, 0, NA, 0, width, NA);
								b.field(fld);
							b.leave();
							left += width;
						}
					b.leave();
				b.leave();
			}
		b.leave();
	}

	private void insertShowPage(PageSetContent ps, ClassContent cls, ClassRelation rel) {
		//insert page
		PageContent pg = new PageContent();
		pg.setPageId(mappers.newPageId());
		pg.setOwnerPageSetId(ps.getPageSetId());
		pg.setContextClassId(cls.getClassId());
		pg.setWidth(0);
		pg.setHeight(0);
		mappers.getPageMapper().insert(pg);
		//TODO insert layouts
	}

	private ClassRelation getRelation(ProjectContent prj) {
		Map<Integer, ClassRelation> relations = new LinkedHashMap<>();
		for (ClassContent cls : mappers.getProjectMapper().listClassesContent(prj, 0, 0)) {
			relations.put(cls.getClassId(), new ClassRelation(cls));
		}
		for (ClassRelation owner : relations.values()) {
			ClassContent cls = owner.clazz;
			for (FieldContent fld : mappers.getClassMapper().listFieldsContent(cls, 0, 0)) {
				if (fld.getType() == DataType.SET) {
					FieldContent fref = mappers.getFieldMapper().getFref(fld);
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
