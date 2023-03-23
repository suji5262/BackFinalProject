package com.example.backfinalpriject.admin.strategy.dto.response;

import com.example.backfinalpriject.admin.strategy.entity.Strategy;
import lombok.*;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class StrategyResponse {


    private String subjectName;

    private String lectureName;

    private String instructorName;

    private String image;

    private String content;

    private LocalDateTime createDate;


    public StrategyResponse(Strategy strategy){
        this.subjectName = strategy.getSubject().getSubjectName();
        this.lectureName = strategy.getLectureName();
        this.instructorName = strategy.getInstructorName();
        this.image = strategy.getImage();
        this.content = strategy.getContent();
        this.createDate = strategy.getCreatedDate();

    }


}
