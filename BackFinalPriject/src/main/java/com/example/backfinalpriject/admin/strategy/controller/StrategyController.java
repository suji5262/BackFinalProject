package com.example.backfinalpriject.admin.strategy.controller;

import com.example.backfinalpriject.admin.strategy.dto.request.StrategyRequest;
import com.example.backfinalpriject.admin.strategy.dto.request.StrategyVideoRequest;
import com.example.backfinalpriject.admin.strategy.dto.response.StrategyDetailPageResponse;
import com.example.backfinalpriject.admin.strategy.dto.response.StrategyPageResponse;
import com.example.backfinalpriject.admin.strategy.dto.response.StrategySearchResponse;
import com.example.backfinalpriject.admin.strategy.service.StrategyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class StrategyController {

    private final StrategyService strategyService;
    private final HttpSession session;



    @PostMapping("/admin/strategy")
    public String strategyBoard(@RequestParam(value="file",required = false) MultipartFile file,@RequestParam(value="video",required = false) MultipartFile video, StrategyRequest strategyRequest, StrategyVideoRequest videoRequest){
        String email = (String) session.getAttribute("email");

        return strategyService.strategyBoard(file,video,strategyRequest,videoRequest,email);
    }

    @PatchMapping("/admin/strategy/{strategyId}")
    public String updateStrategy(Long strategyId,@RequestParam("file") MultipartFile file, @RequestParam("video") MultipartFile video, StrategyRequest strategyRequest, StrategyVideoRequest videoRequest){
        String email = (String) session.getAttribute("email");
        return strategyService.updateStrategy(email,strategyId,file,video,strategyRequest,videoRequest);
    }

    @DeleteMapping("/admin/strategy/{strategyId}")
    public String deleteStrategy(  Long strategyId){

        String email = (String) session.getAttribute("email");
        return strategyService.deleteStrategy(email,strategyId);
    }

    @GetMapping("/strategy") // 페이지 전체조회
    ResponseEntity<List<StrategyPageResponse>> getStrategyPageList() {
        List<StrategyPageResponse> StrategyPageList = strategyService.getStrategyPageList();
        return ResponseEntity.ok().body(StrategyPageList);
    }

    @GetMapping("/strategy/detail") // 페이지 상세조회
    public ResponseEntity<StrategyDetailPageResponse> selectDetailStrategy(@RequestParam Long id){
        StrategyDetailPageResponse response = strategyService.selectDetailStrategy(id);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/strategy/search/subjectName")
    public ResponseEntity<List<StrategySearchResponse>> selectSubjectName(@RequestParam(name="subject") String subject){

        List<StrategySearchResponse> StrategySubjectList = strategyService.selectSubjectName(subject);
        return ResponseEntity.ok().body(StrategySubjectList);

    }

    @GetMapping("/strategy/instructorName")
    public ResponseEntity<List<StrategySearchResponse>> selectInstructorName(@RequestParam(name="instructorName") String instructorName){
        List<StrategySearchResponse> StrategyInstructorList = strategyService.selectInstructorName(instructorName);
        return ResponseEntity.ok().body(StrategyInstructorList);
    }

}
