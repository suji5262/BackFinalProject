package com.example.backfinalpriject.admin.commentary.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentaryRequest {

    private String division;
    private String subject;
    private String instructorName;
    private String title;
    private MultipartFile imageFile;          // 첨부 이미지
    private List<MultipartFile> attachedFile;    // 첨부 파일
    private List<VideoUrlRequest> videoUrlList;
}
