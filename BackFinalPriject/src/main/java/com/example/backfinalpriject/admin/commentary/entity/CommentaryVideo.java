package com.example.backfinalpriject.admin.commentary.entity;

import com.example.backfinalpriject.admin.commentary.dto.request.VideoUrlRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "commentary_id")
    private Commentary commentary;

    @Setter
    @Column(name = "video_name")
    private String videoName;

    @Setter
    @Column(name = "video_link")
    private String videoLink;

    public static CommentaryVideo of(Commentary commentary, VideoUrlRequest request){
        CommentaryVideo commentaryVideo = new CommentaryVideo();
        commentaryVideo.setCommentary(commentary);
        commentaryVideo.setVideoName(request.getVideoName());
        commentaryVideo.setVideoLink(request.getVideoUrl());
        return commentaryVideo;
    }

    public void updateVideo(VideoUrlRequest request){
        this.videoName = request.getVideoName();
        this.videoLink = request.getVideoUrl();
    }
}
