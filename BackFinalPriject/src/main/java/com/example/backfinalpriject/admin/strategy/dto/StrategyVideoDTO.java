package com.example.backfinalpriject.admin.strategy.dto;

import com.example.backfinalpriject.admin.strategy.entity.Strategy;
import com.example.backfinalpriject.admin.strategy.entity.StrategyVideo;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class StrategyVideoDTO {

    private Long strategyVideoId;

    private Strategy strategy;

    private String videoLink;

    public StrategyVideo toEntity(){
        return StrategyVideo.builder()
                .id(strategyVideoId)
                .strategy(strategy)
                .videoLink(videoLink)
                .build();

    }

    public StrategyVideoDTO(StrategyVideo strategyVideo){
        this.strategyVideoId = strategyVideo.getId();
        this.strategy = strategyVideo.getStrategy();
        this.videoLink = strategyVideo.getVideoLink();
    }
}
