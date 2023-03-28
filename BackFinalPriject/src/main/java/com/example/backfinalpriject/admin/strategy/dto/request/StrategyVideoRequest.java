package com.example.backfinalpriject.admin.strategy.dto.request;

import com.example.backfinalpriject.admin.strategy.entity.Strategy;
import com.example.backfinalpriject.admin.strategy.entity.StrategyVideo;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class StrategyVideoRequest {



    private Long strategyId;

    private String videoLink;

    public StrategyVideo toEntity(Strategy strategy){
        return StrategyVideo.builder()
                .strategy(strategy)
                .videoLink(videoLink)
                .build();

    }


}
