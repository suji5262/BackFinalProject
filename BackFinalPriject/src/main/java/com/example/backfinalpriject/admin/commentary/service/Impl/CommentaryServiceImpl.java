package com.example.backfinalpriject.admin.commentary.service.Impl;

import com.example.backfinalpriject.admin.commentary.dto.request.CommentaryRequest;
import com.example.backfinalpriject.admin.commentary.dto.request.VideoUrlRequest;
import com.example.backfinalpriject.admin.commentary.dto.response.CommentaryResponse;
import com.example.backfinalpriject.admin.commentary.dto.response.Response;
import com.example.backfinalpriject.admin.commentary.dto.response.SearchResponse;
import com.example.backfinalpriject.admin.commentary.dto.response.VideoUrlResponse;
import com.example.backfinalpriject.admin.commentary.entity.Commentary;
import com.example.backfinalpriject.admin.commentary.entity.CommentaryFile;
import com.example.backfinalpriject.admin.commentary.entity.CommentaryVideo;
import com.example.backfinalpriject.admin.commentary.entity.InstructorImg;
import com.example.backfinalpriject.admin.commentary.repository.CommentaryFileRepository;
import com.example.backfinalpriject.admin.commentary.repository.CommentaryRepository;
import com.example.backfinalpriject.admin.commentary.repository.CommentaryVideoRepository;
import com.example.backfinalpriject.admin.commentary.repository.InstructorImgRepository;
import com.example.backfinalpriject.admin.commentary.service.CommentaryService;
import com.example.backfinalpriject.admin.commentary.service.FileService;
import com.example.backfinalpriject.distinction.entity.Division;
import com.example.backfinalpriject.distinction.entity.Subject;
import com.example.backfinalpriject.distinction.repository.DivisionRepository;
import com.example.backfinalpriject.distinction.repository.SubjectRepository;
import com.example.backfinalpriject.exception.ErrorCode;
import com.example.backfinalpriject.exception.GlobalException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityManager;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentaryServiceImpl implements CommentaryService {

    private final EntityManager em;
    private final CommentaryRepository commentaryRepository;
    private final CommentaryVideoRepository commentaryVideoRepository;
    private final CommentaryFileRepository commentaryFileRepository;
    private final DivisionRepository divisionRepository;
    private final SubjectRepository subjectRepository;
    private final InstructorImgRepository instructorImgRepository;
    private final FileService fileService;

    /**
     * 기출해설 등록
     */
    @Transactional
    @Override
    public String create(CommentaryRequest request, MultipartFile instructorImg, MultipartFile file, List<VideoUrlRequest> videoUrlRequest) throws Exception {
        Division division = divisionRepository.findByDivisionNameContaining(request.getDivision()).orElseThrow(
                ()->new GlobalException(ErrorCode.DATABASE_ERROR, "해당 과목이 없습니다."));
        Subject subject = subjectRepository.findByDivisionAndSubjectNameContaining(division, request.getSubject())
                .orElseThrow(()->new GlobalException(ErrorCode.DATABASE_ERROR, "해당 과목이 없습니다."));

        Commentary commentary = Commentary.of(request, subject);
        if (videoUrlRequest!= null){
            uploadCommentaryVideos(commentary, videoUrlRequest);
        }
        if (instructorImg != null && file != null){ // 이미지, 파일 두개 다 존재
            InstructorImg newImage = saveImgInDir(instructorImg, commentary);
            instructorImgRepository.save(newImage);
            fileService.store(file, commentary);
        } else if (instructorImg == null && file != null){ //파일만 존재
            fileService.store(file, commentary);
        } else if (instructorImg != null && file == null){ // 이미지만 존재
            InstructorImg newImage = saveImgInDir(instructorImg, commentary);
            instructorImgRepository.save(newImage);
        }
        commentaryRepository.save(commentary);
        return "SUCCESS";
    }

    /**
     * 기출해설 수정
     */
    @Transactional
    @Override
    public CommentaryResponse modify(Long id, CommentaryRequest request,
                                     MultipartFile instructorImg,
                                     MultipartFile file,
                                     List<VideoUrlRequest> videoUrlRequest) throws Exception {

        Commentary commentary = commentaryRepository.findById(id).orElseThrow(
                ()-> new GlobalException(ErrorCode.FILE_NOT_FOUND, "해당 파일을 찾을수 없습니다"));
        Division division = divisionRepository.findByDivisionNameContaining(request.getDivision()).orElseThrow(
                ()->new GlobalException(ErrorCode.DATABASE_ERROR, "해당 과목이 없습니다."));
        Subject subject = subjectRepository.findByDivisionAndSubjectNameContaining(division, request.getSubject())
                .orElseThrow(()->new GlobalException(ErrorCode.DATABASE_ERROR, "해당 과목이 없습니다."));

        List<VideoUrlResponse>  newVideoList = null;
        CommentaryFile originalFile = null;
        try {
            // commentary 업데이트
            commentary.update(request, subject);
            em.flush();
            // 비디오 리스트 업데이트
            List<CommentaryVideo> commentaryVideoList = commentaryVideoRepository.findByCommentary(commentary);

            for (int i = 0; i < videoUrlRequest.size(); i++) {
                if (!videoUrlRequest.get(i).getVideoUrl().equals(commentaryVideoList.get(i).getVideoLink())){
                    commentaryVideoList.get(i).updateVideo(videoUrlRequest.get(i));
                }
            }
                newVideoList = commentaryVideoList.stream().map(n -> new VideoUrlResponse(n)).collect(Collectors.toList());

                //교수 이미지 업데이트
                InstructorImg originalImage = instructorImgRepository.findByCommentary(commentary).orElse(null);
                if (originalImage == null && instructorImg != null) { // 새로운 이미지 추가
                    InstructorImg newImg = saveImgInDir(instructorImg, commentary);
                    instructorImgRepository.save(newImg);
                } else if (instructorImg == null && originalImage != null){ // 기존 이미지 삭제
                    instructorImgRepository.deleteById(originalImage.getId());
                    fileService.deleteFile(originalImage.getStoredFileName()); //기존 이미지 삭제
                } else if (instructorImg != null && !instructorImg.getOriginalFilename().equals(originalImage.getOriginalFileName())) {
                    InstructorImg newImage = saveImgInDir(instructorImg, commentary); //새로운 이미지 저장
                    fileService.deleteFile(originalImage.getStoredFileName()); //기존 이미지 삭제
                    originalImage.updateImg(newImage.getOriginalFileName(), newImage.getStoredFileName(), newImage.getFilePath());
                }

                //파일 업데이트
                originalFile = commentaryFileRepository.findByCommentary(commentary).orElse(null);
                if (file != null && originalFile == null){
                    originalFile = fileService.store(file, commentary);
                } else if (file ==null && originalFile != null){
                    commentaryFileRepository.deleteById(originalFile.getId());
                    originalFile = null;
                } else if (file != null && !file.getOriginalFilename().equals(originalFile.getOriginalFileName())) {
                    originalFile = fileService.modify(file, originalFile);
                    fileService.deleteFile(originalFile.getOriginalFileName()); // 기존 파일 삭제
                }
                return CommentaryResponse.of(commentary, newVideoList, originalFile);

            } catch(Exception e){
                e.printStackTrace();
                return null;
            }
    }

    /**
     * 기출해설 삭제
     */
    @Transactional
    @Override
    public void deleteCommentary(Long id) {
        Commentary commentary = commentaryRepository.findById(id).orElseThrow(
                ()-> new GlobalException(ErrorCode.FILE_NOT_FOUND, "해당 게시글이 존재하지 않습니다"));
        commentaryRepository.delete(commentary);
    }

    /**
     * 기출해설 리스트 조회
     */
    @Transactional
    @Override
    public List<CommentaryResponse> getCommentaryList() {
        List<Commentary> commentaryList = commentaryRepository.findAll();
        List<CommentaryResponse> commentaryResponseList = new ArrayList<>();
        for (Commentary commentary : commentaryList){
            CommentaryResponse commentaryResponse = this.getCommentaryDetail(commentary.getId());
            commentaryResponseList.add(commentaryResponse);
        }
        return commentaryResponseList;
    }

    /**
     * 기출해설 비디오 url 업로드
     */
    @Transactional
    @Override
    public void uploadCommentaryVideos(Commentary commentary, List<VideoUrlRequest> videos) {
        for (VideoUrlRequest request : videos) {
            commentaryVideoRepository.save(CommentaryVideo.of(commentary, request));
        }
    }

    /**
     * 기출해설 상세페이지 조회
     */
    @Transactional
    @Override
    public CommentaryResponse getCommentaryDetail(Long id){

        try {
            Commentary commentary = commentaryRepository.findById(id).orElseThrow(
                    ()-> new GlobalException(ErrorCode.FILE_NOT_FOUND, "해당 게시글이 존재하지 않습니다."));
            List<VideoUrlResponse> commentaryVideoList = commentaryVideoRepository.findByCommentary(commentary)
                    .stream().map(n-> new VideoUrlResponse(n)).collect(Collectors.toList());
            CommentaryFile commentaryFile = commentaryFileRepository.findByCommentary(commentary)
                    .orElseThrow(null);

            return CommentaryResponse.of(commentary, commentaryVideoList, commentaryFile);

        } catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 이미지 저장
     */
    @Transactional
    @Override
    public InstructorImg saveImgInDir(MultipartFile imageFile, Commentary commentary) throws Exception {
        String imagePath = null;
        String absolutePath = new File("").getAbsolutePath() + "\\";
        String path = "images/profile";
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }

        if (!imageFile.isEmpty()) {
            String contentType = imageFile.getContentType();
            String originalFileExtension;
            if (ObjectUtils.isEmpty(contentType)) {
                throw new Exception("이미지 파일은 jpg, png 만 가능합니다.");
            } else {
                if (contentType.contains("image/jpeg")) {
                    originalFileExtension = ".jpg";
                } else if (contentType.contains("image/png")) {
                    originalFileExtension = ".png";
                } else {
                    throw new Exception("이미지 파일은 jpg, png 만 가능합니다.");
                }
            }
            String origName = imageFile.getOriginalFilename();

            // 파일 이름으로 쓸 uuid 생성
            String uuid = UUID.randomUUID().toString();

            // 파일을 불러올 때 사용할 파일 경로
            String savedName = path + "/" + uuid;

            imagePath = path + "/" + uuid + originalFileExtension;

            file = new File(absolutePath + imagePath);
            imageFile.transferTo(file);
            return InstructorImg.of(commentary, origName, savedName, imagePath);
        }
        else {
            throw new Exception("이미지 파일이 비어있습니다.");
        }
    }

    /**
     * 이미지 출력
     */
    @Transactional
    @Override
    public Response getResponse(Long id, Commentary commentary){

        Response response = new Response();

        try {
            if (commentary == null){
                commentary = commentaryRepository.findById(id).orElseThrow(null);
            }

            response.setResponse("success");
            response.setData(getImage(commentary));
        }
        catch (Exception e) {
            e.printStackTrace();
            response.setResponse("failed");
            response.setData(null);
        }
        return response;
    }

    /**
     * 이미지 불러오기
     */
    @Override
    public byte[] getImage(Commentary commentary) throws Exception {
        InstructorImg instructorImg = instructorImgRepository.findByCommentary(commentary).orElseThrow(null);
        String imagePath = instructorImg.getFilePath();
        FileInputStream inputStream = null;
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        String absolutePath = new File("").getAbsolutePath() + "\\";
        try {
            inputStream = new FileInputStream(absolutePath + imagePath);
        }
        catch (FileNotFoundException e) {
            throw new Exception("해당 파일을 찾을 수 없습니다.");
        }

        int readCount = 0;
        byte[] buffer = new byte[1024];
        byte[] fileArray = null;

        try {
            while((readCount = inputStream.read(buffer)) != -1){
                outputStream.write(buffer, 0, readCount);
            }
            fileArray = outputStream.toByteArray();
            inputStream.close();
            outputStream.close();

        }
        catch (IOException e) {
            throw new Exception("파일을 변환하는데 문제가 발생했습니다.");
        }

        return fileArray;
    }

    /**
     * 첨부파일 불러오기
     */
    @Override
    public CommentaryFile getFile(Long id){
        return commentaryFileRepository.findById(id).orElseThrow(null);
    }

    /**
     * 첨부파일 다운로드수 증가
     */
    @Override
    public void upDownloadCnt(CommentaryFile commentaryFile){
        commentaryFile.download();
        commentaryFileRepository.save(commentaryFile);
    }

    /**
     * 교수이름 검색
     */
    @Transactional(readOnly = true)
    @Override
    public List<SearchResponse> searchInstructorName(String instructorName) {
        return commentaryRepository.findCommentaryByInstructorNameContaining(instructorName)
                .stream().map(SearchResponse::new)
                .collect(Collectors.toList());
    }

    /**
     * 과목 검색
     */
    @Transactional(readOnly = true)
    @Override
    public List<SearchResponse> searchSubjectName(String subjectName) {
        return commentaryRepository.findCommentaryBySubjectNameContaining(subjectName)
                .stream().map(SearchResponse::new)
                .collect(Collectors.toList());
    }

    /**
     * 연도 검색
     */
    @Transactional(readOnly = true)
    @Override
    public List<SearchResponse> searchCreatedDate(Integer createdDate) {
        System.out.println("createdDate = " + createdDate);
        return commentaryRepository.findCommentaryByCreatedDateContaining(createdDate)
                .stream().map(SearchResponse::new)
                .collect(Collectors.toList());
    }

}
