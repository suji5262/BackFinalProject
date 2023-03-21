package com.example.backfinalpriject.admin.strategy.controller;

import com.example.backfinalpriject.admin.strategy.service.StrategyService;
import com.example.backfinalpriject.admin.strategy.service.impl.StrategyServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class StrategyController {

    private final StrategyService StrategyService;



}
