package com.markets.results.jdbcTransactions;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class ExcelDeleteRows {
    public void fromWebAPI2Excel() throws EncryptedDocumentException, IOException {

        String baseDir = ".";
        String reportSource = baseDir + "/LocalDB/DATAONE.xls";
        String reportA = baseDir + "/LocalDB/DATA1.xls";

        Workbook workbook = WorkbookFactory.create(new File(reportSource));
        Sheet sheet = workbook.getSheetAt(0);
        workbook.setSheetName(workbook.getSheetIndex(sheet), "PageOne");
        sheet.getLastRowNum();

        sheet.setColumnWidth(0, 2000);
        sheet.setColumnWidth(1, 6000);
        sheet.setColumnWidth(2, 6000);
        sheet.setColumnWidth(3, 6000);
        sheet.setColumnWidth(4, 6000);
        sheet.setColumnWidth(5, 6000);

        for (int i = 0; i < 6; i++) {
            Row row = sheet.createRow((short) i);
            for (int j = 0; j < 6; j++) {
                if (j == 0) {
                    row.createCell(0).setCellValue("HOUR");
                }
                if (j == 1) {
                    row.createCell(1).setCellValue("TRADEVOLUME");
                }
                if (j == 2) {
                    row.createCell(2).setCellValue("AVERAGEPRICE");
                }
                if (j == 3) {
                    row.createCell(3).setCellValue("MINIMUMPRICE");
                }
                if (j == 4) {
                    row.createCell(4).setCellValue("MAXIMUMPRICE");
                }
                if (j == 5) {
                    row.createCell(5).setCellValue("LASTPRICE");
                }
            }
        }
        // Unwanted rows
        sheet.removeRow(sheet.getRow(0));
        sheet.removeRow(sheet.getRow(1));
        sheet.removeRow(sheet.getRow(2));
        sheet.removeRow(sheet.getRow(3));
        sheet.removeRow(sheet.getRow(4));
        sheet.removeRow(sheet.getRow(30));
        FileOutputStream fileOut = null;
        try {
            fileOut = new FileOutputStream(reportA);
            workbook.write(fileOut);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                fileOut.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
