package com.example.backfinalpriject.admin.strategy.controller;

import com.example.backfinalpriject.admin.strategy.dto.request.StrategyRequest;
import com.example.backfinalpriject.admin.strategy.service.StrategyService;
import com.example.backfinalpriject.admin.strategy.service.impl.StrategyServiceImpl;
import com.example.backfinalpriject.distinction.entity.Subject;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
public class StrategyController {

    private final StrategyService strategyService;


    @PostMapping("/admin/strategy")
    public String strategyBoard(@RequestParam("file") MultipartFile file, StrategyRequest strategyRequest){
        return strategyService.strategyBoard(file,strategyRequest);
    }



}
