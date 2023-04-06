package com.example.backfinalpriject.admin.commentary.repository;

import com.example.backfinalpriject.admin.commentary.entity.CommentaryFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CommentaryFileRepository extends JpaRepository<CommentaryFile, Long> {

    @Query(value = "select * from commentary_file where commentary_id = :id", nativeQuery = true)
    Optional<CommentaryFile> findByIdNumber(@Param("id") Long id);
}