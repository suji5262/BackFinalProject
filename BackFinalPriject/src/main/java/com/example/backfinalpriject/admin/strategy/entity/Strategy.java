package com.example.backfinalpriject.admin.strategy.entity;

import com.example.backfinalpriject.entity.AuditingFields;
import com.example.backfinalpriject.distinction.entity.Subject;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "strategy")
public class Strategy extends AuditingFields {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "strategy_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subject_id")
    private Subject subject;

    @Column(name = "lecture_name")
    private String lectureName;

    @Column(name = "instructor_name")
    private String instructorName;


    @Column(name="image")
    private String image;

    @Column(name = "content", columnDefinition = "TEXT")
    private String content;

    @OneToMany(mappedBy = "strategy", cascade = CascadeType.ALL)
    private List<StrategyVideo> strategyVideos = new ArrayList<>();


    public void addStrategyVideo(StrategyVideo strategyVideo){
        if(strategyVideo.getStrategy() != null){
            strategyVideo.getStrategy().getStrategyVideos().remove(strategyVideo);
        }


        strategyVideo.setStrategy(this);
        this.strategyVideos.add(strategyVideo);


//        strategyVideos.add(strategyVideos);
//        if(strategyVideo.getStrategy()!= this){
//            strategyVideo.setStrategy(this);
//        }
////        System.out.println("video3="+ strategyVideos.add(strategyVideo));
//        strategyVideo.setStrategy(strategyVideo.getStrategy());
    }

}
