package com.example.backfinalpriject.admin.commentary.service;

import com.example.backfinalpriject.admin.commentary.dto.request.CommentaryRequest;
import com.example.backfinalpriject.admin.commentary.dto.request.VideoUrlRequest;
import com.example.backfinalpriject.admin.commentary.dto.response.CommentaryResponse;
import com.example.backfinalpriject.admin.commentary.dto.response.Response;
import com.example.backfinalpriject.admin.commentary.dto.response.SearchResponse;
import com.example.backfinalpriject.admin.commentary.entity.Commentary;
import com.example.backfinalpriject.admin.commentary.entity.CommentaryFile;
import com.example.backfinalpriject.admin.commentary.entity.InstructorImg;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface CommentaryService {
    String create(CommentaryRequest request, MultipartFile instructorImg, MultipartFile file, List<VideoUrlRequest> videoUrlRequest) throws Exception;

    CommentaryResponse modify(Long id, CommentaryRequest request, MultipartFile instructorImg, MultipartFile file, List<VideoUrlRequest> videoUrlRequest) throws Exception;

    void deleteCommentary(Long id);

    void upDownloadCnt(CommentaryFile commentaryFile);

    List<CommentaryResponse> getCommentaryList();

    void uploadCommentaryVideos(Commentary commentary, List<VideoUrlRequest> videos);

    CommentaryResponse getCommentaryDetail(Long id);

    InstructorImg saveImgInDir(MultipartFile imageFile, Commentary commentary) throws Exception;

    byte[] getImage(Commentary commentary) throws Exception;

    Response getResponse(Long commentaryId, Commentary commentary);

    CommentaryFile getFile(Long fileId);

    List<SearchResponse> searchInstructorName(String instructorName); // 교수명 검색
    List<SearchResponse> searchSubjectName(String subjectName); // 과목 검색
    List<SearchResponse> searchCreatedDate(Integer createdDate); // 연도 검색
}
