package com.example.backfinalpriject.admin.commentary.dto.response;

import com.example.backfinalpriject.admin.commentary.entity.CommentaryVideo;
import lombok.Data;

@Data
public class VideoUrlResponse {

    public String videoName;
    public String videoUrl;

    public VideoUrlResponse(CommentaryVideo video){
        this.videoName = video.getVideoName();
        this.videoUrl = video.getVideoLink();
    }
}
