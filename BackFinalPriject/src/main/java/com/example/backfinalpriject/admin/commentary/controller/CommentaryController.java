package com.example.backfinalpriject.admin.commentary.controller;

import com.example.backfinalpriject.admin.commentary.dto.request.CommentaryRequest;
import com.example.backfinalpriject.admin.commentary.dto.request.VideoUrlRequest;
import com.example.backfinalpriject.admin.commentary.dto.response.CommentaryResponse;
import com.example.backfinalpriject.admin.commentary.dto.response.Response;
import com.example.backfinalpriject.admin.commentary.dto.response.SearchResponse;
import com.example.backfinalpriject.admin.commentary.entity.CommentaryFile;
import com.example.backfinalpriject.admin.commentary.service.CommentaryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
import java.nio.charset.StandardCharsets;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@Api(tags = {"기출해설 페이지"}, description = "기출해설 등록/조회/삭제/수정을 담당합니다.")
public class CommentaryController {

    @Autowired
    ResourceLoader resourceLoader;

    private final CommentaryService commentaryService;

    @ApiOperation(value = "기출해설 게시글 등록", notes = "기출해설 게시글을 등록합니다.")
    @PostMapping("/admin/commentary")
    public ResponseEntity<String> create(
            @RequestPart(value = "commentaryRequest") CommentaryRequest request,
            @RequestPart(value = "instructorImg", required = false) MultipartFile instructorImg,
            @RequestPart(value = "file", required = false) MultipartFile file,
            @RequestPart(value = "videoUrl", required = false) List<VideoUrlRequest> videoUrlRequests) throws Exception {

        log.info("commentary: {}", request);
        log.info("instructorImg: {}", instructorImg);
        log.info("files: {}", file);
        log.info("videoUrl: {}", videoUrlRequests);
        commentaryService.create(request, instructorImg, file, videoUrlRequests);
        return new ResponseEntity<>("success", HttpStatus.OK);
    }

    @ApiOperation(value = "기출해설 게시글 수정", notes = "기출해설 게시글을 수정합니다.")
    @PatchMapping("/admin/commentary/{id}")
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
        CommentaryResponse response =commentaryService.modify(id, request, instructorImg, file, videoUrlRequests);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ApiOperation(value = "기출해설 게시글 삭제", notes = "기출해설 게시글을 삭제합니다.")
    @DeleteMapping ("/admin/commentary/{id}")
    public ResponseEntity<String> deleteCommentary(@PathVariable Long id){
        commentaryService.deleteCommentary(id);
        return new ResponseEntity<String>("SUCCESS", HttpStatus.OK);
    }

    @ApiOperation(value = "기출해설 리스트 조회", notes = "기출해설 페이지에서 리스트를 보여줍니다.")
    @GetMapping("/commentary")
    public ResponseEntity<List<CommentaryResponse>> getCommentaryList(){
        List<CommentaryResponse> commentaryList = commentaryService.getCommentaryList();
        return ResponseEntity.ok().body(commentaryList);
    }

    @ApiOperation(value = "기출해설 상세 게시글 조회", notes = "기출해설 게시글의 상세게시글을 조회합니다.")
    @GetMapping("/commentary/{id}")
    public ResponseEntity<CommentaryResponse> getCommentaryDetail(@PathVariable Long id){
        CommentaryResponse commentaryDetailResponse = commentaryService.getCommentaryDetail(id);
        return new ResponseEntity<>(commentaryDetailResponse, HttpStatus.OK);
    }

    @ApiOperation(value = "기출해설 교수 이미지 조회", notes = "기출해설의 특정 교수이미지를 보여줍니다.")
    @GetMapping("/image/{commentaryId}")
    public ResponseEntity<Response> getImage(@PathVariable("commentaryId") Long commentaryId){
        Response response = commentaryService.getResponse(commentaryId, null);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ApiOperation(value = "기출해설 첨부파일 다운로드", notes = "기출해설 게시글의 첨부파일을 다운로드합니다.")
    @GetMapping("/download/{fileId}")
    public ResponseEntity<Resource> resourceFileDownload(@PathVariable("fileId") Long fileId) {
        try {
            CommentaryFile commentaryFile = commentaryService.getFile(fileId);
            if (commentaryFile == null) return null;
            commentaryService.upDownloadCnt(commentaryFile);
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

    @ApiOperation(value = "기출해설 페이지내 교수명 검색", notes = "기출해설 페이지에서 교수명을 검색합니다.")
    @GetMapping("/commentary/search/instructorName") //  교수명 검색
    public ResponseEntity<List<SearchResponse>> searchInstructorName
            (@RequestParam String instructorName){
        List<SearchResponse> searchInstructorNameList = commentaryService.searchInstructorName(instructorName);
        return ResponseEntity.ok().body(searchInstructorNameList);
    }

    @ApiOperation(value = "기출해설 페이지내 과목명 검색", notes = "기출해설 페이지에서 과목명을 검색합니다.")
    @GetMapping("/commentary/search/subjectName") //  과목명 검색
    public ResponseEntity<List<SearchResponse>> searchSubjectName
            (@RequestParam String subjectName){
        List<SearchResponse> searchSubjectNameList = commentaryService.searchSubjectName(subjectName);
        return ResponseEntity.ok().body(searchSubjectNameList);
    }

    @ApiOperation(value = "기출해설 페이지내 연도 검색", notes = "기출해설 페이지에서 연도명을 검색합니다.")
    @GetMapping("/commentary/search/createdDate") //  연도 검색
    public ResponseEntity<List<SearchResponse>> searchCreatedDate
            (@RequestParam Integer createdDate){
        List<SearchResponse> searchCreatedDateList = commentaryService.searchCreatedDate(createdDate);
        return ResponseEntity.ok().body(searchCreatedDateList);
    }
}
