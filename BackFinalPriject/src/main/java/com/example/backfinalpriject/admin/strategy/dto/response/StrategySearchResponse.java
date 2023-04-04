package com.example.backfinalpriject.admin.strategy.dto.response;


import com.example.backfinalpriject.admin.strategy.entity.Strategy;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class StrategySearchResponse {

    private Long id;
    private String subjectName;
    private String lectureName;
    private String instructorName;
    private String content;
    private String image;
    private LocalDateTime createdDate;

    public StrategySearchResponse(Strategy strategy) {
        this.id = strategy.getId();
        this.subjectName = strategy.getSubject().getSubjectName();
        this.lectureName = strategy.getLectureName();
        this.instructorName = strategy.getInstructorName();
        this.content = strategy.getContent();
        this.image = strategy.getImage();
        this.createdDate = strategy.getCreatedDate();
    }
}
