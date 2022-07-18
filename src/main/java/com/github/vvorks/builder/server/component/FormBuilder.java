package com.github.vvorks.builder.server.component;

import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Component;

import com.github.vvorks.builder.common.logging.Logger;
import com.github.vvorks.builder.server.domain.ProjectContent;
import com.github.vvorks.builder.server.mapper.Mappers;

@Component
public class FormBuilder {

	private static final Logger LOGGER = Logger.createLogger(SourceWriter.class);

	public void process() throws IOException {
		Mappers m = Mappers.get();
		List<ProjectContent> list = m.getProjectMapper().listAll();
		for (ProjectContent c : list) {
			if ("com.github.vvorks.builder".equals(c.getProjectName())) {
				m.getProjectMapper().delete(c);
			}
		}
	}

}
