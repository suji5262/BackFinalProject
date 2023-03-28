package com.example.backfinalpriject.admin.strategy.service;

import com.example.backfinalpriject.admin.strategy.dto.request.StrategyRequest;
import com.example.backfinalpriject.distinction.entity.Subject;
import org.springframework.web.multipart.MultipartFile;

public interface StrategyService {

    String strategyBoard(MultipartFile file,StrategyRequest strategyRequest);

}
