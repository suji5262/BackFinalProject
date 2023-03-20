package com.example.backfinalpriject.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "commentary_video")
public class CommentaryVideo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "video_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "commentary_id")
    private Commentary commentary;

    @Column(name = "overallVideo")
    private String overallVideo;

    @Column(name = "video_link")
    private String videoLink;
}
