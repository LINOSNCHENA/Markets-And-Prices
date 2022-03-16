package com.markets.results.controller;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import com.markets.results.jdbctransactions.ExcelDeleteRows;
import com.markets.results.jdbctransactions.ExcelExtractData;
import com.markets.results.jdbctransactions.ExcelToMySql;
import com.markets.results.message.ResponseMessage;
import com.markets.results.model.Markets;
import com.markets.results.service.MarketService;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = { "http://localhost:4200", "http://localhost:4400", "http://localhost:4600", "http://172.16.184.235:4600" })
@RequestMapping("/api/markets")
public class MarketsController {

  @Autowired
  MarketService fileService;

  // Function One
  @GetMapping("/listed")
  public ResponseEntity<List<Markets>> getAllMarkets() {
    try {
      List<Markets> markets = fileService.getAllMarkets();

      if (markets.isEmpty()) {
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
      }

      return new ResponseEntity<>(markets, HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  // Function Two
  @PostMapping(path = "/updatedatabase/{year}/{month}/{day}")
  public ResponseEntity<ResponseMessage> recieveDateValues(@PathVariable("year") String year,
      @PathVariable("month") String month, @PathVariable("day") String day) throws IOException {
    String message = "";
    String baseDir = ".";
    String reportA2 = baseDir + "/LocalDB/DATA1.xls";
    String reportC2 = baseDir + "/LocalDB/DATA2.xls";
    try {
      boolean result = Files.deleteIfExists(Paths.get(reportA2));
      boolean result2 = Files.deleteIfExists(Paths.get(reportC2));
      if (result) {
        System.out.println("");
        System.out.println(result + "-1|2-" + result2 + " : Two Files is deleted!");
      } else {
        System.out.println("Sorry, unable to erase past records.");
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    try {
      File myFile1 = new File(reportA2);
      File myFile2 = new File(reportC2);
      if (myFile1.createNewFile() || myFile2.createNewFile()) {
        System.out.println("The two files have been created!");
      } else {
        System.out.println("The two files already exists.");
      }

    } catch (IOException e) {
      e.printStackTrace();
    }

    if (month.length() < 2) {
      month = "0" + month;
    }
    if (day.length() < 2) {
      day = "0" + day;
    }

    String sourceFile = "https://www.ote-cr.cz/pubweb/attachments/27/" + year + "/month" + month + "/day" + day + "/"
        + day + "_" + month + "_" + year + "_EN.xls";

    String destinationFile1 = "./localDB/" + "DATAONE.xls";
    String destinationFile2 = "./localDB/" + "DATAONE.xlsx";

    try {
      FileUtils.copyURLToFile(new URL(sourceFile), new File(destinationFile1), 10500, 10500);
      FileUtils.copyURLToFile(new URL(sourceFile), new File(destinationFile2), 10500, 10500);

    } catch (IOException e) {
      e.printStackTrace();
    }
    // 1
    ExcelDeleteRows firstTask = new ExcelDeleteRows();
    firstTask.fromWebAPI2Excel();
    // 2
    List<String[]> cellValues = ExcelExtractData.extractInfo(new File(reportA2));
    ExcelExtractData.writeToExcel(cellValues, new File(reportC2));
    // 3
    ExcelToMySql thirdTask = new ExcelToMySql();
    thirdTask.pushExcelToMySql();

    if (cellValues.size() > 0) {
      try {
        message = "Uploaded the cells data successfully! Number of Records: " + cellValues.size() + "-| Dated : "
            + year + "|" + month + "|" + day;
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
      } catch (Exception e) {
        message = "Could not upload correct format of the file: " + e;
        return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
      }
    }
    message = "Please check the condition of your excel file!";
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(message));
  }
}
