package com.markets.results.helper;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class helper3three {

    public void pushExcelToMySql() {

        String jdbcURL = "jdbc:mysql://localhost:3306/PRESLY";
        String username = "root";
        String password = "Monze@2019";

             String baseDir = ".";
        String reportC = baseDir + "/localDB/DATA2.xls";

        int batchSize = 20;
        Connection connection = null;

        try {
            long start = System.currentTimeMillis();

            Workbook workbook = WorkbookFactory.create(new File(reportC));

            Sheet firstSheet = workbook.getSheetAt(1);
            Iterator<Row> rowIterator = firstSheet.iterator();

            connection = DriverManager.getConnection(jdbcURL, username, password);
            connection.setAutoCommit(false);
            String deleteSql = "DELETE from MARKETS where Hour > -1";
            PreparedStatement statementx = connection.prepareStatement(deleteSql);
            statementx.executeUpdate(deleteSql);

            String insertSql = "INSERT INTO MARKETS (HOUR,TRADEDVOLUME, AVERAGEPRICE,"
                    + "MINIMUMPRICE, MAXIMUMPRICE, LASTPRICE) VALUES (?,?,?,?,?,?)";
            PreparedStatement statement = connection.prepareStatement(insertSql);

            int count = 0;

           // System.out.println("\n============|MYSQL-ISSUE|==============");
            rowIterator.next();
            while (rowIterator.hasNext()) {
                Row nextRow = rowIterator.next();
                Iterator<Cell> cellIterator = nextRow.cellIterator();
                System.out.println("==========| Record-Number |============= >>" + nextRow.getRowNum());
                while (cellIterator.hasNext()) {
                    Cell nextCell = cellIterator.next();
                    System.out.println(
                            "=======|Data-Details|======= :" + nextCell.getColumnIndex() + " - Data : " + nextCell);
                    int columnIndex = nextCell.getColumnIndex();
                    switch (columnIndex) {
                    case 0:
                        double HOUR = Double.valueOf(nextCell.getStringCellValue());
                        statement.setDouble(1, HOUR);
                    case 1:
                        if (!nextCell.getStringCellValue().isEmpty()) {
                            double VOLUMETRADED = Double.valueOf(nextCell.getStringCellValue());
                            statement.setDouble(2, VOLUMETRADED);
                        } else {
                            double VOLUMETRADED = 0;
                            statement.setDouble(2, VOLUMETRADED);
                        }
                    case 2:
                        double AVERAGEPRICE = Double.valueOf(nextCell.getStringCellValue());
                        statement.setDouble(3, AVERAGEPRICE);
                    case 3:
                        double MINIMUMPRICE = Double.valueOf(nextCell.getStringCellValue());
                        statement.setDouble(4, MINIMUMPRICE);
                    case 4:
                        double MAXIMUMPRICE = Double.valueOf(nextCell.getStringCellValue());
                        statement.setDouble(5, MAXIMUMPRICE);
                    case 5:
                        double LASTPRICE = Double.valueOf(nextCell.getStringCellValue());
                        statement.setDouble(6, LASTPRICE);
                    }
                }

                statement.addBatch();

                if (count % batchSize == 0) {
                    statement.executeBatch();
                }
            }
            workbook.close();
            statement.executeBatch();

            connection.commit();
            connection.close();

            long end = System.currentTimeMillis();
            System.out.println("");
            System.out.printf("MYSQL Importation process completed in %d ms\n", (end - start));
            System.out.println("");
            System.out.println("|=================================================================|");
        } catch (IOException ex1) {
            System.out.println("Error reading file");
            ex1.printStackTrace();
        } catch (SQLException ex2) {
            System.out.println("Database error");
            ex2.printStackTrace();
        }

    }

}
