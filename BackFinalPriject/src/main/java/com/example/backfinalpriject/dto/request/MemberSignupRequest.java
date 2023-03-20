package com.example.backfinalpriject.dto.request;

import lombok.Data;

@Data
public class MemberSignupRequest {

    private String email;
    private String password;
    private String name;
    private int role; // 0은 회원, 1은 admin
}
