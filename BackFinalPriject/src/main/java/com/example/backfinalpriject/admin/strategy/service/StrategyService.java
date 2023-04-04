package com.example.backfinalpriject.admin.strategy.service;

import com.example.backfinalpriject.admin.strategy.dto.request.StrategyRequest;
import com.example.backfinalpriject.admin.strategy.dto.request.StrategyVideoRequest;
import com.example.backfinalpriject.admin.strategy.dto.response.StrategyDetailPageResponse;
import com.example.backfinalpriject.admin.strategy.dto.response.StrategyPageResponse;
import com.example.backfinalpriject.admin.strategy.dto.response.StrategySearchResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface StrategyService {

    String strategyBoard(MultipartFile file,MultipartFile video, StrategyRequest strategyRequest, StrategyVideoRequest videoRequest);

    List<StrategyPageResponse> getStrategyPageList(); // 전체조회

    StrategyDetailPageResponse selectDetailStrategy(Long id); // 상세조회

    List<StrategySearchResponse> selectSubjectName(String subject);

    List<StrategySearchResponse> selectInstructorName(String instructorName);
}
