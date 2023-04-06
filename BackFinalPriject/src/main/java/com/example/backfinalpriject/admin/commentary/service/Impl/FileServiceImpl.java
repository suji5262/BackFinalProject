package com.example.backfinalpriject.admin.commentary.service.Impl;

import com.example.backfinalpriject.admin.commentary.entity.Commentary;
import com.example.backfinalpriject.admin.commentary.entity.CommentaryFile;
import com.example.backfinalpriject.admin.commentary.repository.CommentaryFileRepository;
import com.example.backfinalpriject.admin.commentary.service.FileService;
import com.example.backfinalpriject.exception.ErrorCode;
import com.example.backfinalpriject.exception.GlobalException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {

    @Value("${file.dir}")
    private String fileDir;

    private final CommentaryFileRepository commentaryFileRepository;

    @Override
    public void init() {
        try {
            Files.createDirectories(Paths.get(fileDir));
        } catch (IOException e) {
            throw new RuntimeException("Could not create upload folder!");
        }
    }

    @Override
    public Long store(MultipartFile file, Commentary commentary) {
        try {
            if (file.isEmpty()) {
                throw new Exception("ERROR : File is empty.");
            }
            Path root = Paths.get(fileDir);
            if (!Files.exists(root)) {
                init();
            }

            String origName = file.getOriginalFilename();
            // 파일 이름으로 쓸 uuid 생성
            String uuid = UUID.randomUUID().toString();

            String extension = origName.substring(origName.lastIndexOf("."));

            String savedName = root + "/" + uuid + extension;

            // 파일을 불러올 때 사용할 파일 경로
            String filePath = root + "/" + uuid;

            try (InputStream inputStream = file.getInputStream()) {
                Files.copy(inputStream, root.resolve(savedName),
                        StandardCopyOption.REPLACE_EXISTING);

                CommentaryFile commentaryFile = CommentaryFile.of(commentary, origName, savedName, filePath);
                Long commentaryId = commentaryFileRepository.save(commentaryFile).getId();
                return commentaryId;
            }
        } catch (Exception e) {
            throw new RuntimeException("Could not store the file. Error: " + e.getMessage());
        }
    }

    @Override
    public Stream<Path> loadAll() {
        try {
            Path root = Paths.get(fileDir);
            return Files.walk(root, 1)
                    .filter(path -> !path.equals(root));
        }
        catch (IOException e) {
            throw new RuntimeException("Failed to read stored files", e);
        }
    }

    @Override
    public Path load(String filename) {
        return Paths.get(fileDir).resolve(filename);
    }

    @Override
    public Resource loadAsResource(String filename) {
        try {
            Path file = load(filename);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            }
            else {
                throw new RuntimeException("Could not read file: " + filename);
            }
        }
        catch (MalformedURLException e) {
            throw new RuntimeException("Could not read file: " + filename, e);
        }
    }

    public String deleteFile(String filename) {
        Path file = Paths.get(filename);
        FileSystemUtils.deleteRecursively(file.toFile());
        return "success";
    }

    @Override
    public CommentaryFile getFile(Long id) {
        return commentaryFileRepository.findById(id).orElseThrow(()-> new GlobalException(ErrorCode.FILE_NOT_FOUND, "해당 파일없음"));
    }

}
