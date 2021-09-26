package com.github.vvorks.builder.server.component;

import java.io.File;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.stereotype.Component;

import com.github.vvorks.builder.common.lang.Asserts;
import com.github.vvorks.builder.common.lang.Factory;
import com.github.vvorks.builder.common.lang.Strings;
import com.github.vvorks.builder.common.util.Logger;
import com.github.vvorks.builder.server.common.io.Ios;
import com.github.vvorks.builder.server.common.poi.Cells;
import com.github.vvorks.builder.server.domain.DataType;
import com.github.vvorks.builder.server.extender.ClassExtender;
import com.github.vvorks.builder.server.extender.FieldExtender;

@Component
public class XlsxLoader {

	private static final Class<?> THIS = XlsxLoader.class;
	private static final Logger LOGGER = Factory.newInstance(Logger.class, THIS);

	private static final String EOL = "\n";

	private static final Map<DataType, String> SQLITE_TYPES = new EnumMap<>(DataType.class);
	static {
		String i = "INTEGER";
		String r = "REAL";
		String t = "TEXT";
		String n = "NUMERIC";
		SQLITE_TYPES.put(DataType.BOOLEAN, i);
		SQLITE_TYPES.put(DataType.INTEGER, i);
		SQLITE_TYPES.put(DataType.REAL, r);
		SQLITE_TYPES.put(DataType.DATE, i);
		SQLITE_TYPES.put(DataType.NUMERIC, n);
		SQLITE_TYPES.put(DataType.STRING, t);
	}

	private static final Map<DataType, Integer> JAVA_SQL_TYPES = new EnumMap<>(DataType.class);
	static {
		JAVA_SQL_TYPES.put(DataType.BOOLEAN, java.sql.Types.BOOLEAN);
		JAVA_SQL_TYPES.put(DataType.INTEGER, java.sql.Types.INTEGER); //TODO 仮
		JAVA_SQL_TYPES.put(DataType.REAL, java.sql.Types.REAL); //TODO 仮
		JAVA_SQL_TYPES.put(DataType.DATE, java.sql.Types.BIGINT);
		JAVA_SQL_TYPES.put(DataType.NUMERIC, java.sql.Types.NUMERIC);
		JAVA_SQL_TYPES.put(DataType.STRING, java.sql.Types.VARCHAR);
	}

	private static class ColInfo {
		public final String name;
		public final DataType type;
		public ColInfo(String name, DataType type) {
			this.name = name;
			this.type = type;
		}
		public String sqliteName() {
			return FieldExtender.COLUMN_PREFIX + Strings.toUpperSnake(name);
		}
		public String sqliteType() {
			return SQLITE_TYPES.get(type);
		}
	}

	public File process(File xlsxFile) throws Exception {
		File dbFile = new File(Ios.getBaseName(xlsxFile) + ".db");
		try (
			Workbook book = WorkbookFactory.create(xlsxFile);
			Connection con = DriverManager.getConnection("jdbc:sqlite:" + dbFile.getName())
		) {
			for (int i = 0; i < book.getNumberOfSheets(); i++) {
				Sheet sheet = book.getSheetAt(i);
				process(sheet, con);
			}
		}
		return dbFile;
	}

	private void process(Sheet sheet, Connection con) throws SQLException {
		//行列情報取得
		int rowFirst = sheet.getFirstRowNum();
		if (rowFirst < 0) {
			return;
		}
		rowFirst++;
		int rowLast = sheet.getLastRowNum() + 1;
		Row headerRow = sheet.getRow(rowFirst - 1);
		int colFirst = headerRow.getFirstCellNum();
		if (colFirst < 0) {
			return;
		}
		int colLast = headerRow.getLastCellNum();
		if (colLast - colFirst <= 0) {
			return;
		}
		//ヘッダ情報取得
		List<ColInfo> cols = new ArrayList<>();
		for (int c = colFirst; c < colLast; c++) {
			Cell cell = headerRow.getCell(c);
			String value = Cells.getString(cell, null);
			Asserts.requireNotEmpty(value);
			String[] a = value.split(":");
			String name = a[0];
			DataType type = (a.length > 1) ? DataType.valueOf(a[1]) : DataType.STRING;
			cols.add(new ColInfo(name, type));
		}
		//テーブル作成
		String tableName = ClassExtender.TABLE_PREFIX + Strings.toUpperSnake(sheet.getSheetName());
		StringBuilder sql = new StringBuilder();
		//CreateTable文
		sql.append("CREATE TABLE ").append(tableName).append("(").append(EOL);
		//カラム定義
		for (ColInfo col : cols) {
			sql.append("    ").append(col.sqliteName()).append(" ").append(col.sqliteType());
			sql.append(",").append(EOL);
		}
		//PK定義（先頭カラム、及びその後続に位置し名称末尾が"Id"であるカラムをキーとする）
		sql.append("    ").append("PRIMARY KEY (").append(cols.get(0).sqliteName());
		for (ColInfo col : cols.subList(1, cols.size())) {
			if (!col.name.endsWith("Id")) {
				break;
			}
			sql.append(",").append(col.sqliteName());
		}
		sql.append(")").append(EOL);
		sql.append(")").append(EOL);
		//CREATE TABLE SQL実行
		try (
			PreparedStatement stmt = con.prepareStatement(sql.toString())
		) {
			stmt.execute();
		}
		//INSERT文作成
		int insertPos = sql.length();
		sql.append("INSERT INTO ").append(tableName).append(" VALUES (").append(EOL);
		sql.append("    ").append("?");
		sql.append(Strings.repeat(",?", cols.size() - 1));
		sql.append(EOL);
		sql.append(")").append(EOL);
		//データ挿入SQL実行
		try (
			PreparedStatement stmt = con.prepareStatement(sql.substring(insertPos))
		) {
			for (int r = rowFirst; r < rowLast; r++) {
				Row row = sheet.getRow(r);
				stmt.clearParameters();
				for (int c = colFirst; c < colLast; c++) {
					Cell cell = row.getCell(c);
					ColInfo col = cols.get(c - colFirst);
					DataType colType = col.type;
					int index = c - colFirst + 1;
					setParameter(stmt, index, cell, colType);
				}
				stmt.execute();
			}
		}
		LOGGER.debug(sql.toString());
	}

	private void setParameter(PreparedStatement stmt, int index, Cell cell, DataType colType) throws SQLException {
		if (cell == null) {
			stmt.setNull(index, JAVA_SQL_TYPES.get(colType));
		} else {
			switch (colType) {
			case BOOLEAN:
				stmt.setBoolean(index, Cells.getBoolean(cell, false));
				break;
			case INTEGER:
				stmt.setInt(index, Cells.getInt(cell, 0));
				break;
			case DATE:
				stmt.setLong(index, Cells.getLong(cell, 0));
				break;
			case REAL:
				stmt.setDouble(index, Cells.getDouble(cell, 0.0d));
				break;
			case NUMERIC:
				stmt.setBigDecimal(index, BigDecimal.valueOf(Cells.getDouble(cell, 0.0d)));
				break;
			case STRING:
				stmt.setString(index, Cells.getString(cell, ""));
				break;
			default:
				break;
			}
		}
	}

}
