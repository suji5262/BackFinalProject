package com.example.backfinalpriject.distinction.service;

import com.example.backfinalpriject.distinction.dto.SubjectDTO;

import java.util.List;

public interface SubjectService {

    List<SubjectDTO> subject(String divisionName);
}
