package com.example.backfinalpriject.distinction.service.impl;

import com.example.backfinalpriject.distinction.dto.DivisionDTO;
import com.example.backfinalpriject.distinction.repository.DivisionRepository;
import com.example.backfinalpriject.distinction.service.DivisionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DivisionServiceImpl implements DivisionService {

    private final DivisionRepository divisionRepository;

    @Override
    public List<DivisionDTO> division() {
        return divisionRepository.findAll()
                .stream()
                .map(division -> new DivisionDTO(division))
                .collect(Collectors.toList());
    }
}
