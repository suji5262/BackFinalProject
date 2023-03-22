package com.example.backfinalpriject.distinction.controller;


import com.example.backfinalpriject.distinction.dto.SubjectDTO;
import com.example.backfinalpriject.distinction.service.SubjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class SubjectController {

    private final SubjectService subjectService;

    @GetMapping("/admin/subject")
    public List<SubjectDTO> subject( @RequestParam String divisionName){
        return subjectService.subject(divisionName);
    }
}
