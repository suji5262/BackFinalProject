package com.example.backfinalpriject.distinction.repository;

import com.example.backfinalpriject.distinction.entity.Division;
import com.example.backfinalpriject.distinction.entity.Subject;
import org.hibernate.boot.jaxb.hbm.spi.JaxbHbmParentType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SubjectRepository extends JpaRepository<Subject, Long> {

    List<Subject> findByDivision_DivisionName(String divisionName);

    Optional<Subject> findByDivisionAndSubjectNameContaining(Division division, String subjectName);

    Optional<Subject> findBySubjectName(String subjectName);
}
