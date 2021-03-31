package com.markets.results.jdbcTransactions;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public final class ExcelExtractData {

	private static final int WHENBLANK = 0;

	public static List<String[]> extractInfo(File file) {
		Workbook wb = null;

		List<String[]> infoList = new ArrayList<String[]>();

		try {
			wb = WorkbookFactory.create(file);

			Sheet sheet = wb.getSheetAt(0);
			wb.setSheetName(wb.getSheetIndex(sheet), "PageTwo");

			for (Row row : sheet) {
				if (isRowEmpty(row)) {
					continue;
				}

				List<Cell> cells = new ArrayList<Cell>();

				int lastColumn = Math.max(row.getLastCellNum(), 5);

				for (int cn = 0; cn < lastColumn; cn++) {
					Cell c = row.getCell(cn, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
					cells.add(c);
				}

				String[] cellvalues = extractInfoFromCell(cells);
				infoList.add(cellvalues);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (wb != null) {
				try {
					wb.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		return infoList;
	}

	private static String[] extractInfoFromCell(List<Cell> cells) {

		String[] cellValues = new String[5];
		if(getCellValue(cells.get(0))!=null){
		cellValues[0] = getCellValue(cells.get(0));

		cellValues[1] = getCellValue(cells.get(1));

		cellValues[2] = getCellValue(cells.get(2));

		cellValues[3] = getCellValue(cells.get(3));

		cellValues[4] = getCellValue(cells.get(4));

		return cellValues;}

		return new String[0];
	}

	private static String getCellValue(Cell cell) {
		String val = "";
		if(cell!=null){
		switch (cell.getCellType()) {
		case NUMERIC:
			val = String.valueOf(cell.getNumericCellValue());
			break;
		case STRING:
			val = cell.getStringCellValue();
			break;
		case BLANK:
			break;
		case BOOLEAN:
			val = String.valueOf(cell.getBooleanCellValue());
			break;
		case ERROR:
			break;
		case FORMULA:
			break;
		case _NONE:
			break;
		default:
			break;
		}

		return val;} return String.valueOf(WHENBLANK);
	}

	private static boolean isRowEmpty(Row row) {
		boolean isEmpty = true;
		DataFormatter dataFormatter = new DataFormatter();

		if (row != null) {
				for (Cell cell : row) {
				if (dataFormatter.formatCellValue(cell).trim().length() > 0) {
					isEmpty = false;
					break;
				}
			}
		}
		return isEmpty;
	}

	public static void writeToExcel(List<String[]> cellValues, File outputFile) throws IOException {

			String baseDir = ".";
		String reportB = baseDir + "/localDB/DATA1.xls";
		Workbook wb = WorkbookFactory.create(new File(reportB));

				OutputStream outputStream = new FileOutputStream(outputFile);
		Sheet sheet = wb.createSheet();
		sheet = wb.getSheetAt(1);
		Sheet sheetx = wb.getSheetAt(0);
	  wb.setSheetName(wb.getSheetIndex(sheetx), "PageTwo_TEMPORALDATA");
	  wb.setSheetName(wb.getSheetIndex(sheet), "PageThree_FINALDATA");
		int rows = cellValues.size();
		int cells = cellValues.get(0).length;

		for (int i = 0; i < rows; i++) {
			Row row = sheet.createRow(i);

			for (int j = 0; j < cells; j++) {
				Cell cellX = row.createCell(j + 0);
				if (j < 5) {
					cellX.setCellValue(cellValues.get(i)[j]);
				} else {
					double zed = Double.valueOf(cellValues.get(i)[j]);
					cellX.setCellValue(zed);
					System.out.print(zed);
				}
			}
		}
		wb.write(outputStream);
		wb.close();
	}
}
