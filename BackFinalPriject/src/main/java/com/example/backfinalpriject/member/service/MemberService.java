package com.example.backfinalpriject.member.service;

import com.example.backfinalpriject.member.dto.request.MemberLoginRequest;
import com.example.backfinalpriject.member.dto.request.MemberSignupRequest;

public interface MemberService {
    void login(MemberLoginRequest request);

    void signup(MemberSignupRequest request);

    void adminSignup(MemberSignupRequest request);

    void adminLogin(MemberLoginRequest request);
}
