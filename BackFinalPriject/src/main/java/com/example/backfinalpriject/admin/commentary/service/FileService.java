package com.example.backfinalpriject.admin.commentary.service;

import com.example.backfinalpriject.admin.commentary.entity.Commentary;
import com.example.backfinalpriject.admin.commentary.entity.CommentaryFile;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.stream.Stream;

public interface FileService {

    void init();

    CommentaryFile store(MultipartFile file, Commentary commentary);

    Path load(String filename);

    Resource loadAsResource(String filename);

    public String deleteFile(String filename);

    CommentaryFile getFile(Long id);

    CommentaryFile modify(MultipartFile file, CommentaryFile commentaryFile);

}
