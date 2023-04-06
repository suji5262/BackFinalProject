package com.example.backfinalpriject.admin.commentary.repository;

import com.example.backfinalpriject.admin.commentary.entity.Commentary;
import com.example.backfinalpriject.admin.commentary.entity.CommentaryVideo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentaryVideoRepository extends JpaRepository<CommentaryVideo, Long> {

    List<CommentaryVideo> findByCommentary(Commentary commentary);
}