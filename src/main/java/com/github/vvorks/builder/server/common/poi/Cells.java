package com.github.vvorks.builder.server.common.poi;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;

public class Cells {

	private Cells() {
	}

	public static boolean getBoolean(Cell cell, boolean defaultValue) {
		CellType type = cell.getCellType();
		if (type == CellType.FORMULA) {
			type = cell.getCachedFormulaResultType();
		}
		boolean result;
		switch (type) {
		case BOOLEAN:
			result = cell.getBooleanCellValue();
			break;
		case NUMERIC:
			result = cell.getNumericCellValue() != 0.0;
			break;
		case STRING:
			result = Boolean.valueOf(cell.getStringCellValue());
			break;
		default:
			result = defaultValue;
			break;
		}
		return result;
	}

	public static int getInt(Cell cell, int defaultValue) {
		CellType type = cell.getCellType();
		if (type == CellType.FORMULA) {
			type = cell.getCachedFormulaResultType();
		}
		int result;
		switch (type) {
		case BOOLEAN:
			result = cell.getBooleanCellValue() ? 1 : 0;
			break;
		case NUMERIC:
			result = (int) cell.getNumericCellValue();
			break;
		case STRING:
			try {
				result = Integer.parseInt(cell.getStringCellValue());
			} catch (NumberFormatException err) {
				result = defaultValue;
			}
			break;
		default:
			result = defaultValue;
			break;
		}
		return result;
	}

	public static long getLong(Cell cell, long defaultValue) {
		CellType type = cell.getCellType();
		if (type == CellType.FORMULA) {
			type = cell.getCachedFormulaResultType();
		}
		long result;
		switch (type) {
		case BOOLEAN:
			result = cell.getBooleanCellValue() ? 1 : 0;
			break;
		case NUMERIC:
			result = (long) cell.getNumericCellValue();
			break;
		case STRING:
			try {
				result = Long.parseLong(cell.getStringCellValue());
			} catch (NumberFormatException err) {
				result = defaultValue;
			}
			break;
		default:
			result = defaultValue;
			break;
		}
		return result;
	}

	public static double getDouble(Cell cell, double defaultValue) {
		CellType type = cell.getCellType();
		if (type == CellType.FORMULA) {
			type = cell.getCachedFormulaResultType();
		}
		double result;
		switch (type) {
		case BOOLEAN:
			result = cell.getBooleanCellValue() ? 1 : 0;
			break;
		case NUMERIC:
			result = cell.getNumericCellValue();
			break;
		case STRING:
			try {
				result = Double.parseDouble(cell.getStringCellValue());
			} catch (NumberFormatException err) {
				result = defaultValue;
			}
			break;
		default:
			result = defaultValue;
			break;
		}
		return result;
	}

	public static String getString(Cell cell, String defaultValue) {
		CellType type = cell.getCellType();
		if (type == CellType.FORMULA) {
			type = cell.getCachedFormulaResultType();
		}
		String result;
		switch (type) {
		case BOOLEAN:
			result = String.valueOf(cell.getBooleanCellValue());
			break;
		case NUMERIC:
			result = String.valueOf(cell.getNumericCellValue());
			break;
		case STRING:
			result = cell.getStringCellValue();
			break;
		default:
			result = defaultValue;
			break;
		}
		return result;
	}

	public static Object getValue(Cell cell, Object defaultValue) {
		CellType type = cell.getCellType();
		if (type == CellType.FORMULA) {
			type = cell.getCachedFormulaResultType();
		}
		Object result;
		switch (type) {
		case BOOLEAN:
			result = (Boolean) cell.getBooleanCellValue();
			break;
		case NUMERIC:
			result = (Double) cell.getNumericCellValue();
			break;
		case STRING:
			result = cell.getStringCellValue();
			break;
		default:
			result = defaultValue;
			break;
		}
		return result;
	}

}
