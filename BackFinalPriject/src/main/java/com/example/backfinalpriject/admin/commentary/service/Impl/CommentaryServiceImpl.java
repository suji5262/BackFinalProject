package com.example.backfinalpriject.admin.commentary.service.Impl;

import com.example.backfinalpriject.admin.commentary.dto.request.CommentaryRequest;
import com.example.backfinalpriject.admin.commentary.dto.request.VideoUrlRequest;
import com.example.backfinalpriject.admin.commentary.dto.response.CommentaryResponse;
import com.example.backfinalpriject.admin.commentary.dto.response.Response;
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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Transactional
@Service
@RequiredArgsConstructor
public class CommentaryServiceImpl implements CommentaryService {

    @Value("${site-file.upload-dir}")
    private String fileDir;

    private final CommentaryRepository commentaryRepository;
    private final CommentaryVideoRepository commentaryVideoRepository;
    private final CommentaryFileRepository commentaryFileRepository;
    private final DivisionRepository divisionRepository;
    private final SubjectRepository subjectRepository;
    private final InstructorImgRepository instructorImgRepository;
    private final FileService fileService;

    @Override
    public Long create(CommentaryRequest request, MultipartFile instructorImg, MultipartFile file, List<VideoUrlRequest> videoUrlRequest) throws Exception {
        Division division = divisionRepository.findByDivisionNameContaining(request.getDivision()).orElseThrow(
                ()->new GlobalException(ErrorCode.DATABASE_ERROR, "division 오류"));
        Subject subject = subjectRepository.findByDivisionAndSubjectNameContaining(division, request.getSubject())
                .orElseThrow(()->new GlobalException(ErrorCode.DATABASE_ERROR, "과목 오류"));
        Commentary commentary = commentaryRepository.save(Commentary.of(request, subject));

        if (videoUrlRequest!= null){
            uploadCommentaryVideos(commentary, videoUrlRequest);
        }
        if (instructorImg != null && file != null){ // 이미지, 파일 두개 다 존재
            saveImg(instructorImg, commentary);
            fileService.store(file, commentary);
        } else if (instructorImg == null && file != null){ //파일만 존재
            fileService.store(file, commentary);
        } else { // 이미지만 존재
            saveImg(instructorImg, commentary);
        }
        return commentary.getId();
    }

    @Override
    public CommentaryResponse modify(CommentaryRequest request, MultipartFile instructorImg, MultipartFile file, List<VideoUrlRequest> videoUrlRequest) throws Exception {
        return null;
    }

    @Override
    public void deleteCommentary(Long id) {
        Commentary commentary = commentaryRepository.findById(id).orElseThrow(()-> new GlobalException(ErrorCode.FILE_NOT_FOUND, "해당 게시글이 존재하지 않습니다"));
        commentaryRepository.delete(commentary);
    }

    // 비디오 업로드
    @Override
    public void uploadCommentaryVideos(Commentary commentary, List<VideoUrlRequest> videos) {
        for (VideoUrlRequest request : videos) {
            commentaryVideoRepository.save(CommentaryVideo.of(commentary, request));
        }
    }

    @Override
    public CommentaryResponse getCommentaryDetail(Long id){
        Commentary commentary = commentaryRepository.findById(id).orElseThrow(
                ()-> new GlobalException(ErrorCode.FILE_NOT_FOUND, "해당 게시글이 존재하지 않습니다."));
        List<VideoUrlResponse> commentaryVideoList = commentaryVideoRepository.findByCommentary(commentary)
                .stream().map(n-> new VideoUrlResponse(n)).collect(Collectors.toList());
        CommentaryFile commentaryFile = commentaryFileRepository.findByIdNumber(commentary.getId())
                .orElseThrow(null);
        Response response = new Response();

        try {
            response.setResponse("success");
            response.setData(getImage(commentary));
        }
        catch (Exception e) {
            e.printStackTrace();
            response.setResponse("failed");
            response.setData(null);
        }

        return CommentaryResponse.of(commentary, commentaryVideoList, response, commentaryFile);
    }

    //이미지 저장
    @Override
    public Long saveImg(MultipartFile imageFile, Commentary commentary) throws Exception {
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
            InstructorImg instructorImg = InstructorImg.of(commentary, origName, savedName, imagePath);
            return instructorImgRepository.save(instructorImg).getId();
        }
        else {
            throw new Exception("이미지 파일이 비어있습니다.");
        }
    }

    //이미지 조회
    @Override
    public byte[] getImage(Commentary commentary) throws Exception {
        InstructorImg instructorImg = instructorImgRepository.findByCommentary(commentary).orElseThrow(null);
        String imagePath = instructorImg.getFilePath();
        FileInputStream inputStream = null;
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        String absolutePath = new File("").getAbsolutePath() + "\\";
        System.out.println(absolutePath + imagePath);
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

    @Override
    public CommentaryFile getFile(Long id){ // 파일 찾기
        CommentaryFile file = commentaryFileRepository.findByIdNumber(id).orElseThrow(null);
        return file;
    }

}
