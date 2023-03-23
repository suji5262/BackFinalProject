package com.example.backfinalpriject.admin.strategy.service;

import com.example.backfinalpriject.admin.strategy.dto.request.StrategyVideoRequest;
import org.springframework.web.multipart.MultipartFile;

public interface StrategyVideoService {

    String insertStrategyVideo(MultipartFile file, StrategyVideoRequest videoRequest);
}
