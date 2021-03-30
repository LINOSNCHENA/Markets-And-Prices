package com.markets.results.controller;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.Month;
import java.util.List;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;


import com.markets.results.helper.ExcelMarkets;
import com.markets.results.helper.helper1one;
import com.markets.results.helper.helper2Two;
import com.markets.results.helper.helper3three;
import com.markets.results.message.ResponseMessage;
import com.markets.results.model.Markets;
import com.markets.results.service.ExcelMarketService;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

//@Controller
import org.springframework.web.bind.annotation.RequestMapping;  
import org.springframework.web.bind.annotation.RestController;  
@RestController  
@CrossOrigin(origins = { "http://localhost:4200", "http://localhost:4400", "http://localhost:4600",
    "http://localhost:8081" })
@RequestMapping("/api/markets")
public class ExcelMarketsController {

  @Autowired
  ExcelMarketService fileService;

  // SECOND-1
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

  // FOUR-3
  @PostMapping(path = "/updatedatabase/{year}/{month}/{day}") // GET #2A
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
        System.out.println(result+"-1|2-"+result2+"Two Files is deleted!");
    } else {
        System.out.println("Sorry, unable to delete the file.");
    }
} catch (IOException e) {
    e.printStackTrace();
}
try { 
  File myFile = new File(reportA2);
  File myFile2 = new File(reportC2);
  if (myFile.createNewFile()){
   System.out.println("2 Files is created!");
  }else{
   System.out.println("File already exists.");
  }

 } catch (IOException e) {
  e.printStackTrace();
 }
   
    String dateUpdated = "Z" + year + "" + month + "" + day;
 
    if(month.length()<2){month="0"+month;}
    if(day.length()<2){day="0"+day;}
 
    String sourceFile = "https://www.ote-cr.cz/pubweb/attachments/27/" + year + 
    "/month" + month + "/day" + day + "/" + day + "_" + month + "_" + year + "_EN.xls";

    String destinationFile = "./LocalDB/" + dateUpdated + "Dated.xls";
    String destinationFile1 = "./localDB/" + "DATAONE.xls";
    String destinationFile2 = "./localDB/" + "DATAONE.xlsx";


    System.out.println("");
    System.out.println("---------------------------");
    System.out.println("=====|_Start_Downloading_Process_1_|=========");
     System.out.println("");

    System.out.println("");
    try {
      FileUtils.copyURLToFile(new URL(sourceFile), new File(destinationFile), 10500, 10500);
      FileUtils.copyURLToFile(new URL(sourceFile), new File(destinationFile1), 10500, 10500);
      FileUtils.copyURLToFile(new URL(sourceFile), new File(destinationFile2), 10500, 10500);

      System.out.println("File source #1 : "+sourceFile);
      System.out.println("File destin #2 : "+destinationFile);
      System.out.println("File destin #3 : "+destinationFile1);
      System.out.println("File Dated  #4 : "+dateUpdated);

    } catch (IOException e) {
      e.printStackTrace();
    }
    System.out.println("");
    System.out.println("=====|_Completed_Downloading_Process_2_|=========");
    System.out.println("---------------------------");

    /// PartTwo
    helper1one firstTask = new helper1one();
    System.out.println("");
    System.out.println("===============|HELPER_ONE_Completed|=============\n");
    firstTask.fromWebAPI2Excel();
    System.out.println("===============|HELPER_TWO_Completed|=============\n");
    List<String[]> cellValues = helper2Two.extractInfo(new File(reportA2));
    // cellValues.forEach(c -> System.out.println(c[0] + ", " + c[1] + ", " + c[2]
    //  + ", " + c[3] + ", "  + c[4]));
    helper2Two.writeToExcel(cellValues, new File(reportC2));
    System.out.println("\n=============|HELPER_THREE_Completed|============\n");
    System.out.println("");
    helper3three thirdTask = new helper3three();
    thirdTask.pushExcelToMySql();
    System.out.println("======================|ENDED|========================");

    if (cellValues.size() > 0) {
      try {    
        message = "Uploaded the selected file successfully! Number of Records: "+
        cellValues.size()+"-| Dated : "+year+"|"+month+"|"+day;
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
      } catch (Exception e) {
        message = "Could not upload correct format of the file: "+e;
        return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
      }
    }
    message = "Please check the condition of your excel file!";
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(message));
  }
}
