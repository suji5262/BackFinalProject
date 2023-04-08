package com.example.backfinalpriject.admin.commentary.dto.response;

import com.example.backfinalpriject.admin.commentary.entity.Commentary;
import com.example.backfinalpriject.admin.commentary.entity.CommentaryFile;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class CommentaryResponse {

    private String divisionName;
    private String subjectName;
    private Long commentaryId;
    private String instructorName;
    private String commentaryTitle;
    private LocalDateTime createdDate;
    private String fileName;
    private Long fileId;
    private Long fileDownloadCount;
    private List<VideoUrlResponse> videoUrl;

    public static CommentaryResponse of(Commentary commentary, List<VideoUrlResponse> videoUrl, CommentaryFile commentaryFile) {
        CommentaryResponse commentaryResponse = new CommentaryResponse();
        commentaryResponse.setCommentaryId(commentary.getId());
        commentaryResponse.setDivisionName(commentary.getSubject().getDivision().getDivisionName());
        commentaryResponse.setSubjectName(commentary.getSubject().getSubjectName());
        commentaryResponse.setInstructorName(commentary.getInstructorName());
        commentaryResponse.setCreatedDate(commentary.getCreatedDate());
        commentaryResponse.setCommentaryTitle(commentary.getTitle());
        if (commentaryFile == null){
            commentaryResponse.setFileName(null);
            commentaryResponse.setFileId(null);
            commentaryResponse.setFileDownloadCount(null);
        } else {
            commentaryResponse.setFileName(commentaryFile.getOriginalFileName());
            commentaryResponse.setFileId(commentaryFile.getId());
            commentaryResponse.setFileDownloadCount(commentaryFile.getDownloadCount());
        }
        commentaryResponse.setVideoUrl(videoUrl);
        return commentaryResponse;
    }
}
