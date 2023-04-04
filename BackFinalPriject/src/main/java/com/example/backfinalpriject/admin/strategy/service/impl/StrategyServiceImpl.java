package com.example.backfinalpriject.admin.strategy.service.impl;

import com.example.backfinalpriject.admin.strategy.dto.request.StrategyRequest;
import com.example.backfinalpriject.admin.strategy.dto.request.StrategyVideoRequest;
import com.example.backfinalpriject.admin.strategy.dto.response.StrategyDetailPageResponse;
import com.example.backfinalpriject.admin.strategy.dto.response.StrategyPageResponse;
import com.example.backfinalpriject.admin.strategy.dto.response.StrategySearchResponse;
import com.example.backfinalpriject.admin.strategy.entity.Strategy;
import com.example.backfinalpriject.admin.strategy.entity.StrategyVideo;
import com.example.backfinalpriject.admin.strategy.repository.StrategyRepository;
import com.example.backfinalpriject.admin.strategy.repository.StrategyVideoRepository;
import com.example.backfinalpriject.admin.strategy.service.StrategyService;
import com.example.backfinalpriject.distinction.entity.Subject;
import com.example.backfinalpriject.distinction.repository.SubjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Transactional
public class StrategyServiceImpl implements StrategyService {

    private final StrategyRepository strategyRepository;
    private final StrategyVideoRepository strategyVideoRepository;
    private final SubjectRepository subjectRepository;


    @Value("C:/Users/zan04/file/")
    private String uploadDir;

    @Override
    public String strategyBoard(MultipartFile file,MultipartFile video, StrategyRequest strategyRequest, StrategyVideoRequest videoRequest) {

        try{
            /*
            [ mentor's sudo code ]

            // strategy 1:N video
            Strategy strategy = new Strategy();
            // strategy.setFields();

            StrategyVideo strategyVideo = new StrategyVideo();
            // strategyVideo.setFields();

            // addStrategyVideo();
            strategyVideo.setStrategy(strategy);
            strategy.setStrategyVideo(strategyVideo);

            strategyRepository.save(strategy);
            */
            String image = uploadPic(file);
            strategyRequest.setImage(image);

<<<<<<< HEAD
            Subject subject = subjectRepository.findBySubjectName(strategyRequest.getSubjectName()).orElse(null);
            System.out.println("아이디="+subject.getId());
            System.out.println("과목="+subject.getSubjectName());
=======
            String video1= uploadPic(video);
            videoRequest.setVideoLink(video1);
            
            Subject subject = subjectRepository.findBySubjectName(strategyRequest.getSubjectName()).get();
>>>>>>> feature/insert

            Strategy strategy = strategyRequest.toEntity(subject);


            StrategyVideo strategyVideo = videoRequest.toEntity(strategy);
           // strategyVideo.addStrategy(strategy);
            // strategy.addStrategyVideo(strategyVideo);

            strategyRepository.save(strategy);
            strategyVideoRepository.save(strategyVideo);


        }catch (Exception e){
            e.printStackTrace();
            return "failed";
        }

        return "success";
    }



    /*
    파일 업로드 관련 메서드
     */
    public String uploadPic(MultipartFile file) throws IOException {
        Path uploadPath = Paths.get(uploadDir);
        if (!Files.isDirectory(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        UUID uuid = UUID.randomUUID(); // 중복 방지를 위한 랜덤 값
        String originFileName = file.getOriginalFilename(); //파일 원래 이름
        String fullPath = uploadDir + uuid.toString() + "_" + originFileName;
        file.transferTo(new File(fullPath));

        return fullPath;
    }


    /**
     * 전체조회
     */
    @Transactional(readOnly = true)
    @Override
    public List<StrategyPageResponse> getStrategyPageList() {
        return strategyRepository.findFetchSubjectAll().stream()
                .map(StrategyPageResponse::new)
                .collect(Collectors.toList());
    }


    /**
     * 상세조회
     */
    @Transactional(readOnly = true)
    @Override
    public StrategyDetailPageResponse selectDetailStrategy(Long id) {
        Optional<Strategy> strategyItem = strategyRepository.findById(id);
        if(strategyItem.isPresent()){
            StrategyDetailPageResponse response = new StrategyDetailPageResponse(strategyItem.get());
            return response;
        }else{
            return null;
        }
    }


    /**
     * 과목 검색
     */

    @Override
    public List<StrategySearchResponse> selectSubjectName(String subject) {
        return strategyRepository.findBySubject_SubjectName(subject).stream()
                .map(subjectName -> new StrategySearchResponse(subjectName))
                .collect(Collectors.toList());
    }


    /**
     * 교수 검색
     */
    @Override
    public List<StrategySearchResponse> selectInstructorName(String instructorName) {
        return strategyRepository.findByInstructorName(instructorName).stream()
                .map(instructor -> new StrategySearchResponse(instructor))
                .collect(Collectors.toList());
    }


}
