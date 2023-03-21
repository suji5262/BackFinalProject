package com.example.backfinalpriject.distinction.controller;


import com.example.backfinalpriject.distinction.dto.DivisionDTO;
import com.example.backfinalpriject.distinction.service.DivisionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class DivisionController {

    private final DivisionService divisionService;



    @GetMapping("/admin/division")
    public List<DivisionDTO> division(){
        return divisionService.division();
    }
}
