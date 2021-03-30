package com.markets.results.helper;

import com.markets.results.model.Markets;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class ExcelMarkets {

  public static String TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
  static String[] HEADERs = { "Id", "Hours", "Tradedvolume", "Averageprice",
   "Minimumprice", "Maximumprice", "Lastprice", };
  static String SHEET = "IM Results";

  public static boolean hasExcelFormat(MultipartFile file) {
    if (!TYPE.equals(file.getContentType())) {
      return false;
    }
    return true;
  }

  public static ByteArrayInputStream marketsToExcel(List<Markets> markets) {
    try (
      Workbook workbook = new XSSFWorkbook(); 
    ByteArrayOutputStream out = new ByteArrayOutputStream();) 
    
    {
      Sheet sheet = workbook.createSheet(SHEET); 

      // Header
      Row headerRow = sheet.createRow(0);
      for (int col = 0; col < HEADERs.length; col++) {
        Cell cell = headerRow.createCell(col);
        cell.setCellValue(HEADERs[col]);
      }
      int rowIdx = 1;
      for (Markets market : markets) {
        Row row = sheet.createRow(rowIdx++);
        row.createCell(0).setCellValue(market.getHour());
        row.createCell(1).setCellValue(market.getTradedvolume());
        row.createCell(2).setCellValue(market.getAverageprice());
        row.createCell(3).setCellValue(market.getMinimumprice());
        row.createCell(4).setCellValue(market.getMaximumprice());
        row.createCell(5).setCellValue(market.getLastprice());
      }

      workbook.write(out);
      return new ByteArrayInputStream(out.toByteArray());
    } catch (IOException e) {
      throw new RuntimeException("fail to import data to Excel file: " + e.getMessage());
    }

  }

  public static List<Markets> excelToMarkets(InputStream is) {
    try {
      Workbook workbook = new XSSFWorkbook(is);
   //   Workbook workbook = WorkbookFactory.create(new File(is));
      Sheet sheet = workbook.getSheet(SHEET);

      Iterator<Row> rows = sheet.iterator();

      List<Markets> markets = new ArrayList<Markets>();

      int rowNumber = 0;
      while (rows.hasNext()) {
        Row currentRow = rows.next();

        // skip header
        if (rowNumber == 0) {
          rowNumber++;
          continue;
        }
        Iterator<Cell> cellsInRow = currentRow.iterator();
        Markets market = new Markets();

        int cellIdx = 0;
        while (cellsInRow.hasNext()) {
          Cell currentCell = cellsInRow.next();
          switch (cellIdx) {
          case 0:
            Long obj1 = (long) currentCell.getNumericCellValue();
            Long obj3 = (long) 0.0;
            if (currentCell.getNumericCellValue() % 1 == 0) {
              market.setHour(obj1);
            } else {
              market.setHour(obj3);
            }
            break;
          case 1:
            Double obj = currentCell.getNumericCellValue();
            Double obj2 = (Double) 0.0;
            if (currentCell.getNumericCellValue() % 1 == 0) {
              market.setTradedvolume(obj);
            } else {
              market.setTradedvolume(obj2);
            }
            break;
          case 2:
            obj = currentCell.getNumericCellValue();
            obj2 = 0.0;
            if (currentCell.getNumericCellValue() % 1 == 0) {
              market.setAverageprice(obj);
            } else {
              market.setAverageprice(obj2);
            }
            break;
          case 3:
            obj = currentCell.getNumericCellValue();
            obj2 = 0.0;
            if (currentCell.getNumericCellValue() % 1 == 0) {
              market.setMinimumprice(obj);
            } else {
              market.setMinimumprice(obj2);
            }
            break;
          case 4:
            obj = currentCell.getNumericCellValue();
            obj2 = 0.0;
            if (currentCell.getNumericCellValue() % 1 == 0) {
              market.setMaximumprice(obj);
            } else {
              market.setMaximumprice(obj2);
            }
            break;
          case 5:
            obj = currentCell.getNumericCellValue();
            obj2 = 0.0;
            if (currentCell.getNumericCellValue() % 1 == 0) {
              market.setLastprice(obj);
            } else {
              market.setLastprice(obj2);
            }
            break;
          default:
            break;
          }
          cellIdx++;
        }
        markets.add(market);
      }
      workbook.close();
      return markets;
    } catch (IOException e) {
      throw new RuntimeException("fail to parse Excel file: " + e.getMessage());
    }
  }
}
