package com.github.vvorks.builder.server.component;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
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
import com.github.vvorks.builder.server.extender.ClassRelation;
import com.github.vvorks.builder.server.extender.ProjectExtender;
import com.github.vvorks.builder.server.mapper.Mappers;
import com.github.vvorks.builder.shared.common.lang.RichIterable;

@Component
public class PageBuilder {

	private static final String PAGESET_SHOW = "show";

	private static final String PAGESET_EDIT = "edit";

	private static final String SUFFIX_FIELD = "Field";

	private static final String SUFFIX_LABEL = "Label";

	private double NA = LayoutBuilder.NA;

	@Autowired
	private Mappers mappers;

	@Autowired
	private ProjectExtender projectExtender;

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
		ClassRelation rel = projectExtender.getRelation(prj);
		PageSetContent ps = insertPageSet(prj, PAGESET_SHOW);
		insertShowPage(ps, rel);
		visitRelation(rel, r -> insertShowPage(ps, r));
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
		boolean hasOwns = !RichIterable.from(rel.getOwns()).isEmpty();
		if (hasFields && hasOwns) {
			b.enter(LayoutType.PARTED_PANE, "frame");
				if (cls != null) {
					b.refClass(cls);
				}
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
				if (cls != null) {
					b.refClass(cls);
				}
				insertDetail(b, rel);
			b.leave();
		} else if (hasOwns) {
			b.enter(LayoutType.SIMPLE_PANE, "frame");
				if (cls != null) {
					b.refClass(cls);
				}
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
			//TODO Query追加してSQLレベルで絞り込み
			RichIterable<FieldContent> fields = RichIterable
					.from(mappers.getClassMapper().listFieldsContent(cls, 0, 0))
					.filter(f -> f.getType() != DataType.SET);
			top = 0;
			labelWidth = 10;
			for (FieldContent fld : fields) {
				height = 2; //TODO 仮。本当はField書式から設定
				b.enter(LayoutType.LABEL, fld.getFieldName() + SUFFIX_LABEL);
					b.locate(0, top, NA, NA, labelWidth, height);
					b.refField(fld);
				b.leave();
				b.enter(LayoutType.FIELD, fld.getFieldName() + SUFFIX_FIELD);
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
		b.enter(LayoutType.SIMPLE_PANE, "head");
			b.locate(0, 0, 0, NA, NA, 2);
			left = 0;
			width = 10;
			for (ClassRelation r : rel.getOwns()) {
				ClassContent cls = r.getContent();
				FieldContent ownerField = r.getOwnerSetField();
				String fieldName = ownerField != null ? ownerField.getFieldName() : cls.getClassName();
				b.enter(LayoutType.TAB, fieldName);
					b.locate(left, 0, NA, 0, width, NA);
					b.refClass(cls);
					b.refField(ownerField);
					b.related("../body/" + fieldName);
				b.leave();
				left += width;
			}
		b.leave();
		b.enter(LayoutType.TABBED_PANE, "body");
			b.locate(0, 2, 0, 0, NA, NA);
			for (ClassRelation r : rel.getOwns()) {
				ClassContent cls = r.getContent();
				FieldContent ownerField = r.getOwnerSetField();
				String fieldName = ownerField != null ? ownerField.getFieldName() : cls.getClassName();
				//TODO Query追加してSQLレベルで絞り込み
				Iterable<FieldContent> fields = RichIterable
						.from(mappers.getClassMapper().listFieldsContent(cls, 0, 0))
						.filter(f -> f.getType() != DataType.SET);
				b.enter(LayoutType.SIMPLE_PANE, fieldName);
					b.enter(LayoutType.SIMPLE_PANE, "head");
						b.locate(0, 0, 1, NA, NA, 2);
						b.related("list");
						left = 0;
						for (FieldContent fld : fields) {
							width = 10; //TODO 仮。本当はField書式から設定
							b.enter(LayoutType.LABEL, fld.getFieldName() + SUFFIX_LABEL);
								b.locate(left, 0, NA, 0, width, NA);
								b.refField(fld);
							b.leave();
							left += width;
						}
					b.leave();
					b.enter(LayoutType.V_LIST, "list");
						b.locate(0, 2, 1, 1);
						b.refClass(cls);
						b.refField(ownerField);
						left = 0;
						for (FieldContent fld : fields) {
							width = 10; //TODO 仮。本当はField書式から設定
							b.enter(LayoutType.FIELD, fld.getFieldName() + SUFFIX_FIELD);
								b.locate(left, 0, NA, NA, width, 2);
								b.refField(fld);
							b.leave();
							left += width;
						}
					b.leave();
					b.enter(LayoutType.V_SCROLLBAR, "vsb");
						b.locate(NA, 2, 0, 1, 1, NA);
						b.related("list");
					b.leave();
					b.enter(LayoutType.H_SCROLLBAR, "hsb");
						b.locate(0, NA, 1, 0, NA, 1);
						b.related("list");
					b.leave();
				b.leave();
			}
		b.leave();
	}

}
