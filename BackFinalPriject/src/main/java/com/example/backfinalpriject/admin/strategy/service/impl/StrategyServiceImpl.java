package com.example.backfinalpriject.admin.strategy.service.impl;

import com.example.backfinalpriject.admin.strategy.dto.request.StrategyRequest;
import com.example.backfinalpriject.admin.strategy.entity.Strategy;
import com.example.backfinalpriject.admin.strategy.repository.StrategyRepository;
import com.example.backfinalpriject.admin.strategy.service.StrategyService;
import com.example.backfinalpriject.distinction.repository.Subject;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional
public class StrategyServiceImpl implements StrategyService {

    private final StrategyRepository strategyRepository;


    @Override
    public void strategyBoard(StrategyRequest strategyRequest, Subject subject) {
        Strategy strategy = strategyRequest.toEntity(subject);

        strategyRepository.save(strategy);
    }
}
