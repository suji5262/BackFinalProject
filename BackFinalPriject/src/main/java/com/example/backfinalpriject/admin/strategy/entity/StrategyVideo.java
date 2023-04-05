package com.example.backfinalpriject.admin.strategy.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name="strategy_video")
@Builder
public class StrategyVideo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "video_id")
    private Long id;

    @ManyToOne(targetEntity = Strategy.class,fetch = FetchType.LAZY)
    @JoinColumn(name="strategy_id")
    private Strategy strategy;

    @Column(name="video_link")
    private String videoLink;

    public void updateVideo(String videoLink){
        this.videoLink = videoLink;
    }

    public void  addStrategy(Strategy strategy){
        this.strategy = strategy;
        strategy.getStrategyVideos().add(this);
       // strategy.getStrategyVideos().add(this);
    }
}
