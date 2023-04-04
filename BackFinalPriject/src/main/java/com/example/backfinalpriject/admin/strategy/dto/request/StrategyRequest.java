package com.example.backfinalpriject.admin.strategy.dto.request;


import com.example.backfinalpriject.admin.strategy.entity.Strategy;
import com.example.backfinalpriject.admin.strategy.entity.StrategyVideo;
import com.example.backfinalpriject.distinction.entity.Subject;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class StrategyRequest {



    private String subjectName;

    private String lectureName;

    private String instructorName;

    private String image;

    private String content;
    private List<StrategyVideo> strategyVideos;



    public Strategy toEntity(Subject subject){
            return Strategy.builder()
                .subject(subject)
                .lectureName(lectureName)
                .instructorName(instructorName)
                .image(image)
                .content(content)
                    .strategyVideos(strategyVideos)
                .build();
    }


}
