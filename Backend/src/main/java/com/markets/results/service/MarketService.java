package com.markets.results.service;

import java.util.List;
import com.markets.results.model.Markets;
import com.markets.results.repository.MarketsRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MarketService{
  @Autowired
  MarketsRepository repository;

  public List<Markets> getAllMarkets() {
    return repository.findAll();
  }

}
