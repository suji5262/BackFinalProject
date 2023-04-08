package com.example.backfinalpriject.admin.commentary.repository;

import com.example.backfinalpriject.admin.commentary.entity.Commentary;
import com.example.backfinalpriject.admin.commentary.entity.CommentaryFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface CommentaryFileRepository extends JpaRepository<CommentaryFile, Long>, JpaSpecificationExecutor<CommentaryFile> {

    Optional<CommentaryFile> findById(Long id);

    Optional<CommentaryFile> findByCommentary(Commentary commentary);
}