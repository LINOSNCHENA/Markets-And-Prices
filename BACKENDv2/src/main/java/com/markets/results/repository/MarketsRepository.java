package com.markets.results.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.markets.results.model.Markets;

public interface MarketsRepository extends JpaRepository<Markets, Long> {
}
