package com.example.backfinalpriject.admin.strategy.controller;


import com.example.backfinalpriject.admin.strategy.dto.request.StrategyVideoRequest;
import com.example.backfinalpriject.admin.strategy.service.StrategyVideoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
public class StrategyVideoController {

    private final StrategyVideoService strategyVideoService;

    @PostMapping("/admin/strategyVideo")
    public String insertStrategyVideo(@RequestParam("file") MultipartFile file, StrategyVideoRequest videoRequest){
        return strategyVideoService.insertStrategyVideo(file,videoRequest);
    }
}
