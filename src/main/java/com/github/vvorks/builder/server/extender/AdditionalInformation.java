package com.github.vvorks.builder.server.extender;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.github.vvorks.builder.server.common.sql.SqlHelper;
import com.github.vvorks.builder.server.domain.ClassContent;
import com.github.vvorks.builder.server.domain.DataType;
import com.github.vvorks.builder.server.domain.FieldContent;
import com.github.vvorks.builder.server.domain.ProjectContent;
import com.github.vvorks.builder.server.mapper.Mappers;

@Component
public class AdditionalInformation {

	public static final String CLASS_NAME = "className";

	public static final String SURROGATE_COUNT = "surrogateCount";

	public static final String LAST_UPDATED_AT = "_lastUpdatedAt";

	@Autowired
	private ProjectExtender projectExtender;

	private SqlHelper sqlHelper = SqlHelper.get();

	public List<ClassContent> getAdditionalClasses(ProjectContent prj) {
		List<ClassContent> list = new ArrayList<>();
		ClassContent prjCls = new ClassContent();
		prjCls.setClassName(projectExtender.getUpperLastName(prj));
		prjCls.setOwnerProjectId(prj.getProjectId());
		list.add(prjCls);
		return list;
	}

	public List<FieldContent> getAdditionalFields(ClassContent cls) {
		Mappers m = Mappers.get();
		ProjectContent prj = m.getClassMapper().getOwner(cls);
		String prjName = projectExtender.getUpperLastName(prj);
		List<FieldContent> list = new ArrayList<>();
		//追加フィールド挿入
		if (cls.getClassName().equals(prjName)) {
			//プロジェクトクラス向け追加フィールド挿入
			FieldContent className = new FieldContent();
			className.setFieldName(CLASS_NAME);
			className.setType(DataType.STRING);
			className.setPk(true);
			list.add(className);
			FieldContent surrogateCount = new FieldContent();
			surrogateCount.setFieldName(SURROGATE_COUNT);
			surrogateCount.setType(DataType.INTEGER);
			surrogateCount.setPk(false);
			list.add(surrogateCount);
		}
		//全クラス共通の追加フィールド挿入
		FieldContent lastUpdated = new FieldContent();
		lastUpdated.setFieldName(LAST_UPDATED_AT);
		lastUpdated.setTitle("最終更新時刻"); //TODO I18N
		lastUpdated.setType(DataType.DATE);
		lastUpdated.setPk(false);
		lastUpdated.setNeedsMax(true);
		list.add(lastUpdated);
		//
		return list;
	}

	public List<String[]> getAdditionalValues(ClassContent cls) {
		Mappers m = Mappers.get();
		ProjectContent prj = m.getClassMapper().getOwner(cls);
		String prjName = projectExtender.getUpperLastName(prj);
		//追加フィールドの初期値作成
		if (cls.getClassName().equals(prjName)) {
			List<ClassContent> classes = m.getProjectMapper().listClassesContent(prj, 0, 0);
			List<String[]> list = new ArrayList<>();
			for (ClassContent c : classes) {
				String[] fld = new String[3];
				fld[0] = sqlHelper.quote(c.getClassName());
				fld[1] = "0";
				fld[2] = sqlHelper.getNow();
				list.add(fld);
			}
			return list;
		} else {
			return Collections.emptyList();
		}
	}

}
