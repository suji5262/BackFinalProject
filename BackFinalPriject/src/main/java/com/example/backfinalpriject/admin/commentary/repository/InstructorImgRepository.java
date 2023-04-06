package com.example.backfinalpriject.admin.commentary.repository;

import com.example.backfinalpriject.admin.commentary.entity.Commentary;
import com.example.backfinalpriject.admin.commentary.entity.InstructorImg;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface InstructorImgRepository extends JpaRepository<InstructorImg, Long> {

    Optional<InstructorImg> findByCommentary (Commentary commentary);
}