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

	private double NA = LayoutBuilder.NA;

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

		public boolean isContained() {
			return owner != null;
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
		insertShowPage(ps, rel);
		visitRelation(rel, r -> insertShowPage(ps, r));
	}

	private ClassRelation getRelation(ProjectContent prj) {
		Map<Integer, ClassRelation> relations = new LinkedHashMap<>();
		//クラス一覧からリレーションのリストを作成
		for (ClassContent cls : mappers.getProjectMapper().listClassesContent(prj, 0, 0)) {
			relations.put(cls.getClassId(), new ClassRelation(cls));
		}
		//各クラス中のSETフィールドを元に関連付け
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
		//誰からも保有されていないクラスをトップレベルクラスとし、rootに追加
		ClassRelation root = new ClassRelation();
		for (ClassRelation r : relations.values()) {
			if (!r.isContained()) {
				root.addSet(r, true);
			}
		}
		return root;
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

	private void insertShowPage(PageSetContent ps, ClassRelation rel) {
		ClassContent cls = rel.getContent();
		Integer classId;
		List<FieldContent> fields;
		if (cls == null) {
			classId = null;
			fields = Collections.emptyList();
		} else {
			classId = cls.getClassId();
			fields = mappers.getClassMapper().listFieldsContent(cls, 0, 0);
		}
		//insert page
		PageContent pg = new PageContent();
		pg.setPageId(mappers.newPageId());
		pg.setOwnerPageSetId(ps.getPageSetId());
		pg.setContextClassId(classId);
		pg.setWidth(0);
		pg.setHeight(0);
		mappers.getPageMapper().insert(pg);
		//insert layouts
		LayoutBuilder b = new LayoutBuilder(pg, "em", mappers);
		boolean hasFields = !fields.isEmpty();
		boolean hasOwns = !Iterables.isEmpty(rel.getOwns());
		if (hasFields && hasOwns) {
			b.enter(LayoutType.PARTED_PANE, "frame");
				b.enter(LayoutType.SIMPLE_PANE, "top", "", "top");
					b.locate(null, "50%");
					insertDetail(b, rel);
				b.leave();
				b.enter(LayoutType.SIMPLE_PANE, "center", "", "center");
					b.locate(null, null);
					insertOwnsLists(b, rel);
				b.leave();
			b.leave();
		} else if (hasFields) {
			b.enter(LayoutType.SIMPLE_PANE, "frame");
				insertDetail(b, rel);
			b.leave();
		} else if (hasOwns) {
			b.enter(LayoutType.SIMPLE_PANE, "frame");
				insertOwnsLists(b, rel);
			b.leave();
		}
		b.finish();
	}

	private void insertDetail(LayoutBuilder b, ClassRelation rel) {
		int top;
		int height;
		int labelWidth;
		ClassContent cls = rel.getContent();
		b.enter(LayoutType.SIMPLE_PANE, "detail");
			b.locate(0, 0, 1, 0);
			List<FieldContent> fields = mappers.getClassMapper().listFieldsContent(cls, 0, 0);
			top = 0;
			labelWidth = 10;
			for (FieldContent fld : fields) {
				height = 2; //TODO 仮。本当はField書式から設定
				b.enter(LayoutType.LABEL, fld.getFieldName() + "Label");
					b.locate(0, top, NA, NA, labelWidth, height);
					b.refField(fld);
				b.leave();
				b.enter(LayoutType.FIELD, fld.getFieldName() + "Field");
					b.locate(labelWidth, top, 0, NA, NA, height);
					b.refField(fld);
				b.leave();
				top += height;
			}
		b.leave();
		b.enter(LayoutType.V_SCROLLBAR, "sb");
			b.locate(NA, 0, 0, 0, 1, NA);
			b.related("detail");
		b.leave();
	}

	private void insertOwnsLists(LayoutBuilder b, ClassRelation rel) {
		int left;
		int width;
		Iterable<ClassContent> classes = Iterables.from(rel.getOwns(), (r) -> r.getContent());
		b.enter(LayoutType.SIMPLE_PANE, "head");
			b.locate(0, 0, 0, NA, NA, 2);
			left = 0;
			width = 10;
			for (ClassContent cls : classes) {
				b.enter(LayoutType.TAB, cls.getClassName());
					b.locate(left, 0, NA, 0, width, NA);
					b.refClass(cls);
					b.related("../body/" + cls.getClassName());
				b.leave();
				left += width;
			}
		b.leave();
		b.enter(LayoutType.TABBED_PANE, "body");
			b.locate(0, 2, 0, 0, NA, NA);
			for (ClassContent cls : classes) {
				List<FieldContent> fields = mappers.getClassMapper().listFieldsContent(cls, 0, 0);
				b.enter(LayoutType.SIMPLE_PANE, cls.getClassName());
					b.enter(LayoutType.SIMPLE_PANE, "head");
						b.locate(0, 0, 0, NA, NA, 2);
						b.related("body");
						left = 0;
						for (FieldContent fld : fields) {
							width = 10; //TODO 仮。本当はField書式から設定
							b.enter(LayoutType.LABEL, fld.getFieldName() + "Label");
								b.locate(left, 0, NA, 0, width, NA);
								b.refField(fld);
							b.leave();
							left += width;
						}
					b.leave();
					b.enter(LayoutType.SIMPLE_PANE, "body");
						b.locate(0, 2, 0, 0);
						left = 0;
						for (FieldContent fld : fields) {
							width = 10; //TODO 仮。本当はField書式から設定
							b.enter(LayoutType.FIELD, fld.getFieldName() + "Field");
								b.locate(left, 0, NA, 0, width, NA);
								b.refField(fld);
							b.leave();
							left += width;
						}
					b.leave();
				b.leave();
			}
		b.leave();
	}

}
