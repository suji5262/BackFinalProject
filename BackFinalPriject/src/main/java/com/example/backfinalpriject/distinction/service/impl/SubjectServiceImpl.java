package com.example.backfinalpriject.distinction.service.impl;

import com.example.backfinalpriject.distinction.dto.SubjectDTO;
import com.example.backfinalpriject.distinction.repository.SubjectRepository;
import com.example.backfinalpriject.distinction.service.SubjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SubjectServiceImpl implements SubjectService {

    private final SubjectRepository subjectRepository;


    @Override
    public List<SubjectDTO> subject(String divisionName) {

        return subjectRepository.findByDivision_DivisionName(divisionName)
                .stream()
                .map(subject -> new SubjectDTO(subject))
                .collect(Collectors.toList());

    }
}
