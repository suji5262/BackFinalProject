package com.example.backfinalpriject.admin.strategy.service.impl;

import com.example.backfinalpriject.admin.strategy.dto.request.StrategyVideoRequest;
import com.example.backfinalpriject.admin.strategy.entity.Strategy;
import com.example.backfinalpriject.admin.strategy.repository.StrategyRepository;
import com.example.backfinalpriject.admin.strategy.repository.StrategyVideoRepository;
import com.example.backfinalpriject.admin.strategy.service.StrategyVideoService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class StrategyVideoServiceImpl implements StrategyVideoService {

    private final StrategyVideoRepository strategyVideoRepository;

    private final StrategyRepository strategyRepository;

    @Value("C:/Users/zan04/file/")
    private String uploadDir;

    @Override
    public String insertStrategyVideo(MultipartFile file, StrategyVideoRequest videoRequest) {

        try{

            String video = uploadVideo(file);
            videoRequest.setVideoLink(video);

            Strategy strategy = strategyRepository.findById(videoRequest.getStrategyId()).get();

            strategyVideoRepository.save(videoRequest.toEntity(strategy));

        }catch (Exception e) {
            e.printStackTrace();
            return "failed";
        }

        return "success";
    }

    public String uploadVideo(MultipartFile file) throws IOException {
        Path uploadPath = Paths.get(uploadDir);
        if(!Files.isDirectory(uploadPath)){
            Files.createDirectories(uploadPath);
        }

        UUID uuid= UUID.randomUUID(); // 중복  방지를 위한 랜덤 값
        String originFileName = file.getOriginalFilename(); // 파일 원래 이름
        String fullPath = uploadDir + uuid.toString() + "_" + originFileName;
        file.transferTo(new File(fullPath));

        return fullPath;
    }
}
