package com.example.backfinalpriject.admin.strategy.dto;


import com.example.backfinalpriject.admin.strategy.entity.Strategy;
import com.example.backfinalpriject.admin.strategy.entity.StrategyVideo;
import com.example.backfinalpriject.entity.Subject;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class StrategyDTO {

    private Long strategyId;

    private Subject subject;

    private String lectureName;

    private String instructorName;

    private String image;

    private String content;

    private List<StrategyVideo> videoLink;

    public Strategy toEntity(){
        return Strategy.builder()
                .id(strategyId)
                .lectureName(lectureName)
                .instructorName(instructorName)
                .image(image)
                .content(content)
                .videoLink(videoLink)
                .build();
    }


    public StrategyDTO(Strategy strategy){
        this.strategyId = strategy.getId();
        this.lectureName = strategy.getLectureName();
        this.instructorName = strategy.getInstructorName();
        this.image = strategy.getImage();
        this.content = strategy.getContent();
        this.videoLink = strategy.getVideoLink();
    }
}
