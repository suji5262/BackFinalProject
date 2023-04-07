package com.example.backfinalpriject.admin.commentary.repository;

import com.example.backfinalpriject.admin.commentary.entity.Commentary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommentaryRepository extends JpaRepository<Commentary, Long> {

    @Query("select c from Commentary c join fetch c.instructorImg where c.instructorName like %:name%")
    List<Commentary> findCommentaryByInstructorNameContaining(@Param("name") String instructorName); // 교수명 검색

    @Query("select c from Commentary c join fetch c.instructorImg join fetch c.subject s where s.subjectName like %:name%") //과목명 검색
    List<Commentary> findCommentaryBySubjectNameContaining(@Param("name") String subjectName);

    @Query("select c from Commentary c join fetch c.instructorImg where year(c.createdDate) = :year")
    List<Commentary> findCommentaryByCreatedDateContaining(@Param("year") Integer createdDate); // 연도별 검색
}