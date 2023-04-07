package com.example.backfinalpriject.admin.commentary.controller;

<<<<<<< HEAD
import com.example.backfinalpriject.admin.commentary.dto.request.CommentaryRequest;
import com.example.backfinalpriject.admin.commentary.dto.response.CommentaryResponse;
import com.example.backfinalpriject.admin.commentary.dto.request.VideoUrlRequest;
import com.example.backfinalpriject.admin.commentary.dto.response.SearchResponse;
=======

import com.example.backfinalpriject.admin.commentary.dto.request.CommentaryRequest;
import com.example.backfinalpriject.admin.commentary.dto.request.VideoUrlRequest;
import com.example.backfinalpriject.admin.commentary.dto.response.CommentaryResponse;
>>>>>>> feature/Authentication
import com.example.backfinalpriject.admin.commentary.entity.CommentaryFile;
import com.example.backfinalpriject.admin.commentary.service.CommentaryService;
import com.example.backfinalpriject.admin.commentary.service.FileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class CommentaryController {

    @Value("${site-file.upload-dir}")
    private String fileDir;

    @Autowired
    ResourceLoader resourceLoader;

    private final CommentaryService commentaryService;
    private final FileService fileService;

    @PostMapping("/admin/commentary")
    public ResponseEntity<Long> create(
            @RequestPart(value = "commentaryRequest") CommentaryRequest request,
            @RequestPart(value = "instructorImg", required = false) MultipartFile instructorImg,
            @RequestPart(value = "file", required = false) MultipartFile file,
            @RequestPart(value = "videoUrl", required = false) List<VideoUrlRequest> videoUrlRequests) throws Exception {

        log.info("commentary: {}", request);
        log.info("instructorImg: {}", instructorImg);
        log.info("files: {}", file);
        log.info("videoUrl: {}", videoUrlRequests);
        Long id =commentaryService.create(request, instructorImg, file, videoUrlRequests);
        return new ResponseEntity<Long>(id, HttpStatus.OK);
    }

    @PatchMapping("/commentary/{id}")
    public ResponseEntity<CommentaryResponse> modifyCommentary(
            @PathVariable("id") Long id,
            @RequestPart(value = "commentaryRequest") CommentaryRequest request,
            @RequestPart(value = "instructorImg", required = false) MultipartFile instructorImg,
            @RequestPart(value = "file", required = false) MultipartFile file,
            @RequestPart(value = "videoUrl", required = false) List<VideoUrlRequest> videoUrlRequests) throws Exception {

        log.info("commentary: {}", request);
        log.info("instructorImg: {}", instructorImg);
        log.info("files: {}", file);
        log.info("videoUrl: {}", videoUrlRequests);
        CommentaryResponse response =commentaryService.modify(request, instructorImg, file, videoUrlRequests);
        return new ResponseEntity<CommentaryResponse>(response, HttpStatus.OK);
    }

    @DeleteMapping ("/commentary/{id}")
    public ResponseEntity<String> deleteCommentary(@PathVariable Long id){
        commentaryService.deleteCommentary(id);
        return new ResponseEntity<String>("SUCCESS", HttpStatus.OK);
    }

    @GetMapping("/commentary/{id}")
    public ResponseEntity<CommentaryResponse> getCommentaryDetail(@PathVariable Long id){
        CommentaryResponse commentaryResponse = commentaryService.getCommentaryDetail(id);
        return new ResponseEntity<>(commentaryResponse, HttpStatus.OK);
    }

    @PostMapping(value="uploadFile")
    public ResponseEntity<String> uploadFile(MultipartFile file) throws IllegalStateException, IOException{

        if( !file.isEmpty() ) {
            log.debug("file org name = {}", file.getOriginalFilename());
            log.debug("file content type = {}", file.getContentType());
            file.transferTo(new File(file.getOriginalFilename()));
        }

        return new ResponseEntity<>("", HttpStatus.OK);
    }

    @GetMapping("/download/{id}")
    public ResponseEntity<Resource> resourceFileDownload(@PathVariable Long id) {
        try {
            CommentaryFile commentaryFile = commentaryService.getFile(id);
            commentaryFile.download();
            if (commentaryFile == null) return null;
            String storedFileName = commentaryFile.getStoredFileName();
            String originalFileName = commentaryFile.getOriginalFileName();
            String encodedUploadFileName = UriUtils.encode(originalFileName, StandardCharsets.UTF_8);
            String contentDisposition = "attachment; filename =\"" + encodedUploadFileName + "\"";
            Resource resource = resourceLoader.getResource("file:" + storedFileName);
            File file = resource.getFile();

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION,contentDisposition)	//다운 받아지는 파일 명 설정
                    .header(HttpHeaders.CONTENT_LENGTH, String.valueOf(file.length()))	//파일 사이즈 설정
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_OCTET_STREAM.toString())	//바이너리 데이터로 받아오기 설정
                    .body(resource);	//파일 넘기기
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest()
                    .body(null);
        } catch (Exception e ) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    @GetMapping("/commentary/search/instructorName") //  교수명 검색
    public ResponseEntity<List<SearchResponse>> searchInstructorName
            (@RequestParam String instructorName){
        List<SearchResponse> searchInstructorNameList = commentaryService.searchInstructorName(instructorName);
        return ResponseEntity.ok().body(searchInstructorNameList);
    }

    @GetMapping("/commentary/search/subjectName") //  과목명 검색
    public ResponseEntity<List<SearchResponse>> searchSubjectName
            (@RequestParam String subjectName){
        List<SearchResponse> searchSubjectNameList = commentaryService.searchSubjectName(subjectName);
        return ResponseEntity.ok().body(searchSubjectNameList);
    }

    @GetMapping("/commentary/search/createdDate") //  연도 검색
    public ResponseEntity<List<SearchResponse>> searchCreatedDate
            (@RequestParam Integer createdDate){
        List<SearchResponse> searchCreatedDateList = commentaryService.searchCreatedDate(createdDate);
        return ResponseEntity.ok().body(searchCreatedDateList);
    }
}



