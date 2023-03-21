package com.example.backfinalpriject.admin.strategy.service;

import com.example.backfinalpriject.admin.strategy.dto.request.StrategyRequest;
import com.example.backfinalpriject.distinction.entity.Subject;

public interface StrategyService {

    void strategyBoard(StrategyRequest strategyRequest, Subject subject);

}
