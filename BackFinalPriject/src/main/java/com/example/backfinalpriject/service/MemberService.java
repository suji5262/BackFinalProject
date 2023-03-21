package com.example.backfinalpriject.service;

import com.example.backfinalpriject.controller.dto.request.MemberLoginRequest;
import com.example.backfinalpriject.controller.dto.request.MemberSignupRequest;

public interface MemberService {
    void login(MemberLoginRequest request);

    void signup(MemberSignupRequest request);

    void adminSignup(MemberSignupRequest request);

    void adminLogin(MemberLoginRequest request);
}
