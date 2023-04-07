package com.example.backfinalpriject.admin.commentary.dto.response;

import com.example.backfinalpriject.admin.commentary.entity.Commentary;
import com.example.backfinalpriject.admin.commentary.entity.CommentaryFile;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class CommentaryResponse {

    private String division;
    private String subject;
    private String instructorName;
    private Response instructorImg;
    private String title;
    private LocalDateTime createdDate;
    private String fileName;
    private Long fileId;
    private List<VideoUrlResponse> videoUrl;

    public static CommentaryResponse of(Commentary commentary, List<VideoUrlResponse> videoUrl, Response response, CommentaryFile commentaryFile) {
        CommentaryResponse commentaryResponse = new CommentaryResponse();
        commentaryResponse.setDivision(commentary.getSubject().getDivision().getDivisionName());
        commentaryResponse.setSubject(commentary.getSubject().getSubjectName());
        commentaryResponse.setInstructorImg(response);
        commentaryResponse.setInstructorName(commentary.getInstructorName());
        commentaryResponse.setCreatedDate(commentary.getCreatedDate());
        commentaryResponse.setTitle(commentary.getTitle());
        commentaryResponse.setFileName(commentaryFile.getOriginalFileName());
        commentaryResponse.setFileId(commentaryFile.getId());
        commentaryResponse.setVideoUrl(videoUrl);
        return commentaryResponse;
    }
}

