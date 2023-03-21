package com.example.backfinalpriject.admin.strategy.dto.request;


import com.example.backfinalpriject.admin.strategy.entity.Strategy;
import com.example.backfinalpriject.distinction.repository.Subject;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class StrategyRequest {



    private String subject;

    private String lectureName;

    private String instructorName;

    private String image;

    private String content;



    public Strategy toEntity(Subject subject){
        return Strategy.builder()
                .subject(subject)
                .lectureName(lectureName)
                .instructorName(instructorName)
                .image(image)
                .content(content)
                .build();
    }


}
