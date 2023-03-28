package com.example.backfinalpriject.distinction.repository;

import com.example.backfinalpriject.distinction.entity.Division;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DivisionRepository extends JpaRepository<Division, Long> {



    Optional<Division> findByDivisionName(String divisionName);
}
