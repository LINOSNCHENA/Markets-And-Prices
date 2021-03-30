package com.markets.results.service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.markets.results.helper.ExcelMarkets;
import com.markets.results.model.Markets;
import com.markets.results.repository.MarketsRepository;

@Service
public class ExcelMarketService {
  @Autowired
  MarketsRepository repository;

  // First save
  public void save(MultipartFile file) {
    try {
      List<Markets> markets = ExcelMarkets.excelToMarkets(file.getInputStream());
      repository.saveAll(markets);
    } catch (IOException e) {
      throw new RuntimeException("fail to store excel data: " + e.getMessage());
    }
  }

  // Third
  public List<Markets> getAllMarkets() {
    return repository.findAll();
  }

}
