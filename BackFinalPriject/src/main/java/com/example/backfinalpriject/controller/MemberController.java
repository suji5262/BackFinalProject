package com.example.backfinalpriject.controller;

import com.example.backfinalpriject.dto.request.MemberLoginRequest;
import com.example.backfinalpriject.dto.request.MemberSignupRequest;

import com.example.backfinalpriject.service.MemberService;
import com.example.backfinalpriject.service.impl.MemberServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody MemberLoginRequest request) {
        memberService.login(request);
        return ResponseEntity.ok().body("success");
    }

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody MemberSignupRequest request) {
        memberService.signup(request);
        return ResponseEntity.ok().body("success");
    }

    @PostMapping("/admin/signup")
    public ResponseEntity<String> adminSignup(@RequestBody MemberSignupRequest request) {
        memberService.adminSignup(request);
        return ResponseEntity.ok().body("success");
    }

    @PostMapping("/admin/login")
    public ResponseEntity<String> adminLogin(@RequestBody MemberLoginRequest request) {
        memberService.adminLogin(request);
        return ResponseEntity.ok().body("success");
    }
}
