package com.github.vvorks.builder;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.github.vvorks.builder.common.lang.Factory;
import com.github.vvorks.builder.common.logging.Logger;
import com.github.vvorks.builder.server.common.logging.Slf4jLogger;
import com.github.vvorks.builder.server.common.sql.SqlHelper;
import com.github.vvorks.builder.server.common.sql.SqliteHelper;
import com.github.vvorks.builder.server.extender.SqlWriter;
import com.github.vvorks.builder.server.extender.SqliteWriter;

@SpringBootTest
class BuilderApplicationTests {

	@BeforeAll
	public static void setup() {
		Factory.configure()
				.bindTo(Logger.class, a -> new Slf4jLogger((Class<?>) a[0]))
				.bindIn(SqlHelper.class,	a -> new SqliteHelper())
				.bindIn(SqlWriter.class,	a -> new SqliteWriter())
				;
	}

	@Test
	public void test1() {
		assertTrue(true);
	}

}
