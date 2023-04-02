package com.example.backfinalpriject.admin.strategy.dto.response;

import com.example.backfinalpriject.admin.strategy.entity.Strategy;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class StrategyDetailPageResponse {

    private Long strategyId;
    private String subjectName;
    private String lectureName;
    private String instructorName;
    private List<StrategyVideoDTO> videos;
    private String image;
    private LocalDateTime createdDate;

    public StrategyDetailPageResponse(Strategy strategy) {
        this.strategyId = strategy.getId();
        this.subjectName = strategy.getSubject().getSubjectName();
        this.lectureName = strategy.getLectureName();
        this.instructorName = strategy.getInstructorName();
        this.videos = strategy.getStrategyVideos().stream().map(StrategyVideoDTO::new).collect(Collectors.toList());
        this.image = strategy.getImage();
        this.createdDate = strategy.getCreatedDate();

    }
}


