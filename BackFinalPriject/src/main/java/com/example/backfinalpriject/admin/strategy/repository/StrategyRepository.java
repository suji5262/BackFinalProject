package com.example.backfinalpriject.admin.strategy.repository;

import com.example.backfinalpriject.admin.strategy.entity.Strategy;
import com.example.backfinalpriject.admin.strategy.entity.StrategyVideo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StrategyRepository extends JpaRepository<Strategy, Long> {


    Optional<Strategy> findById(Long strategyId);
}
