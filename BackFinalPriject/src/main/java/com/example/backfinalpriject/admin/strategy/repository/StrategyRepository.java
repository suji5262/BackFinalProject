package com.example.backfinalpriject.admin.strategy.repository;

import com.example.backfinalpriject.admin.strategy.entity.Strategy;
import com.example.backfinalpriject.admin.strategy.entity.StrategyVideo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface StrategyRepository extends JpaRepository<Strategy, Long> {

    Optional<Strategy> findById(Long strategyId);

    @Query("select s from Strategy s join fetch s.subject ss join fetch ss.division")
    List<Strategy> findFetchSubjectAll(); // 전체조회


    List<Strategy> findBySubject_SubjectName(String subject); // 과목검색

    List<Strategy> findByInstructorName(String instructorName);
}

