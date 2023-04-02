package com.example.backfinalpriject.admin.strategy.dto.response;

import com.example.backfinalpriject.admin.strategy.entity.StrategyVideo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class StrategyVideoDTO {
    Long id;
    String link;

    public StrategyVideoDTO(StrategyVideo strategyVideo){
        this.id = strategyVideo.getId();
        this.link = strategyVideo.getVideoLink();
    }

}
