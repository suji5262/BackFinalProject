package com.example.backfinalpriject.controller;

import com.example.backfinalpriject.dto.request.MemberLoginRequest;
import com.example.backfinalpriject.dto.request.MemberSignupRequest;
import com.example.backfinalpriject.dto.response.MemberLoginResponse;

import com.example.backfinalpriject.service.MemberService;
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
    public ResponseEntity<MemberLoginResponse> login(@RequestBody MemberLoginRequest request) {
        MemberLoginResponse response = memberService.login(request);
        return ResponseEntity.ok().body(response);
    }

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody MemberSignupRequest request) {
        String message = memberService.signup(request);
        return ResponseEntity.ok().body(message);
    }

    @PostMapping("/admin/signup")
    public ResponseEntity<String> adminSignup(@RequestBody MemberSignupRequest request) {
        String message = memberService.adminSignup(request);
        return ResponseEntity.ok().body(message);
    }

    @PostMapping("/admin/login")
    public ResponseEntity<MemberLoginResponse> adminLogin(@RequestBody MemberLoginRequest request) {
        MemberLoginResponse response = memberService.adminLogin(request);
        return ResponseEntity.ok().body(response);
    }
}
