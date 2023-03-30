package com.example.backfinalpriject.admin.strategy.dto.response;

import com.example.backfinalpriject.admin.strategy.entity.Strategy;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class StrategyPageResponse {

    private Long id;
    private Long subjectId;
    private String lectureName;
    private String instructorName;
    private String content;
    private String image;
    private LocalDateTime createdDate;

    public StrategyPageResponse(Strategy strategy) {
        this.id = strategy.getId();
        this.subjectId = strategy.getSubject().getId();
        this.lectureName = strategy.getLectureName();
        this.instructorName = strategy.getInstructorName();
        this.content = strategy.getContent();
        this.image = strategy.getImage();
        this.createdDate = strategy.getCreatedDate();
    }
}


