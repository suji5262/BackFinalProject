package com.example.backfinalpriject.admin.strategy.service.impl;

import com.example.backfinalpriject.admin.strategy.dto.request.StrategyRequest;
import com.example.backfinalpriject.admin.strategy.entity.Strategy;
import com.example.backfinalpriject.admin.strategy.repository.StrategyRepository;
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
import java.util.UUID;

@RequiredArgsConstructor
@Service
@Transactional
public class StrategyServiceImpl implements StrategyService {

    private final StrategyRepository strategyRepository;

    private final SubjectRepository subjectRepository;


    @Value("C:/Users/zan04/file/")
    private String uploadDir;

    @Override
    public String strategyBoard(MultipartFile file,StrategyRequest strategyRequest) {

        try{
            String image = uploadPic(file);
            strategyRequest.setImage(image);

            Subject subject = subjectRepository.findBySubjectName(strategyRequest.getSubjectName()).get();


            strategyRepository.save(strategyRequest.toEntity(subject));

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
}
